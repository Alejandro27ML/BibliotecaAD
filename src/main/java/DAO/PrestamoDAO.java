package DAO;

import model.Prestamo;
import database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public void insertarPrestamo(Prestamo prestamo) {

        // ✔ Fecha de devolución automática: 15 días después
        LocalDate fechaDevolucionCalculada = prestamo.getFechaPrestamo().plusDays(15);

        String sql = "INSERT INTO prestamos (id, id_usuario, id_libro, f_prestamo, f_devolucion) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, prestamo.getId());
            ps.setInt(2, prestamo.getIdUsuario());
            ps.setInt(3, prestamo.getIdLibro());
            ps.setDate(4, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.setDate(5, Date.valueOf(fechaDevolucionCalculada));

            ps.executeUpdate();
            System.out.println("Préstamo insertado con éxito. Fecha devolución: " + fechaDevolucionCalculada);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prestamo> consultarPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";

        try {
            Connection conn = DatabaseConnection.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Prestamo prestamo = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_libro"),
                        rs.getDate("f_prestamo").toLocalDate(),
                        rs.getDate("f_devolucion") != null ? rs.getDate("f_devolucion").toLocalDate() : null
                );
                prestamos.add(prestamo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prestamos;
    }

    public Prestamo buscarPorId(int id) {
        String sql = "SELECT * FROM prestamos WHERE id = ?";
        Prestamo prestamo = null;

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                prestamo = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_libro"),
                        rs.getDate("f_prestamo").toLocalDate(),
                        rs.getDate("f_devolucion") != null ? rs.getDate("f_devolucion").toLocalDate() : null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prestamo;
    }

    public void actualizarPrestamo(int id, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        String sql = "UPDATE prestamos SET f_prestamo = ?, f_devolucion = ? WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(fechaPrestamo));
            ps.setDate(2, fechaDevolucion != null ? Date.valueOf(fechaDevolucion) : null);
            ps.setInt(3, id);

            ps.executeUpdate();
            System.out.println("Préstamo actualizado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPrestamo(int id) {
        String sql = "DELETE FROM prestamos WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Préstamo eliminado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Prestamo> consultarPrestamosPorUsuario(int idUsuario) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE id_usuario = ?";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prestamos.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_libro"),
                        rs.getDate("f_prestamo").toLocalDate(),
                        rs.getDate("f_devolucion") != null ? rs.getDate("f_devolucion").toLocalDate() : null
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prestamos;
    }
}

