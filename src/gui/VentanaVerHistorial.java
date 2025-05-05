package gui;

import javax.swing.*;
import java.sql.*;
import persistencia.ConexionBD;

public class VentanaVerHistorial extends JFrame {
    public VentanaVerHistorial() {
        setTitle("Historial por Estudiante");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextField campoCodigo = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar");
        JTextArea area = new JTextArea();
        area.setEditable(false);

        btnBuscar.addActionListener(e -> {
            String codigo = campoCodigo.getText();
            try (Connection conn = ConexionBD.conectar()) {
                PreparedStatement stmt = conn.prepareStatement(
                    "SELECT e.nombre, e.fecha FROM inscripciones i " +
                    "JOIN estudiantes est ON est.id = i.estudiante_id " +
                    "JOIN eventos e ON e.id = i.evento_id WHERE est.codigo = ?");
                stmt.setString(1, codigo);
                ResultSet rs = stmt.executeQuery();
                area.setText("");
                while (rs.next()) {
                    area.append("Evento: " + rs.getString("nombre") + ", Fecha: " + rs.getDate("fecha") + "\n");
                }
            } catch (SQLException ex) {
                area.setText("Error: " + ex.getMessage());
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Código:"));
        panel.add(campoCodigo);
        panel.add(btnBuscar);

        add(panel, "North");
        add(new JScrollPane(area), "Center");
        setVisible(true);
    }
}