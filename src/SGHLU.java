import javax.swing.JOptionPane;
import gui.VentanaLoginEstudiante;
import gui.VentanaLoginDocente;
import gui.ConexionBD;
import java.sql.*;

public class SGHLU {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            crearDocenteInicialSiNoExiste();

            String[] opciones = {"Estudiante", "Docente"};
            int seleccion = JOptionPane.showOptionDialog(
                null,
                "¿Cómo deseas iniciar sesión?",
                "Inicio de Sesión",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (seleccion == 0) {
                new VentanaLoginEstudiante().setVisible(true);
            } else if (seleccion == 1) {
                new VentanaLoginDocente().setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }

    private static void crearDocenteInicialSiNoExiste() {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sqlCheck = "SELECT COUNT(*) FROM docentes";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCheck);
            if (rs.next() && rs.getInt(1) == 0) {
                String sqlInsert = "INSERT INTO docentes (nombre, correo, contrasena) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sqlInsert);
                ps.setString(1, "Administrador SGHLU");
                ps.setString(2, "admin@unab.edu.co");
                ps.setString(3, "admin123");
                ps.executeUpdate();
                System.out.println("✅ Usuario docente inicial creado: admin@unab.edu.co / admin123");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}