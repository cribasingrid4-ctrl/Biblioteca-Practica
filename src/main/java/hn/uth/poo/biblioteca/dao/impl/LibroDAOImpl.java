/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hn.uth.poo.biblioteca.dao.impl;

import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.modelo.Libro;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public void insertar(Libro libro) {
        throw new UnsupportedOperationException(
            "Pendiente: insertar libro en SQLite");
    }

    @Override
    public Libro buscarPorId(int id) {
        throw new UnsupportedOperationException(
            "Pendiente: buscar libro por ID");
    }

    @Override
    public List<Libro> listar() {
        throw new UnsupportedOperationException(
            "Pendiente: listar libros");
    }

    @Override
    public void actualizar(Libro libro) {
        throw new UnsupportedOperationException(
            "Pendiente: actualizar libro");
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException(
            "Pendiente: eliminar libro");
    }

    @Override
    public List<Libro> listarPorAutor(int autorId) {
        throw new UnsupportedOperationException(
            "Pendiente: listar libros por autor");
    }
}
