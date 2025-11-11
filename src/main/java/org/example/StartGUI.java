package org.example;

import javax.swing.*;
import java.awt.*;

public class StartGUI extends JFrame {
    public StartGUI() {
        setTitle("Simulador del Campeón del Siglo - Configuración inicial"); // título de la ventana
        setSize(400, 250); // tamaño de la ventana (ancho, alto)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // define que al cerrar la ventana se termine el programa
        setLocationRelativeTo(null); // centra la ventana en la pantalla

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // GridLayout(4, 2, 10, 10): 4 filas, 2 columnas, con separación de 10px
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // margen interno del panel (arriba, izquierda, abajo, derecha)

        JLabel capacityLabel = new JLabel("Cantidad de asientos disponibles en el estadio:");
        JTextField capacityField = new JTextField(); // para escribir la capacidad

        JLabel hooligansLabel = new JLabel("Cantidad de hinchas que llegan al estadio:");
        JTextField hooligansField = new JTextField(); // para escribir el número de hinchas

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

        add(panel); // agrega el panel completo al JFrame (ventana)

        startButton.addActionListener(e -> { // accion del botón Iniciar
            try {

                int capacity = Integer.parseInt(capacityField.getText().trim()); // lee y convierte los valores ingresados a enteros
                int totalHooligans = Integer.parseInt(hooligansField.getText().trim());


                int confirm = JOptionPane.showConfirmDialog( // muestra un cuadro de confirmación antes de iniciar
                        this, "Desea iniciar la simulación?", "Confirmación",
                        JOptionPane.YES_NO_OPTION
                );


                if (confirm == JOptionPane.YES_OPTION) { // si el usuario confirma con "S"
                    dispose(); // cierra esta ventana
                    new Thread(() -> Simulation.startSimulation(capacity, totalHooligans)).start();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Debe ingresar solo números enteros.", "Error", JOptionPane.ERROR_MESSAGE); // si el usuario escribió algo que no es un número entero
            }
        });

        cancelButton.addActionListener(e -> {  // acción del botón cancelar
            JOptionPane.showMessageDialog(this, "Simulación cancelada. Hasta pronto!");
            System.exit(0);  // cierra el programa
        });
    }

    public static void showMenu() { // metodo que muestra el menu
        SwingUtilities.invokeLater(() -> { // ejecuta la creación de la GUI dentro del hilo principal de Swing
            StartGUI gui = new StartGUI(); // crea una instancia de la ventana
            gui.setVisible(true); // la muestra en pantalla
        });
    }
}
