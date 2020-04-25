import java.util.ArrayList;
import java.util.Arrays;

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
            //(a|b(ab*a)*b)*
        }
    }

    // subsetConstruction using a matrix
    public static DFA subsetConstruction (CreateNFA.NFA n) {

        CreateNFA.NFA myNFA = n;
        CreateDFA.DFA myDFA = new CreateDFA.DFA();
        
        // holds 400 elements should be enough
        int [][] scTable = new int [3][5];
        
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

        // parsing every line of the 2D array
        // this will determine what gets added to the table at each transition
        // Loop through all rows 
        for (int row = 0; row < scTable.length; row++){
            
            int aStates = 0;
            int bStates = 0;
            int column = -1;
            boolean wasSet = false;

            do{
                column++;

                // checking each nfa transition
                for (int transition = 0; transition < myNFA.transitions.size(); transition++) {

                    // if the current transition matches our current node in our table
                    if (scTable[row][column] == myNFA.transitions.get(transition).state_from) {
                        
                        // if this node has a transition using 'a'
                        if (myNFA.transitions.get(transition).transition_symbol == 'a') {

                            // if its still unset
                            // if else is for creating the unique name for each node
                            if (aStates == 0) {
                                
                                aStates = myNFA.transitions.get(transition).state_to;
                                wasSet = true;

                            }else { // if its already set then add to it

                                String one = Integer.toString(aStates);
                                String two = Integer.toString(myNFA.transitions.get(transition).state_to);
                                String s = one + two;
                                aStates = Integer.parseInt(s);
                            }
                        } else if (wasSet == true) { // aStates was set but not a valid transition

                            aStates = 0;
                        }

                    } else if (wasSet == true) {

                        aStates = 0;
                    }

                    // if the current transition matches our current node in our table
                    if (scTable[row][column] == myNFA.transitions.get(transition).state_from) {
                        
                        // if this node has a transition using 'b'
                        if (myNFA.transitions.get(transition).transition_symbol == 'b') {

                            // if its still unset
                            // if else is for creating the unique name for each node
                            if (bStates == 0) {
                                
                                bStates = myNFA.transitions.get(transition).state_to;
                                wasSet = true;

                            }else { // if its already set then add to it

                                String one = Integer.toString(bStates);
                                String two = Integer.toString(myNFA.transitions.get(transition).state_to);
                                String s = one + two;
                                bStates = Integer.parseInt(s);
                            }
                        } else if (wasSet == true) { // aStates was set but not a valid transition

                            bStates = 0;
                        }

                    } else if (wasSet == true) {

                        bStates = 0;
                    }

                    // add aStates and bStates to our row in that order
                    // this is what we will use to build our DFA
                    scTable[row][scTable[row].length-2] = aStates;
                    scTable[row][scTable[row].length-1] = bStates;
                }

            // as long as the next index exists and the next number is not 0
            }while(column < scTable[row].length-1 && scTable[row][column+1] != 0);

        }




        //TODO: CREATE DFA BASED ON SC TABLE



        // DEBUGGING
        print2D(scTable);

        // create DFA based on the table
        for (int row = 0; row <= scTable.length-2; row++) {

            int aStateLocation = scTable[row].length-2;
            int bStateLocation = scTable[row].length-1;

            if ()
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