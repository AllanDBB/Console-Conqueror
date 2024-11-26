package org.abno.gui.game;

import javax.swing.*;
import java.awt.*;

public class GameFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLUE);

        // Left Panel: Ranking and Status
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1));
        leftPanel.setPreferredSize(new Dimension(250, 800));
        leftPanel.setBackground(Color.BLUE);

        JTextArea rankingArea = new JTextArea("RANKING\n\n1. Player 1 [135/20]\n2. Player 2 [140/40]\n...");
        rankingArea.setEditable(false);
        rankingArea.setForeground(Color.WHITE);
        rankingArea.setBackground(Color.BLUE);
        rankingArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        rankingArea.setMargin(new Insets(10, 10, 10, 10));
        leftPanel.add(new JScrollPane(rankingArea));

        JTextArea statusArea = new JTextArea("MY STATUS:\n\nWins: 135\nLosses: 4\nAttacks: 1954\n...");
        statusArea.setEditable(false);
        statusArea.setForeground(Color.WHITE);
        statusArea.setBackground(Color.BLUE);
        statusArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        statusArea.setMargin(new Insets(10, 10, 10, 10));
        leftPanel.add(new JScrollPane(statusArea));

        // Center Panel: Divided into Two Sections
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        centerPanel.setBackground(Color.BLUE);

        // Top Section (Attack Info)
        JPanel topCenterPanel = new JPanel(new GridLayout(4, 1));
        topCenterPanel.setBackground(Color.BLUE);

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
        bottomCenterPanel.setBackground(Color.BLUE);

        JPanel attackInfoPanel = new JPanel(new GridLayout(2, 1));
        attackInfoPanel.setBackground(Color.BLUE);

        JLabel attackLabel2 = new JLabel("You attacked Player 2 with Bruja Oscura [Magia oscura]");
        attackLabel2.setForeground(Color.WHITE);
        attackLabel2.setFont(new Font("Arial", Font.BOLD, 16));
        attackInfoPanel.add(attackLabel2);

        JLabel weaponLabel2 = new JLabel("Weapon: Hechizo");
        weaponLabel2.setForeground(Color.WHITE);
        weaponLabel2.setFont(new Font("Arial", Font.PLAIN, 14));
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
        percentageCircle.setBackground(Color.BLUE);
        bottomCenterPanel.add(percentageCircle, BorderLayout.EAST);

        centerPanel.add(bottomCenterPanel);

        // Right Panel: Cards and Stats
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(400, 800));
        rightPanel.setBackground(Color.BLUE);

        JLabel cardsLabel = new JLabel("YOUR CARDS", JLabel.CENTER);
        cardsLabel.setForeground(Color.WHITE);
        cardsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(cardsLabel, BorderLayout.NORTH);

        JPanel cardPanel = new JPanel(new GridLayout(2, 4));
        cardPanel.setBackground(Color.BLUE);
        for (int i = 1; i <= 4; i++) {
            JLabel cardImage = new JLabel(new ImageIcon("path/to/card" + i + ".png"));
            cardImage.setHorizontalAlignment(JLabel.CENTER);
            cardPanel.add(cardImage);
        }
        for (int i = 0; i < 4; i++) {
            JLabel percentageLabel = new JLabel((i + 1) * 25 + "%", JLabel.CENTER);
            percentageLabel.setForeground(Color.WHITE);
            percentageLabel.setFont(new Font("Arial", Font.BOLD, 14));
            cardPanel.add(percentageLabel);
        }
        rightPanel.add(cardPanel, BorderLayout.CENTER);

        // Bottom Panel: Input and Log
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JTextArea logArea = new JTextArea(5, 20);
        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);
        bottomPanel.add(logScroll, BorderLayout.CENTER);

        JTextField inputField = new JTextField();
        bottomPanel.add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(e -> {
            String text = inputField.getText();
            logArea.append("> " + text + "\n");
            inputField.setText("");
        });

        // Add Panels to Frame
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
