/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hn.uth.poo.biblioteca.dao;
import hn.uth.poo.biblioteca.modelo.Libro;
import java.util.List;
/**
 *
 * @author criba
 */
public interface LibroDAO {
void insertar(Libro libro);
Libro buscarPorId(int id);
List<Libro> listar();
void actualizar(Libro libro);
void eliminar(int id); 
List<Libro> listarPorAutor(int autorId);
}

