package gui;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import persistencia.ConexionBD;

public class VentanaRegistroEstudiante extends JFrame {
    public VentanaRegistroEstudiante() {
        setTitle("Registrar Estudiante");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JTextField campoNombre = new JTextField(15);
        JTextField campoCodigo = new JTextField(15);
        JButton btnGuardar = new JButton("Guardar");

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText();
                String codigo = campoCodigo.getText();
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

        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Código:"));
        panel.add(campoCodigo);
        panel.add(btnGuardar);

        add(panel);
        setVisible(true);
    }
}