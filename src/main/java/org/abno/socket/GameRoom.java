package org.abno.socket;

import org.abno.logic.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class GameRoom implements Runnable {
    private final String roomId;
    private final List<Player> players = new ArrayList<>();
    private final Socket hostSocket;
    private PrintWriter hostOut;
    private BufferedReader hostIn;

    public GameRoom(Socket hostSocket, String hostUsername, String hostUserId) {
        this.hostSocket = hostSocket;
        this.roomId = generateRoomId();
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

    void handleMessage(String message, Socket senderSocket, String senderUsername) {
        if (message.startsWith("CHAT:")) {
            String chatMessage = message.substring(5).trim();
            broadcast(senderUsername + ": " + chatMessage);
        } else if (message.startsWith("COMMAND:")) {
            String command = message.substring(8).trim();
            processCommand(command, senderSocket, senderUsername);
        }
    }


    private void processCommand(String command, Socket senderSocket, String senderUsername) {
        switch (command) {
            case "/exit":
                disconnect();
                break;
            case "/say Hello":
                broadcast(senderUsername + " says: Hello");
                break;
            default:
                broadcast("Unknown command: " + command);
        }
    }

    // Método para agregar jugadores a la sala
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
}
