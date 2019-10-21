package skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util;

import org.bouncycastle.util.encoders.Base64;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class RSAPublicKeyParser {
    private static KeyFactory RSA_FACTORY;

    static {
        try {
            RSA_FACTORY = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String toString(PublicKey pk) {
        return new String(Base64.encode(pk.getEncoded()));
    }

    public static PublicKey fromString(String pk) throws InvalidKeySpecException {
        X509EncodedKeySpec x509PK = new X509EncodedKeySpec(Base64.decode(pk));
        return RSA_FACTORY.generatePublic(x509PK);
    }
}
