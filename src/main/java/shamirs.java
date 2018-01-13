import org.mitre.secretsharing.Part;
import org.mitre.secretsharing.Secrets;

import java.util.Random;

/**
 * @author bipin khatiwada
 * rtrbpn@gmail.com
 * github.com/bipinkh
 */

public class shamirs {

    static String secretString = " this is the chemical formula of coca cola ";
    static Random rand;
    static Integer seed = 945156;

    public static void main(String[] args){

        rand = new Random(seed);

        //get splitted parts
        Part[] parts = split();

        System.out.println("Splitted Parts:: " + parts);

    }


    public static Part[] split() {
        byte[] secret = secretString.getBytes();
        return Secrets.split(secret, 5, 3, rand);
    }


}
