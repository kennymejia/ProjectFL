import java.io.*;
import java.util.Scanner;

public class grepy {

    public static void main (String [] args) throws IOException {

        String alphabet = "";
        CreateNFA.NFA myNFA = new CreateNFA.NFA();
        CreateDFA.DFA myDFA = new CreateDFA.DFA();
        Scanner input = new Scanner(System.in);

        try {
            File alphabetFile = new File ("alphabet.txt");
            Scanner reader = new Scanner (alphabetFile);
            alphabet = reader.nextLine();
            System.out.println("The Alphabet: " + alphabet.toString());
            reader.close();

        } catch (FileNotFoundException e) {

            System.out.println("No File Found");
        }

        // while our input from the keyboard is not empty we continue to loop
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.println("Enter A Regular Expression\n" +
                                "Alphabet = {a, b}\n" +  
                                "E = Epsilon Or Empty\n" +
                                "* = Kleene Star\n" +
                                "| = Union\n" +
                                "q = quit\n" +
                                "Note: Elements With Nothing Between Them Creates Automatic Concatenation\n");
            String s = in.readLine();
            if (s.equals("q")) 
                break;
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