package hn.uth.poo.biblioteca.excepciones;

public class DatosException extends RuntimeException {

    public DatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
