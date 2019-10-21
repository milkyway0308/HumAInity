package skywolf46.HumAInity.PacketConnection.Data;

import skywolf46.HumAInity.PacketConnection.PacketConnection;

import java.io.*;
import java.util.HashMap;

public abstract class AbstractPacket {
    private static HashMap<Integer, AbstractPacket> readPackets = new HashMap<>();

    public abstract int getPacketID();

    public abstract AbstractPacket readPacket(DataInputStream ois) throws IOException;

    public abstract void writePacket(DataOutputStream bos) throws IOException;

    public final void register() {
        readPackets.put(getPacketID(), this);
    }

    public static AbstractPacket getPacket(int id) {
        return readPackets.get(id);
    }

    public void onPacketSended(PacketConnection c) {

    }
}
