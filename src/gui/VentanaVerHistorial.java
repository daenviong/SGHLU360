package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class VentanaVerHistorial extends JFrame {
    public VentanaVerHistorial() {
        setTitle("Historial de Inscripciones");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBackground(Color.WHITE);
        JLabel lblCodigo = new JLabel("Código del estudiante:");
        JTextField campoCodigo = new JTextField("2024101001", 15);
        JButton buscar = new JButton("Buscar");
        buscar.setBackground(new Color(255, 102, 0));
        buscar.setForeground(Color.WHITE);
        buscar.setFocusPainted(false);

        panelSuperior.add(lblCodigo);
        panelSuperior.add(campoCodigo);
        panelSuperior.add(buscar);

        String[] columnas = {"Evento", "Fecha"};
        String[][] datos = {
            {"Taller de Robótica", "2025-05-10"},
            {"Club de Lectura", "2025-05-15"},
            {"Charla de Innovación", "2025-05-17"},
            {"Hackathon UNAB", "2025-05-18"},
            {"Cine Foro", "2025-05-19"},
            {"Feria de Ciencias", "2025-05-20"},
            {"Expo Proyectos", "2025-05-22"},
            {"Concurso de Ensayo", "2025-05-23"},
            {"Torneo de Ajedrez", "2025-05-24"},
            {"Encuentro de Liderazgo", "2025-05-25"}
        };

        JTable tabla = new JTable(datos, columnas);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.setGridColor(Color.LIGHT_GRAY);
        tabla.setSelectionBackground(new Color(255, 204, 153));

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setBackground(new Color(255, 102, 0));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }
}