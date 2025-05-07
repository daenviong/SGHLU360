package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

public class VentanaReportePorFechas extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaReportePorFechas() {
        setTitle("Inscripciones por Rango de Fechas");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con campos de fecha
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBackground(Color.WHITE);
        JLabel lblInicio = new JLabel("Desde (yyyy-MM-dd):");
        JTextField campoInicio = new JTextField("2025-05-01", 10);
        JLabel lblFin = new JLabel("Hasta:");
        JTextField campoFin = new JTextField("2025-05-10", 10);
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(255, 102, 0));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFocusPainted(false);
        panelSuperior.add(lblInicio);
        panelSuperior.add(campoInicio);
        panelSuperior.add(lblFin);
        panelSuperior.add(campoFin);
        panelSuperior.add(btnFiltrar);

        // Tabla
        String[] columnas = {"Estudiante", "Evento", "Fecha inscripción"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.setSelectionBackground(new Color(255, 204, 153));
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(255, 102, 0));
        header.setForeground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Datos ficticios base
        String[][] baseDatos = {
            {"Laura Díaz", "Taller de Robótica", "2025-05-01"},
            {"Juan Ríos", "Taller de Robótica", "2025-05-02"},
            {"Carlos Peña", "Hackathon UNAB", "2025-05-04"},
            {"María Contreras", "Club de Lectura", "2025-05-06"},
            {"Ana Gómez", "Cine Foro", "2025-05-08"},
            {"Sofía Torres", "Cine Foro", "2025-05-10"},
            {"Diego Castro", "Expo Proyectos", "2025-05-12"}
        };

        btnFiltrar.addActionListener(e -> {
            modelo.setRowCount(0);
            String desde = campoInicio.getText().trim();
            String hasta = campoFin.getText().trim();
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = df.parse(desde);
                Date d2 = df.parse(hasta);
                for (String[] fila : baseDatos) {
                    Date fecha = df.parse(fila[2]);
                    if (!fecha.before(d1) && !fecha.after(d2)) {
                        modelo.addRow(fila);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Formato de fecha inválido.");
            }
        });

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }
}