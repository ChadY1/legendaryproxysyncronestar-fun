package gg.starfun.proxy.security;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES-GCM 256 bits, sortie: Base64( IV(12) || ciphertext+tag )
 */
public class CryptoUtil {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;
    private final SecretKey key;
    private final SecureRandom secureRandom = new SecureRandom();

    public CryptoUtil(String base64Key) {
        byte[] raw = Base64.getDecoder().decode(base64Key);
        if (raw.length != 16 && raw.length != 24 && raw.length != 32) {
            throw new IllegalArgumentException("AES key must be 128/192/256 bits");
        }
        this.key = new SecretKeySpec(raw, "AES");
    }

    public String encrypt(String plaintext) throws Exception {
        byte[] iv = new byte[IV_LENGTH];
        secureRandom.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);

        byte[] cipherBytes = cipher.doFinal(plaintext.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        byte[] out = new byte[iv.length + cipherBytes.length];
        System.arraycopy(iv, 0, out, 0, iv.length);
        System.arraycopy(cipherBytes, 0, out, iv.length, cipherBytes.length);

        return Base64.getEncoder().encodeToString(out);
    }

    public String decrypt(String base64Cipher) throws Exception {
        byte[] all = Base64.getDecoder().decode(base64Cipher);
        if (all.length < IV_LENGTH + 16) {
            throw new IllegalArgumentException("ciphertext too short");
        }

        byte[] iv = new byte[IV_LENGTH];
        byte[] cipherBytes = new byte[all.length - IV_LENGTH];

        System.arraycopy(all, 0, iv, 0, IV_LENGTH);
        System.arraycopy(all, IV_LENGTH, cipherBytes, 0, cipherBytes.length);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);

        byte[] plain = cipher.doFinal(cipherBytes);
        return new String(plain, java.nio.charset.StandardCharsets.UTF_8);
    }
}
