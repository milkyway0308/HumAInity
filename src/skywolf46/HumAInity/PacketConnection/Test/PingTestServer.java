package skywolf46.HumAInity.PacketConnection.Test;

import skywolf46.HumAInity.PacketConnection.Encryption.AbstractEncryption;
import skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util.RSAKeypairGenerator;
import skywolf46.HumAInity.PacketConnection.PacketConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;

public class PingTestServer {
    public static void main(String[] args) {
        try {
            PacketConnection.attachPacketListener(10000,(con,packet) -> {
                TestPingPacket ping = (TestPingPacket) packet;
                System.out.println("Ping packet recieved : " + ping.getPing());
            });
            ServerSocket so = new ServerSocket(9817);
            KeyPair kp = RSAKeypairGenerator.generateKeypair(1024);
            Socket next = so.accept();

            new TestPingPacket("").register();
            PacketConnection con = new PacketConnection(next, AbstractEncryption.getEncryption("RSA"), kp);
//            con._innerAPI_writeEncryptedPacket(new TestPingPacket("Hello, Client!"));
            while (true){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
