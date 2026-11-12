package hn.uth.poo.biblioteca.dao;

import hn.uth.poo.biblioteca.modelo.Libro;
import java.util.List;

public interface LibroDAO extends CrudDAO<Libro, Integer> {

    @Override
    void insertar(Libro libro);

    @Override
    Libro buscarPorId(Integer id);

    @Override
    List<Libro> listar();

    @Override
    boolean actualizar(Libro libro);

    @Override
    boolean eliminar(Integer id);

    List<Libro> listarPorAutor(int autorId);

    Libro buscarPorIsbn(String isbn);
}
