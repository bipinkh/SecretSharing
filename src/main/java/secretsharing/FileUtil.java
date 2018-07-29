package secretsharing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bipin khatiwada
 * github.com/bipinkh
 */

public class FileUtil {

    static long numSplits = 10;
    static int maxReadBufferSize = 8 * 1024; //8KB

    public static void main(String[] args) throws Exception {
        //        File targetFile = new File("src//main//resources//op.txt");
        File targetFile = new File("src//main//resources//op.mp3");

        chunkFiles();
        List<File> splittedParts = new ArrayList<>();
        for (int i =1 ; i<= numSplits; i++){
            File file = new File("src//main//resources//split."+i);
            splittedParts.add(file);
        }

        mergeFiles(splittedParts,targetFile);
    }

    public static void chunkFiles() throws Exception
    {
        RandomAccessFile raf = null;
        try {
//            raf = new RandomAccessFile("src//main//resources//testfile.txt", "r");
            raf = new RandomAccessFile("src//main//resources//MileyCyrus.mp3", "r");
            long sourceSize = raf.length();
            long bytesPerSplit = sourceSize / numSplits;
            long remainingBytes = sourceSize % numSplits;

            for (int destIx = 1; destIx <= numSplits; destIx++) {
                BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("src//main//resources//split." + destIx));
                if (bytesPerSplit > maxReadBufferSize) {
                    long numReads = bytesPerSplit / maxReadBufferSize;
                    long numRemainingRead = bytesPerSplit % maxReadBufferSize;
                    for (int i = 0; i < numReads; i++) {
                        readWrite(raf, bw, maxReadBufferSize);
                    }
                    if (numRemainingRead > 0) {
                        readWrite(raf, bw, numRemainingRead);
                    }
                } else {
                    readWrite(raf, bw, bytesPerSplit);
                }
                bw.close();
            }
            if (remainingBytes > 0) {
                BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("split." + (numSplits + 1)));
                readWrite(raf, bw, remainingBytes);
                bw.close();
            }
        }finally {
            if (raf != null)
                raf.close();
        }
    }

    public static void mergeFiles(List<File> files, File target) {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File f : files) {
            InputStream fis = null;
            try {
                fis = new FileInputStream(f);
                byte[] buf = new byte[4096];
                int i;
                while ((i = fis.read(buf)) != -1) {
                    fos.write(buf, 0, i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) throws IOException {
        byte[] buf = new byte[(int) numBytes];
        int val = raf.read(buf);
        if(val != -1) {
            bw.write(buf);
        }
    }

}
