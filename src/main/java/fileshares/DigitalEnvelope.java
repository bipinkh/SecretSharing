package fileshares;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class DigitalEnvelope {


    public static SecretKey createMasterSecretKey(String password) throws Exception {
        return AesCipher.generateKey(password);
    }
    public static IvParameterSpec createMasterIV() throws Exception {
        return AesCipher.generateIV();
    }

	//encryption
			public static void encryption (String inputFile, String outputFile, SecretKey masterAesKey, IvParameterSpec masterIvParameterSpec) throws Exception {
                System.out.println("Encrypting");

                //get aes key and ivparameterspec and file size
                SecretKey aesKey = AesCipher.generateKey();                             // get random aes key
                IvParameterSpec ivParameterSpec = AesCipher.generateIV();       //16 byte
                long sourceSize;
                RandomAccessFile rra = null;
                try {
                    rra = new RandomAccessFile(inputFile, "r");
                    sourceSize = rra.length();
                } finally {
                    if (rra != null)
                        rra.close();
                }

                try(
                        //open file to read and write
                        FileOutputStream fileOutput = new FileOutputStream(outputFile);
                        FileInputStream fileInput = new FileInputStream(inputFile);
                        )
                {
                    // get key and then encrypt it using RSA publicKey
                    byte[] byteAESkey = aesKey.getEncoded();
                    byte[] encryptedAESkey = AesCipher.encryption(byteAESkey,masterAesKey,masterIvParameterSpec);

                    // write the aes key and iv in the 32 byte header of file
                    fileOutput.write(ivParameterSpec.getIV());
                    fileOutput.write(encryptedAESkey);


                    // Data Encryption
                    byte[] readBytes = new byte[16];    //read 16 bytes at a time to encrypt using AES
                    long numOfIter = sourceSize / 16;
                    int remainingSize = (int) sourceSize % 16;
                    for (int i = 1; i <= numOfIter; i++) {
                        fileInput.read(readBytes);
                        fileOutput.write(AesCipher.encryption(readBytes, aesKey, ivParameterSpec)); //decrypted and written
                    }
                    byte[] finalBytes = new byte[(int) remainingSize];
                    fileInput.read(finalBytes);
                    fileOutput.write(AesCipher.encryption(finalBytes, aesKey, ivParameterSpec)); //decrypted and written
                    System.out.println("Encryption Successful");
                }catch(Exception e) {
                    System.out.println("Exception occured ::: " + e);
                }
            }
			

	//decryption
			public static void decryption (String inputFile, String outputFile, SecretKey masterAesKey, IvParameterSpec masterIvParameterSpec) throws Exception
			{

				System.out.println("Decrypting");

                long sourceSize;
                RandomAccessFile rra = null;
                try {
                    rra = new RandomAccessFile(inputFile, "r");
                    sourceSize = rra.length() -  32;    // file content size = total size - 32 bytes of header
                } finally {
                    if (rra != null)
                        rra.close();
                }
				
				try(
                        //open file to read and write
                        FileOutputStream fileOutput = new FileOutputStream(outputFile);
                        FileInputStream fileInput = new FileInputStream(inputFile);
                        )
                {

                    //read IV
                    byte[] ivBytes = new byte[16];    //ivsize 16 bytes
                    fileInput.read(ivBytes);
                    IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

                    //read and decrypt byte aesKey and create secretyKey from it
                    byte[] keyBytes = new byte[16]; //256
                    fileInput.read(keyBytes);
                    byte[] aesKeyEncrypted = AesCipher.decryption(keyBytes,masterAesKey,masterIvParameterSpec);    //decrypt

                    SecretKey aesKey = new SecretKeySpec(aesKeyEncrypted, 0, aesKeyEncrypted.length, "AES");

                    // Data Decryption
                    byte[] readBytes = new byte[16];    //read 16 bytes at a time to decrypt using AES cipher
                    long numOfIter = sourceSize / 16;
                    int remainingSize = (int) sourceSize % 16;
                    for (int i = 1; i <= numOfIter; i++) {
                        fileInput.read(readBytes);
                        fileOutput.write(AesCipher.decryption(readBytes, aesKey, ivParameterSpec)); //decrypted and written
                    }
                    byte[] finalBytes = new byte[(int) remainingSize];
                    fileInput.read(finalBytes);
                    fileOutput.write(AesCipher.decryption(finalBytes, aesKey, ivParameterSpec)); //decrypted and written

                }
				catch(Exception e) {
				    System.out.println("Exception occured ::: " + e);
				}
			System.out.println("Decryption Successful");
			}
}	
