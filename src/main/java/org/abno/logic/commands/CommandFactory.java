package org.abno.logic.commands;


import java.util.Map;
import org.abno.logic.player.Player;
import org.abno.socket.GameRoom;

public class CommandFactory {

    // Método para crear un comando basado en su nombre
    public static Command createCommand(String commandName, Map<String, Player> players, GameRoom gameRoom) {
        // Se utiliza el nombre del comando para decidir qué clase instanciar
        switch (commandName.toUpperCase()) {
            case "/ATTACK":
                return new AttackCommand(players); // Crea un AttackCommand pasando los jugadores
            case "/DRAW":
                return new DrawCommand(gameRoom); // Crea un DrawCommand (ajustar si es necesario)
            case "/RECHARGEWEAPONS":
                return new RechargeWeaponsCommand(); // Igual para otros comandos
            case "/SELECTPLAYER":
                return new SelectPlayerCommand(); // Similar para otros comandos
            case "/SKIPTURN":
                return new SkipTurnCommand(gameRoom); // Otro comando
            case "/SURRENDER":
                return new SurrenderCommand(gameRoom); // Otro comando
            case "/WILDCARD":
                return new WildcardCommand(); // Otro comando
            default:
                return null; // Si el comando no se encuentra, retorna null
        }
    }
}
