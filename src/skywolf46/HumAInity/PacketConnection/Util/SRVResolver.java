package skywolf46.HumAInity.PacketConnection.Util;

import org.minidns.hla.DnssecResolverApi;
import org.minidns.record.SRV;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class SRVResolver {

    public static SocketAddress resolve(String domain, String query, ProtocolType protocolType) throws IOException {
        String url = query + "._" + protocolType.name().toLowerCase() + "." + domain;
        SRV srv = DnssecResolverApi.INSTANCE.resolveSrv(url).getAnswers().iterator().next();
//        String next = split[3].substring(0, split[3].length() - 1);
//        int port = Integer.parseInt(split[2]);
        return new InetSocketAddress(srv.target.toString(),srv.port);
    }

    public static enum ProtocolType {
        TCP, UDP
    }
}
