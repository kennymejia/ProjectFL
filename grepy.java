import java.io.*;
import java.util.Scanner;

public class grepy {

    public static void main (String [] args) throws IOException {

        CreateNFA.NFA myNFA = new CreateNFA.NFA();
        CreateDFA.DFA myDFA = new CreateDFA.DFA();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter The Name Of The Regex File: ");

        String fileName = Alphabet.learnAlphabet(in.readLine());
        File regexFile = new File(fileName);
        Scanner input = new Scanner(regexFile);
        in.close();

        // was getting errors by previous input so created in again after closing
        in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter A Name For The NFA File: ");
        String fileNameNFA = Alphabet.learnAlphabet(in.readLine());
        FileManip.Create(fileNameNFA);

        System.out.print("Enter A Name For The DFA File: ");
        String fileNameDFA = Alphabet.learnAlphabet(in.readLine());
        FileManip.Create(fileNameDFA);
        
        // inputting each regex one line at a time
        do {
            
            //TODO: FIX OVERWRTING OF DATA IN TXT FILES
            //TODO: ADD REGEX NAME TO THE FILES AS WELL

            String s = input.nextLine();
            System.out.println("\nThe Current REGEX: " + s);

            myNFA = CreateNFA.generate(s);
            System.out.println("\nNFA:");
            myNFA.build();
            myNFA.writeToFile(fileNameNFA);

            myDFA = CreateDFA.subsetConstruction(myNFA);
            System.out.println("\nDFA:");
            myDFA.build();
            myNFA.writeToFile(fileNameDFA);

        } while(input.hasNextLine());

        input.close();
    }
}