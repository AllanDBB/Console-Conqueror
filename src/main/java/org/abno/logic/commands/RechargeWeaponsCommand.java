package org.abno.logic.commands;

import org.abno.logic.Player;

import java.io.OutputStream;
import java.io.PrintWriter;

public class RechargeWeaponsCommand implements Command{
    public static final String COMMAND_NAME = "Recharge Weapons";


    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, Player player) {

    }

}
