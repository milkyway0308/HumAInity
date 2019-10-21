package skywolf46.HumAInity.PacketConnection.Thread;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;
import skywolf46.HumAInity.PacketConnection.PacketConnection;
import skywolf46.HumAInity.PacketConnection.Util.PacketUtility;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class PacketReadThread extends Thread {
    private PacketConnection connection;
    private DataInputStream ois;
    private AtomicBoolean breakThread = new AtomicBoolean(false);

    public PacketReadThread(PacketConnection connection, DataInputStream ois) {
        this.connection = connection;
        this.ois = ois;
    }

    @Override
    public void run() {
        while (!breakThread.get()) {
            try {
                Thread.sleep(1L);
                if (ois.available() <= 0)
                    continue;
                AbstractPacket packet = PacketUtility.readPacket(ois, connection.getEncryption());
//                System.out.println("Packet ID: " + packet.getPacketID());
                connection.invokeListener(packet);
            } catch (EOFException e) {

            } catch (IOException e) {
                e.printStackTrace();
                connection.breakConnection();
                return;
            } catch (BadPaddingException e) {
                e.printStackTrace();
                connection.breakConnection();
                return;
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
                connection.breakConnection();
                return;
            } catch (InterruptedException exx) {
                connection.breakConnection();
            }
        }
    }

    public void breakThread() {
        breakThread.set(true);
        try {
            interrupt();
        } catch (Exception ex) {

        }
    }

    public boolean isBroken() {
        return breakThread.get();
    }
}
