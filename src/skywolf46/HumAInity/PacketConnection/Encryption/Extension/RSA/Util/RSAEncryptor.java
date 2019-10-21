package skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class RSAEncryptor {
    private PublicKey encryptKey;
    private Cipher cipher;

    public RSAEncryptor(PublicKey encryptKey) {
        this.encryptKey = encryptKey;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, encryptKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt(byte[] b) throws BadPaddingException, IllegalBlockSizeException {
        return cipher.doFinal(b);
    }

}
