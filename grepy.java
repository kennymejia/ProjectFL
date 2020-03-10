import java.io.*;
import java.util.Scanner;

public class grepy {

    public static void main (String [] args) throws IOException {

        String alphabet = "";
        CreateNFA.NFA nfa_of_input = new CreateNFA.NFA();
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

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        do {

            System.out.println("Enter A Regular Expression\n" +
                                "Alphabet = {a, b}\n" +  
                                "E = Epsilon Or Empty\n" +
                                "* = Kleene Star\n" +
                                "| = Union\n" +
                                "q = quit\n" +
                                "Note: Elements With Nothing Between Them Creates Automatic Concatenation");
            String s = in.readLine();
            if (s.equals("q")) 
                break;
            nfa_of_input = CreateNFA.generate(s);
            System.out.println("\nNFA:");
            nfa_of_input.build();

        } while(input.hasNextLine());

        input.close();
    }
}