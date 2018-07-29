package fileshares;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

//import org.junit.Test;

public class TestFile {

	public static void main(String[] args) throws Exception {

//        String inputFilepath = "src/main/java/fileshares/fileshares.textfiles/originalText.txt";
//        String outputFilepath = "src/main/java/fileshares/fileshares.textfiles/encrypted.txt";
//        String decryptedFile = "src/main/java/fileshares/fileshares.textfiles/decrypted.txt";
        String inputFilepath = "src/main/java/fileshares/imagefiles/logo.jpg";
        String outputFilepath = "src/main/java/fileshares/imagefiles/encryptedlogo.jpg";
        String decryptedFile = "src/main/java/fileshares/imagefiles/declogo.jpg";

        String pw = "iGotThisOK";   // password

		SecretKey masterAESkey = DigitalEnvelope.createMasterSecretKey(pw);
		IvParameterSpec masterIvParam= DigitalEnvelope.createMasterIV();
		
		DigitalEnvelope.encryption(inputFilepath, outputFilepath, masterAESkey, masterIvParam);
        DigitalEnvelope.decryption(outputFilepath,decryptedFile, masterAESkey, masterIvParam);
	}
}
