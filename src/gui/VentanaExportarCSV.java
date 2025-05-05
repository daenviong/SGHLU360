package gui;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import persistencia.ConexionBD;

public class VentanaExportarCSV extends JFrame {
    public VentanaExportarCSV() {
        try (Connection conn = ConexionBD.conectar();
             PrintWriter writer = new PrintWriter(new FileWriter("reporte_sghlu.csv"))) {
            writer.println("Nombre,Código,Horas,Evento,Fecha");

            String sql = "SELECT est.nombre, est.codigo, est.horas, e.nombre AS evento, e.fecha " +
                         "FROM inscripciones i " +
                         "JOIN estudiantes est ON est.id = i.estudiante_id " +
                         "JOIN eventos e ON e.id = i.evento_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                writer.printf("%s,%s,%d,%s,%s%n",
                    rs.getString("nombre"),
                    rs.getString("codigo"),
                    rs.getInt("horas"),
                    rs.getString("evento"),
                    rs.getDate("fecha"));
            }

            JOptionPane.showMessageDialog(null, "Reporte exportado como reporte_sghlu.csv");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al exportar: " + ex.getMessage());
        }
    }
}