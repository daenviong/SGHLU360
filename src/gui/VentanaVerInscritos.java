package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class VentanaVerInscritos extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaVerInscritos() {
        setTitle("Ver Inscritos por Evento");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Datos ficticios por evento
        Map<String, String[][]> inscritosPorEvento = new HashMap<>();
        inscritosPorEvento.put("Taller de Robótica", new String[][] {
            {"Laura Díaz", "2025-05-01"},
            {"Juan Ríos", "2025-05-02"}
        });
        inscritosPorEvento.put("Club de Lectura", new String[][] {
            {"María López", "2025-05-03"},
            {"Andrés Martínez", "2025-05-03"}
        });
        inscritosPorEvento.put("Hackathon UNAB", new String[][] {
            {"Carlos Peña", "2025-05-04"},
            {"Ana Gómez", "2025-05-05"}
        });
        inscritosPorEvento.put("Cine Foro", new String[][] {
            {"Sofía Torres", "2025-05-06"},
            {"Luis Delgado", "2025-05-07"}
        });
        inscritosPorEvento.put("Expo Proyectos", new String[][] {
            {"Natalia Ruiz", "2025-05-08"},
            {"Diego Castro", "2025-05-09"}
        });

        // ComboBox con eventos
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBackground(Color.WHITE);
        JLabel lblEvento = new JLabel("Seleccionar evento:");
        JComboBox<String> comboEventos = new JComboBox<>(inscritosPorEvento.keySet().toArray(new String[0]));
        panelSuperior.add(lblEvento);
        panelSuperior.add(comboEventos);

        // Tabla
        String[] columnas = {"Estudiante", "Fecha inscripción"};
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

        // Actualizar tabla cuando se selecciona un evento
        comboEventos.addActionListener(e -> {
            String evento = (String) comboEventos.getSelectedItem();
            actualizarTabla(inscritosPorEvento.get(evento));
        });

        // Cargar inicialmente
        comboEventos.setSelectedIndex(0);
        actualizarTabla(inscritosPorEvento.get(comboEventos.getSelectedItem()));

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private void actualizarTabla(String[][] datos) {
        modelo.setRowCount(0); // limpiar
        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
    }
}