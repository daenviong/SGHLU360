package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import persistencia.ConexionBD;

public class VentanaRegistroEstudiante extends JFrame {
    public VentanaRegistroEstudiante() {
        setTitle("Registrar Estudiante");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelTitulo = new JLabel("Formulario de Registro");
        labelTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(labelTitulo, gbc);

        gbc.gridwidth = 1;

        JLabel lblNombre = new JLabel("Nombre:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblNombre, gbc);
        JTextField campoNombre = new JTextField(15);
        gbc.gridx = 1;
        panel.add(campoNombre, gbc);

        JLabel lblCodigo = new JLabel("Código:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblCodigo, gbc);
        JTextField campoCodigo = new JTextField(15);
        gbc.gridx = 1;
        panel.add(campoCodigo, gbc);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(255, 102, 0));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnGuardar, gbc);

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText().trim();
                String codigo = campoCodigo.getText().trim();
                if (!nombre.isEmpty() && !codigo.isEmpty()) {
                    try (Connection conn = ConexionBD.conectar()) {
                        PreparedStatement stmt = conn.prepareStatement("INSERT INTO estudiantes(nombre, codigo) VALUES (?, ?)");
                        stmt.setString(1, nombre);
                        stmt.setString(2, codigo);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Estudiante registrado correctamente.");
                        dispose();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al registrar: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos.");
                }
            }
        });

        add(panel);
        setVisible(true);
    }
}