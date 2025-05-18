import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VentanaLoginDocente extends JFrame {
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;

    public VentanaLoginDocente() {
        setTitle("Login Docente");
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
            if (autenticarDocente(correo, clave)) {
                JOptionPane.showMessageDialog(this, "Bienvenido Docente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            }
        });
    }

    private boolean autenticarDocente(String correo, String clave) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT * FROM docentes WHERE correo = ? AND contrasena = ?";
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