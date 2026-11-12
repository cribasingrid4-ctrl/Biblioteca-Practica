package hn.uth.poo.biblioteca.servicio;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;
import java.util.List;

public class ServicioAutores {

    private final AutorDAO autorDAO;
    private final LibroDAO libroDAO;

    public ServicioAutores(AutorDAO autorDAO, LibroDAO libroDAO) {
        this.autorDAO = autorDAO;
        this.libroDAO = libroDAO;
    }

    public Autor crear(Autor autor) {
        validar(autor);
        autorDAO.insertar(autor);
        return autor;
    }

    public List<Autor> listar() {
        return autorDAO.listar();
    }

    public Autor buscarPorId(int id) {
        Autor autor = autorDAO.buscarPorId(id);
        if (autor == null) {
            throw new DatoInvalidoException("No existe un autor con id " + id + ".");
        }
        return autor;
    }

    public void actualizar(Autor autor) {
        validar(autor);
        buscarPorId(autor.getId());
        autorDAO.actualizar(autor);
    }

    public void eliminar(int id) {
        buscarPorId(id);
        int cantidad = libroDAO.listarPorAutor(id).size();
        if (cantidad > 0) {
            throw new DatoInvalidoException("No se puede eliminar el autor: tiene "
                    + cantidad + " libro(s) registrado(s).");
        }
        autorDAO.eliminar(id);
    }

    private void validar(Autor autor) {
        if (autor == null) {
            throw new DatoInvalidoException("El autor no puede ser nulo.");
        }
        if (autor.getNombre() == null || autor.getNombre().isBlank()) {
            throw new DatoInvalidoException("El nombre del autor es obligatorio.");
        }
        autor.setNombre(autor.getNombre().trim());
        if (autor.getNacionalidad() != null) {
            autor.setNacionalidad(autor.getNacionalidad().trim());
        }
    }
}
