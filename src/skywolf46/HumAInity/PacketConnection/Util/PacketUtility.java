package skywolf46.HumAInity.PacketConnection.Util;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;
import skywolf46.HumAInity.PacketConnection.Encryption.AbstractEncryption;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class PacketUtility {
    private static HashMap<Integer, AbstractPacket> packetInstance = new HashMap<>();

    public static AbstractPacket readPacket(DataInputStream ois, AbstractEncryption encryption) throws BadPaddingException, IllegalBlockSizeException, IOException {
        int packetLength = ois.readInt();
        boolean encrypted = ois.readBoolean();
        byte[] read = new byte[packetLength];
//        System.out.println("Aval: " + ois.available());
//        System.out.println("Len: " + read.length);
        int current = 0;
//        while (current < read.length) {
//            if(ois.available() > 0)
//                read[current++] = (byte) ois.read();
//        }
        readBytes(ois,read,0,read.length);
//        System.out.println("Data: " + Arrays.toString(read));
        DataInputStream bois = new DataInputStream(new ByteArrayInputStream(read));
//        System.out.println("Read: " + Arrays.toString(read));
        if (encrypted) {
            System.out.println(Arrays.toString(read));
            read = encryption.readDecryptedContent(bois);
            bois = new DataInputStream(new ByteArrayInputStream(read));
        }
        return AbstractPacket.getPacket(bois.readInt()).readPacket(bois);
    }

    public static void writePacket(DataOutputStream oos, AbstractEncryption encryption, AbstractPacket packet) throws BadPaddingException, IllegalBlockSizeException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        DataOutputStream boos = new DataOutputStream(bos);
        boos.writeInt(packet.getPacketID());
        packet.writePacket(boos);
//        System.out.println("Decrypted Buffer : " + Arrays.toString(bos.toByteArray()));

        ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
        boos = new DataOutputStream(finalOutput);
        encryption.writeEncryptedContent(boos, bos.toByteArray());
        byte[] buffer = finalOutput.toByteArray();
//        System.out.println("Output: " + Arrays.toString(buffer));
//        System.out.println("Buffer: " + Arrays.toString(buffer));
//        System.out.println("Len: " + buffer.length);
        oos.writeInt(buffer.length);
        oos.writeBoolean(true);
        oos.write(buffer);
        oos.flush();
    }

    public static void readBytes(InputStream is, byte[] bytes, int off, int len) throws IOException {
        int readed = 0;

        while (readed < bytes.length) {
            readed += is.read(bytes, off + readed, len - readed);
        }
    }
}
