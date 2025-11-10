package org.example;

import javax.swing.*; // libreria Swing (interfaz gráfica)
import java.awt.*; // librearia AWT (colores, gráficos, layout)

public class StadiumGUI extends JFrame { // hereda de JFrame: una ventana completa

    private final JProgressBar totalBar; // barras de progreso para mostrar la ocupación
    private final JProgressBar penarolBar; // barras de progreso para mostrar la ocupación
    private final JProgressBar nacionalBar; // barras de progreso para mostrar la ocupación

    private final JTextArea consoleArea;   // area de texto donde se muestran los mensajes (como la consola)

    private final int capacity; // capacidad total del estadio

    private final JButton restartButton;

    public StadiumGUI(int capacity) { // constructor: se ejecuta cuando creamos una instancia de StadiumGUI
        this.capacity = capacity;

        // configuración básica de la ventana
        setTitle("Simulador del Campeón del Siglo"); // título de la ventana
        setSize(500, 400); // tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // cierra el programa al salir
        setLocationRelativeTo(null); // centra la ventana en pantalla

        // panel principal con separación entre componentes (BorderLayout)
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno

        JPanel barPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // panel que contendrá las 3 barras, distribuidas en 3 filas

        // creamos cada barra usando la clase interna personalizada (ver abajo)
        // parámetros: (mínimo, máximo, color de barra, color del texto)
        totalBar = new ColoredTextProgressBar(0, capacity, new Color(150, 170, 190), Color.BLACK);
        totalBar.setString("Ocupación total"); // texto que aparece encima de la barra

        penarolBar = new ColoredTextProgressBar(0, capacity, Color.YELLOW, Color.BLACK);
        penarolBar.setString("Peñarol");

        nacionalBar = new ColoredTextProgressBar(0, capacity, Color.BLUE, Color.RED);
        nacionalBar.setString("Nacional");

        // agregamos las barras al panel vertical
        barPanel.add(totalBar);
        barPanel.add(penarolBar);
        barPanel.add(nacionalBar);

        // area de texto (actúa como consola visual dentro de la ventana)
        consoleArea = new JTextArea();
        consoleArea.setEditable(false); // no se puede escribir a mano
        JScrollPane scrollPane = new JScrollPane(consoleArea); // agregamos scroll automático

        restartButton = new JButton("Reiniciar simulación");
        restartButton.setEnabled(false); // deshabilitado hasta el final
        restartButton.addActionListener(e -> {
            dispose(); // cierra la ventana actual
            StartGUI.showMenu(); // vuelve al menú inicial
        });

        // agregamos los componentes al panel principal
        panel.add(barPanel, BorderLayout.NORTH);  // las barras arriba
        panel.add(scrollPane, BorderLayout.CENTER); // el área de texto abajo
        panel.add(restartButton, BorderLayout.SOUTH); // boton de reinicio

        add(panel); // agregamos el panel completo a la ventana
    }

    public void updateBars(int penarolCount, int nacionalCount, int total) { // // metodo para actualizar los valores de las barras desde tu simulador

        SwingUtilities.invokeLater(() -> { // SwingUtilities.invokeLater asegura que el cambio se haga en el hilo gráfico de Swing
            totalBar.setValue(total); // actualiza la barra total
            penarolBar.setValue(penarolCount); // actualiza la barra de Peñarol
            nacionalBar.setValue(nacionalCount); // actualiza la barra de Nacional
        });
    }

    public void appendMessage(String message) { // metodo para agregar texto al área de consola dentro de la ventana
        SwingUtilities.invokeLater(() -> {
            consoleArea.append(message + "\n"); // agrega el mensaje al final
            consoleArea.setCaretPosition(consoleArea.getDocument().getLength()); // hace que el scroll baje automáticamente al final del texto
        });
    }

    public void onSimulationEnd() { // metodo que muestra la finalizacion de la simulacion
        SwingUtilities.invokeLater(() -> {
            appendMessage("\nSimulación finalizada.");
            restartButton.setEnabled(true); // habilita el botón
        });
    }

    private static class ColoredTextProgressBar extends JProgressBar { // clase interna que hereda de JProgressBar para poder personalizar el color del texto
        private final Color textColor; // color del texto dentro de la barra


        public ColoredTextProgressBar(int min, int max, Color barColor, Color textColor) { // constructor: recibe el rango, el color de la barra y el color del texto
            super(min, max); // llama al constructor de JProgressBar
            this.textColor = textColor;
            setForeground(barColor); // Cambia el color del relleno de la barra
            setStringPainted(true); // Habilita el texto sobre la barra
        }

        @Override
        protected void paintComponent(Graphics g) { // paintComponent dibuja el componente gráfico de la barra. Lo sobreescribimos para cambiar el color del texto
            super.paintComponent(g); // dibuja la barra normal
            g.setColor(textColor); // cambia el color con el que se dibujará el texto

            FontMetrics fm = g.getFontMetrics(); // para calcular el tamaño del texto
            String text = getString(); // obtiene el texto actual (setString)

            // calcula la posición para centrar el texto dentro de la barra
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 2;

            g.drawString(text, x, y); // dibuja el texto en la posición calculada
        }
    }
}