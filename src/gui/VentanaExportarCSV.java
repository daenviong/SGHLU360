package gui;

import javax.swing.*;
import java.awt.Color; // ✅ IMPORTANTE
import java.io.*;
import java.sql.*;
import persistencia.ConexionBD;

public class VentanaExportarCSV extends JFrame {
    public VentanaExportarCSV() {
        setTitle("Exportar Reporte");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        JButton exportar = new JButton("Exportar CSV");
        exportar.setBackground(new java.awt.Color(255,102,0));
        exportar.setForeground(Color.WHITE);
        exportar.setFocusPainted(false);
        exportar.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        exportar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        exportar.addActionListener(e -> {
            try (Connection conn = ConexionBD.conectar();
                 PrintWriter writer = new PrintWriter(new FileWriter("reporte_sghlu.csv"))) {
                writer.println("Nombre,Código,Horas,Evento,Fecha");
                String sql = "SELECT est.nombre, est.codigo, est.horas, e.nombre AS evento, e.fecha " +
                             "FROM inscripciones i JOIN estudiantes est ON est.id = i.estudiante_id " +
                             "JOIN eventos e ON e.id = i.evento_id";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    writer.printf("%s,%s,%d,%s,%s%n",
                        rs.getString("nombre"), rs.getString("codigo"),
                        rs.getInt("horas"), rs.getString("evento"), rs.getDate("fecha"));
                }
                JOptionPane.showMessageDialog(null, "Reporte exportado como reporte_sghlu.csv");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al exportar: " + ex.getMessage());
            }
        });

        panel.add(exportar);
        add(panel);
        setVisible(true);
    }
}