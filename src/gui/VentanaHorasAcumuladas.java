package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class VentanaHorasAcumuladas extends JFrame {
    public VentanaHorasAcumuladas() {
        setTitle("Horas Acumuladas por Estudiante");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnas = {"Nombre", "Código", "Horas"};
        String[][] datos = {
            {"Laura Díaz", "2024101001", "6"},
            {"Juan Ríos", "2024101002", "5"},
            {"Ana Gómez", "2024101003", "3"},
            {"Carlos Peña", "2024101004", "4"},
            {"María Contreras", "2024101005", "2"}
        };

        JTable tabla = new JTable(datos, columnas);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.setSelectionBackground(new Color(255, 204, 153));
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(255, 102, 0));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);
        setVisible(true);
    }
}