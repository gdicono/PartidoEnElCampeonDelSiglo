package org.example;

import javax.swing.*;
import java.awt.*;

public class StartGUI extends JFrame {
    public StartGUI() {
        setTitle("Simulador del Campeón del Siglo - Configuración inicial");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel capacityLabel = new JLabel("Capacidad del estadio:");
        JTextField capacityField = new JTextField();

        JLabel hooligansLabel = new JLabel("Cantidad de hinchas que llegan:");
        JTextField hooligansField = new JTextField();

        JButton startButton = new JButton("Iniciar simulación");
        JButton cancelButton = new JButton("Cancelar");

        panel.add(capacityLabel);
        panel.add(capacityField);
        panel.add(hooligansLabel);
        panel.add(hooligansField);
        panel.add(new JLabel()); // celda vacía
        panel.add(new JLabel()); // celda vacía
        panel.add(startButton);
        panel.add(cancelButton);

        add(panel);

        // Acción del botón Iniciar
        startButton.addActionListener(e -> {
            try {
                int capacity = Integer.parseInt(capacityField.getText().trim());
                int totalHooligans = Integer.parseInt(hooligansField.getText().trim());

                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea iniciar la simulación?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose(); // cierra esta ventana
                    Simulation.startSimulation(capacity, totalHooligans);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Debe ingresar solo números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción del botón Cancelar
        cancelButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Simulación cancelada. ¡Hasta pronto!");
            System.exit(0);
        });
    }

    // Metodo para iniciar esta GUI
    public static void showMenu() {
        SwingUtilities.invokeLater(() -> {
            StartGUI gui = new StartGUI();
            gui.setVisible(true);
        });
    }
}
