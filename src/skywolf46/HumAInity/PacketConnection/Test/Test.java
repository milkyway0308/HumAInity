package skywolf46.HumAInity.PacketConnection.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Test {
    public static void main(String[] args) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("localhost",41110),1500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
