package fileshares;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bipin khatiwada
 * github.com/bipinkh
 */

public class FileUtil {

    public static void chunkFiles(String filePath, String opFolder, long numSplits, int maxReadBufferSize) throws Exception
    {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(filePath, "r");
            long sourceSize = raf.length();
            long bytesPerSplit = sourceSize / numSplits;
            long remainingBytes = sourceSize % numSplits;
            long loopCntr = numSplits;
            boolean includeExtraByte = false;
            if (remainingBytes > 0){
                loopCntr = numSplits-1;
                includeExtraByte = true;
                bytesPerSplit = sourceSize / loopCntr;
                remainingBytes = sourceSize % loopCntr;
            }

            for (int destIx = 1; destIx <= loopCntr; destIx++) {
                BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(opFolder+"split"+destIx));
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
            if (includeExtraByte) {
                BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(opFolder+"split" + numSplits ));
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
