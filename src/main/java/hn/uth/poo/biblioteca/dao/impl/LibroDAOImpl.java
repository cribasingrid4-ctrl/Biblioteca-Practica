package hn.uth.poo.biblioteca.dao.impl;

import hn.uth.poo.biblioteca.dao.Conexion;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    private static final String SELECT_BASE =
            "SELECT l.id, l.isbn, l.titulo, l.anio, l.precio, l.autor_id, "
            + "a.nombre AS autor_nombre, a.nacionalidad AS autor_nacionalidad "
            + "FROM libros l INNER JOIN autores a ON a.id = l.autor_id ";

    private static final String SQL_INSERTAR =
            "INSERT INTO libros (isbn, titulo, anio, precio, autor_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_BUSCAR = SELECT_BASE + "WHERE l.id = ?";
    private static final String SQL_BUSCAR_ISBN = SELECT_BASE + "WHERE l.isbn = ?";
    private static final String SQL_LISTAR = SELECT_BASE + "ORDER BY l.id";
    private static final String SQL_LISTAR_POR_AUTOR = SELECT_BASE + "WHERE l.autor_id = ? ORDER BY l.id";
    private static final String SQL_ACTUALIZAR =
            "UPDATE libros SET isbn = ?, titulo = ?, anio = ?, precio = ?, autor_id = ? WHERE id = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM libros WHERE id = ?";

    @Override
    public void insertar(Libro libro) {
        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setInt(3, libro.getAnio());
            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutor().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    libro.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al insertar el libro", e);
        }
    }

    @Override
    public Libro buscarPorId(Integer id) {
        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(SQL_BUSCAR)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) {
            throw new DatosException("Error al buscar el libro con id " + id, e);
        }
    }

    @Override
    public Libro buscarPorIsbn(String isbn) {
        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(SQL_BUSCAR_ISBN)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) {
            throw new DatosException("Error al buscar el libro con ISBN " + isbn, e);
        }
    }

    @Override
    public List<Libro> listar() {
        List<Libro> lista = new ArrayList<>();
        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DatosException("Error al listar los libros", e);
        }
        return lista;
    }

    @Override
    public List<Libro> listarPorAutor(int autorId) {
        List<Libro> lista = new ArrayList<>();
        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(SQL_LISTAR_POR_AUTOR)) {
            ps.setInt(1, autorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al listar los libros del autor " + autorId, e);
        }
        return lista;
    }

    @Override
    public boolean actualizar(Libro libro) {
        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setInt(3, libro.getAnio());
            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutor().getId());
            ps.setInt(6, libro.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatosException("Error al actualizar el libro", e);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatosException("Error al eliminar el libro con id " + id, e);
        }
    }

    private Libro mapear(ResultSet rs) throws SQLException {
        Autor autor = new Autor(
                rs.getInt("autor_id"),
                rs.getString("autor_nombre"),
                rs.getString("autor_nacionalidad"));
        return new Libro(
                rs.getInt("id"),
                rs.getString("isbn"),
                rs.getString("titulo"),
                rs.getInt("anio"),
                rs.getDouble("precio"),
                autor);
    }
}
