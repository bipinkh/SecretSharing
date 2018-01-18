import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author bipin khatiwada
 * github.com/bipinkh
 */

public class AES {

    SecretKeySpec myKey;
    IvParameterSpec ivParameterSpec;

    //constructor for iv generation
    public AES() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        byte[] iv = new byte[constants.IVSIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        ivParameterSpec = new IvParameterSpec(iv);

        generateKey( constants.PASSWORD.getBytes());
    }

    // key generation
    public void generateKey(byte[] password) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); //hash functions
        digest.update(new String (password).getBytes("UTF-8"));
        byte[] keyBytes = new byte[16];
        //digest.digest(byte of string x) gives hash value
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        myKey = new SecretKeySpec(keyBytes, "AES");
    }

    //encryption method
    public byte[] encryption(byte[] bytePlainText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, myKey, ivParameterSpec );
        byte[] byteResult = cipher.doFinal(bytePlainText);
        return byteResult;
    }

    //decryption method
    public byte[] decryption(byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,myKey, ivParameterSpec);
        byte[] originalMessage = cipher.doFinal(ciphertext);
        return originalMessage;
    }
}