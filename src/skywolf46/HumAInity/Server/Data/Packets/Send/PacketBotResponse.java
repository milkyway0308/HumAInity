package skywolf46.HumAInity.Server.Data.Packets.Send;

import skywolf46.HumAInity.PacketConnection.Data.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketBotResponse extends AbstractPacket {
    private String text;
    public static final PacketBotResponse INSTANCE = new PacketBotResponse("");

    public PacketBotResponse(String text) {
        this.text = text;
    }

    @Override
    public int getPacketID() {
        return 201;
    }

    @Override
    public AbstractPacket readPacket(DataInputStream ois) throws IOException {
        return null;
    }

    @Override
    public void writePacket(DataOutputStream bos) throws IOException {
        bos.writeUTF(text);
    }
}
