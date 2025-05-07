package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class VentanaEventos extends JFrame {
    public VentanaEventos() {
        setTitle("Ver e Inscribirse a Eventos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con campo de código
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBackground(Color.WHITE);
        JLabel lblCodigo = new JLabel("Código del estudiante:");
        JTextField campoCodigo = new JTextField("2024101001", 15);
        JButton btnInscribir = new JButton("Inscribirse");
        btnInscribir.setBackground(new Color(255, 102, 0));
        btnInscribir.setForeground(Color.WHITE);
        btnInscribir.setFocusPainted(false);
        panelSuperior.add(lblCodigo);
        panelSuperior.add(campoCodigo);
        panelSuperior.add(btnInscribir);

        // Tabla de eventos
        String[] columnas = {"ID", "Evento", "Tipo", "Fecha", "Horas"};
        String[][] datos = {
            {"1", "Taller de Robótica", "Tecnología", "2025-05-10", "2"},
            {"2", "Club de Lectura", "Académico", "2025-05-11", "1"},
            {"3", "Charla de Innovación", "Tecnología", "2025-05-12", "2"},
            {"4", "Hackathon UNAB", "Competencia", "2025-05-13", "4"},
            {"5", "Cine Foro", "Cultural", "2025-05-14", "1"},
            {"6", "Feria de Ciencias", "Tecnología", "2025-05-15", "3"},
            {"7", "Expo Proyectos", "Académico", "2025-05-16", "2"},
            {"8", "Concurso de Ensayo", "Humanidades", "2025-05-17", "1"},
            {"9", "Torneo de Ajedrez", "Deportivo", "2025-05-18", "2"},
            {"10", "Encuentro de Liderazgo", "Desarrollo", "2025-05-19", "2"}
        };

        JTable tabla = new JTable(datos, columnas);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setSelectionBackground(new Color(255, 204, 153));
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(255, 102, 0));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnInscribir.addActionListener(e -> {
            String codigo = campoCodigo.getText().trim();
            int fila = tabla.getSelectedRow();
            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese el código del estudiante.");
            } else if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un evento.");
            } else {
                String evento = tabla.getValueAt(fila, 1).toString();
                JOptionPane.showMessageDialog(null, "Inscripción exitosa al evento: " + evento);
            }
        });

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }
}