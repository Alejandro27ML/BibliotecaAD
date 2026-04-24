package DAO;

import model.Autor;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {

    public void insertarAutor(Autor autor) {
        String sql = "INSERT INTO autores (id, nombre, apellido) VALUES (?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, autor.getId());
            ps.setString(2, autor.getNombre());
            ps.setString(3, autor.getApellido());

            ps.executeUpdate();
            System.out.println("Autor insertado con éxito.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Autor> consultarAutores() {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM autores";

        try {
            Connection conn = DatabaseConnection.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Autor autor = new Autor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
                autores.add(autor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return autores;
    }

    public Autor buscarPorId(int id) {
        String sql = "SELECT * FROM autores WHERE id = ?";
        Autor autor = null;

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                autor = new Autor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return autor;
    }

    public void actualizarAutor(int id, String nombre, String apellido) {
        String sql = "UPDATE autores SET nombre = ?, apellido = ? WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, id);

            ps.executeUpdate();
            System.out.println("Autor actualizado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void contarLibrosPorAutor() {
        String sql = "SELECT a.nombre, a.apellido, COUNT(l.id) AS total_libros " +
                "FROM autores a LEFT JOIN libros l ON a.id = l.id_autor " +
                "GROUP BY a.id, a.nombre, a.apellido";

        try {
            Connection conn = DatabaseConnection.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(
                        rs.getString("nombre") + " " +
                                rs.getString("apellido") + " → " +
                                rs.getInt("total_libros") + " libros"
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

