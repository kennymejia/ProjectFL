/*
 * KΣППY MΣJIΛ
 * Grepy
 * Execute Example:
 * java Grepy [NAME OF REGEX INPUT FILE] [NAME OF NFA OUTPUT FILE] [NAME OF DFA OUTPUT FILE]
 */

import java.io.*;
import java.util.Scanner;

public class Grepy {

    public static void main (String [] args) throws IOException {

        // taking arguments from terminal input and assigning them
        String regex = args[0] + ".txt";
        String fileNameNFA = args[1] + ".txt";
        String fileNameDFA = args[2] + ".txt";

        // creating our nfa and dfa structures
        CreateNFA.NFA myNFA = new CreateNFA.NFA();
        CreateDFA.DFA myDFA = new CreateDFA.DFA();
        
        /*
         * we are checking to see if the file exists
         * in this block we are calling our function to learn the alphabet
         * creating a scanner for later use with our nfa and dfa
         */
        try {
            File fileToRead = new File (regex);
            Scanner check = new Scanner(fileToRead);
            check.close();
        } catch (FileNotFoundException e) {
            System.out.println("No Such File Found\n" +
                                "File May Have Been Moved Or Deleted\n" +
                                "Terminating Program NOW");
        }

        File regexFile = new File(regex);
        Scanner input = new Scanner(regexFile);

        // creating our nfa and dfa files for output
        FileManip.Create(fileNameNFA);
        FileManip.Create(fileNameDFA);

        // inputting each regex one line at a time
        do{
            String s = input.nextLine();
            Alphabet.learnAlphabet(s);
            System.out.println("\nThe Current REGEX: " + s);
            FileManip.Write(fileNameNFA, s + ": ");
            FileManip.Write(fileNameDFA, s + ": ");

            myNFA = CreateNFA.generate(s);
            System.out.println("\nNFA:");
            myNFA.build();
            myNFA.writeToFile(fileNameNFA);

            myDFA = CreateDFA.subsetConstruction(myNFA);
            System.out.println("\nDFA:");
            myDFA.build();
            myNFA.writeToFile(fileNameDFA);

            FileManip.Write(fileNameNFA, "\n");
            FileManip.Write(fileNameDFA, "\n");
        }while(input.hasNextLine());

        input.close();
    }
}