package tech.nan.demo.gateway.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AesUtils {

    public static final String DEFAULT_CIPhER_INSTANCE = "AES/ECB/PKCS5Padding";

    public static final String AES = "AES";

    private static final String AES_KEY = "pECc8LPpT5wgfiFNe28JP2Etpt43CS7eej949Otk00W44DdQ2563BP2w9BDTtABm879BDA47fL0z89KB4g159DBdfgnAZIn2xoIB9Na1CO1Ib2A6670TNHEAlEB72Gsw";


    public static String decrypt(String encryptedValue) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPhER_INSTANCE);
            SecretKeySpec key = new SecretKeySpec(getSecretKey().getEncoded(), AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedValue = cipher.doFinal(decodedValue);
            return new String(decryptedValue, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }

    private static SecretKey getSecretKey() {
        KeyGenerator keyGenerator;
        SecureRandom secureRandom;
        try {
            keyGenerator = KeyGenerator.getInstance(AES);
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Get secretKey fail.");
        }
        //初始化密钥生成器，指定密钥长度为128，指定随机源的种子为指定的密钥
        secureRandom.setSeed(AES_KEY.getBytes(StandardCharsets.UTF_8));
        keyGenerator.init(128, secureRandom);
        return keyGenerator.generateKey();
    }

}
