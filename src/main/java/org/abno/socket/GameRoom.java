package org.abno.socket;

import org.abno.logic.commands.Command;
import org.abno.logic.commands.CommandManager;
import org.abno.logic.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameRoom implements Runnable {
    private final String roomId;
    private final List<Player> players = new ArrayList<>();
    private final Socket hostSocket;
    private PrintWriter hostOut;
    private BufferedReader hostIn;
    private final CommandManager commandManager;

    private int currentTurnIndex = 0;
    private boolean isTurnBased = false;

    public GameRoom(Socket hostSocket, String hostUsername, String hostUserId) {
        this.hostSocket = hostSocket;
        this.roomId = generateRoomId();
        this.commandManager = CommandManager.getIntance();
        players.add(new Player(hostSocket, hostUsername, hostUserId));
    }

    @Override
    public void run() {
        try {
            // Manejo de la entrada del host
            hostIn = new BufferedReader(new InputStreamReader(hostSocket.getInputStream()));
            hostOut = new PrintWriter(hostSocket.getOutputStream(), true);

            hostOut.println("Welcome to the game! You are the host.");
            broadcast("Host has created the game room: " + roomId);

            String message;
            while ((message = hostIn.readLine()) != null) {
                // Verificar si el mensaje es un comando o un mensaje de chat
                if (message.startsWith("/")) {
                    // Procesar como comando
                    handleMessage("COMMAND:" + message, hostSocket, players.get(0).getUsername());
                } else {
                    // Procesar como mensaje de chat
                    handleMessage("CHAT:" + message, hostSocket, players.get(0).getUsername());
                }
            }
        } catch (IOException e) {
            System.err.println("Error in game room: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    void handleMessage(String message, Socket senderSocket, String senderUsername) throws IOException {
        Player senderPlayer = findPlayerByUser(senderUsername);

        if (isTurnBased && senderPlayer != players.get(currentTurnIndex)) {
            PrintWriter out = new PrintWriter(senderSocket.getOutputStream(), true);
            out.println("It's not your turn!");
            return;
        }

        if (message.startsWith("CHAT:")) {
            String chatMessage = message.substring(5).trim();
            broadcast(senderUsername + ": " + chatMessage);
        } else if (message.startsWith("COMMAND:")) {
            String command = message.substring(8).trim();
            processCommand(command, senderSocket, senderUsername);
        }

        if (isTurnBased) {
            advanceTurn();
        }
    }

    private void processCommand(String command, Socket senderSocket, String senderUsername) throws IOException {
        String[] commandParts = command.split(" ");
        String commandName = commandParts[0];

        switch (commandName) {
            case "/exit":
                disconnect();
                return;
            case "/say":
                if (commandParts.length > 1) {
                    String message = String.join(" ", Arrays.copyOfRange(commandParts, 1, commandParts.length));
                    broadcast(senderUsername + " says: " + message);
                } else {
                    broadcast(senderUsername + " tried to say something, but it was empty.");
                }
                return;
            case "/start":
                if (findPlayerByUser(senderUsername) == players.get(0)){
                    enableTurnBasedSystem();}
                return;
            case "/endTurn":
                if (isTurnBased && senderUsername.equals(players.get(currentTurnIndex).getUsername())) {
                    advanceTurn();
                } else {
                    PrintWriter out = new PrintWriter(senderSocket.getOutputStream(), true);
                    out.println("You can't end the turn because it's not your turn!");
                }
                return;
        }

        Command c = commandManager.getCommand(commandName);
        if (c != null) {
            Player p = findPlayerByUser(senderUsername);
            if (p != null) {
                c.execute(commandParts, senderSocket.getOutputStream(), p);
            }
        } else {
            broadcast("Unknown command: " + commandName);
        }
    }

    public void addPlayer(Socket playerSocket, String playerUsername, String playerUserId) {
        players.add(new Player(playerSocket, playerUsername, playerUserId));
        broadcast(playerUsername + " has joined the game room.");
    }

    public void broadcast(String message) {
        players.forEach(player -> {
            try {
                PrintWriter playerOut = new PrintWriter(player.getSocket().getOutputStream(), true);
                playerOut.println(message);
            } catch (IOException e) {
                System.err.println("Error sending message to player: " + e.getMessage());
            }
        });
    }

    private String generateRoomId() {
        return "ROOM-" + System.currentTimeMillis();
    }

    private void closeResources() {
        try {
            if (hostIn != null) hostIn.close();
            if (hostOut != null) hostOut.close();
            if (hostSocket != null && !hostSocket.isClosed()) hostSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

    public void disconnect() {
        broadcast("The host has left the game room: " + roomId);
        closeResources();
    }

    public String getRoomId() {
        return roomId;
    }

    public Player findPlayerByUser(String username) {
        for (Player p : players) {
            if (p.getUsername().equals(username)) {
                return p;
            }
        }
        return null;
    }

    // Métodos para manejo de turnos
    public void enableTurnBasedSystem() {
        if (players.size() > 1) {
            isTurnBased = true;
            notifyCurrentPlayerTurn();
        } else {
            broadcast("Not enough players to enable turn-based system.");
        }
    }

    private void notifyCurrentPlayerTurn() {
        if (!isTurnBased) return;

        Player currentPlayer = players.get(currentTurnIndex);
        try {
            PrintWriter out = new PrintWriter(currentPlayer.getSocket().getOutputStream(), true);
            out.println("It's your turn!");
        } catch (IOException e) {
            System.err.println("Error notifying current player: " + e.getMessage());
        }
        broadcast("It's " + currentPlayer.getUsername() + "'s turn!");
    }

    public void advanceTurn() {
        if (!isTurnBased) return;

        currentTurnIndex = (currentTurnIndex + 1) % players.size();
        notifyCurrentPlayerTurn();
    }
}
