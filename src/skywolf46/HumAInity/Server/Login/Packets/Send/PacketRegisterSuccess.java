package skywolf46.HumAInity.Server.Login.Packets.Send;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketRegisterSuccess extends AbstractPacket {
    public static final PacketRegisterSuccess INSTANCE = new PacketRegisterSuccess("");
    private String name;
    public PacketRegisterSuccess(String userName){
        this.name = userName;
    }
    @Override
    public int getPacketID() {
        return 11;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return null;
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {
        bos.writeUTF(name);
    }
}
