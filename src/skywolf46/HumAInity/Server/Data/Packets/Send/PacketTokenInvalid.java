package skywolf46.HumAInity.Server.Data.Packets.Send;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;
import skywolf46.HumAInity.PacketConnection.PacketConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketTokenInvalid extends AbstractPacket {
    @Override
    public int getPacketID() {
        return 200;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return null;
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {

    }

    @Override
    public void onPacketSended(PacketConnection c) {
        c.breakConnection();
    }
}
