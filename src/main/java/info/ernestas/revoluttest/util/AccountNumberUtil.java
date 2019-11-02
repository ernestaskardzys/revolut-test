package info.ernestas.revoluttest.util;

import info.ernestas.revoluttest.exception.CanNotOpenAccountException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class AccountNumberUtil {

    public static String generateAccountNumber(String name) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String message = name + LocalDateTime.now();
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new CanNotOpenAccountException("Can not generate account number");
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }
        return hexString.toString();
    }

}
