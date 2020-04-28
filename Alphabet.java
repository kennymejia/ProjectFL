import java.util.ArrayList;

public class Alphabet {
    
    public static void learnAlphabet(String regex) {

        ArrayList<Character> alphabetArray = new ArrayList<Character>();
        char line[]= regex.toCharArray();
        for(int x = 0; x < line.length; x++) {

            if(!alphabetArray.contains(line[x])) {
                        
                        alphabetArray.add(line[x]);
            }
        }
        if (alphabetArray.contains('a') || alphabetArray.contains('b'))
            System.out.println("The Alphabet " + alphabetArray.toString());
    }
}
