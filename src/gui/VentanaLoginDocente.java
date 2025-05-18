package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class VentanaLoginDocente extends JFrame {
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;

    public VentanaLoginDocente() {
        setTitle("Login Docente");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Inicio de Sesión - Docente", SwingConstants.CENTER);
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
            System.out.println("🔐 Intento de login con: " + correo);

            if (autenticarDocente(correo, clave)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
                new VentanaAdminDocente().setVisible(true);
                new VentanaListaDocentes().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            }
        });
    }

    private boolean autenticarDocente(String correo, String clave) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            System.out.println("✅ Conexión establecida con la base de datos");

            String sql = "SELECT * FROM docentes WHERE correo = ? AND contrasena = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, clave);
            System.out.println("🔍 Ejecutando consulta para: " + correo);

            ResultSet rs = ps.executeQuery();
            boolean existe = rs.next();
            System.out.println("🔎 Resultado de búsqueda: " + existe);

            return existe;
        } catch (Exception ex) {
            System.err.println("❌ Error de conexión o consulta: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}