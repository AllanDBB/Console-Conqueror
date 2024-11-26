package org.abno.socket;

import org.abno.logic.cards.Card;
import org.abno.logic.cards.TypeOfCard;
import org.abno.logic.cards.Weapon;
import org.abno.logic.commands.Command;
import org.abno.logic.commands.CommandManager;
import org.abno.logic.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class GameRoom implements Runnable {
    private final String roomId;
    private final List<Player> players = new ArrayList<>();
    private final Socket hostSocket;
    private PrintWriter hostOut;
    private BufferedReader hostIn;
    private final CommandManager commandManager;

    private int currentTurnIndex = 0;
    private boolean isTurnBased = false;

    private boolean drawRequested = false;


    public GameRoom(Socket hostSocket, String hostUsername, String hostUserId) {
        this.hostSocket = hostSocket;
        this.roomId = generateRoomId();
        this.commandManager = CommandManager.getIntance();
        CommandManager.setGameRoom(this);
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
                    handleCommand(message, hostSocket, players.get(0).getUsername());
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
        if (message.startsWith("CHAT:")) {
            String chatMessage = message.substring(5).trim();
            broadcast(senderUsername + ": " + chatMessage);
        }
    }

    private void handleCommand(String command, Socket senderSocket, String senderUsername) throws IOException {
        Player senderPlayer = findPlayerByUser(senderUsername);

        // Validar si es el turno del jugador para usar comandos
        if (isTurnBased && senderPlayer != players.get(currentTurnIndex)) {
            PrintWriter out = new PrintWriter(senderSocket.getOutputStream(), true);
            out.println("It's not your turn! You can only chat.");
            return;
        }

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
                if (findPlayerByUser(senderUsername) == players.get(0)) {
                    for (Player player : players) {
                        configureCards(player);
                    }
                    enableTurnBasedSystem();
                }
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

        Command c = commandManager.getCommand(commandName, getPlayersMap());

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
            currentTurnIndex = 0; // Reinicia al primer jugador
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

    private Map<String, Player> getPlayersMap() {
        Map<String, Player> playersMap = new HashMap<>();
        for (Player player : players) {
            playersMap.put(player.getUsername(), player);
        }

        return playersMap;
    }

    private void configureCards(Player player) throws IOException {
        // Notificar al jugador que debe configurar sus cartas
        PrintWriter playerOut = new PrintWriter(player.getSocket().getOutputStream(), true);
        playerOut.println("Es tu turno para configurar tus cartas.");

        // Crear un BufferedReader para leer entradas del jugador
        InputStreamReader playerIn = new InputStreamReader(player.getSocket().getInputStream());
        BufferedReader reader = new BufferedReader(playerIn);

        // Recorremos el ciclo para que se configuren 4 cartas
        for (int i = 1; i <= 4; i++) {
            playerOut.println("Configura tu carta " + i + ":");

            // Leer el comando del jugador
            String command = reader.readLine();

            if (command == null || command.trim().isEmpty()) {
                playerOut.println("Entrada vacía. Intenta de nuevo.");
                i--; // Si la entrada es vacía, no avanzamos a la siguiente carta
                continue;
            }

            // Split the command into parts
            String[] parts = command.split("-");

            // Validar el formato del comando
            if (parts.length != 9) {
                playerOut.println("Formato de comando inválido. El formato esperado es: CARD-TYPE-NAME-IMAGE-ARM1-ARM2-ARM3-ARM4-ARM5");
                i--; // Si el formato no es correcto, no avanzamos a la siguiente carta
                continue;
            }

            // Parse the command parts
            String cardTypeString = parts[1]; // Tipo de carta (e.g., FIRE)
            String cardName = parts[2]; // Nombre de la carta
            String image = parts[3]; // Nombre de la imagen
            Weapon[] weapons = new Weapon[5]; // Array de armas

            // Crear las armas a partir de los partes del comando
            for (int j = 0; j < 5; j++) {
                weapons[j] = new Weapon(parts[4 + j]); // ARM1, ARM2, ..., ARM5
            }

            // Convertir el string a un tipo de carta (TypeOfCard enum)
            TypeOfCard selectedType;
            try {
                selectedType = TypeOfCard.valueOf(cardTypeString.toUpperCase()); // Convierte el tipo de carta
            } catch (IllegalArgumentException e) {
                playerOut.println("Tipo de carta inválido: " + cardTypeString);
                i--; // Si el tipo de carta no es válido, no avanzamos a la siguiente carta
                continue;
            }

            // Crear la carta y añadirla a las cartas del jugador
            Card newCard = new Card(selectedType, cardName, image, weapons);

            // Obtener la lista de cartas del jugador y añadir la nueva carta
            List<Card> playerCards = player.getCards();
            playerCards.add(newCard); // Añadir la nueva carta a la lista de cartas


            // Mensaje de confirmación
            playerOut.println("Carta configurada exitosamente: " + newCard.getName());
        }
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void endGameInDraw() {
        // Lógica para finalizar el juego por empate
        broadcast("The game has ended in draw!");
        // Realiza cualquier limpieza necesaria o finalización del juego
    }

    public void endGameSurrender(){
        broadcast("The game has ended because everyone else surrendered!");
        broadcast(players.getFirst().getUsername()+ " won!");
    }


}
