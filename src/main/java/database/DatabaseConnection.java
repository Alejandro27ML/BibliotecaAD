package database;

import java.sql.*;

//Visto en clase para establecer la conexión con la BBDD
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/Biblioteca";
    private static final String USER = "Alejandro";
    private static final String PASSWORD = "44487322";

    public static Connection conectar() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return conexion;
    }
}
