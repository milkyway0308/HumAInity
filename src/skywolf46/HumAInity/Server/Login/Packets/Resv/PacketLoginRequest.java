package skywolf46.HumAInity.Server.Login.Packets.Resv;

//import com.google.common.hash.Hashing;
import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PacketLoginRequest extends AbstractPacket {
    private String id;
    private String pw;

    public PacketLoginRequest(String id, String pw) {
        this.id = id;
//        this.pw = Hashing.sha512().hashString(pw, StandardCharsets.UTF_8).toString();
        this.pw = pw;
    }

    @Override
    public int getPacketID() {
        return 1;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return new PacketLoginRequest(ois.readUTF(), ois.readUTF());
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {
        bos.writeUTF(id);
        bos.writeUTF(pw);
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }
}
