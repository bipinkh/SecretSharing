package fileshares;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCipher {
	//symmetric aes key generation
	
	public static SecretKey generateKey(String password) throws Exception
	{
		MessageDigest digest = MessageDigest.getInstance("SHA-256"); //hash functions
		digest.update(password.getBytes("UTF-8"));
		byte[] keyBytes = new byte[16];
		System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
		return new SecretKeySpec(keyBytes, "AES");
	}


	public static SecretKey generateKey() throws Exception
	{
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecretKey aesKey = keyGenerator.generateKey();
		return aesKey;
	}

	public static IvParameterSpec generateIV() throws Exception
	{
		byte[] iv = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		return ivParameterSpec;
	}
	
	public static byte[] encryption(byte[]message,SecretKey aesKey, IvParameterSpec ivParameterSpec)
	{
		byte[] result =null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec );
			result = cipher.doFinal(message);
		}
		catch(Exception e){
			System.out.println("Exception Occured during Encryption ::: " +e );
		}
		return result;
	}
	
	public static byte[] decryption(byte[]message,SecretKey aesKey, IvParameterSpec ivParameterSpec)
	{
		byte[] result =null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE,aesKey, ivParameterSpec);
			result = cipher.doFinal(message);
		}
		catch(Exception e){
			System.out.println("Exception Occured during Decryption ::: " +e );
		}
		return result;
	}

}
