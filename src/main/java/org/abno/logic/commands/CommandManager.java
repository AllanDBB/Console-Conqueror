package org.abno.logic.commands;

import java.util.HashMap;

public class CommandManager {
    //singleton
    private static CommandManager commandManager;
    //hash de Commands: nombre, class que extiende Command
    private static final HashMap<String, Class<? extends Command>> COMMANDS =
            new HashMap<String, Class<? extends Command>>();

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

    // obtiene un Command por nombre
    // obtien una instancia con el nombre de la clase
    public Command getCommand(String commandName) {
        if (COMMANDS.containsKey(commandName.toUpperCase())) {
            try {
                //retorna nueva isntancia de comando solicitado
                return COMMANDS.get(commandName.toUpperCase()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                //retorna comando de error en la exception
                //return new ErrorCommand();
                return null;
            }
        }
        else {
            // retorno de error comando no encontrado
            //return new NotFoundCommand();
            return null;
        }
    }

    // para registrar un comando, nombre y clase de tipo ICommand
    public void registCommand(String commandName, Class<? extends Command> command) {
        COMMANDS.put(commandName.toUpperCase(), command);
    }
}

