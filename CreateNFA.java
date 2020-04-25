
import java.util.ArrayList;
import java.util.Stack;

// Class Used To Create NFA's
public class CreateNFA {
    
    // Transition Object Used To Depict Transition From One Vertex To Another
    public static class Transition {
        public int state_from, state_to;
        public char transition_symbol;

        public Transition(int vertex1, int vertex2, char symbol) {
            this.state_from = vertex1;
            this.state_to = vertex2;
            this.transition_symbol = symbol;
        }
    }

    // Used To Combine the States
    public static class NFA {
        public ArrayList <Integer> states;
        public ArrayList <Transition> transitions;
        public int final_state;
        
        public NFA() {
            this.states = new ArrayList <Integer> ();
            this.transitions = new ArrayList <Transition> ();
            this.final_state = 1;
        }

        public NFA(int size) {
            this.states = new ArrayList <Integer> ();
            this.transitions = new ArrayList <Transition> ();
            this.final_state = 1;
            this.setSize(size);
        }

        public NFA(char c) {
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
        // Along With Th Symbol
        public void build() {
            for (Transition trans: transitions) {
                System.out.println("("+ trans.state_from +" --- "+ trans.transition_symbol +
                    " ---> "+ trans.state_to +")");
            }
            //(a|b(ab*a)*b)*
        }
    }

    // Kleene Star Has Highest Precendence
    public static NFA kleeneStar(NFA n) {

        NFA nfa = new NFA(n.states.size()+2);
        nfa.transitions.add(new Transition(0, 1, 'E'));

        // Bringing In Existing Transitions
        for (Transition trans: n.transitions){
            nfa.transitions.add(
                new Transition(trans.state_from + 1, trans.state_to + 1, trans.transition_symbol)
            );
        }
        
        // Create An Empty Transition From Old Final State To New
        nfa.transitions.add(
            new Transition(n.states.size(), n.states.size() + 1, 'E')
        );
        
        // Looping Back From Last State To Start State
        nfa.transitions.add(
            new Transition(n.states.size(), 1, 'E')
        );

        // Empty Transition From New Initial To New Final
        nfa.transitions.add(
            new Transition(0, n.states.size() + 1, 'E')
        );

        // Update Final State
        nfa.final_state = n.states.size() + 1;
        return nfa;
    }

    // Concatenation
    public static NFA concatenate(NFA q0, NFA q1) {

        q1.states.remove(0);

        // Connecting q0 and q1
        for (Transition trans: q1.transitions){
            q0.transitions.add(
                new Transition(trans.state_from + q0.states.size()-1, 
                               trans.state_to + q0.states.size() - 1, 
                               trans.transition_symbol)
            );
        }

        // Erasing q1 State Then Connecting
        for (Integer state: q1.states) {
            
            q0.states.add(state + q0.states.size() + 1);
        }
        
        q0.final_state = q0.states.size() + q1.states.size() - 2;
        return q0;
    }

    // Pipe Or +
    public static NFA union(NFA q0, NFA q1) {
        
        NFA nfa = new NFA(q0.states.size() + q1.states.size() + 2);
        nfa.transitions.add(new Transition(0, 1, 'E'));
        
        // Copying Transitions
        for (Transition trans: q0.transitions) {
            nfa.transitions.add(
                new Transition(trans.state_from + 1,trans.state_to + 1, trans.transition_symbol));
        }
        
        // Transtion To FInal State
        nfa.transitions.add(
            new Transition(q0.states.size(),q0.states.size() + q1.states.size() + 1, 'E')
        );

        // Branching Back To The Beginning
        nfa.transitions.add(new Transition(0, q0.states.size() + 1, 'E'));

        // Copying Existing Transitions
        for (Transition trans: q1.transitions){
            nfa.transitions.add(
                new Transition(trans.state_from + q0.states.size()+ 1, 
                               trans.state_to + q0.states.size() + 1, 
                               trans.transition_symbol)
            );
        }
        
        // Transition From Last To FInal State
        nfa.transitions.add(new Transition(q1.states.size() + q0.states.size(),
        q0.states.size() + q1.states.size() + 1, 'E'));
       
        // Two New States And Shifted To Avoid Some Repetition
        nfa.final_state = q0.states.size() + q1.states.size() + 1;
        return nfa;
    }

    // Conditional Checking
    // Check To See If Its A Part of Sigma
    private static boolean alphabetCheck(char in){ 
        
        return in >= 'a' && in <= 'b';
    }
    
    // Could Also Possibly Be Epsilon
    private static boolean alphabet(char in) {
        
        return alphabetCheck(in) || in == 'E';
    }
    
    // Checking For OPerators
    private static boolean regexOperator(char in) {

        return in == '(' || in == ')' || in == '*' || in == '|';
    }

