package org.example;

import java.io.PrintStream;
import java.io.OutputStream;


public class DualPrint extends PrintStream { // su objetivo es enviar la misma información a dos lugares a la vez: la consola y un archivo.

    private final PrintStream consoleOut; // referencia al printStream original de la consola (System.out)

    public DualPrint(OutputStream fileOut, PrintStream consoleOut) { // recibe dos flujos de salida. fileOut (hacia el xml) y consoleOut (hacia la consola)
        super(fileOut, true); // true = auto-flush. llama al constructor de PrintStream con auto-flush activado (true). esto hace que se vacíe el buffer automáticamente después de cada println()
        this.consoleOut = consoleOut;
    }

    @Override
    public void write(byte[] buf, int off, int len) { // metodo que sobreescribe el metodo write byte[] - array de bytes que se quiere sobreescirbir. int off - inidice inicial dentro del buffer donde se empieza a escribir. int len - cantidad de bytes que se va a escrinbir a partir del off
        try {

            super.write(buf, off, len); // escribe los datos en el archivo indicado por fileOut.
            super.flush(); // asegura que el texto se grabe de inmediato

            consoleOut.write(buf, off, len); // escribimos en la consola
            consoleOut.flush(); // fuerza la salida en la terminal

        } catch (Exception e) {
            setError();
        }
    }
}
