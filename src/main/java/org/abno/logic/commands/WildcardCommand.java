package org.abno.logic.commands;

import org.abno.logic.player.Player;

import java.io.OutputStream;

public class WildcardCommand implements Command{
    public static final String COMMAND_NAME = "/WILDCARD";


    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, Player player) {

    }
}
