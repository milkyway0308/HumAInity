package skywolf46.HumAInity.PacketConnection.Thread;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;
import skywolf46.HumAInity.PacketConnection.PacketConnection;
import skywolf46.HumAInity.PacketConnection.Util.PacketUtility;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PacketWriteThread extends Thread {
    private AtomicBoolean isBroken = new AtomicBoolean(false);
    private DataOutputStream writeStream;
    private PacketConnection connection;

    public PacketWriteThread(PacketConnection connection, DataOutputStream oos) {
        this.connection = connection;
        this.writeStream = oos;
    }

    @Override
    public void run() {
        while (!isBroken.get()) {
            try {
//                System.out.println("test");
                Thread.sleep(1L);
                List<AbstractPacket> lastQueue = connection.getLeftQueue();
//                if (lastQueue.size() != 0)
//                    System.out.println("Writing " + lastQueue.size());
                for (AbstractPacket ap : lastQueue) {
                    writeEncryptedPacket(ap);
                }
                for (AbstractPacket ap : lastQueue)
                    ap.onPacketSended(connection);
            } catch (Exception ex) {
//                ex.printStackTrace();
                connection.breakConnection();
                return;
            }
        }
    }

    public void breakThread() {
//        if(isBroken.get())
//            return;
        isBroken.set(true);
        try {
            interrupt();
        } catch (Exception ex) {

        }
    }

    public boolean isBroken() {
        return isBroken.get();
    }

    private void writeEncryptedPacket(AbstractPacket packet) {
        if (isBroken())
            throw new IllegalStateException("Connection broken");
        try {
//            System.out.println("Writing packet...");
            PacketUtility.writePacket(writeStream, connection.getEncryption(), packet);
//            System.out.println("Packet write complete");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            connection.breakConnection();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            connection.breakConnection();
        } catch (IOException e) {
            e.printStackTrace();
            connection.breakConnection();

        }
    }
}
