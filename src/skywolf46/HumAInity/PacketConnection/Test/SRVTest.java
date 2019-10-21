package skywolf46.HumAInity.PacketConnection.Test;

import org.minidns.hla.DnssecResolverApi;
import skywolf46.HumAInity.PacketConnection.Util.SRVResolver;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SRVTest {
    public static void main(String[] args) {
//        try {
//            String query = "_login._tcp.humainity.skywolf.kr";
//            Hashtable<String, String> environment = new Hashtable<>();
//            environment.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
//            environment.put("java.naming.provider.url", "dns:");
//            InitialDirContext dirContext = new InitialDirContext(environment);
//            Attributes records = dirContext.getAttributes(query, new String[] {"SRV"});
//            Attribute attr = records.get("SRV");
//            for(int i = 0;i < attr.size();i++)
//                System.out.println(attr.get(i));
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }

        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:userDB.db");
            System.out.println("Complete");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
