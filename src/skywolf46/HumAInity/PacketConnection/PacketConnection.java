package skywolf46.HumAInity.PacketConnection;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;
import skywolf46.HumAInity.PacketConnection.Encryption.AbstractEncryption;
import skywolf46.HumAInity.PacketConnection.Thread.PacketReadThread;
import skywolf46.HumAInity.PacketConnection.Thread.PacketWriteThread;

import java.io.*;
import java.net.Socket;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.function.BiConsumer;

public class PacketConnection {
    private static HashMap<Integer, List<BiConsumer<PacketConnection, AbstractPacket>>> packetListener = new HashMap<>();
    private static List<BiConsumer<PacketConnection, AbstractPacket>> EMPTY_LIST = new ArrayList<>();
    private Queue<AbstractPacket> sendQueue = new ArrayDeque<>();
    private Socket socket;
    private DataInputStream ois;
    private DataOutputStream oos;
    private AbstractEncryption encryption;
    private PacketReadThread reader;
    private PacketWriteThread writer;
    private final Object LOCK = new Object();

    public PacketConnection(Socket soc, AbstractEncryption encryption, KeyPair keyPair) {
        try {
            this.socket = soc;
            this.ois = new DataInputStream(new BufferedInputStream(soc.getInputStream()));
            this.oos = new DataOutputStream(new BufferedOutputStream(soc.getOutputStream()));
            this.encryption = encryption.createEncryptInstance(this.ois, this.oos, keyPair);
            startReadThread();
            startWriteThread();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }


    private void startReadThread() {
        if (reader != null)
            return;
        this.reader = new PacketReadThread(this, ois);
        this.reader.start();
    }


    private void startWriteThread() {
        System.out.println("Write thread");
        if (writer != null)
            return;
        this.writer = new PacketWriteThread(this, oos);
        this.writer.start();
    }

    public void queue(AbstractPacket packet) {
        synchronized (LOCK) {
            this.sendQueue.add(packet);
        }
    }

//    void _innerAPI_writeEncryptedPacket(AbstractPacket packet) {
//        if (Thread.currentThread() != writer)
//            throw new IllegalStateException("Encrypted packet direct write is only allowed for packet writer thread");
//        if (reader.isBroken())
//            throw new IllegalStateException("Connection broken");
//        try {
//            System.out.println("Writing packet...");
//            PacketUtility.writePacket(oos, encryption, packet);
//            System.out.println("Packet write complete");
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void attachPacketListener(int id, BiConsumer<PacketConnection, ? extends AbstractPacket> listener) {
        packetListener.computeIfAbsent(id, i -> new ArrayList<>()).add((BiConsumer<PacketConnection, AbstractPacket>) listener);
    }

    public AbstractEncryption getEncryption() {
        return encryption;
    }

    public void invokeListener(AbstractPacket packet) {
        for (BiConsumer<PacketConnection, AbstractPacket> listener : packetListener.getOrDefault(packet.getPacketID(), EMPTY_LIST))
            listener.accept(this, packet);
    }

    public void breakConnection() {
        if (socket == null)
            return;
        System.out.println("Connection closed.");
        if (!reader.isBroken())
            reader.breakThread();
        if(!writer.isBroken())
            writer.breakThread();
        try {
            ois.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket = null;
    }

    public void test() {

    }

    public List<AbstractPacket> getLeftQueue() {
        synchronized (LOCK) {
            List<AbstractPacket> pac = new ArrayList<>(sendQueue);
            sendQueue.clear();
            return pac;
        }
    }

    public boolean isBroken() {
        return socket == null;
    }
}
