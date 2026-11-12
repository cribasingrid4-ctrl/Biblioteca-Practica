package hn.uth.poo.biblioteca.excepciones;

/**
 * Se lanza cuando un dato no cumple una regla de negocio.
 */
public class DatoInvalidoException extends RuntimeException {

    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
