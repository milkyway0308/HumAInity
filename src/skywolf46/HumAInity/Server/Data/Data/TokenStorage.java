package skywolf46.HumAInity.Server.Data.Data;

import java.util.HashMap;
import java.util.UUID;

public class TokenStorage {
    private static HashMap<String, String> tokens = new HashMap<>();
    private static final Object LOCK = new Object();

    public static String getUserByToken(String token) {
        synchronized (LOCK) {
            return tokens.remove(token);
        }
    }

    public static String insertToken(String userID) {
        synchronized (LOCK) {
            String token = UUID.randomUUID().toString();
            while (tokens.containsKey(token))
                token = UUID.randomUUID().toString();
            tokens.put(token, userID);
            return token;
        }
    }
}
