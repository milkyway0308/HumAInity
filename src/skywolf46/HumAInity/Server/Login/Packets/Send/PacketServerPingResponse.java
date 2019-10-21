package skywolf46.HumAInity.Server.Login.Packets.Send;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketServerPingResponse extends AbstractPacket {
    private long resv = System.currentTimeMillis();
    public PacketServerPingResponse(){
        resv = System.currentTimeMillis();
    }
    @Override
    public int getPacketID() {
        return 9;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return null;
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {
        bos.writeLong(resv);
    }
}
