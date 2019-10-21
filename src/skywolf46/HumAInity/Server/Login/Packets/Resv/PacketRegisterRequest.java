package skywolf46.HumAInity.Server.Login.Packets.Resv;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;
import skywolf46.HumAInity.Server.Login.LoginServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketRegisterRequest extends AbstractPacket {
    public static final PacketRegisterRequest INSTANCE = new PacketRegisterRequest();
    private String id;
    private String pw;

    public PacketRegisterRequest(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    private PacketRegisterRequest() {

    }

    @Override
    public int getPacketID() {
        return 2;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return new PacketRegisterRequest(ois.readUTF(), ois.readUTF());
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {

    }


    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }
}
