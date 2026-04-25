package DAO;

import model.Usuario;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    //Metodo para añadir nuevo usuario.
    public void insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (id, nombre, apellido) VALUES (?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, usuario.getId());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());

            ps.executeUpdate();
            System.out.println("Usuario insertado con éxito.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para consultar datos de un usuario.
    public List<Usuario> consultarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try {
            Connection conn = DatabaseConnection.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    //Metodo para buscar usuario según su id.
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        Usuario usuario = null;

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    //Metodo para eliminar un usuario.
    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Usuario eliminado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

