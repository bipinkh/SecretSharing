//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * @author bipin khatiwada
// * github.com/bipinkh
// */
//public class FileUtilTest {
//
//    @Test
//    public void fileChunkingMergingTest() throws IOException {
//
//        String fileName = "src//main//resources//testfile.txt";
//        String outputFileName = "src//main//resources//testOPfile.txt";
//
//        //split
//        List<byte[]> parts = fileshares.FileUtil.split(new File(fileName));
//        System.out.println("Total parts :: " + parts.size());
//
//        File f = new File(outputFileName);
//        f.getParentFile().mkdirs();
//        f.createNewFile();
//        fileshares.FileUtil.merge(parts, f);
//
//    }
//
//    @Test
//    public void cryptoTest() throws Exception {
//
//        String fileName = "src//main//resources//testfile.txt";
//        String outputFileName = "src//main//resources//testOPfile.txt";
//
//        //split
//        List<byte[]> parts = fileshares.FileUtil.split(new File(fileName));
//        System.out.println("Total parts :: " + parts.size());
//
//        //encrypt
//        List<byte[]> encParts = secretsharing.CryptoUtils.encryptParts(parts);
//
//        //decrypt
//        List<byte[]> decParts = secretsharing.CryptoUtils.decryptParts(encParts);
//
//        File f = new File(outputFileName);
//        f.getParentFile().mkdirs();
//        f.createNewFile();
//        boolean mergeSuccess = fileshares.FileUtil.merge(decParts, f);
//        assertTrue(mergeSuccess);
//    }
//}