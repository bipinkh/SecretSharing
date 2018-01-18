import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bipin khatiwada
 * github.com/bipinkh
 */

public class Splitter {

    public static List<byte[]> split(File f) {
        byte[] buffer = new byte[constants.FILE_CHUNK_SIZE];
        List<byte[]> fileParts = new ArrayList<byte[]>();

        //try-with-resources to ensure closing stream
        try (
            FileInputStream fis =new FileInputStream(f);
            BufferedInputStream bis =new BufferedInputStream(fis)
        ) {
            while (bis.read(buffer) > 0) {
                fileParts.add(buffer);
            }
        } catch (Exception e) {
            System.out.println("Failed to part the file \n " + e.getMessage());
        }
        return fileParts;
    }

    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
//        List<byte[]> parts = split(new File("src//main//resources//testfile.txt"));
        List<byte[]> parts = split(new File("src//main//resources//MileyCyrus.mp3"));
        System.out.println("Total parts :: "+ parts.size());
        for (int i = 0; i<= parts.size(); i++){
            System.out.println("Part "+String.valueOf(i)+"\n"+ new String(parts.get(0)));
//            System.out.println("Part "+String.valueOf(i)+"\n"+ parts.get(0));
        }
    }
}
