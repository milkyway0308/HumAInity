package skywolf46.HumAInity.Server.Login.Packets.Resv;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketExitConnection extends AbstractPacket {
    public static final PacketExitConnection INSTANCE = new PacketExitConnection();

    @Override
    public int getPacketID() {
        return -1;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return null;
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {

    }
}
