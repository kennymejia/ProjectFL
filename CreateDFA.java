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
            this.final_state = 0;
        }

        public DFA(int size) {
            this.states = new ArrayList <Integer> ();
            this.transitions = new ArrayList <Transition> ();
            this.final_state = 0;
            this.setSize(size);
        }

        public DFA(char c) {
            this.states = new ArrayList<Integer> ();
            this.transitions = new ArrayList <Transition> ();
            this.setSize(2);
            this.final_state = 1;
            this.transitions.add(new Transition(0, 1, c));
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

    // failed attempt at SC
    public static DFA subsetConstruction2 (CreateNFA.NFA n) {

        CreateNFA.NFA myNFA = n;
        CreateDFA.DFA myDFA = new CreateDFA.DFA();


        // for every transition we add it to the DFA but first we check to see if other other nodes have same symbol and state from

        // problem is that we have to go form the created state to the next possible state 

        // table will probably we easier with 2d array


        for (int q = 0; q < myNFA.transitions.size(); q++) {

            // create temporary transtion
            Transition trans = new Transition(
                    myNFA.transitions.get(q).state_from,
                    myNFA.transitions.get(q).state_to,
                    myNFA.transitions.get(q).transition_symbol
            );

            int state_to = trans.state_to;

            // check to see which transition we are dealing with 
            // we have an a as the transtion symbol
            if (trans.transition_symbol == 'a') {

                // is there another transition with the same symbol and same start state
                // if so we must add it to our created node
                for (int r = q+1; r < myNFA.transitions.size(); r++) {

                    if (myNFA.transitions.get(r).transition_symbol == 'a' && myNFA.transitions.get(r).state_from == trans.state_from) {

                        // creating our labels for the node
                        String one = Integer.toString(state_to);
                        String two = Integer.toString(myNFA.transitions.get(r).state_to);
                        String s = one + two;
                        state_to = Integer.parseInt(s);

                        // actually creating the node to add to the DFA
                        trans = new Transition(
                            myNFA.transitions.get(r).state_from,
                            state_to,
                            myNFA.transitions.get(r).transition_symbol
                        );
                    }
                }

                myDFA.transitions.add(trans);

                // we have a b as the transition symbol
            } else if (trans.transition_symbol == 'b') {

                // is there another transition with the same symbol and same start state
                // if so we must add it to our created node
                for (int r = q+1; r < myNFA.transitions.size(); r++) {

                    if (myNFA.transitions.get(r).transition_symbol == 'b' && myNFA.transitions.get(r).state_from == trans.state_from) {

                        // creating our labels for the node
                        String one = Integer.toString(state_to);
                        String two = Integer.toString(myNFA.transitions.get(r).state_to);
                        String s = one + two;
                        state_to = Integer.parseInt(s);

                        // actually creating the node to add to the DFA
                        trans = new Transition(
                            myNFA.transitions.get(r).state_from,
                            state_to,
                            myNFA.transitions.get(r).transition_symbol
                        );
                    }
                }

                myDFA.transitions.add(trans);

                // the transition symbol is an epsilon
            } else {//if (trans.transition_symbol == 'E')

                // do something here
                // does epsilon automatically move for each symbol?
            }
        }

        return myDFA;
    }

    // subsetConstruction using a matrix
    public static DFA subsetConstruction (CreateNFA.NFA n) {

        CreateNFA.NFA myNFA = n;
        CreateDFA.DFA myDFA = new CreateDFA.DFA();
        
        // holds 400 elements should be enough
        int [][] scTable = new int [100][5];
        
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
        scTable = combinations(nodesArray);
        
        // printing our table for debugging purposes
        print2D(scTable);
        
        // parsing every line of the 2D array
        // this will determine what gets added to the table at each transition
        // Loop through all rows 
        for (int row = 0; row < scTable.length; row++){
            
            int aStates = -1;
            int bStates = -1;

            // Loop through all elements of current row 
            for (int node = 0; node < scTable[node].length; node++) {

                // checking each nfa transition
                for (int transition = 0; transition < myNFA.transitions.size(); transition++) {

                    // if the current transition matches our current node
                    if (scTable[row][node] == myNFA.transitions.get(transition).state_from) {
                        
                        // if this node has a transition using 'a'
                        if (myNFA.transitions.get(transition).transition_symbol == 'a') {

                            // if its still unset
                            if (aStates == -1) {
                                
                                aStates = scTable[row][node];

                            }else { // if its already set then add to it

                                String one = Integer.toString(aStates);
                                String two = Integer.toString(scTable[row][node]);
                                String s = one + two;
                                aStates = Integer.parseInt(s);
                            }
                        }

                        // if this node has a transition using 'b'
                        if (myNFA.transitions.get(transition).transition_symbol == 'b') {

                            // if its still unset
                            if (bStates == -1) {
                                
                                bStates = scTable[row][node];

                            }else { // if its already set then add to it

                                String one = Integer.toString(bStates);
                                String two = Integer.toString(scTable[row][node]);
                                String s = one + two;
                                bStates = Integer.parseInt(s);
                            }
                        }

                        //add aStates and bStates to our row in that order
                        // this is what we will use to build our DFA
                        scTable[row][scTable[row].length] = aStates;
                        scTable[row][scTable[row].length+1] = bStates;
                    }
                }
            }
        }

        // create DFA based on the table
        for (int row = 0; row < scTable[row].length; row++) {

            int aStates = scTable[row].length-2;
            int bStates = scTable[row].length-1;

            
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