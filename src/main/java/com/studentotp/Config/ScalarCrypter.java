package com.studentotp.Config;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScalarCrypter {
    public static byte[] SCALAR_PASSWORD_CRYPTO_KEY = new byte[] { 'T', 'h', 'i', 's', 'i', 's', 'a', 't', 'e', 's', 't',
            'e', 'd', 'k', 'e', 'y' };
    public static String SCALAR_PASSWORD_CRYPTO_ALGORITHM = "Blowfish";

    public static String encrypt(String valueToEncrypt) {
        byte[] encryptedBytes = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(ScalarCrypter.SCALAR_PASSWORD_CRYPTO_KEY,
                    ScalarCrypter.SCALAR_PASSWORD_CRYPTO_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ScalarCrypter.SCALAR_PASSWORD_CRYPTO_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encryptedBytes = cipher.doFinal(valueToEncrypt.getBytes());

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                 | BadPaddingException ex) {
            Logger.getLogger(ScalarCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Base64.encodeBase64String(encryptedBytes);
//		return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * This method will decrypt the password using blowfish algorithm.
     *
     * @param_encryptedPassword
     * @return
     */
    public static String decrypt(String valueToDecrypt) {
        byte[] decryptedBytes = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(ScalarCrypter.SCALAR_PASSWORD_CRYPTO_KEY,
                    ScalarCrypter.SCALAR_PASSWORD_CRYPTO_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ScalarCrypter.SCALAR_PASSWORD_CRYPTO_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            decryptedBytes = cipher.doFinal(Base64.decodeBase64(valueToDecrypt));
//			decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(valueToDecrypt));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                 | BadPaddingException ex) {
            Logger.getLogger(ScalarCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(decryptedBytes);
    }


////    /**
////     * This method will decrypt the question attempted data using MD5 algorithm.
////     *
////     * @param_encryptedString
////     * @return decrypted result string
////     */
////    public static String decryptResult(final String cipherText) throws Exception {
////
////        String secret = "secret";
////        //String cipherText = "U2FsdGVkX1+tsmZvCEFa/iGeSA0K7gvgs9KXeZKwbCDNCs2zPo+BXjvKYLrJutMK+hxTwl/hyaQLOaD7LLIRo2I5fyeRMPnroo6k8N9uwKk=";
////
////        byte[] cipherData = Base64.decodeBase64(cipherText);
//////		 byte[] cipherData = Base64.getDecoder().decode(cipherText);
////        byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);
////
////        MessageDigest md5 = MessageDigest.getInstance("MD5");
////        final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
////        SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
////        IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);
////
////        byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
////        Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
////        aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
////        byte[] decryptedData = aesCBC.doFinal(encrypted);
////        String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);
////        return decryptedText;
////
////    }
////
////    public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {
////
////        int digestLength = md.getDigestLength();
////        int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
////        byte[] generatedData = new byte[requiredLength];
////        int generatedLength = 0;
////
////        try {
////            md.reset();
////
////            // Repeat process until sufficient data has been generated
////            while (generatedLength < keyLength + ivLength) {
////
////                // Digest data (last digest if available, password data, salt if available)
////                if (generatedLength > 0)
////                    md.update(generatedData, generatedLength - digestLength, digestLength);
////                md.update(password);
////                if (salt != null)
////                    md.update(salt, 0, 8);
////                md.digest(generatedData, generatedLength, digestLength);
////
////                // additional rounds
////                for (int i = 1; i < iterations; i++) {
////                    md.update(generatedData, generatedLength, digestLength);
////                    md.digest(generatedData, generatedLength, digestLength);
////                }
////
////                generatedLength += digestLength;
////            }
////
////            // Copy key and IV into separate byte arrays
////            byte[][] result = new byte[2][];
////            result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
////            if (ivLength > 0)
////                result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);
////
////            return result;
////
////        } catch (DigestException e) {
////            throw new RuntimeException(e);
////
////        } finally {
////            // Clean out temporary data
////            Arrays.fill(generatedData, (byte)0);
////        }
//
//    }
}
