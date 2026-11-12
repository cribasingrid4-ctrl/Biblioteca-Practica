package hn.uth.poo.biblioteca.dao;

import java.util.List;

/**
 * Operaciones CRUD genéricas.
 *
 * @param <T>  tipo de la entidad
 * @param <ID> tipo del identificador
 */
public interface CrudDAO<T, ID> {

    void insertar(T entidad);

    T buscarPorId(ID id);

    List<T> listar();

    boolean actualizar(T entidad);

    boolean eliminar(ID id);
}
