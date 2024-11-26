package org.abno.logic.commands;

import org.abno.logic.player.Player;

import java.io.OutputStream;

public class RechargeWeaponsCommand implements Command{
    public static final String COMMAND_NAME = "RechargeWeapons";


    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, Player player) {
        player.getCard(1).setUsed(1,false);
        player.getCard(1).setUsed(2,false);
        player.getCard(1).setUsed(3,false);
        player.getCard(1).setUsed(4,false);

        player.getCard(2).setUsed(1,false);
        player.getCard(2).setUsed(2,false);
        player.getCard(2).setUsed(3,false);
        player.getCard(2).setUsed(4,false);

        player.getCard(3).setUsed(1,false);
        player.getCard(3).setUsed(2,false);
        player.getCard(3).setUsed(3,false);
        player.getCard(3).setUsed(4,false);

        player.getCard(4).setUsed(1,false);
        player.getCard(4).setUsed(2,false);
        player.getCard(4).setUsed(3,false);
        player.getCard(4).setUsed(4,false);
    }

}
