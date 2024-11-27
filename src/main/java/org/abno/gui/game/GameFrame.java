package org.abno.gui.game;

import javax.swing.*;
import java.awt.*;
import org.abno.socket.Client;

public class GameFrame {
    private JTextArea chatArea; // Para actualizar el área de chat
    private JTextArea logArea; // Para el área de log

    public GameFrame() {
        // Constructor
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame();
            gameFrame.init();
        });
    }

    public void init() {
        JFrame frame = new JFrame("Game Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1366, 768); // Tamaño ajustado
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 30)); // Color de fondo oscuro

        // Paneles y componentes
        JPanel leftPanel = createLeftPanel();
        frame.add(leftPanel, BorderLayout.WEST);
        JPanel centerPanel = createCenterPanel();
        frame.add(centerPanel, BorderLayout.CENTER);
        JPanel rightPanel = createRightPanel();
        frame.add(rightPanel, BorderLayout.EAST);
        JPanel bottomPanel = createBottomPanel();
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Panel de chat en la parte derecha
        JPanel chatPanel = createChatPanel();
        frame.add(chatPanel, BorderLayout.EAST); // Añadir el panel de chat en la parte derecha

        frame.setVisible(true);
    }

    private JPanel createChatPanel() {
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setPreferredSize(new Dimension(300, 800));
        chatPanel.setBackground(new Color(50, 50, 50));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setForeground(Color.WHITE);
        chatArea.setBackground(new Color(40, 40, 40));
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        chatArea.setMargin(new Insets(10, 10, 10, 10));
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JTextField chatInputField = new JTextField();
        chatInputField.setBackground(new Color(60, 60, 60));
        chatInputField.setForeground(Color.WHITE);
        chatInputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        chatInputField.addActionListener(e -> {
            String message = chatInputField.getText();
            chatArea.append("You: " + message + "\n");
            chatInputField.setText("");
            Client.send(message); // Enviar el mensaje al servidor
        });
        chatPanel.add(chatInputField, BorderLayout.SOUTH);

        return chatPanel;
    }

    public void receiveMessage(String message) {
        chatArea.append(message + "\n"); // Actualizar el área de chat con el mensaje recibido
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(30, 30, 30)); // Color de fondo del panel inferior

        logArea = new JTextArea(5, 20);
        logArea.setEditable(false);
        logArea.setForeground(Color.WHITE);
        logArea.setBackground(new Color(40, 40, 40));
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane logScroll = new JScrollPane(logArea);
        bottomPanel.add(logScroll, BorderLayout.CENTER);

        JTextField inputField = new JTextField();
        inputField.setBackground(new Color(60, 60, 60));
        inputField.setForeground(Color.WHITE);
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        bottomPanel.add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(e -> {
            String text = inputField.getText();
            logArea.append("> " + text + "\n");
            inputField.setText("");

            System.out.println(text);
            Client.send(text); // Llamar al método send de Client
        });

        return bottomPanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1));
        leftPanel.setPreferredSize(new Dimension(250, 800));
        leftPanel.setBackground(new Color(30, 30, 30)); // Color de fondo del panel izquierdo

        JTextArea rankingArea = new JTextArea("RANKING\n\nAllan y Nati en 0 y 0");
        rankingArea.setEditable(false);
        rankingArea.setForeground(Color.WHITE);
        rankingArea.setBackground(new Color(30, 30, 30));
        rankingArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        rankingArea.setMargin(new Insets(10, 10, 10, 10));
        leftPanel.add(new JScrollPane(rankingArea));

        JTextArea statusArea = new JTextArea("MY STATUS:\n\nWins: 0\nLosses: 0\nAttacks: 0\nCorrect: 0\nFailed: 0");
        statusArea.setEditable(false);
        statusArea.setForeground(Color.WHITE);
        statusArea.setBackground(new Color(30, 30, 30));
        statusArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        statusArea.setMargin(new Insets(10, 10, 10, 10));
        leftPanel.add(new JScrollPane(statusArea));

        return leftPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(2, 2));
        centerPanel.setBackground(new Color(30, 30, 30)); // Color de fondo del panel central

        // Crear cuadros verdes
        for (int i = 0; i < 4; i++) {
            JPanel cardBox = new JPanel();
            cardBox.setBackground(new Color(60, 80, 70));
            cardBox.setPreferredSize(new Dimension(200, 200));
            centerPanel.add(cardBox);
        }

        return centerPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(400, 800));
        rightPanel.setBackground(new Color(30, 30, 30)); // Color de fondo del panel derecho

        JLabel cardsLabel = new JLabel("YOUR CARDS", JLabel.CENTER);
        cardsLabel.setForeground(Color.WHITE);
        cardsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(cardsLabel, BorderLayout.NORTH);

        JPanel cardPanel = new JPanel(new GridLayout(2, 2));
        cardPanel.setBackground(new Color(30, 30, 30));

        for (int i = 0; i < 4; i++) {
            JPanel cardBox = new JPanel();
            cardBox.setBackground(Color.GREEN);
            cardBox.setPreferredSize(new Dimension(150, 200));
            cardPanel.add(cardBox);
        }

        rightPanel.add(cardPanel, BorderLayout.CENTER);
        return rightPanel;
    }
}
