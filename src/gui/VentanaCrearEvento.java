package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import persistencia.ConexionBD;

public class VentanaCrearEvento extends JFrame {
    public VentanaCrearEvento() {
        setTitle("Crear Evento");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Formulario de Evento");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nombre:"), gbc);
        JTextField nombre = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Tipo:"), gbc);
        JTextField tipo = new JTextField(15);
        gbc.gridx = 1;
        panel.add(tipo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        JTextField fecha = new JTextField(15);
        gbc.gridx = 1;
        panel.add(fecha, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Horas:"), gbc);
        JTextField horas = new JTextField(15);
        gbc.gridx = 1;
        panel.add(horas, gbc);

        JButton crear = new JButton("Crear");
        crear.setBackground(new Color(255,102,0));
        crear.setForeground(Color.WHITE);
        crear.setFocusPainted(false);
        crear.setFont(new Font("SansSerif", Font.BOLD, 14));
        crear.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(crear, gbc);

        crear.addActionListener(e -> {
            try (Connection conn = ConexionBD.conectar()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO eventos(nombre, tipo, fecha, horas) VALUES (?, ?, ?, ?)");
                stmt.setString(1, nombre.getText().trim());
                stmt.setString(2, tipo.getText().trim());
                stmt.setDate(3, Date.valueOf(fecha.getText().trim()));
                stmt.setInt(4, Integer.parseInt(horas.getText().trim()));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Evento creado correctamente.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        add(panel);
        setVisible(true);
    }
}