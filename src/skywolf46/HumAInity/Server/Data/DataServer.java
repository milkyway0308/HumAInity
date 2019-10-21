package skywolf46.HumAInity.Server.Data;

import skywolf46.HumAInity.GrammerParser.GrammarInvoker;
import skywolf46.HumAInity.GrammerParser.GrammerDataResultSet;
import skywolf46.HumAInity.GrammerParser.ParseAttribute;
import skywolf46.HumAInity.GrammerParser.Parser.Data.TargetDate;
import skywolf46.HumAInity.PacketConnection.Encryption.AbstractEncryption;
import skywolf46.HumAInity.PacketConnection.PacketConnection;
import skywolf46.HumAInity.Server.Data.Data.TokenStorage;
import skywolf46.HumAInity.Server.Data.Packets.Resv.PacketLoginWithToken;
import skywolf46.HumAInity.Server.Data.Packets.Resv.PacketUserChat;
import skywolf46.HumAInity.Server.Data.Packets.Send.PacketBotResponse;
import skywolf46.HumAInity.Server.Data.Packets.Send.PacketTokenInvalid;
import skywolf46.HumAInity.Server.ServerThread;

import java.net.Socket;
import java.security.KeyPair;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Consumer;

public class DataServer extends ServerThread {
    public DataServer(int port, KeyPair kp) {
        super(port, soc -> {
            new PacketConnection(soc, AbstractEncryption.getEncryption("RSA"), kp);
        });

        PacketLoginWithToken.INSTANCE.register();
        PacketUserChat.INSTANCE.register();
        GrammarInvoker.init();
        PacketConnection.attachPacketListener(PacketLoginWithToken.INSTANCE.getPacketID(), (con, packet) -> {
            PacketLoginWithToken tok = (PacketLoginWithToken) packet;
            String name = TokenStorage.getUserByToken(tok.getToken());
            if (name == null) {
                con.queue(new PacketTokenInvalid());
            } else {
                con.queue(new PacketBotResponse("Hello, " + name + "!"));
                con.queue(new PacketBotResponse("This version of Project HumAInity is demo."));
                con.queue(new PacketBotResponse("Almost features will be disabled, or unstable."));
            }
        });

        PacketConnection.attachPacketListener(PacketUserChat.INSTANCE.getPacketID(), (con, packet) -> {
            try {
                PacketUserChat chat = (PacketUserChat) packet;
                ParseAttribute attr = new ParseAttribute();
                attr.at("Data")
                        .add("halloween", (str, cur, tar) -> {
                            Calendar c = Calendar.getInstance();
                            Calendar current = new GregorianCalendar(c.get(Calendar.YEAR), 10, 31);
                            if (current.after(c))
                                current.set(Calendar.YEAR, current.get(Calendar.YEAR) + 1);
                            TargetDate t = new TargetDate(current.get(Calendar.YEAR), current.get(Calendar.MONTH) + 1, current.get(Calendar.DATE));
                            return new GrammerDataResultSet(t, str.substring(cur.length()).trim());
                        });
                con.queue(new PacketBotResponse(GrammarInvoker.parse(attr, chat.getText())));
            } catch (Exception ex) {
                con.queue(new PacketBotResponse("Error: Cannot parse question.\nPlease check grammar of sentence and retry.\n(Beta version of HumAInity is unstable.)"));
            }
        });
    }
}
