package org.abno.gui.lobby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyFrame extends JFrame {

    private JTextField nameField; // Campo para ingresar el nombre
    private JButton readyButton; // Botón para marcar como listo
    private JCheckBox readyCheckBox; // Checkbox para indicar que está listo

    public LobbyFrame() {
        setTitle("Lobby");
        setSize(800, 600); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        // Crear un panel con fondo blanco
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Usar GridBagLayout para organizar los componentes
        panel.setBackground(Color.WHITE); // Fondo blanco
        add(panel); // Agregar el panel al JFrame

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        nameField.setBackground(Color.WHITE); // Fondo del campo de texto
        nameField.setForeground(Color.BLACK); // Texto negro
        nameField.setCaretColor(Color.BLACK); // Color del cursor
        nameField.setText("Enter your name: "); // Texto de marcador de posición
        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (nameField.getText().equals("Enter your name: ")) {
                    nameField.setText("");
                    nameField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (nameField.getText().isEmpty()) {
                    nameField.setForeground(Color.GRAY);
                    nameField.setText("Enter your name: ");
                }
            }
        });
        gbc.gridy = 1; // Fila 1
        panel.add(nameField, gbc);

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 2; // Fila 2
        gbc.gridwidth = 1; // Ocupa una columna

        // Botón para marcar como listo
        readyButton = new JButton("Go to the game!");
        readyButton.setFont(new Font("Arial", Font.BOLD, 16));
        readyButton.setBackground(new Color(46, 46, 46)); // Fondo del botón
        readyButton.setForeground(Color.WHITE); // Texto blanco
        readyButton.setBorder(BorderFactory.createRaisedBevelBorder()); // Añadir borde elevado
        readyButton.setFocusPainted(false); // Quitar el borde de enfoque
        readyButton.setContentAreaFilled(false); // Hacer que el área del botón sea transparente
        readyButton.setOpaque(true); // Hacer que el botón sea opaco
        readyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                readyButton.setBackground(new Color(51, 52, 54)); // Color al pasar el mouse
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                readyButton.setBackground(new Color(32, 32, 32)); // Color original
            }
        });
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (readyCheckBox.isSelected()) {
                    JOptionPane.showMessageDialog(LobbyFrame.this, "You are ready!", "Status", JOptionPane.INFORMATION_MESSAGE);
                    readyButton.setBackground(new Color(0, 128, 0)); // Verde para indicar listo
                    readyButton.setText("Ready!"); // Cambiar el texto del botón
                } else {
                    JOptionPane.showMessageDialog(LobbyFrame.this, "You need to be marked as ready.", "Status", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        gbc.gridx = 1;
        panel.add(readyButton, gbc);
    }

    public static void init() {
        SwingUtilities.invokeLater(() -> {
            LobbyFrame lobbyFrame = new LobbyFrame();
            lobbyFrame.setVisible(true);
        });
    }
}