package fileshares;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestFile {

	public static void main(String[] args) throws Exception {

	    String baseFolder = "src/main/java/fileshares/imagefiles/";
        String inputFilepath = "src/main/java/fileshares/imagefiles/logo.jpg";
        String sendEncFilePath = "src/main/java/fileshares/imagefiles/sendEncLogo.jpg";
        String receiveEncFilePath = "src/main/java/fileshares/imagefiles/receivedEncLogo.jpg";
        String decryptedFile = "src/main/java/fileshares/imagefiles/final.jpg";

//        String baseFolder = "src/main/java/fileshares/music/";
//        String inputFilepath = "src/main/java/fileshares/music/MileyCyrus.mp3";
//        String sendEncFilePath = "src/main/java/fileshares/music/sendEncLogo.mp3";
//        String receiveEncFilePath = "src/main/java/fileshares/music/receivedEncLogo.mp3";
//        String decryptedFile = "src/main/java/fileshares/music/final.mp3";

//        String baseFolder = "src/main/java/fileshares/textfiles/";
//        String inputFilepath = "src/main/java/fileshares/textfiles/originalText.txt";
//        String sendEncFilePath = "src/main/java/fileshares/textfiles/sendEncLogo.txt";
//        String receiveEncFilePath = "src/main/java/fileshares/textfiles/receivedEncLogo.txt";
//        String decryptedFile = "src/main/java/fileshares/textfiles/final.txt";

        String pw = "iGotThisOK";   // password
		SecretKey masterAESkey = DigitalEnvelope.createMasterSecretKey(pw);
		IvParameterSpec masterIvParam= DigitalEnvelope.createMasterIV();

		// encrypt and make envelope
		DigitalEnvelope.encryption(inputFilepath, sendEncFilePath, masterAESkey, masterIvParam);

		// create splits
        int maxReadBufferSize = 8 * 1024;
        long numSplits = 10;
        FileUtil.chunkFiles(sendEncFilePath, baseFolder, numSplits, maxReadBufferSize);

        // merge splits
        List<File> splittedParts = new ArrayList<>();
        for (int i =1 ; i<= numSplits; i++){
            File file = new File(baseFolder+"split"+i);
            splittedParts.add(file);
        }
        FileUtil.mergeFiles(splittedParts,new File(receiveEncFilePath));

        // decrypt and open envelope
        DigitalEnvelope.decryption(receiveEncFilePath,decryptedFile, masterAESkey, masterIvParam);
	}
}
