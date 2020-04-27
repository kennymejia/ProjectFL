import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Alphabet {
    
    public static String learnAlphabet(String fileName) {

        ArrayList<Character> alphabetArray = new ArrayList<Character>();

        // checks to make sure the file exists
        // exits program if file name is incorrect or non existant
        try {

            File fileToRead = new File (fileName);
            
            // parsing each line of the file
            // using arraylist for easy check
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

        } catch (FileNotFoundException e) {

            System.out.println("No Such File Found\n" +
                                "File May Have Been Moved Or Deleted\n" +
                                "Terminating Program NOW");
        }

        return fileName;
    }
}