package org.abno.logic.commands;

import org.abno.logic.player.Player;
import org.abno.socket.GameRoom;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    //singleton
    private static CommandManager commandManager;
    //hash de Commands: nombre, class que extiende Command
    private static final HashMap<String, Class<? extends Command>> COMMANDS =
            new HashMap<String, Class<? extends Command>>();
    private static final HashMap<String, Command> commandCache = new HashMap<>();
    private static GameRoom gameRoom;

    private CommandManager() {
        registCommand(AttackCommand.COMMAND_NAME, AttackCommand.class);
        registCommand(DrawCommand.COMMAND_NAME, DrawCommand.class);
        registCommand(RechargeWeaponsCommand.COMMAND_NAME, RechargeWeaponsCommand.class);
        registCommand(SelectPlayerCommand.COMMAND_NAME, SelectPlayerCommand.class);
        registCommand(SkipTurnCommand.COMMAND_NAME, SkipTurnCommand.class);
        registCommand(SurrenderCommand.COMMAND_NAME, SurrenderCommand.class);
        registCommand(WildcardCommand.COMMAND_NAME, WildcardCommand.class);
    }

    public static synchronized CommandManager getIntance() {
        if (commandManager == null) {
            commandManager = new CommandManager();
        }
        return commandManager;
    }

    public static void setGameRoom(GameRoom gameRoom) {
        CommandManager.gameRoom = gameRoom;
    }

    // obtiene un Command por nombre
    // obtien una instancia con el nombre de la clase
    public Command getCommand(String commandName, Map<String, Player> players) {
        Command command = commandCache.get(commandName.toUpperCase()); // Buscar en caché
        if (command == null) {
            command = CommandFactory.createCommand(commandName, players, gameRoom); // Crear el comando si no está en caché
            if (command != null) {
                commandCache.put(commandName.toUpperCase(), command); // Guardar el comando en caché
            }
        }
        return command; // Retorna el comando encontrado o null si no se encuentra
    }


    // para registrar un comando, nombre y clase de tipo ICommand
    public void registCommand(String commandName, Class<? extends Command> command) {
        COMMANDS.put(commandName.toUpperCase(), command);
    }


}

