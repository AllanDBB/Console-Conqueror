package org.abno.gui.init;

import org.abno.gui.lobby.LobbyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InitFrame extends JFrame {

    LobbyFrame lobbyFrame = new LobbyFrame();

    public InitFrame() {
        setTitle("Console Conqueror - Lobby");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel titleLabel = new JLabel("Console Conqueror", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        Color titleColor = titleLabel.getForeground();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JButton startButton = new JButton("Start!");
        startButton.setFont(new Font("Arial", Font.PLAIN, 24));
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(titleColor, 4),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        startButton.setContentAreaFilled(false);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fadeButtonBackground(startButton, titleColor.brighter().brighter(), true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fadeButtonBackground(startButton, null, false);
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lobbyFrame.init();
                System.out.println("¡El juego ha comenzado!");
                dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(startButton, gbc);

        add(panel);
    }

    private void fadeButtonBackground(JButton button, Color targetColor, boolean fadeIn) {
        Timer timer = new Timer(30, null);
        timer.addActionListener(new ActionListener() {
            private float alpha = fadeIn ? 0.0f : 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (fadeIn) {
                    alpha += 0.05f;
                    if (alpha >= 1.0f) {
                        alpha = 1.0f;
                        timer.stop();
                    }
                } else {
                    alpha -= 0.05f;
                    if (alpha <= 0.0f) {
                        alpha = 0.0f;
                        button.setBackground(null);
                        timer.stop();
                    }
                }

                if (fadeIn) {
                    button.setBackground(new Color(255, 255, 255, (int) (alpha * 255)));
                } else {
                    button.setBackground(new Color(255, 255, 255, (int) (alpha * 255)));
                }
            }
        });
        timer.start();
    }

    public static void init() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InitFrame initFrame = new InitFrame();
                initFrame.setVisible(true);
            }
        });
    }
}