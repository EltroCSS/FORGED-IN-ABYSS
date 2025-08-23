/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package forgedinabyss;

import java.util.Scanner;
import java.util.Random;

public class ForgedInAbyss {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        String[] monstruos = {"Vhacan", "Eryndor", "Relict", "Okrun"};
        int[] vida = {500, 700, 900, 550};
        int[] ataque = {60, 55, 45, 65};

        //Eleccion de monstruo
        System.out.println("Bienvenido a Forged in Abyss, elige a tu monstruo ganador");
        for (int i = 0; i < monstruos.length; i++) {
            System.out.println((i + 1) + ". " + monstruos[i] + " - vida: " + vida[i] + " - ataque: " + ataque[i]);
        }
        System.out.println("\nMonstruo: ");
        int eleccion = sc.nextInt() - 1;
        String jugador = monstruos[eleccion];
        System.out.println("\n Elegiste a " + jugador + "!\n");

        int ronda = 1;
        int maxVida = vida[eleccion];
        int curar = 25;

        while (true) {
            System.out.println("*---------------ronda " + ronda + "------------------*");

            //comprobar turno de monstruo vivo
            for (int atacante = 0; atacante < monstruos.length; atacante++) {
                if (vida[atacante] <= 0) {
                    continue; //monstruos muertos ya no atacan
                }
                int objetivo;

                if (atacante == eleccion) {
                    int opcion;
                    while (true) {
                        System.out.println("\nEs tu turno, atacar[1] o curarte [2]");
                        opcion = sc.nextInt();

                        if (opcion == 1) {
                            System.out.println("Elige a quien atacar: ");
                            for (int i = 0; i < monstruos.length; i++) {
                                if (i != atacante && vida[i] >= 0) {
                                    System.out.println((i + 1) + ". " + monstruos[i] + " - vida: " + vida[i]);
                                }
                            }
                            objetivo = objetivoValido(sc, atacante, vida, monstruos.length);
                            break;

                        } else if (opcion == 2) {
                            if (vida[atacante] >= maxVida) {
                                System.out.println("ya tienes tu vida al maximo, solo te queda atacar!");
                            } else {
                                vida[atacante] += curar;
                                if (vida[atacante] > maxVida) {
                                    vida[atacante] = maxVida; //evitar que se cure de mas
                                }
                                System.out.println(monstruos[atacante] + "se ha curado +" + curar
                                        + " puntos. Vida actual: " + vida[atacante]);
                                objetivo = -1; //si se cura, no puede atacar
                                break;
                            }
                        } else {
                            System.out.println("Solo tienes 2 opciones, no es tan dificil, intenta de nuevo: ");
                        }
                    }

                } else {

                    do {
                        objetivo = random.nextInt(monstruos.length);
                    } while (objetivo == atacante || vida[objetivo] <= 0);
                }

                //asignar el ataque y restar vida
                if (objetivo != -1) {
                    vida[objetivo] -= ataque[atacante];
                    if (vida[objetivo] < 0) {
                        vida[objetivo] = 0;
                    }

                    System.out.println(monstruos[atacante] + " ataca a: " + monstruos[objetivo] + " y le quita: " + ataque[atacante] + " puntos de vida. Vida restante de "
                            + monstruos[objetivo] + ": " + vida[objetivo] + "\n");

                    if (vida[objetivo] == 0) {
                        System.out.println(monstruos[objetivo] + " ha sido derrotado!");
                    }

                    //estado de ronda
                    for (int i = 0; i < monstruos.length; i++) {
                        System.out.println(monstruos[i] + ": " + vida[i] + " puntos de vida");
                    }
                    System.out.println("\n");//espaciado 

                    //verificar condiciones de victoria o derrota
                    if (vida[eleccion] <= 0) {
                        System.out.println("Tu monstruo ha sido derrotado! Perdiste todo tu dinero!");
                        sc.close();
                        return;
                    }

                    boolean npcsVivos = false;

                    for (int i = 0; i < monstruos.length; i++) {
                        if (i != eleccion && vida[i] > 0) {
                            npcsVivos = true;
                            break;
                        }
                    }

                    if (!npcsVivos) {
                        System.out.println("Felicitaciones! " + monstruos[eleccion] + "ha derrotado a todos y te volviste mega-millonario! Conseguiste la victoria!");
                        sc.close();
                        return;
                    }
                }

            }
            //final de ronda, aumenta el contador
            ronda++;

        }
    }

    static int objetivoValido(Scanner sc, int atacante, int[] vida, int total) {
        while (true) {
            System.out.println("Objetivo(numero): ");
            int idx = sc.nextInt() - 1;
            if (idx <= 0 || idx >= total) {
                System.out.println("Elige uno de los monstruos que si estan en el ring!, intenta de nuevo: ");
            } else if (idx == atacante) {
                System.out.println("Tienes tendencias suicidas? no te ataques a ti mismo!");
            } else if (vida[idx] <= 0) {
                System.out.println("Ya deja ese cuerpo! Elige un monstruo vivo!: ");
            } else {
                return idx;
            }
        }
    }

}
