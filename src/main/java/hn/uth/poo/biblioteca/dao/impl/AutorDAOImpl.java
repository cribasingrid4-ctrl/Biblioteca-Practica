/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hn.uth.poo.biblioteca.dao.impl;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.modelo.Autor;
import java.util.List;

public class AutorDAOImpl implements AutorDAO {

    @Override
    public void insertar(Autor autor) {
        throw new UnsupportedOperationException(
            "Pendiente: insertar autor en SQLite");
    }

    @Override
    public Autor buscarPorId(int id) {
        throw new UnsupportedOperationException(
            "Pendiente: buscar autor por ID");
    }

    @Override
    public List<Autor> listar() {
        throw new UnsupportedOperationException(
            "Pendiente: listar autores");
    }

    @Override
    public void actualizar(Autor autor) {
        throw new UnsupportedOperationException(
            "Pendiente: actualizar autor");
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException(
            "Pendiente: eliminar autor");
    }
}
