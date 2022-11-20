package src;

import java.util.List;
import java.util.Scanner;

public class FAMain {
    static FiniteAutomata finiteAutomata = new FiniteAutomata();
    public static void printMenu(){
        System.out.println("0 - Display set of states");
        System.out.println("1 - Display the alphabet");
        System.out.println("2 - Display all the transitions");
        System.out.println("3 - Display the initial state");
        System.out.println("4 - Display the list of final states");
        System.out.println("5 - Check if sequence is accepted by DFA");

    }
    public static void main (String[] args) {

        finiteAutomata.parseInputFile("FA.in");
        java.util.Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int option = scanner.nextInt();
            System.out.println("Enter option: ");
            switch (option) {
                case 0:
                    System.out.println("The states are: ");
                    printList(finiteAutomata.getStates());
                    break;
                case 1:
                    System.out.println("The alphabet is: ");
                    printList(finiteAutomata.getAlphabet());
                    break;
                case 2:
                    System.out.println("The transitions are: ");
                    for (Transition transition : finiteAutomata.getTransitionList()){
                        System.out.println(transition);
                    }
                    break;
                case 3:
                    System.out.println("The initial state is: ");
                    System.out.println(finiteAutomata.getInitialState());
                    break;
                case 4:
                    System.out.println("The final states are: ");
                    printList(finiteAutomata.getFinalStates());
                    break;
                case 5:
                    if(!finiteAutomata.isDFA()){
                        System.out.println("Cannot check sequence. Finite automata is not DFA");
                    }
                    else {
                        System.out.println("Input sequence : ");
                        scanner.nextLine();
                        String sequence = scanner.nextLine();
                        System.out.println(finiteAutomata.isSequenceAccepted(sequence.split("")));
                    }
                    break;
                default:
                    System.out.println("Invalid option");
            }
            System.out.println("------------------------------");
        }
    }

    public static void printList(List<String> list){
        for (String item : list){
            System.out.print(item);
            System.out.print(' ');
        }
        System.out.println();
    }
}
