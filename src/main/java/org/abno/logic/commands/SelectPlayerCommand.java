package org.abno.logic.commands;

import org.abno.logic.player.Player;

import java.io.OutputStream;

public class SelectPlayerCommand implements Command{
    public static final String COMMAND_NAME = "/SELECTPLAYER";


    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, Player player) {

    }
}
