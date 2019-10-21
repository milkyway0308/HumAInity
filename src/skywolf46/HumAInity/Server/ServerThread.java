package skywolf46.HumAInity.Server;

import skywolf46.HumAInity.PacketConnection.PacketConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public abstract class ServerThread extends Thread {
    private int port;
    private Consumer<Socket> socketMethod;
    private AtomicBoolean isStarted = new AtomicBoolean(false);

    public ServerThread(int port, Consumer<Socket> socketMethod) {
        this.port = port;
        this.socketMethod = socketMethod;
    }

    @Override
    public void run() {
        System.out.println("Started!");
        System.out.println(isStarted.get());
        if (isStarted.get())
            return;
        isStarted.set(true);
        ServerSocket so = null;
        try {
//            System.out.println("Starting serversocket");
            so = new ServerSocket(port);
//            System.out.println("Started serversocket");
            while (isStarted.get()) {
//                System.out.println("Waiting socket");
                Socket socket = so.accept();
                System.out.println("Socket connection recieved.");
                new Thread(() -> {
//                    System.out.println("Thread.");
                    socketMethod.accept(socket);
                }).start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (so != null) {
                try {
                    so.close();
                } catch (IOException e) {
                }
            }
            isStarted.set(false);
        }
    }
}
