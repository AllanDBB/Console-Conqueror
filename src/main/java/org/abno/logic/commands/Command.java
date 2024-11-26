package org.abno.logic.commands;

import org.abno.logic.Player;

import java.io.OutputStream;
import java.io.PrintWriter;

public interface Command {
    public String getCommandName();
    public void execute(String[] args, OutputStream out, Player player);
}
