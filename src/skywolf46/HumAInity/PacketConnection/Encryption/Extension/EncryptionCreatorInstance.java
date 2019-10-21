package skywolf46.HumAInity.PacketConnection.Encryption.Extension;

import skywolf46.HumAInity.PacketConnection.Encryption.AbstractEncryption;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;

public class EncryptionCreatorInstance extends AbstractEncryption {
    private AbstractEncryption encryption;

    public EncryptionCreatorInstance(AbstractEncryption enc) {
        this.encryption = enc;
    }

    @Override
    public String getEncryptionName() {
        return encryption.getEncryptionName();
    }

    @Override
    public AbstractEncryption createEncryptInstance(DataInputStream input, DataOutputStream output) throws IOException, InvalidKeySpecException {
        return encryption.createEncryptInstance(input, output);
    }

    @Override
    public AbstractEncryption createEncryptInstance(DataInputStream input, DataOutputStream output, KeyPair keypair) throws IOException, InvalidKeySpecException {
        return encryption.createEncryptInstance(input, output, keypair);
    }

    @Override
    public void writeEncryptedContent(DataOutputStream ois, byte[] b) throws BadPaddingException, IllegalBlockSizeException, IOException {
        throw new IllegalStateException("This instance only allowed for create encrypt instance.");
    }

    @Override
    public byte[] readDecryptedContent(DataInputStream ois) throws IOException, BadPaddingException, IllegalBlockSizeException {
        throw new IllegalStateException("This encryption instance only allowed for create encrypt instance.");
    }
}
