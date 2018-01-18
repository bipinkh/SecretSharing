import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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


    public static void main(String[] args){
        String fileName = "src//main//resources//testfile.txt";

//        List<byte[]> parts = split(new File());

        List<byte[]> parts = split(new File(fileName));
        System.out.println("Total parts :: "+ parts.size());

//        for (int i = 0; i<= parts.size(); i++){
//            System.out.println("Part "+String.valueOf(i)+"\n"+ new String(parts.get(0)));
////            System.out.println("Part "+String.valueOf(i)+"\n"+ parts.get(0));
//        }



    }
}
