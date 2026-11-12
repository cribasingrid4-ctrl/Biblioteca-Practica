package hn.uth.poo.biblioteca.dao;

import hn.uth.poo.biblioteca.modelo.Autor;
import java.util.List;

public interface AutorDAO extends CrudDAO<Autor, Integer> {

    @Override
    void insertar(Autor autor);

    @Override
    Autor buscarPorId(Integer id);

    @Override
    List<Autor> listar();

    @Override
    boolean actualizar(Autor autor);

    @Override
    boolean eliminar(Integer id);
}
