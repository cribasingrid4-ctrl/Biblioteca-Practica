package hn.uth.poo.biblioteca.app;

import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import java.awt.Component;
import javax.swing.JOptionPane;

final class Mensajes {

    private Mensajes() {
    }

    static void info(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    static boolean confirmar(Component padre, String mensaje) {
        return JOptionPane.showConfirmDialog(padre, mensaje, "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    static void error(Component padre, Exception e) {
        String titulo;
        String mensaje;
        if (e instanceof DatoInvalidoException) {
            titulo = "Dato inválido";
            mensaje = e.getMessage();
        } else if (e instanceof DatosException) {
            titulo = "Error de base de datos";
            mensaje = e.getMessage() + (e.getCause() != null ? "\nDetalle: " + e.getCause().getMessage() : "");
        } else if (e instanceof NumberFormatException) {
            titulo = "Dato inválido";
            mensaje = "Revise los campos numéricos (id, año y precio).";
        } else {
            titulo = "Error";
            mensaje = e.getMessage();
        }
        JOptionPane.showMessageDialog(padre, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }
}
