import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class grepy {

    public static void main (String [] args) throws IOException {

        ArrayList<Character> alphabetArray = new ArrayList<Character>();
        CreateNFA.NFA myNFA = new CreateNFA.NFA();
        CreateDFA.DFA myDFA = new CreateDFA.DFA();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        File fileToRead = new File("");
        
        try {
            System.out.print("Enter The Name Of A File: ");
            File check = new File (in.readLine());
            Scanner reader = new Scanner (check);
            reader.close();
            fileToRead = check;
        } catch (FileNotFoundException e) {

            System.out.println("No Such File Found\n" +
                                "File May Have Been Moved Or Deleted\n" +
                                "Terminating Program NOW");
        }

        Scanner reader = new Scanner (fileToRead);
        while(reader.hasNextLine()) {

            char line[]= reader.nextLine().toCharArray();
            
            for(int x = 0; x < line.length; x++) {

                if(!alphabetArray.contains(line[x])) {
                    
                    alphabetArray.add(line[x]);
                }
            }
        }
        reader.close();

        System.out.println("The Alphabet " + alphabetArray.toString());

        Scanner input = new Scanner(fileToRead);

        do {
            
            String s = input.nextLine();
            System.out.println("\nThe Current REGEX: " + s);

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