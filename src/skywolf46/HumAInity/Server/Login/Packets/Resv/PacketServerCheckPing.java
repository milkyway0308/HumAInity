package skywolf46.HumAInity.Server.Login.Packets.Resv;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketServerCheckPing extends AbstractPacket {
    @Override
    public int getPacketID() {
        return 0;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return new PacketServerCheckPing();
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {

    }
}
