package com.bodymass.app.crypto;

public class Crypter {
    private static String secret = "secret";

    static public String encode(String originalString) {
        return AES.encrypt(originalString, secret);
    }

    static public String decode(String encryptedString) {
        return AES.decrypt(encryptedString, secret);
    }

    public static void main(String[] args) {
        System.out.println(decode(encode("123")));
    }
}
