
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public static boolean merge(List<byte[]> fileParts, File into){
        try (FileOutputStream fos = new FileOutputStream(into) ) {
            for (byte[] part : fileParts){
                fos.write(part);
            }
        }catch (Exception e){
            System.out.println("could not merge the files :\n"+e.getMessage());
            return false;
        }
        return true;
    }


    public static void main(String[] args) throws IOException {
        String fileName = "src//main//resources//testfile.txt";
        String outputFileName = "src//main//resources//testOPfile.txt";

        List<byte[]> parts = split(new File(fileName));

        System.out.println("Total parts :: "+ parts.size());

        File f = new File(outputFileName);
        f.getParentFile().mkdirs();
        f.createNewFile();
        merge(parts, f);


    }
}
