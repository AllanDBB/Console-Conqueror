package org.abno.gui.game;

import javax.swing.*;
import java.awt.*;
import org.abno.socket.Client; // Importar Client

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
        chatPanel.setPreferredSize(new Dimension(300, 800)); // Ajustar el tamaño del panel de chat
        chatPanel.setBackground(new Color(50, 50, 50)); // Color de fondo del panel de chat

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setForeground(Color.WHITE);
        chatArea.setBackground(new Color(40, 40, 40)); // Color de fondo del área de chat
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Aumentar el tamaño de la fuente
        chatArea.setMargin(new Insets(10, 10, 10, 10));
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JTextField chatInputField = new JTextField();
        chatInputField.setBackground(new Color(60, 60, 60)); // Color de fondo del campo de entrada
        chatInputField.setForeground(Color.WHITE);
        chatInputField.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Aumentar el tamaño de la fuente
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
        logArea.setBackground(new Color(40, 40, 40)); // Color de fondo del área de log
        logArea.setFont(new Font(" Monospaced", Font.PLAIN, 14)); // Aumentar el tamaño de la fuente
        JScrollPane logScroll = new JScrollPane(logArea);
        bottomPanel.add(logScroll, BorderLayout.CENTER);

        JTextField inputField = new JTextField();
        inputField.setBackground(new Color(60, 60, 60)); // Color de fondo del campo de entrada
        inputField.setForeground(Color.WHITE);
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Aumentar el tamaño de la fuente
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

        JTextArea rankingArea = new JTextArea("RANKING\n\n1. Player 1 [135/20]\n2. Player 2 [140/40]\n...");
        rankingArea.setEditable(false);
        rankingArea.setForeground(Color.WHITE);
        rankingArea.setBackground(new Color(30, 30, 30)); // Color de fondo del área de ranking
        rankingArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        rankingArea.setMargin(new Insets(10, 10, 10, 10));
        leftPanel.add(new JScrollPane(rankingArea));

        JTextArea statusArea = new JTextArea("MY STATUS:\n\nWins: 135\nLosses: 4\nAttacks: 1954\n...");
        statusArea.setEditable(false);
        statusArea.setForeground(Color.WHITE);
        statusArea.setBackground(new Color(30, 30, 30)); // Color de fondo del área de estado
        statusArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        statusArea.setMargin(new Insets(10, 10, 10, 10));
        leftPanel.add(new JScrollPane(statusArea));

        return leftPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        centerPanel.setBackground(new Color(30, 30, 30)); // Color de fondo del panel central

        // Top Section (Attack Info)
        JPanel topCenterPanel = new JPanel(new GridLayout(4, 1));
        topCenterPanel.setBackground(new Color(30, 30, 30)); // Color de fondo del panel superior

        JLabel attackLabel1 = new JLabel("Attacked by Player 1 with DRAGON [ICE]");
        attackLabel1.setForeground(Color.WHITE);
        attackLabel1.setFont(new Font("Arial", Font.BOLD, 16));
        topCenterPanel.add(attackLabel1);

        JLabel weaponLabel1 = new JLabel("Weapon: Ice Shoot");
        weaponLabel1.setForeground(Color.WHITE);
        weaponLabel1.setFont(new Font("Arial", Font.PLAIN, 14));
        topCenterPanel.add(weaponLabel1);

        JLabel statsLabel1 = new JLabel("Bruja Oscura: 0%  |  Dragón Blanco: -71%  |  Tayler: 0%  |  Chaos King: -22%");
        statsLabel1.setForeground(Color.WHITE);
        statsLabel1.setFont(new Font("Arial", Font.PLAIN, 14));
        topCenterPanel.add(statsLabel1);

        centerPanel.add(topCenterPanel);

        // Bottom Section (Your Attack Info)
        JPanel bottomCenterPanel = new JPanel(new BorderLayout());
        bottomCenterPanel.setBackground(new Color(30, 30, 30)); // Color de fondo del panel inferior

        JPanel attackInfoPanel = new JPanel(new GridLayout(2, 1));
        attackInfoPanel.setBackground(new Color(30, 30, 30)); // Color de fondo del panel de información de ataque

        JLabel attackLabel2 = new JLabel("You attacked Player 2 with Bruja Oscura [Magia oscura]");
        attackLabel2.setForeground(Color.WHITE);
        attackLabel2.setFont(new Font("Arial", Font.BOLD, 16));
        attackInfoPanel.add(attackLabel2);

        JLabel weaponLabel2 = new JLabel("Weapon: Hechizo");
        weaponLabel2.setForeground(Color.WHITE);
        weaponLabel2.setFont(new Font("Arial", Font .PLAIN, 14));
        attackInfoPanel.add(weaponLabel2);

        bottomCenterPanel.add(attackInfoPanel, BorderLayout.CENTER);

        JPanel percentageCircle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(128, 0, 64));
                g.fillOval(10, 10, 100, 100);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("-127%", 30, 65);
            }
        };
        percentageCircle.setPreferredSize(new Dimension(120, 120));
        percentageCircle.setBackground(new Color(30, 30, 30)); // Color de fondo del círculo
        bottomCenterPanel.add(percentageCircle, BorderLayout.EAST);

        centerPanel.add(bottomCenterPanel);
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

        JPanel cardPanel = new JPanel(new GridLayout(2, 2)); // Cambiado a GridLayout(2, 2) para mostrar 4 cartas
        cardPanel.setBackground(new Color(30, 30, 30)); // Color de fondo del panel de cartas

        // Rutas de las imágenes de las cartas
        String[] cardPaths = {
                "C:\\Users\\adbyb\\OneDrive\\Documentos\\GitHub\\ConsoleConqueror\\Console Conqueror\\src\\main\\resources\\card1.png",
                "C:\\Users\\adbyb\\OneDrive\\Documentos\\GitHub\\ConsoleConqueror\\Console Conqueror\\src\\main\\resources\\card2.png",
                "C:\\Users\\adbyb\\OneDrive\\Documentos\\GitHub\\ConsoleConqueror\\Console Conqueror\\src\\main\\resources\\card3.png",
                "C:\\Users\\adbyb\\OneDrive\\Documentos\\GitHub\\ConsoleConqueror\\Console Conqueror\\src\\main\\resources\\card4.png"
        };

        for (String path : cardPaths) {
            try {
                ImageIcon cardImage = new ImageIcon(path);
                JLabel cardLabel = new JLabel(cardImage);
                cardLabel.setHorizontalAlignment(JLabel.CENTER);
                cardLabel.setPreferredSize(new Dimension(150, 200)); // Tamaño de las cartas
                cardPanel.add(cardLabel);
            } catch (Exception e) {
                System.err.println("Error loading image: " + path);
                e.printStackTrace();
            }
        }

        rightPanel.add(cardPanel, BorderLayout.CENTER);
        return rightPanel;
    }
}