    // Used To Check Input All At Once
    private static boolean validInput(char in) {

        return alphabet(in) || regexOperator(in);
    }

    // Passing In The ReGex
    private static boolean validRegularExpression(String reGex) {
        
        if (reGex.isEmpty())
            return false;
        
        for (char in: reGex.toCharArray())
            if (!validInput(in))
                return false;
        
        // We Have A Valid ReGex String W/ Correct Alphabet + Operators
        return true;
    }


    public static NFA generate(String regex) {

        // Before Anything Check To See If Valid ReGex
        if (!validRegularExpression(regex)) {

            System.out.println("Invalid Regular Expression\n");
            return new NFA();
        }
        
        Stack <Character> operators = new Stack <Character> ();
        Stack <NFA> operands = new Stack <NFA> ();
        Stack <NFA> concat_stack = new Stack <NFA> ();
        boolean concatFlag = false;
        char operator, nextCharacter;
        int parameterCount = 0;
        NFA nfaOne;
        NFA nfaTwo;

        // Iterating Over Every Character In The ReGex
        for (int x = 0; x < regex.length(); x++) {
            
            // Getting Our Next Character
            nextCharacter = regex.charAt(x);
            
            // Checking To See If We Have An Element Of The Alphabet
            if (alphabet(nextCharacter)) {
                
                // If We Do We Push To The OPerands Stack
                operands.push(new NFA(nextCharacter));
                
                // Do We Need To Concatenate
                if (concatFlag) {

                    operators.push('.');

                } else // Reset The Flag
                    concatFlag = true;

            } else { // We Have An OPerator Instead

                if (nextCharacter == ')') {
                    
                    // Concatenation Flag Needs To Be Set To False
                    concatFlag = false;
                    
                    // Flagging Paenthesis Error
                    if (parameterCount == 0) {

                        System.out.println("ReGex Error: Too Many End Parenthesis");
                        System.exit(1);

                    } else { 
                        
                        // Keeping Track Of The Parameters
                        parameterCount--;
                    }

                    // We Keep Operating Until We Hit (
                    while (!operators.empty() && operators.peek() != '(') {

                        // Grabbing The Operator
                        operator = operators.pop();

                        // Concatenation That We Set
                        if (operator == '.') {

                            nfaTwo = operands.pop();
                            nfaOne = operands.pop();
                            operands.push(concatenate(nfaOne, nfaTwo));
                        }

                        // Case For Union
                        else if (operator == '|') {

                            nfaTwo = operands.pop();
                            
                            // Concatenation 
                            if (!operators.empty() && operators.peek() == '.') {
                                
                                concat_stack.push(operands.pop());

                                // Grabbing Everything We Are Concatenating
                                while (!operators.empty() && operators.peek() == '.') {
                                    
                                    concat_stack.push(operands.pop());
                                    operators.pop();
                                }

                                nfaOne = concatenate(concat_stack.pop(),
                                concat_stack.pop());

                                // Poping The Concat Stack
                                while (concat_stack.size() > 0) {

                                   nfaOne =  concatenate(nfaOne, concat_stack.pop());
                                }

                            } else {

                                nfaOne = operands.pop();
                            }
                            operands.push(union(nfaOne, nfaTwo));
                        }
                    }
                }

                
                else if (nextCharacter == '*') {

                    operands.push(kleeneStar(operands.pop()));
                    concatFlag = true;
                }

                else if (nextCharacter == '(') {

                    operators.push(nextCharacter);
                    parameterCount++;
                }

                else if (nextCharacter == '|') {

                    operators.push(nextCharacter);
                    concatFlag = false;
                }
            }
        }

        while (operators.size() > 0) {
            
            // Catching Possible Errors
            if (operands.empty()) {
                System.out.println("ReGex Error: More Operands Than Operators");
                System.exit(1);
            }

            operator = operators.pop();

            if (operator == '.') {

                nfaTwo = operands.pop();
                nfaOne = operands.pop();
                operands.push(concatenate(nfaOne, nfaTwo));
            }

            else if (operator == '|') {
                
                nfaTwo = operands.pop();
                
                if(!operators.empty() && operators.peek() == '.') {
                    
                    concat_stack.push(operands.pop());
                    
                    while (!operators.empty() && operators.peek() == '.') {
                        
                        concat_stack.push(operands.pop());
                        operators.pop();
                    }

                    nfaOne = concatenate(concat_stack.pop(),concat_stack.pop());

                    while (concat_stack.size() > 0) {
                    
                        nfaOne =  concatenate(nfaOne, concat_stack.pop());
                    }

                } else {

                    nfaOne = operands.pop();
                }

                operands.push(union(nfaOne, nfaTwo));
            }
        }

        return operands.pop();
    }
}

