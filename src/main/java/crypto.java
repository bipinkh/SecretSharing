
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bipin khatiwada
 * github.com/bipinkh
 */
public class crypto {
    public static AES aes;

    static {
        try {
            aes = new AES();
        } catch (Exception e) { }
    }


    public static List<byte[]> encryptParts(List<byte[]> parts) throws Exception{
        List<byte[]> result = new ArrayList<byte[]>();
        for (byte[] p : parts){
            result.add(encrypt(p));
        }
        return result;
    }


    public static byte[] encrypt(byte[] part) throws Exception {
        return aes.encryption(part);
    }


    public static List<byte[]> decryptParts(List<byte[]> parts) throws Exception{
        List<byte[]> result = new ArrayList<byte[]>();
        for (byte[] p : parts){
            result.add(decrypt(p));
        }
        return result;
    }


    public static byte[] decrypt(byte[] part) throws Exception{
        return aes.decryption(part);
    }

}
