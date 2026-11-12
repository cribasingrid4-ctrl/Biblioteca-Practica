package hn.uth.poo.biblioteca.servicio;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import java.time.Year;
import java.util.List;

public class ServicioLibros {

    public static final int ANIO_MINIMO = 1450;

    private final LibroDAO libroDAO;
    private final AutorDAO autorDAO;

    public ServicioLibros(LibroDAO libroDAO, AutorDAO autorDAO) {
        this.libroDAO = libroDAO;
        this.autorDAO = autorDAO;
    }

    public Libro crear(Libro libro) {
        validar(libro);
        if (libroDAO.buscarPorIsbn(libro.getIsbn()) != null) {
            throw new DatoInvalidoException("Ya existe un libro con el ISBN " + libro.getIsbn() + ".");
        }
        libroDAO.insertar(libro);
        return libro;
    }

    public List<Libro> listar() {
        return libroDAO.listar();
    }

    public Libro buscarPorId(int id) {
        Libro libro = libroDAO.buscarPorId(id);
        if (libro == null) {
            throw new DatoInvalidoException("No existe un libro con id " + id + ".");
        }
        return libro;
    }

    public List<Libro> listarPorAutor(int autorId) {
        if (autorDAO.buscarPorId(autorId) == null) {
            throw new DatoInvalidoException("No existe un autor con id " + autorId + ".");
        }
        return libroDAO.listarPorAutor(autorId);
    }

    public void actualizar(Libro libro) {
        validar(libro);
        buscarPorId(libro.getId());
        Libro otro = libroDAO.buscarPorIsbn(libro.getIsbn());
        if (otro != null && otro.getId() != libro.getId()) {
            throw new DatoInvalidoException("El ISBN " + libro.getIsbn() + " ya pertenece a otro libro.");
        }
        libroDAO.actualizar(libro);
    }

    public void eliminar(int id) {
        buscarPorId(id);
        libroDAO.eliminar(id);
    }

    private void validar(Libro libro) {
        if (libro == null) {
            throw new DatoInvalidoException("El libro no puede ser nulo.");
        }
        if (libro.getIsbn() == null || libro.getIsbn().isBlank()) {
            throw new DatoInvalidoException("El ISBN es obligatorio.");
        }
        libro.setIsbn(libro.getIsbn().trim());
        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            throw new DatoInvalidoException("El título es obligatorio.");
        }
        libro.setTitulo(libro.getTitulo().trim());
        int anioActual = Year.now().getValue();
        if (libro.getAnio() < ANIO_MINIMO || libro.getAnio() > anioActual) {
            throw new DatoInvalidoException("El año debe estar entre " + ANIO_MINIMO
                    + " y " + anioActual + ".");
        }
        if (libro.getPrecio() < 0) {
            throw new DatoInvalidoException("El precio debe ser mayor o igual a 0.");
        }
        Autor autor = libro.getAutor();
        if (autor == null || autorDAO.buscarPorId(autor.getId()) == null) {
            throw new DatoInvalidoException("El autor indicado no existe.");
        }
    }
}
