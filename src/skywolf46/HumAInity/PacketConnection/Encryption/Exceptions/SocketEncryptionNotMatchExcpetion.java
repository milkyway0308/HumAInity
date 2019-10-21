package skywolf46.HumAInity.PacketConnection.Encryption.Exceptions;

public class SocketEncryptionNotMatchExcpetion extends RuntimeException {
    public SocketEncryptionNotMatchExcpetion(String current,String target){
        super("ERROR: Encrypt type of socket connection is not matched. (Required Encryption: " + current + " / Socket Encryption: " + target + ")");
    }
}
