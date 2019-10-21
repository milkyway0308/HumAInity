package skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class RSADecryptor {
    private PrivateKey decryptKey;
    private Cipher cipher;

    public RSADecryptor(PrivateKey decryptKey) {
        this.decryptKey = decryptKey;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, decryptKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public byte[] decrypt(byte[] b) throws BadPaddingException, IllegalBlockSizeException {
        return cipher.doFinal(b);
    }
}
