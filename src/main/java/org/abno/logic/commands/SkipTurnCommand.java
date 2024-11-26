package org.abno.logic.commands;

import org.abno.logic.player.Player;
import org.abno.socket.GameRoom;

import java.io.OutputStream;
import java.io.PrintWriter;

public class SkipTurnCommand implements Command{
    public static final String COMMAND_NAME = "/SKIPTURN";
    private final GameRoom gameRoom;

    public SkipTurnCommand(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }


    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, Player player) {
        gameRoom.advanceTurn();

        // Enviamos una notificación al jugador de que su turno fue saltado
        PrintWriter writer = new PrintWriter(out, true);
        writer.println("You have skipped your turn. It's now the next player's turn.");
    }
}
