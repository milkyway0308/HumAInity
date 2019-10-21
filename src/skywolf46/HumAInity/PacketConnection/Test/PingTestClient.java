package skywolf46.HumAInity.PacketConnection.Test;

import skywolf46.HumAInity.PacketConnection.Encryption.AbstractEncryption;
import skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util.RSAKeypairGenerator;
import skywolf46.HumAInity.PacketConnection.PacketConnection;
import skywolf46.HumAInity.Server.Login.Packets.Resv.PacketLoginRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PingTestClient {
    private static String TEXT = "What is Lorem Ipsum?\n" +
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
            "\n" +
            "Why do we use it?\n" +
            "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n";
    public static void main(String[] args) {
        try {
            PacketConnection.attachPacketListener(10000,(con,packet) -> {
                TestPingPacket ping = (TestPingPacket) packet;
                System.out.println("Ping packet recieved from server: " + ping.getPing());
            });
            KeyPair kp = RSAKeypairGenerator.generateKeypair(1024);
            Socket socket = new Socket();
            socket.setSoTimeout(3000);
            socket.connect(new InetSocketAddress("15.164.150.175", 9817));
            new TestPingPacket("").register();
            PacketConnection connection = new PacketConnection(socket, AbstractEncryption.getEncryption("RSA"), kp);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (true){
                connection.queue(new TestPingPacket("Test Packet"));
//                connection.queue(new TestPingPacket("Test packet on " + format.format(new Date(System.currentTimeMillis()))));
//                connection.queue(new PacketLoginRequest("","test"));
//                connection._innerAPI_writeEncryptedPacket(new TestPingPacket(TEXT));
//                connection.queue(new TestPingPacket(TEXT));
                Thread.sleep(100000L);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
