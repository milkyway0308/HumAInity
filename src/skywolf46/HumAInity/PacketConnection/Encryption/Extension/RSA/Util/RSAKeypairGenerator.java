package skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class RSAKeypairGenerator {
    public static KeyPair generateKeypair(int keySize){
        SecureRandom sRandom = new SecureRandom();
        sRandom.setSeed(new Random().nextLong());
        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            keygen.initialize(keySize);
            return keygen.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
