package skywolf46.HumAInity.Server.Login.Packets.Send;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketLoginSuccess extends AbstractPacket {
    private String token;

    public PacketLoginSuccess(String token) {
        this.token = token;
    }

    @Override
    public int getPacketID() {
        return 10;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return null;
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {
        bos.writeUTF(token);
    }
}
