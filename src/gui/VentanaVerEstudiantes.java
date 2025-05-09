package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class VentanaVerEstudiantes extends JFrame {
    public VentanaVerEstudiantes() {
        setTitle("Lista de Estudiantes");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] columnas = {"Nombre", "Código", "Horas"};
        String[][] datos = {
            {"Laura Díaz", "2024101001", "5"},
            {"Juan Ríos", "2024101002", "3"}
        };

        JTable tabla = new JTable(datos, columnas);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.setGridColor(Color.LIGHT_GRAY);
        tabla.setSelectionBackground(new Color(255, 204, 153));
        tabla.setSelectionForeground(Color.BLACK);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setBackground(new Color(255, 102, 0));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(scroll, BorderLayout.CENTER);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }
}