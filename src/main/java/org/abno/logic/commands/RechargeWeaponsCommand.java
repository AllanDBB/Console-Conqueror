package org.abno.logic.commands;

import org.abno.logic.cards.Card;
import org.abno.logic.cards.Weapon;
import org.abno.logic.player.Player;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RechargeWeaponsCommand implements Command{
    public static final String COMMAND_NAME = "/RECHARGEWEAPONS";


    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, Player player) {
        for (Card c: player.getCards()){
            for (Weapon w: c.getWeapons()){
                w.setUsed(false);
            }
        }
        PrintWriter writer = new PrintWriter(out, true);
        writer.println("Weapons recharged.");
    }

}
