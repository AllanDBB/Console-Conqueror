package org.abno.logic.commands;

import org.abno.logic.Player;
import org.abno.logic.Card;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public class AttackCommand implements Command {
    public static final String COMMAND_NAME = "Attack";

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

        // Verifica que los argumentos son suficientes
        if (args.length < 3) {
            writer.println("Error: Uso incorrecto. @Attack <target> <cardName> <weaponIndex>");
            return;
        }

        String targetName = args[0];
        String cardName = args[1];
        int weaponIndex;

        try {
            weaponIndex = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            writer.println("Error: El índice del arma debe ser un número.");
            return;
        }

        // Busca al jugador objetivo
        Player target = players.get(targetName);

        if (target == null) {
            writer.println("Error: Jugador objetivo no encontrado.");
            return;
        }

        // Busca la carta del atacante
        Card warrior = null;
        for (int i = 1; i <= 4; i++) {
            Card card = attacker.getCard(i);
            if (card != null && card.getName().equalsIgnoreCase(cardName)) {
                warrior = card;
                break;
            }
        }

        if (warrior == null) {
            writer.println("Error: Carta no encontrada en tu mazo.");
            return;
        }

        // Realiza el ataque
        if (warrior.isUsed(weaponIndex)) {
            writer.println("Error: El arma ya ha sido usada.");
            return;
        }

        attacker.attack(target, warrior, weaponIndex);
        writer.println("Has atacado a " + targetName + " con " + cardName + " usando el arma " + weaponIndex + ".");
    }
}
