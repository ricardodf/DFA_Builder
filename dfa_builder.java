import java.util.Arrays;
import java.util.Scanner;

public class dfa_builder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int flag;
        String input; // Buffer for scanner
        String[] alphabet, states, finalStates, strToEvaluate;

        // ALPHABET
        System.out.print("Alphabet (each element divided by commas or spaces): ");
        alphabet = sc.nextLine().split("\\s*(,|\\s)\\s*");

        // STATES
        System.out.print("States (each element divided by commas or spaces): ");
        states = sc.nextLine().split("\\s*(,|\\s)\\s*");

        // FINAL STATES
        do {
            flag = 0;
            System.out.print("Final States (each element divided by commas or spaces): ");
            finalStates = sc.nextLine().split("\\s*(,|\\s)\\s*");

            // Check if every final states exists in the states
            for (String estado : finalStates){
                if (!Arrays.asList(states).contains(estado)){
                    flag = 0;
                    break;
                } else
                    flag = 1;
            }
        } while (flag == 0);

        // Table if transitions, Q (states) x Sigma (alphabet)
        String[][] transitions = new String[states.length][alphabet.length];

        // For each transition...
        for (int i = 0; i < transitions.length; i++) {
            for (int j = 0; j < transitions[0].length; j++) {
                do {
                    flag = 0;
                    System.out.printf("Transition for (%s, %s): ", states[i], alphabet[j]);
                    input = sc.nextLine();
                    if (Arrays.asList(states).contains(input)) {
                        transitions[i][j] = input;
                        flag = 1;
                    }
                } while (flag == 0);
            }
        }

        String currentState;
        int indexState = 0, indexPath = 0;

        // Check a string if its valid
        do {
            currentState = states[0];
            System.out.print("Ingresar cadena a evaluar: ");
            input = sc.nextLine();

            // IF the string is not finished
            if (!input.equals("-1")) {
                strToEvaluate = input.split("(?!^)");
                for (int i = 0; i < input.length(); i++) {
                    if (!Arrays.asList(alphabet).contains(strToEvaluate[i])) {  
                        System.out.printf("Elemento '%s' no pertenece al alfabeto\n", strToEvaluate[i]);
                        currentState = "invalid";
                        break;
                    }
                    currentState = strToEvaluate[i]; //caminoActual toma el indice de la letra
                    for (int indexOnStatus = 0; indexOnStatus < states.length; indexOnStatus++)
                        if (currentState.equals(states[indexOnStatus])) indexState = indexOnStatus;
                    for (int indexOnPath = 0; indexOnPath < alphabet.length; indexOnPath++) {
                        if (currentState.equals(alphabet[indexOnPath])) indexPath = indexOnPath;
                    }
                    currentState = transitions[indexState][indexPath];
                }
                
                for (String estadoFinal : finalStates) {
                    if (currentState.equals(estadoFinal))
                        System.out.println("Cadena Aceptada");
                    else
                        System.out.println("Cadena Rechazada");
                }
            }
        } while (!input.equals("-1"));
        sc.close();
    }
}
