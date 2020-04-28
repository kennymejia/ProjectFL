import java.util.ArrayList;

public class CreateDFA {

    public static class Transition {
        public int state_from, state_to;
        public char transition_symbol;

        public Transition(int vertex1, int vertex2, char symbol) {
            this.state_from = vertex1;
            this.state_to = vertex2;
            this.transition_symbol = symbol;
        }
    }

    public static class DFA {
        public ArrayList<Integer> states;
        public ArrayList <Transition> transitions;
        public int final_state;

        public DFA() {
            this.states = new ArrayList <Integer> ();
            this.transitions = new ArrayList <Transition> ();
            this.final_state = 1;
        }

        public DFA(int size) {
            this.states = new ArrayList <Integer> ();
            this.transitions = new ArrayList <Transition> ();
            this.final_state = 1;
            this.setSize(size);
        }

        public DFA(char c) {
            this.states = new ArrayList<Integer> ();
            this.transitions = new ArrayList <Transition> ();
            this.setSize(2);
            this.final_state = 1;
            this.transitions.add(new Transition(1, 2, c));
        }

        // Setting State Size
        public void setSize(int size) {
            for (int i = 0; i < size; i++)
                this.states.add(i);
        }

        // For Every Transition We Print The Start State and The Final State 
        // Along With The Symbol
        public void build() {
            for (Transition trans: transitions) {
                System.out.println("("+ trans.state_from +" --- "+ trans.transition_symbol +
                    " ---> "+ trans.state_to +")");
            }
        }

        public void writeToFile(String fileName) {
            for (Transition trans: transitions) {
                String data = ("("+ trans.state_from +" --- "+ trans.transition_symbol +
                                " ---> "+ trans.state_to +")");
                FileManip.Write(fileName, data);
            }
        }
    }

