package org.abno.socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8060;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            Thread readerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
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
