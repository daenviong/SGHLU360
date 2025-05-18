package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class VentanaLoginEstudiante extends JFrame {
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;

    public VentanaLoginEstudiante() {
        setTitle("Login Estudiante");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Inicio de Sesión - Estudiante", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 153));
        add(lblTitulo, BorderLayout.NORTH);

        JLabel lblCorreo = new JLabel("Correo:");
        campoCorreo = new JTextField(20);

        JLabel lblContrasena = new JLabel("Contraseña:");
        campoContrasena = new JPasswordField(20);

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(0, 153, 76));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0; gbc.gridy = 0;
        panelPrincipal.add(lblCorreo, gbc);
        gbc.gridx = 1;
        panelPrincipal.add(campoCorreo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelPrincipal.add(lblContrasena, gbc);
        gbc.gridx = 1;
        panelPrincipal.add(campoContrasena, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panelPrincipal.add(btnIngresar, gbc);

        add(panelPrincipal, BorderLayout.CENTER);

        btnIngresar.addActionListener(e -> {
            String correo = campoCorreo.getText().trim();
            String clave = new String(campoContrasena.getPassword()).trim();
            if (autenticarEstudiante(correo, clave)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
                new VentanaEstudiante(correo).setVisible(true);
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