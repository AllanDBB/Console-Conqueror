package org.abno.socket;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.abno.Main;
import org.abno.gui.init.InitFrame;
import org.abno.gui.game.GameFrame; // Importar GameFrame

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8060;
    private static final String LOG_FILE_PATH = "chat_log.txt"; // Ruta del archivo de log
    private static InitFrame initFrame;
    private static GameFrame gameFrame; // Referencia a GameFrame
    private static BufferedReader inCopy;
    private static PrintWriter outCopy;
    private static Scanner scanner;

    public static void send(String message) {
        outCopy.println(message);
        outCopy.flush();
        logMessage(message); // Guardar el mensaje en el archivo de texto
    }

    public static void setGameFrame(GameFrame frame) {
        gameFrame = frame; // Establecer la referencia del GameFrame
    }

    private static void logMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            String timestamp = new SimpleDateFormat("dd:HH:mm").format(new Date());
            writer.write("[" + timestamp + "] " + message);
            writer.newLine(); // Nueva línea después de cada mensaje
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            outCopy = out;
            inCopy = in;

            initFrame = new InitFrame();
            initFrame.init();

            Thread readerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                        if (gameFrame != null) {
                            gameFrame.receiveMessage(serverMessage); // Enviar mensaje al GameFrame
                            logMessage(serverMessage);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading from server: " + e.getMessage());
                }
            });

            readerThread.start();

            while (true) {
                String userInput = scanner.nextLine();
                if (!userInput.isEmpty()) {
                    if (userInput.startsWith("/")) {
                        // Enviar un comando al servidor
                        out.println(userInput);
                    } else {
                        // Enviar un mensaje de chat al servidor
                        out.println(userInput);
                    }
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}