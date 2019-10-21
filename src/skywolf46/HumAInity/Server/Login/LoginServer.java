package skywolf46.HumAInity.Server.Login;

import org.minidns.util.Base64;
import skywolf46.HumAInity.PacketConnection.Encryption.AbstractEncryption;
import skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util.RSAKeypairGenerator;
import skywolf46.HumAInity.PacketConnection.PacketConnection;
import skywolf46.HumAInity.PacketConnection.Test.TestPingPacket;
import skywolf46.HumAInity.Server.Data.Data.TokenStorage;
import skywolf46.HumAInity.Server.Login.Packets.Resv.PacketExitConnection;
import skywolf46.HumAInity.Server.Login.Packets.Resv.PacketLoginRequest;
import skywolf46.HumAInity.Server.Login.Packets.Resv.PacketRegisterRequest;
import skywolf46.HumAInity.Server.Login.Packets.Resv.PacketServerCheckPing;
import skywolf46.HumAInity.Server.Login.Packets.Send.*;
import skywolf46.HumAInity.Server.ServerThread;

import java.net.Socket;
import java.security.KeyPair;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class LoginServer extends ServerThread {
    private static Connection c;

    private ExecutorService QUEUE = Executors.newFixedThreadPool(20);
    private HashMap<String, String> idPw = new HashMap<>();
    private KeyPair kp;

    static {
        initPacket();
        initListener();
    }

    public LoginServer(int port, KeyPair kp) {
        super(port, socket -> {
//            System.out.println("Socket received");
            PacketConnection pc = new PacketConnection(socket, AbstractEncryption.getEncryption("RSA"), kp);
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//            while (!pc.isBroken()){
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                pc.queue(new TestPingPacket("Test packet on " + format.format(new Date(System.currentTimeMillis()))));
//            }
        });
//        System.out.println("Shared public: " + Base64.encodeToString(kp.getPublic().getEncoded()));
        this.kp = kp;
        try {
            loadData("userDB.db");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getDatabase() {
        return c;
    }

    public KeyPair getKeyPair() {
        return kp;
    }

    public static void loadData(String dbName) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        PreparedStatement stmt = c.prepareStatement("create table if not exists USER_LIST( " +
                "userID varchar(180) PRIMARY KEY," +
                "userPW varchar (180))");
        stmt.executeUpdate();
//        c.commit();
    }


    public void startServer() {
        start();
    }

    private static void initPacket() {
        new PacketServerCheckPing().register();
        new PacketLoginRequest("", "").register();
        new PacketRegisterRequest("", "").register();
    }

    private static void initListener() {
        PacketConnection.attachPacketListener(new PacketServerCheckPing().getPacketID(), (con, packet) -> {
            con.queue(new PacketServerPingResponse());
        });
        PacketConnection.attachPacketListener(new PacketLoginRequest("", "").getPacketID(), (con, packet) -> {
            try {
                PacketLoginRequest lq = (PacketLoginRequest) packet;
                PreparedStatement stmt = c.prepareStatement("select * from USER_LIST where userID = ?");
                stmt.setString(1, lq.getId());
                ResultSet rs = stmt.executeQuery();
                if (!rs.next())
                    con.queue(new PacketLoginFailed("ID or Password not match."));
                else {
                    if (!rs.getString(2).equals(lq.getPw()))
                        con.queue(new PacketLoginFailed("ID or Password not match."));
                    else {
                        String token = TokenStorage.insertToken(lq.getId().toLowerCase());
                        con.queue(new PacketLoginSuccess(token));
                        // Connection will be release from client
                    }
                }
            } catch (SQLException e) {
                con.queue(new PacketLoginFailed("System error."));
            }
            //TODO Debug
        });

        PacketConnection.attachPacketListener(PacketRegisterRequest.INSTANCE.getPacketID(), (con, packet) -> {
            try {
                PacketRegisterRequest reg = (PacketRegisterRequest) packet;
                PreparedStatement stmt = LoginServer.getDatabase().prepareStatement("insert into USER_LIST values(?,?)");
                stmt.setString(1, reg.getId().toLowerCase());
                stmt.setString(2, reg.getPw());
                stmt.executeUpdate();
                con.queue(new PacketRegisterSuccess(reg.getId()));
            } catch (SQLException e) {
                con.queue(new PacketRegisterFailed("ID exists. Please use another ID."));
            }
        });

        PacketConnection.attachPacketListener(PacketExitConnection.INSTANCE.getPacketID(), (con, packet) -> {
            con.breakConnection();
        });


    }
}
