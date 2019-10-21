package skywolf46.HumAInity.PacketConnection.Encryption;

import skywolf46.HumAInity.PacketConnection.Encryption.Extension.EncryptionCreatorInstance;
import skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.RSASocketEncryption;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractEncryption {
    private static HashMap<String, AbstractEncryption> encryptionInstance = new HashMap<>();

    private static AtomicBoolean init = new AtomicBoolean(false);

    private static final Object LOCK = new Object();

    public abstract String getEncryptionName();

//
//    public final AbstractEncryption createEncryptInstance(ObjectInputStream ois, ObjectOutputStream oos) {
//        return createEncryptInstance(ois, oos, true);
//    }
//
//
//    public final AbstractEncryption createEncryptInstance(ObjectInputStream ois, ObjectOutputStream oos,KeyPair keyPair) {
//        return createEncryptInstance(ois, oos, keyPair,true);
//    }

    public abstract AbstractEncryption createEncryptInstance(DataInputStream input, DataOutputStream output) throws IOException, InvalidKeySpecException;

    public abstract AbstractEncryption createEncryptInstance(DataInputStream input, DataOutputStream output, KeyPair keypair) throws IOException, InvalidKeySpecException;

    public abstract void writeEncryptedContent(DataOutputStream ois, byte[] b) throws BadPaddingException, IllegalBlockSizeException, IOException;

    public abstract byte[] readDecryptedContent(DataInputStream ois) throws IOException, BadPaddingException, IllegalBlockSizeException;

    public static AbstractEncryption getEncryption(String encryptName) {
        synchronized (LOCK) {
            if (!init.get()) {
                RSASocketEncryption.INSTANCE.register();
                init.set(true);
            }
        }
        return encryptionInstance.get(encryptName);
    }

    public void register() {
        encryptionInstance.put(getEncryptionName(), new EncryptionCreatorInstance(this));
    }
}
