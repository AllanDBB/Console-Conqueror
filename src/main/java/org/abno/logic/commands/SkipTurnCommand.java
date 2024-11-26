package org.abno.logic.commands;

import org.abno.logic.Player;

import java.io.OutputStream;

public class SkipTurnCommand implements Command{
    public static final String COMMAND_NAME = "SkipTurn";

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, Player player) {

    }
}
