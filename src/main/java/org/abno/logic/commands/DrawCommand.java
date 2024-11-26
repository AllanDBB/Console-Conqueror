package org.abno.logic.commands;

import org.abno.logic.player.Player;
import org.abno.socket.GameRoom;

import java.io.OutputStream;
import java.io.PrintWriter;

public class DrawCommand implements Command{
    public static final String COMMAND_NAME = "/DRAW";
    private final GameRoom gameRoom;

    public DrawCommand(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, Player player) {

        PrintWriter writer = new PrintWriter(out, true);
        writer.println("You requested a draw.");

        gameRoom.endGameInDraw();
    }

}
