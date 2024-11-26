package org.abno.logic.commands;

import org.abno.logic.player.Player;

import java.io.OutputStream;

public interface Command {
    public String getCommandName();
    public void execute(String[] args, OutputStream out, Player player);
}
