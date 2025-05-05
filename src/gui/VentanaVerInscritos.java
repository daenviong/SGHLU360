package gui;

import javax.swing.*;
import java.sql.*;
import persistencia.ConexionBD;

public class VentanaVerInscritos extends JFrame {
    public VentanaVerInscritos() {
        setTitle("Inscritos por Evento");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        JComboBox<String> comboEventos = new JComboBox<>(modelo);
        JTextArea area = new JTextArea();
        area.setEditable(false);

        try (Connection conn = ConexionBD.conectar()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre FROM eventos");
            while (rs.next()) {
                modelo.addElement(rs.getInt("id") + " - " + rs.getString("nombre"));
            }
        } catch (SQLException e) {
            area.setText("Error cargando eventos.");
        }

        comboEventos.addActionListener(e -> {
            String seleccion = (String) comboEventos.getSelectedItem();
            if (seleccion != null) {
                int id = Integer.parseInt(seleccion.split(" - ")[0]);
                try (Connection conn = ConexionBD.conectar()) {
                    PreparedStatement stmt = conn.prepareStatement(
                        "SELECT e.nombre, est.nombre AS estudiante FROM inscripciones i " +
                        "JOIN estudiantes est ON est.id = i.estudiante_id " +
                        "JOIN eventos e ON e.id = i.evento_id WHERE i.evento_id = ?");
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                    area.setText("");
                    while (rs.next()) {
                        area.append("Estudiante: " + rs.getString("estudiante") + "\n");
                    }
                } catch (SQLException ex) {
                    area.setText("Error al consultar inscritos.");
                }
            }
        });

        add(comboEventos, "North");
        add(new JScrollPane(area), "Center");
        setVisible(true);
    }
}