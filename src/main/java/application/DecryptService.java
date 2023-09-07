package application;

import config.exception.DecryptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class DecryptService {
    private static final Logger logger = LoggerFactory.getLogger(DecryptService.class);
    public static final int KEY_LENGTH = 256;
    public static final int IV_LENGTH = 12; // 96 bits for GCM
    public static final int TAG_LENGTH = 128;
    public static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    private static final String AES_ALGORITHM = "AES";
    private static final String PBK_DF2_WITH_H_MAC_SHA256 = "PBKDF2WithHmacSHA256";

    public String from(String encryptedMessage, String secret) {
        SecretKey derivedKey = obtainDerivedSecretKey(secret, false);
        byte[] combined = decodeEncryptedMessageFromBase64(encryptedMessage);

        byte[] iv = extractIv(combined);
        byte[] encryptedData = extractEncryptedData(combined);
        Cipher cipher = createCipherForEncryption();

        initCipher(cipher, derivedKey, iv);
        byte[] decryptedBytes = getDecryptedBytes(cipher, encryptedData);
        if (decryptedBytes == null) {
            return null;
        }
        return new String(decryptedBytes);
    }

    private byte[] decodeEncryptedMessageFromBase64(String encryptedMessage) {
        return Base64.getDecoder().decode(encryptedMessage);
    }

    private byte[] extractIv(byte[] combined) {
        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(combined, 0, iv, 0, IV_LENGTH);
        return iv;
    }

    private byte[] extractEncryptedData(byte[] combined) {
        byte[] encryptedData = new byte[combined.length - IV_LENGTH];
        System.arraycopy(combined, IV_LENGTH, encryptedData, 0, encryptedData.length);
        return encryptedData;
    }

    private byte[] getDecryptedBytes(Cipher cipher, byte[] encryptedData) {
        try {
            byte[] decryptedBytes = cipher.doFinal(encryptedData);
            return decryptedBytes;
        } catch (IllegalBlockSizeException e) {
            logger.error(e.getMessage());
            throw new DecryptException();
        } catch (BadPaddingException e) {
            logger.error(e.getMessage());
            throw new DecryptException();
        }
    }

    private void initCipher(Cipher cipher, SecretKey derivedKey, byte[] iv) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, derivedKey, new GCMParameterSpec(TAG_LENGTH, iv));
        } catch (InvalidKeyException e) {
            logger.error(e.getMessage());
            throw new DecryptException();
        } catch (InvalidAlgorithmParameterException e) {
            logger.error(e.getMessage());
            throw new DecryptException();
        }
    }

    private Cipher createCipherForEncryption() {
        try {
            return Cipher.getInstance(AES_GCM_NO_PADDING);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            throw new DecryptException();
        } catch (NoSuchPaddingException e) {
            logger.error(e.getMessage());
            throw new DecryptException();
        }
    }

    private SecretKey obtainDerivedSecretKey(String secret, Boolean secureRandom) {
        byte[] salt = generateSalt(secureRandom);
        PBEKeySpec keySpec = new PBEKeySpec(secret.toCharArray(), salt, 10000, KEY_LENGTH);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBK_DF2_WITH_H_MAC_SHA256);
            return new SecretKeySpec(keyFactory.generateSecret(keySpec).getEncoded(), AES_ALGORITHM);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DecryptException();
        }
    }

    private byte[] generateSalt(Boolean secureRandom) {
        byte[] salt = new byte[16];
        if (secureRandom) {
            new SecureRandom().nextBytes(salt);
        }
        return salt;
    }
}