    // subsetConstruction using a matrix
    public static DFA subsetConstruction (CreateNFA.NFA n) {

        CreateNFA.NFA myNFA = n;
        CreateDFA.DFA myDFA = new CreateDFA.DFA();
        
        // have to make it big enough for all possible combinations
        int [][] scTable = new int [1000][1000];
        
        // holds our nodes from the NFA
        // ArrayList because we dont know how many nodes we will have
        ArrayList <Integer> nodesAL = new ArrayList<Integer>();

        // looping over the NFA nodes
        for (int q = 0; q < myNFA.transitions.size(); q++) {

            // is the from node already in the array
            if (!nodesAL.contains(myNFA.transitions.get(q).state_from)) {

                nodesAL.add(myNFA.transitions.get(q).state_from);
            }

            // is the to node already in the array
            if ((!nodesAL.contains(myNFA.transitions.get(q).state_to))) {

                nodesAL.add(myNFA.transitions.get(q).state_to);
            }
        }

        //converting our ArrayList to an Array
        int [] nodesArray = new int [nodesAL.size()];

        for (int q = 0; q < nodesAL.size(); q++) {

            nodesArray[q] = nodesAL.get(q);
        }

        // create our combinations and adding them to our final table
         int tempSCTable[][]= combinations(nodesArray);

        for (int i = 0; i < tempSCTable.length; i++){

            // Loop through all elements of current row 
            for (int j = 0; j < tempSCTable[i].length; j++) {
                scTable[i][j] = tempSCTable[i][j];
            }
        }

        // every row is a combination of states
        // we check every row to see if it can create a transition
        // if it create a transition we add it to the table
        // if no possible transition created we discard it
        for (int row = 0; row < scTable.length; row++){
            
            int aStates = 0;
            int bStates = 0;
            int column = 0;
            boolean wasSetA = false;
            boolean wasSetB = false;

            do{

                // checking each nfa transition
                for (int transition = 0; transition < myNFA.transitions.size(); transition++) {


                    if (wasSetA == false && myNFA.transitions.size() >= scTable[row][column]) {

                        // if the current transition matches our current node in our table
                        if (scTable[row][column] == myNFA.transitions.get(transition).state_from) {

                            // if this node has a transition using 'a'
                            // if we dont have an 'a' transition then the row is not needed 
                            if (myNFA.transitions.get(transition).transition_symbol == 'a') {

                                String one = Integer.toString(aStates);
                                String two = Integer.toString(myNFA.transitions.get(transition).state_to);
                                String s = one + two;
                                aStates = Integer.parseInt(s);

                            } else { // aStates was set but not a valid transition

                                wasSetA = true;
                            }
                        }
                    } else {

                        wasSetA = true;
                    }

                    if (wasSetB == false && myNFA.transitions.size() >= scTable[row][column]) {

                        // if the current transition matches our current node in our table
                        if (scTable[row][column] == myNFA.transitions.get(transition).state_from) {

                            // if this node has a transition using 'b'
                            // if we dont have an 'a' transition then the row is not needed 
                            if (myNFA.transitions.get(transition).transition_symbol == 'b') {

                                String one = Integer.toString(bStates);
                                String two = Integer.toString(myNFA.transitions.get(transition).state_to);
                                String s = one + two;
                                bStates = Integer.parseInt(s);

                            } else { // bStates was set but not a valid transition

                                wasSetB= true;
                            }
                        }
                    } else {

                        wasSetB = true;
                    }
                }

                if (wasSetA == true) {
                    scTable[row][scTable[row].length-2] = 0;
                } else {
                    scTable[row][scTable[row].length-2] = aStates;
                }

                if (wasSetB == true) {
                    scTable[row][scTable[row].length-1] = 0;
                } else {
                    scTable[row][scTable[row].length-1] = bStates;
                }

                column++;

            // as long as the next index exists and the next number is not 0
            }while(column < scTable[row].length && scTable[row][column] != 0);

        }

        // create DFA based on the table
        // first numbers in scTable are our from states
        // last two numbers in each row are our to states
        // if their is a 0 in that location then we do not have a valid transition
        for (int row = 0; row <= scTable.length-2; row++) {

            int aTo = scTable[row][scTable[row].length-2];
            int bTo = scTable[row][scTable[row].length-1];
            int column = 0;
            int aFrom = 0;
            int bFrom = 0;

            // if we have a valid transition
            if (aTo != 0) {

                do {
                    String one = Integer.toString(aFrom);
                    String two = Integer.toString(scTable[row][column]);
                    String s = one + two;
                    aFrom = Integer.parseInt(s);
                    column++;
                }while(scTable[row][column] != 0);

                myDFA.transitions.add(new Transition(aFrom, aTo, 'a'));
            }

            // if we have a valid transition
            if (bTo!= 0) {
                
                column = 0;

                do {
                    String one = Integer.toString(bFrom);
                    String two = Integer.toString(scTable[row][column]);
                    String s = one + two;
                    bFrom = Integer.parseInt(s);
                    column++;
                }while(scTable[row][column] != 0);

                myDFA.transitions.add(new Transition(bFrom, bTo, 'b'));
            }
        }

        return myDFA;
    }

    public static int[][] combinations(int[] a) {

        int len = a.length;
        if (len > 31)
            throw new IllegalArgumentException();
    
        int numCombinations = (1 << len) - 1;
    
        int[][] combinations = (int[][]) java.lang.reflect.Array.newInstance(a.getClass(), numCombinations);
    
        // Start i at 1, so that we do not include the empty set in the results
        for (int i = 1; i <= numCombinations; i++) {
    
            int[] combination = (int[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(),
                    Integer.bitCount(i));
    
            for (int j = 0, ofs = 0; j < len; j++)
                if ((i & (1 << j)) > 0)
                    combination[ofs++] = a[j];
    
            combinations[i - 1] = combination;
        }
    
        return combinations;
    }

    public static void print2D(int matrix[][]) 
    { 
        // Loop through all rows 
        for (int i = 0; i < matrix.length; i++){
  
            System.out.println("\n");
            // Loop through all elements of current row 
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
        }
    } 

}