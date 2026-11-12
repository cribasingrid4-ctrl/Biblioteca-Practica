/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hn.uth.poo.biblioteca.dao;
import hn.uth.poo.biblioteca.modelo.Autor;
import java.util.List;
/**
 *
 * @author criba
 */
public interface AutorDAO {
void insertar(Autor autor);
Autor buscarPorId(int id);
List<Autor> listar();
void actualizar(Autor autor);
void eliminar(int id);
}
