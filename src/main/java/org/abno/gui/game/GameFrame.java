package org.abno.gui.game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        // Configurar la ventana
        setTitle("Game Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Ajusta el tamaño según necesites
        setLocationRelativeTo(null); // Centra la ventana
        setLayout(new BorderLayout());

        // Crear los paneles
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 1)); // 3 filas, 1 columna para la parte superior

        JPanel panel1 = createPanel("Panel 1", Color.RED); // Panel del 1/6
        JPanel panel2 = createPanel("Panel 2", Color.GREEN); // Panel del 2/6
        JPanel panel3 = createPanel("Panel 3", Color.BLUE); // Panel del resto

        // Agregar los paneles a la parte superior
        topPanel.add(panel1);
        topPanel.add(panel2);
        topPanel.add(panel3);

        // Crear el panel de chat para la parte inferior (1/4 de la altura)
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatPanel.add(scrollPane, BorderLayout.CENTER);

        // Agregar los paneles a la ventana principal
        add(topPanel, BorderLayout.NORTH); // Parte superior
        add(chatPanel, BorderLayout.SOUTH); // Parte inferior (chat)

        setVisible(true);
    }

    // Método para crear paneles con título y color
    private JPanel createPanel(String title, Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
