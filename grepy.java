import java.io.*;
import java.util.Scanner;
import java.util.regex.*;

public class grepy {

    public static void main (String [] args) throws IOException {

        String alphabet = "";

        try{
            File alphabetFile = new File ("alphabet.txt");
            Scanner reader = new Scanner (alphabetFile);
            alphabet = reader.nextLine();

            echoString(alphabet.toString());

            reader.close();
        } catch (FileNotFoundException e){


        }
        
        System.out.println(args[0]);

        // has to be deckared when compiling
        Pattern p = Pattern.compile(args[0]);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s = in.readLine();
        // while our input from the keyboard is not empty we continue to loop
        // javac grepy.java && java grepy '^(a|b)*$'
        while (!s.isEmpty()) {

            Matcher m = p.matcher(s);
            if (m.matches()) System.out.println(s + " Matches Our Pattern");
            s = in.readLine();
        }

        
    }
}