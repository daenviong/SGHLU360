package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:postgresql://dpg-d0c2j01r0fns73e05ba0-a.oregon-postgres.render.com:5432/sghlu_db";
    private static final String USER = "sghlu_user";
    private static final String PASSWORD = "hURiKnDSetYGCpUrFCRUDdrTpqrzmOOD";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}