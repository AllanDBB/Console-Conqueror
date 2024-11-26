package org.abno.logic.commands;

import org.abno.logic.cards.Weapon;
import org.abno.logic.player.Player;
import org.abno.logic.cards.Card;

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


        if (args.length < 3) {
            writer.println("Error: Uso incorrecto. @Attack <target> <cardName> <weapon>");
            return;
        }

        String targetName = args[0];
        String cardName = args[1];
        String weaponName = args[2];


        Player target = players.get(targetName);

        if (target == null) {
            writer.println("Error: Jugador objetivo no encontrado.");
            return;
        }


        Card card = attacker.getSpecificCard(cardName);
        if (card == null){
            writer.println("Error: Carta no encontrada en tu mazo.");
            return;
        }

        Weapon weapon = card.getSpecificWeapon(weaponName);
        if (weapon == null) {
            writer.println("Error: El arma ya ha sido usada.");
            return;
        }

        attacker.attack(target, card, weaponName);
        writer.println("Has atacado a " + targetName + " con " + cardName + " usando el arma " + weaponName + ".");
    }
}
