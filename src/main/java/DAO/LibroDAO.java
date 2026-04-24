package DAO;

import model.Libro;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    public void insertarLibro(Libro libro) {
        String sql = "INSERT INTO libros (id, nombre, id_autor) VALUES (?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, libro.getId());
            ps.setString(2, libro.getNombre());
            ps.setInt(3, libro.getIdAutor());

            ps.executeUpdate();
            System.out.println("Libro insertado con éxito.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Libro> consultarLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";

        try {
            Connection conn = DatabaseConnection.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("id_autor")
                );
                libros.add(libro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libros;
    }

    public Libro buscarPorId(int id) {
        String sql = "SELECT * FROM libros WHERE id = ?";
        Libro libro = null;

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("id_autor")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libro;
    }

    public void actualizarLibro(int id, String nombre, int idAutor) {
        String sql = "UPDATE libros SET nombre = ?, id_autor = ? WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setInt(2, idAutor);
            ps.setInt(3, id);

            ps.executeUpdate();
            System.out.println("Libro actualizado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Libro> consultarLibrosPorAutor(int idAutor) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE id_autor = ?";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idAutor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                libros.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("id_autor")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libros;
    }

    public List<String> obtenerLibrosMasPrestados() {
        List<String> lista = new ArrayList<>();

        String sql = """
        SELECT l.id, l.nombre, COUNT(p.id_libro) AS total_prestamos
        FROM libros l
        INNER JOIN prestamos p ON l.id = p.id_libro
        GROUP BY l.id, l.nombre
        ORDER BY total_prestamos DESC
    """;

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int total = rs.getInt("total_prestamos");

                lista.add("ID: " + id + " | " + nombre + " | Prestado: " + total + " veces");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener libros más prestados: " + e.getMessage());
        }

        return lista;
    }
}

