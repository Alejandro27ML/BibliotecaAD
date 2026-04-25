package DAO;

import model.Prestamo;
import database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    //Metodo para insertar un nuevo préstamo
    public void insertarPrestamo(Prestamo prestamo) {

        //Fecha de devolución automática de 15 días
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

    //Metodo para consultar los prestamos
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

    //Metodo para buscar un prestamo por su id
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

    //Metodo para eliminar un préstamo:
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

    //Metodo para consultar los préstamos de un usuario:
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

