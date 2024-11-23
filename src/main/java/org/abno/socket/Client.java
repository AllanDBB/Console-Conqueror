package org.abno.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.abno.gui.init.InitFrame;

public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8060;

    private static BufferedReader reader;
    private static PrintWriter writer;

    private static InitFrame initFrame;

    public static BufferedReader getReader() { return reader; }
    public static PrintWriter getWriter() { return writer; }

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            writer = out;
            reader = in;

            initFrame = new InitFrame();
            initFrame.init();

            Thread listener = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {


                        if (response.startsWith("$")){
                            //serverCommand(response);
                        }

                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.err.println("Connection closed.");
                }
            });

            listener.start();

            String input;
            while ((input = console.readLine()) != null) {
                if ("exit".equalsIgnoreCase(input)) {
                    System.out.println("Disconnected from server.");
                    break;
                }
                out.println(input);
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("Can't connect " + e.getMessage());
        }
    }

    private void serverCommand(String response) {
        switch (response) {
            case "exit":
                System.exit(0);
        }
    }

}
