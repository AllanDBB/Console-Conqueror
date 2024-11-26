package org.abno.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private String userId;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.userId = UUID.randomUUID().toString();
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter your username:");
            username = in.readLine();

            if (username == null || username.isEmpty()) {
                out.println("Invalid username. Connection closing.");
                socket.close();
                return;
            }

            out.println("Enter room ID to join or type 'create' to create a new room:");
            String roomId = in.readLine();

            if ("create".equalsIgnoreCase(roomId)) {
                GameRoom gameRoom = new GameRoom(socket, username, userId);
                Server.gameRooms.add(gameRoom);
                out.println("You are the host. Room created with ID: " + gameRoom.getRoomId());
                new Thread(gameRoom).start();
            } else {
                GameRoom gameRoom = Server.getGameRoom(roomId);
                if (gameRoom != null) {
                    gameRoom.addPlayer(socket, username, userId);
                    out.println("You joined the room: " + roomId);
                    new Thread(() -> handlePlayerMessages(gameRoom)).start(); // Hilo para manejar mensajes del jugador
                } else {
                    out.println("Room not found.");
                }
            }
        } catch (IOException e) {
            System.err.println ("Error handling client: " + e.getMessage());
        }
    }

    private void handlePlayerMessages(GameRoom gameRoom) {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/")) {
                    gameRoom.handleMessage("COMMAND:" + message, socket, username); // Enviar comando
                } else {
                    gameRoom.handleMessage("CHAT:" + message, socket, username); // Enviar mensaje
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from player: " + e.getMessage());
        }
    }
}