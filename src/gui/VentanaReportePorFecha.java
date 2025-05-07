package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class VentanaReportePorFecha extends JFrame {
    public VentanaReportePorFecha() {
        setTitle("Reporte de Inscripciones por Rango de Fechas");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel filtroPanel = new JPanel();
        filtroPanel.setBackground(Color.WHITE);
        filtroPanel.add(new JLabel("Fecha inicio (YYYY-MM-DD):"));
        JTextField campoInicio = new JTextField("2025-05-01", 10);
        filtroPanel.add(campoInicio);
        filtroPanel.add(new JLabel("Fecha fin (YYYY-MM-DD):"));
        JTextField campoFin = new JTextField("2025-05-10", 10);
        filtroPanel.add(campoFin);

        JButton filtrar = new JButton("Filtrar");
        filtrar.setBackground(new Color(255,102,0));
        filtrar.setForeground(Color.WHITE);
        filtrar.setFocusPainted(false);
        filtroPanel.add(filtrar);

        String[] columnas = {"Estudiante", "Evento", "Fecha"};
        String[][] datos = {
            {"Laura Díaz", "Taller de Robótica", "2025-05-02"},
            {"Juan Ríos", "Club de Lectura", "2025-05-04"},
            {"Carlos Peña", "Hackathon UNAB", "2025-05-06"},
            {"María López", "Cine Foro", "2025-05-07"},
            {"Ana Gómez", "Expo Proyectos", "2025-05-09"}
        };

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setSelectionBackground(new Color(255, 204, 153));
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(255, 102, 0));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        filtrar.addActionListener(e -> {
            String inicio = campoInicio.getText().trim();
            String fin = campoFin.getText().trim();
            JOptionPane.showMessageDialog(null,
                "Filtrando inscripciones entre " + inicio + " y " + fin + " (simulado)");
        });

        add(filtroPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }
}