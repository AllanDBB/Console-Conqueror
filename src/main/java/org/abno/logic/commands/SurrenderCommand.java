package org.abno.logic.commands;

import org.abno.logic.player.Player;
import org.abno.socket.GameRoom;

import java.io.OutputStream;
import java.io.PrintWriter;

public class SurrenderCommand implements Command{
    public static final String COMMAND_NAME = "/SURRENDER";
    private final GameRoom gameRoom;

    public SurrenderCommand(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }


    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(String[] args, OutputStream out, Player player) {
        player.getStats()[5] +=1;
        gameRoom.getPlayers().remove(player);

        PrintWriter writer = new PrintWriter(out, true);
        writer.println("You have surrendered.");

        if (gameRoom.getPlayers().size() <= 1){
            gameRoom.endGameSurrender();
        }
    }

}
