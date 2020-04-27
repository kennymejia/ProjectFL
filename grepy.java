import java.io.*;
import java.util.Scanner;

public class grepy {

    public static void main (String [] args) throws IOException {

        CreateNFA.NFA myNFA = new CreateNFA.NFA();
        CreateDFA.DFA myDFA = new CreateDFA.DFA();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter The Name Of A File: ");

        String fileName = Alphabet.learnAlphabet(in.readLine());
        File regexFile = new File(fileName);
        Scanner input = new Scanner(regexFile);
        
        // inputting each regex one line at a time
        do {
            
            String s = input.nextLine();
            System.out.println("\nThe Current REGEX: " + s);

            myNFA = CreateNFA.generate(s);
            System.out.println("\nNFA:");
            myNFA.build();

            myDFA = CreateDFA.subsetConstruction(myNFA);
            System.out.println("\nDFA:");
            myDFA.build();

        } while(input.hasNextLine());

        input.close();
    }
}