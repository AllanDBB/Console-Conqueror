package org.abno.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8060; // Cambia esto si el servidor tiene un puerto diferente

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor. Ingresa comandos:");

            // Hilo para escuchar respuestas del servidor
            Thread listener = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.err.println("Conexión con el servidor cerrada.");
                }
            });

            listener.start();

            // Leer entrada del usuario y enviarla al servidor
            String input;
            while ((input = console.readLine()) != null) {
                if ("exit".equalsIgnoreCase(input)) { // Permitir salir del cliente
                    System.out.println("Desconectando del servidor...");
                    break;
                }
                out.println(input);
            }

            // Cerrar el socket después de salir
            socket.close();
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor: " + e.getMessage());
        }
    }
}
