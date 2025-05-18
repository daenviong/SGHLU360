package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class VentanaLoginEstudiante extends JFrame {
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;

    public VentanaLoginEstudiante() {
        setTitle("Login Estudiante");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Correo:"));
        campoCorreo = new JTextField();
        add(campoCorreo);

        add(new JLabel("Contraseña:"));
        campoContrasena = new JPasswordField();
        add(campoContrasena);

        JButton btnIngresar = new JButton("Ingresar");
        add(btnIngresar);

        btnIngresar.addActionListener(e -> {
            String correo = campoCorreo.getText();
            String clave = new String(campoContrasena.getPassword());
            if (autenticarEstudiante(correo, clave)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            }
        });
    }

    private boolean autenticarEstudiante(String correo, String clave) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT * FROM estudiantes WHERE correo = ? AND contrasena = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}