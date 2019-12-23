import java.util.Arrays;
import java.util.Scanner;

public class dfa_builder {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int flag;   //Un indicador para romper bucles
        String inputBuffer; //Recibe el dato de entrada sin limpiar
        String[] alfabeto, estadosTotales, estadosFinales, cadena; //Almacenando los datos del AFD

        // Entrada de alfabeto
        System.out.print("Ingresar alfabeto (cada elemento dividido por comas y/o espacios): ");
        inputBuffer = sc.nextLine();
        alfabeto = inputBuffer.split("\\s*(,|\\s)\\s*"); //De la entrada, quita cualquier espacio y/o coma

        //Entrada de los estados del AFD
        System.out.print("Ingresar estados del AFD (cada elemento dividido por comas y/o espacios): ");
        inputBuffer = sc.nextLine();
        estadosTotales = inputBuffer.split("\\s*(,|\\s)\\s*");

        //Entrada de los estados finales
        do {
            flag = 0;
            System.out.print("Ingresar estados finales validos (cada elemento dividido por comas y/o espacios): ");
            inputBuffer = sc.nextLine();
            estadosFinales = inputBuffer.split("\\s*(,|\\s)\\s*");

            // Comprobación: Revisa si cada estado final ingresado existe dentro del conjunto de los estados Q
            for (String estado : estadosFinales) {
                if (!Arrays.asList(estadosTotales).contains(estado)) {
                    flag = 0;
                    break;
                } else
                    flag = 1;
            }
            // Si es una entrada inválida, vuelve a pedir la entrada
        } while (flag == 0);

        // Tabla de transiciones, toma las dimensiones de la cardinalidad de Q (estados) y Sigma (alfabeto)
        String[][] transiciones = new String[estadosTotales.length][alfabeto.length];

        //Ingresando cada transición conforme al alfabeto y estados
        for (int i = 0; i < transiciones.length; i++) {
            for (int j = 0; j < transiciones[0].length; j++) {
                do {
                    flag = 0;
                    System.out.printf("Ingresar transicion valida (%s, %s): ", estadosTotales[i], alfabeto[j]);
                    inputBuffer = sc.nextLine();
                    if (Arrays.asList(estadosTotales).contains(inputBuffer)) {
                        transiciones[i][j] = inputBuffer;
                        flag = 1;
                    }
                } while (flag == 0);
            }
        }

        //Variables a utilizar para evaluar cadena
        String estadoActual, caminoActual;
        int indexEstado = 0, indexCamino = 0;

        //Continuar ciclo mientras que la cadena no sea "-1"
        do {
            inputBuffer = "";
            estadoActual = estadosTotales[0]; //El estado inicial va ser el primer estado dentro del conjunto de los estados
            System.out.print("Ingresar cadena a evaluar: ");
            inputBuffer = sc.nextLine();

            // Si es distinto que "-1"
            if (!inputBuffer.equals("-1")) {
                cadena = inputBuffer.split("(?!^)"); //Dividir cadena por letra y guardarlo en un arreglo
                for (int i = 0; i < inputBuffer.length(); i++) {
                    if (!Arrays.asList(alfabeto).contains(cadena[i])) {  //Si una letra de al cadena no pertence al alfabeto, se rechaza la cadena
                        System.out.printf("Elemento '%s' no pertenece al alfabeto\n", cadena[i]);
                        estadoActual = "invalid";
                        break;
                    }
                    caminoActual = cadena[i]; //caminoActual toma el indice de la letra
                    //Encontrar index del estadoActual
                    for (int indexOnStatus = 0; indexOnStatus < estadosTotales.length; indexOnStatus++)
                        if (estadoActual.equals(estadosTotales[indexOnStatus])) indexEstado = indexOnStatus;
                    //Encontrar index del camino
                    for (int indexOnPath = 0; indexOnPath < alfabeto.length; indexOnPath++) {
                        if (caminoActual.equals(alfabeto[indexOnPath])) indexCamino = indexOnPath;
                    }
                    //El nuevo estado es dado por los dos indice encontrados
                    estadoActual = transiciones[indexEstado][indexCamino];
                }

                //Buscar si el estadoActual pertenece dentro del conjunto de los estados finales
                for (String estadoFinal : estadosFinales) {
                    if (estadoActual.equals(estadoFinal))
                        System.out.println("Cadena Aceptada");
                    else
                        System.out.println("Cadena Rechazada");
                }
            }
        } while (!inputBuffer.equals("-1"));
        sc.close();
    }
}


