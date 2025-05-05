package gui;

import javax.swing.*;
import java.sql.*;
import persistencia.ConexionBD;

public class VentanaVerEstudiantes extends JFrame {
    public VentanaVerEstudiantes() {
        setTitle("Estudiantes Registrados");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);

        try (Connection conn = ConexionBD.conectar()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nombre, codigo, horas FROM estudiantes");
            while (rs.next()) {
                area.append("Nombre: " + rs.getString("nombre") +
                            ", Código: " + rs.getString("codigo") +
                            ", Horas: " + rs.getInt("horas") + "\n");
            }
        } catch (SQLException e) {
            area.setText("Error al cargar estudiantes: " + e.getMessage());
        }

        add(scroll);
        setVisible(true);
    }
}