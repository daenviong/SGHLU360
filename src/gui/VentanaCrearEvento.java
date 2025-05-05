package gui;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import persistencia.ConexionBD;

public class VentanaCrearEvento extends JFrame {
    public VentanaCrearEvento() {
        setTitle("Crear Evento");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JTextField campoNombre = new JTextField(15);
        JTextField campoTipo = new JTextField(15);
        JTextField campoFecha = new JTextField(10);
        JTextField campoHoras = new JTextField(5);
        JButton btnCrear = new JButton("Crear");

        btnCrear.addActionListener(e -> {
            try (Connection conn = ConexionBD.conectar()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO eventos(nombre, tipo, fecha, horas) VALUES (?, ?, ?, ?)");
                stmt.setString(1, campoNombre.getText());
                stmt.setString(2, campoTipo.getText());
                stmt.setDate(3, Date.valueOf(campoFecha.getText()));
                stmt.setInt(4, Integer.parseInt(campoHoras.getText()));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Evento creado correctamente.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("Nombre:")); panel.add(campoNombre);
        panel.add(new JLabel("Tipo:")); panel.add(campoTipo);
        panel.add(new JLabel("Fecha (YYYY-MM-DD):")); panel.add(campoFecha);
        panel.add(new JLabel("Horas:")); panel.add(campoHoras);
        panel.add(btnCrear);

        add(panel);
        setVisible(true);
    }
}