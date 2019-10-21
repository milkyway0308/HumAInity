package skywolf46.HumAInity.Server.Data.Packets.Resv;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketUserChat extends AbstractPacket {
    private String text;
    public static final PacketUserChat INSTANCE = new PacketUserChat("");

    private PacketUserChat(String text) {
        this.text = text;
    }

    @Override
    public int getPacketID() {
        return 101;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return new PacketUserChat(ois.readUTF());
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {

    }

    public String getText() {
        return text;
    }
}
