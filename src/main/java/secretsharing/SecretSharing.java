package secretsharing;

import org.mitre.secretsharing.Part;
import org.mitre.secretsharing.Secrets;
import org.mitre.secretsharing.codec.PartFormats;

import java.util.Random;

/**
 * @author bipin khatiwada
 * rtrbpn@gmail.com
 * github.com/bipinkh
 */

public class SecretSharing {

    static String secretString = " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus condimentum sagittis lacus, laoreet luctus ligula laoreet ut. Vestibulum ullamcorper accumsan velit vel vehicula. Proin tempor lacus arcu. Nunc at elit condimentum, semper nisi et, condimentum mi. In venenatis blandit nibh at sollicitudin. Vestibulum dapibus mauris at orci maximus pellentesque. Nullam id elementum ipsum. Suspendisse cursus lobortis viverra. Proin et erat at mauris tincidunt porttitor vitae ac dui.\n" +
            "\n" +
            "Donec vulputate lorem tortor, nec fermentum nibh bibendum vel. Lorem ipsum dolor sit amet, consectetur adipisc";
    static Random rand;

    static Integer maxShares = 5;
    static Integer thresholdToRecover = 3;

    public static void main(String[] args) {

        rand = new Random(constants.RANDOM_SEED);

        System.out.println("splitting ... ");

        //get splitted parts
        Part[] parts = split();

        System.out.println("splitted !");

        for (int i = 0; i < parts.length; i++) {
            System.out.println(" Part number " + String.valueOf(i) + " ::");
            String formatted = PartFormats.currentStringFormat().format(parts[i]);  //convert part to string
            System.out.println(formatted);
        }

        //join result from all parts
        byte[] joinedResult = join(parts);
        System.out.println("\n\n Joined result from all parts :: " + new String(joinedResult));


        //join result from just three
        Part[] parts1 = {parts[0], parts[1], parts[2]};
        joinedResult = join(parts1);
        System.out.println("\n\n Joined Result from 3 parts (0,1,2) :: " + new String(joinedResult));
        Part[] parts2 = {parts[2], parts[4], parts[0]};
        joinedResult = join(parts2);
        System.out.println("\n\n Joined Result from 3 parts (2,4,0) :: " + new String(joinedResult));


        //join from just two parts
        /**
         * Note: this should yield error since the threshold to recover is 3.
         * */
        Part[] parts3 = {parts[0], parts[1]};
        try {
            joinedResult = join(parts3);
            System.out.println("\n\n Joined Result from 2 parts :: " + new String(joinedResult));
        } catch (Exception e) {
            System.out.println("\n\n Could not join the result because :: " + e.getMessage());
        }

    }

    private static byte[] join(Part[] parts) {
        return Secrets.join(parts);
    }


    public static Part[] split() {
        return Secrets.split(secretString.getBytes(), maxShares, thresholdToRecover, rand);
    }


}
