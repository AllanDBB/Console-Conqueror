package org.abno.logic.commands;

import org.abno.logic.cards.Weapon;
import org.abno.logic.player.Player;
import org.abno.logic.cards.Card;
import org.abno.logic.strategies.*;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class AttackCommand implements Command {
    public static final String COMMAND_NAME = "/ATTACK";

    private final Map<String, Player> players;

    public AttackCommand(Map<String, Player> players) {
        this.players = players;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, Player attacker) {
        PrintWriter writer = new PrintWriter(out, true);

        // Comprobamos que el comando esté correctamente formado
        if (args.length < 4) {
            writer.println("Error: Uso incorrecto. /ATTACK <target> <cardName> <weapon> [strategy]");
            return;
        }

        String targetName = args[1];
        String cardName = args[2];
        String weaponName = args[3];

        Player target = players.get(targetName);

        if (target == null) {
            writer.println("Error: Jugador objetivo no encontrado.");
            return;
        }

        Card card = attacker.getSpecificCard(cardName);

        if (card == null) {
            writer.println("Error: Carta no encontrada en tu mazo.");
            return;
        }

        Weapon weapon = card.getSpecificWeapon(weaponName);
        if (weapon == null) {
            writer.println("Error: El arma ya ha sido usada.");
            return;
        }

        if (args.length > 4) {
            Random random = new Random();
            String strategyName = args[4];
            System.out.println(strategyName);

                if (Objects.equals(strategyName, "RANDOMDUPLEX")) {
                    Weapon[] ws = attacker.getSpecificCard(cardName).getWeapons();
                    for (Weapon w : ws) {
                        int index = random.nextInt(10);
                        w.getArray()[index] = w.getArray()[index] * 2;
                    }

                    attacker.attack(target, card, weaponName);
                    writer.println("Has atacado a " + targetName + " con la estrategia RANDOMDUPLEX.");

                } else if (Objects.equals(strategyName, "RANDOMCOMBINATION")) {

                    Weapon selectedWeapon = attacker.getSpecificCard(cardName).getSpecificWeapon(weaponName);
                    if (selectedWeapon == null) {
                        writer.println("Error: El arma seleccionada no existe.");
                        return;
                    }


                    List<Card> otherCards = attacker.getCards();
                    otherCards.removeIf(card_ -> card_.getName().equals(cardName));

                    if (otherCards.isEmpty()) {
                        writer.println("Error: No hay otras cartas para combinar.");
                        return;
                    }


                    Card randomCard = otherCards.get(random.nextInt(otherCards.size()));
                    Weapon randomWeapon = randomCard.getWeapons()[random.nextInt(randomCard.getWeapons().length)];


                    int[] selectedArray = selectedWeapon.getArray();
                    int[] randomArray = randomWeapon.getArray();

                    for (int i = 0; i < selectedArray.length; i++) {
                        selectedArray[i] = Math.max(selectedArray[i], randomArray[i]);
                    }


                    attacker.attack(target, card, weaponName);
                    writer.println("Has atacado a " + targetName + " con la estrategia RANDOMCOMBINATION.");
                } else if (strategyName.equals("BESTCOMBINATION")) {
                    // Obtener todas las cartas del atacante
                    List<Card> cards = attacker.getCards();

                    if (cards.size() < 4) {
                        writer.println("Error: El jugador no tiene suficientes cartas para aplicar BESTCOMBINATION.");
                        return;
                    }

                    // Inicializar un arreglo para almacenar los mejores valores
                    int[] bestCombination = new int[10]; // Asumiendo que hay 10 tipos de ataque

                    // Iterar sobre las cartas del jugador
                    for (Card cardd : cards) {
                        for (Weapon weaponn : cardd.getWeapons()) {
                            int[] weaponArray = weaponn.getArray();

                            // Comparar los valores actuales con los valores del mejor arreglo
                            for (int i = 0; i < weaponArray.length; i++) {
                                bestCombination[i] = Math.max(bestCombination[i], weaponArray[i]);
                            }
                        }
                    }

                    // Obtener la carta y el arma seleccionada para el ataque
                    Card selectedCard = attacker.getSpecificCard(cardName);
                    Weapon selectedWeapon = selectedCard.getSpecificWeapon(weaponName);

                    if (selectedWeapon == null) {
                        writer.println("Error: El arma seleccionada no existe.");
                        return;
                    }

                    // Modificar el arma seleccionada con los valores de la mejor combinación
                    System.arraycopy(bestCombination, 0, selectedWeapon.getArray(), 0, bestCombination.length);

                    // Realizar el ataque
                    attacker.attack(target, selectedCard, weaponName);
                    writer.println("Has atacado a " + targetName + " con la estrategia BESTCOMBINATION.");
                }

        } else {
                writer.println("Error: Estrategia no válida.");
                return;
        }



        // Realizamos el ataque
        attacker.attack(target, card, weaponName);
        writer.println("Has atacado a " + targetName + " con " + cardName + " usando el arma " + weaponName + ".");
    }

    // Método para obtener la estrategia según el nombre
    private IStrategy getStrategyByName(String strategyName) {
        switch (strategyName) {
            case "AVERAGE":
                return new Average();
            case "BESTCOMBINATION":
                return new BestCombination();
            case "OPTIMAL":
                return new Optimal();
            case "RANDOMDUPLEX":
                return new RandomDuplex();
            case "RANDOMCOMBINATION":
                return new RandomCombination();
            default:
                return null;
        }
    }


    private int[][] getFightersStats() {
        // Implementa la lógica para obtener las estadísticas de los luchadores, algo como:
        // return new int[][]{fighter1Stats, fighter2Stats, fighter3Stats, ...};
        return new int[0][0]; // Este es un ejemplo, debes modificar esto para obtener las estadísticas reales.
    }
}
