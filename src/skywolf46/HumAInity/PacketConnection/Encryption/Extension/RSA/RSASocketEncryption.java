package skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA;

import skywolf46.HumAInity.PacketConnection.Encryption.AbstractEncryption;
import skywolf46.HumAInity.PacketConnection.Encryption.Exceptions.SocketEncryptionNotMatchExcpetion;
import skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util.RSADecryptor;
import skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util.RSAEncryptor;
import skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util.RSAKeypairGenerator;
import skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util.RSAPublicKeyParser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class RSASocketEncryption extends AbstractEncryption {
    public static final RSASocketEncryption INSTANCE = new RSASocketEncryption();
    private RSAEncryptor encryptor;
    private RSADecryptor decryptor;
    private RSAEncryptor testEncryptor;
    private KeyPair keyPair;

    // Cannot create without AbstractEncryption#creatEncryptInstance
    private RSASocketEncryption(DataInputStream ois, DataOutputStream oos) throws IOException, InvalidKeySpecException {
        oos.writeUTF(getEncryptionName());
        oos.flush();
        String encryptType = ois.readUTF();
        if (!encryptType.equals(getEncryptionName())) {
            throw new SocketEncryptionNotMatchExcpetion(getEncryptionName(), encryptType);
        }
        keyPair = RSAKeypairGenerator.generateKeypair(1024);
        oos.writeUTF(RSAPublicKeyParser.toString(keyPair.getPublic()));
        oos.flush();
        decryptor = new RSADecryptor(keyPair.getPrivate());
        encryptor = new RSAEncryptor(RSAPublicKeyParser.fromString(ois.readUTF()));
        testEncryptor = new RSAEncryptor(keyPair.getPublic());
//        System.out.println("Encrypt Key: " + new String(Base64.decode(keyPair.getPublic().getEncoded())));
//        System.out.println("Decrypt Key: " + new String(Base64.decode(keyPair.getPrivate().getEncoded())));

    }

    private RSASocketEncryption(DataInputStream ois, DataOutputStream oos, KeyPair keyPair) throws IOException, InvalidKeySpecException {
        oos.writeUTF(getEncryptionName());
        oos.flush();
        String encryptType = ois.readUTF();
        if (!encryptType.equals(getEncryptionName())) {
            throw new SocketEncryptionNotMatchExcpetion(getEncryptionName(), encryptType);
        }
        this.keyPair = keyPair;
        oos.writeUTF(RSAPublicKeyParser.toString(this.keyPair.getPublic()));
        oos.flush();
        decryptor = new RSADecryptor(this.keyPair.getPrivate());
        PublicKey pk = RSAPublicKeyParser.fromString(ois.readUTF());
        encryptor = new RSAEncryptor(pk);
        testEncryptor = new RSAEncryptor(keyPair.getPublic());

//        System.out.println("Public Key: " + new String(Base64.encode(keyPair.getPublic().getEncoded())));
//        System.out.println("Encrypt Key: " + new String(Base64.encode(pk.getEncoded())));
//        System.out.println("Decrypt Key: " + new String(Base64.encode(keyPair.getPrivate().getEncoded())));

    }

    private RSASocketEncryption() {

    }

    @Override
    public String getEncryptionName() {
        return "RSA";
    }

    @Override
    public AbstractEncryption createEncryptInstance(DataInputStream input, DataOutputStream output) throws IOException, InvalidKeySpecException {
        return new RSASocketEncryption(input, output);
    }

    @Override
    public AbstractEncryption createEncryptInstance(DataInputStream input, DataOutputStream output, KeyPair keypair) throws IOException, InvalidKeySpecException {
        return new RSASocketEncryption(input, output, keypair);
    }

    @Override
    public void writeEncryptedContent(DataOutputStream oos, byte[] b) throws BadPaddingException, IllegalBlockSizeException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ByteArrayOutputStream testBos = new ByteArrayOutputStream();
//        int testLength = 0;
        int splitAmount = b.length / 117 + (b.length % 117 == 0 ? 0 : 1);
        oos.writeInt(splitAmount * 128);
        oos.writeInt(b.length);

        int startingPoint = 0;
        int endPoint = 0;
        for (int i = 0; i < splitAmount; i++) {
            endPoint = Math.min((i + 1) * 117, b.length);
            byte[] data = new byte[endPoint - startingPoint];
            for (int x = 0; x < data.length; x++) {
                data[x] = b[startingPoint + x];
            }
//            System.out.println("Original(" + data.length + ") / " + startingPoint + ":" + endPoint + " : ");
//            System.out.println(Arrays.toString(data));
            byte[] enc = encryptor.encrypt(data);
//            testLength += enc.length;
//            System.out.println("Encrypted(" + enc.length + ") / " + startingPoint + ":" + endPoint + " : ");
//            System.out.println(Arrays.toString(enc));
            bos.write(enc);
//            testBos.write(testEncryptor.encrypt(data));
            startingPoint = endPoint;
        }
//        System.out.println("Original Amount: " + b.length);
//        System.out.println("EndPoint: " + endPoint);
//        System.out.println("Byte amount : " + testBos.size());
//        byte[] test = testBos.toByteArray();
        byte[] data = bos.toByteArray();
//        byte[] orig = new byte[b.length];
//        int split = test.length / 128;
//        for (int i = 0; i < split; i++) {
//            byte[] bx = new byte[128];
//            for (int x = 0; x < bx.length; x++)
//                bx[x] = test[i * 128 + x];
//            bx = decryptor.decrypt(bx);
//            for (int x = 0; x < bx.length; x++)
//                orig[i * 117 + x] = bx[x];
//        }
//        ByteArrayInputStream bais = new ByteArrayInputStream(orig);
//        DataInputStream dis = new DataInputStream(bais);
//        System.out.println("Original Packet: " + dis.readInt());
//        System.out.println("Original: " + dis.readUTF());
        oos.write(data);
//        int byteSplit = data.length / 512 + (b.length % 512 == 0 ? 0 : 1);
//        System.out.println("Data Split: " + byteSplit);
//        System.out.println("Start: " + (byteSplit - 1) * 512);
//        System.out.println("Length: " + data.length);
//        for(int i = 0;i < byteSplit-1;i++){
//            oos.write(data,i * 512,512);
//        }
//        oos.write(data,(byteSplit-1) * 512,data.length - (byteSplit-1) * 512);
        oos.flush();
    }

    @Override
    public byte[] readDecryptedContent(DataInputStream ois) throws IOException, BadPaddingException, IllegalBlockSizeException {
        byte[] original = new byte[ois.readInt()];
        byte[] data = new byte[ois.readInt()];
//        System.out.println("Read length:" + original.length);
        ois.read(original);
        int splitAmount = original.length / 128;
        for (int i = 0; i < splitAmount; i++) {
            byte[] bx = new byte[128];
            for (int x = 0; x < bx.length; x++)
                bx[x] = original[i * 128 + x];
            bx = decryptor.decrypt(bx);
            for (int x = 0; x < bx.length; x++)
                data[i * 117 + x] = bx[x];
        }
        return data;
    }
}
