package gui;

import javax.swing.*;
import java.io.*;
import java.awt.*;

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
            try (PrintWriter writer = new PrintWriter(new FileWriter("exportado_sghlu.csv"))) {
                writer.println("Nombre,Código,Horas,Evento,Fecha");
                String[][] datos = {
                    {"Laura Díaz", "2024101001", "6", "Taller de Robótica", "2025-05-01"},
                    {"Juan Ríos", "2024101002", "5", "Hackathon UNAB", "2025-05-04"},
                    {"Ana Gómez", "2024101003", "3", "Cine Foro", "2025-05-08"}
                };
                for (String[] fila : datos) {
                    writer.printf("%s,%s,%s,%s,%s%n", fila[0], fila[1], fila[2], fila[3], fila[4]);
                }
                JOptionPane.showMessageDialog(null, "Archivo 'exportado_sghlu.csv' exportado con éxito.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al exportar: " + ex.getMessage());
            }
        });

        panel.add(exportar);
        add(panel);
        setVisible(true);
    }
}