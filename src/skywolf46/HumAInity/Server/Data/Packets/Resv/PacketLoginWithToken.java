package skywolf46.HumAInity.Server.Data.Packets.Resv;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketLoginWithToken extends AbstractPacket {
    public static final PacketLoginWithToken INSTANCE = new PacketLoginWithToken("");
    private String token;

    private PacketLoginWithToken(String token) {
        this.token = token;
    }

    @Override
    public int getPacketID() {
        return 100;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return new PacketLoginWithToken(ois.readUTF());
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {

    }

    public String getToken() {
        return token;
    }
}
