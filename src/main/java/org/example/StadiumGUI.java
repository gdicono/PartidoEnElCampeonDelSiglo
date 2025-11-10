package org.example;

import javax.swing.*;
import java.awt.*;

public class StadiumGUI extends JFrame {

    private final JProgressBar totalBar;
    private final JProgressBar penarolBar;
    private final JProgressBar nacionalBar;
    private final JTextArea consoleArea;
    private final int capacity;

    public StadiumGUI(int capacity) {
        this.capacity = capacity;

        setTitle("Simulador del Campeón del Siglo");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel barPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        totalBar = new JProgressBar(0, capacity);
        totalBar.setStringPainted(true);
        totalBar.setString("Ocupación total");

        penarolBar = new JProgressBar(0, capacity);
        penarolBar.setStringPainted(true);
        penarolBar.setForeground(Color.YELLOW);
        penarolBar.setString("Peñarol");

        nacionalBar = new JProgressBar(0, capacity);
        nacionalBar.setStringPainted(true);
        nacionalBar.setForeground(Color.BLUE);
        nacionalBar.setString("Nacional");

        barPanel.add(totalBar);
        barPanel.add(penarolBar);
        barPanel.add(nacionalBar);

        consoleArea = new JTextArea();
        consoleArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(consoleArea);

        panel.add(barPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
    }

    public void updateBars(int penarolCount, int nacionalCount, int total) {
        SwingUtilities.invokeLater(() -> {
            totalBar.setValue(total);
            penarolBar.setValue(penarolCount);
            nacionalBar.setValue(nacionalCount);
        });
    }

    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            consoleArea.append(message + "\n");
            consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
        });
    }
}
