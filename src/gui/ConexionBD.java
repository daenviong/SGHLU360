package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection obtenerConexion() throws SQLException {
        String url = "jdbc:postgresql://dpg-d0c2j01r0fns73e05ba0-a.oregon-postgres.render.com/sghlu_db?sslmode=require";
        String user = "sghlu_user";
        String password = "hURiKnDSetYGCpUrFCRUDdrTpqrzmOOD";
        System.out.println("🌐 Intentando conectar a PostgreSQL...");
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Conexión a la base de datos exitosa.");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Fallo de conexión a la base de datos:");
            e.printStackTrace();
            throw e;
        }
    }
}