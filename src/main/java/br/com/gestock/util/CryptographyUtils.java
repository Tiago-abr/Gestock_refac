package br.com.gestock.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptographyUtils {

    public static String getMD5(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            byte[] digest = messageDigest.digest(string.getBytes());

            BigInteger bigInteger = new BigInteger(1, digest);

            String hashtext = bigInteger.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception);
        }
    }
}
