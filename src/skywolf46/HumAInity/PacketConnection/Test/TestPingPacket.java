package skywolf46.HumAInity.PacketConnection.Test;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TestPingPacket extends AbstractPacket {
    private String ping;

    public TestPingPacket(String str) {
        this.ping = str;
    }

    @Override
    public int getPacketID() {
        return 10000;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return new TestPingPacket(ois.readUTF());
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {
        bos.writeUTF(ping);
    }

    public String getPing() {
        return ping;
    }
}
