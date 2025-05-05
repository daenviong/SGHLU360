package gui;

import javax.swing.*;
import java.sql.*;
import persistencia.ConexionBD;

public class VentanaEventos extends JFrame {
    public VentanaEventos() {
        setTitle("Eventos disponibles");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);
        JScrollPane scroll = new JScrollPane(lista);
        JTextField campoCodigo = new JTextField(10);
        JButton btnInscribir = new JButton("Inscribirse");

        try (Connection conn = ConexionBD.conectar()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM eventos");
            while (rs.next()) {
                modelo.addElement(rs.getInt("id") + " - " + rs.getString("nombre") + " (" + rs.getInt("horas") + "h)");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error cargando eventos: " + e.getMessage());
        }

        btnInscribir.addActionListener(e -> {
            String seleccion = lista.getSelectedValue();
            String codigo = campoCodigo.getText();
            if (seleccion != null && !codigo.isEmpty()) {
                int idEvento = Integer.parseInt(seleccion.split(" - ")[0]);
                try (Connection conn = ConexionBD.conectar()) {
                    PreparedStatement pstEst = conn.prepareStatement("SELECT id FROM estudiantes WHERE codigo=?");
                    pstEst.setString(1, codigo);
                    ResultSet rs = pstEst.executeQuery();
                    if (rs.next()) {
                        int idEst = rs.getInt("id");

                        PreparedStatement ins = conn.prepareStatement("INSERT INTO inscripciones(estudiante_id, evento_id) VALUES (?, ?)");
                        ins.setInt(1, idEst);
                        ins.setInt(2, idEvento);
                        ins.executeUpdate();

                        PreparedStatement pstH = conn.prepareStatement("UPDATE estudiantes SET horas = horas + (SELECT horas FROM eventos WHERE id=?) WHERE id=?");
                        pstH.setInt(1, idEvento);
                        pstH.setInt(2, idEst);
                        pstH.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Inscripción exitosa.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Estudiante no encontrado.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al inscribir: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un evento y escriba su código.");
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Código estudiante:"));
        panel.add(campoCodigo);
        panel.add(btnInscribir);

        add(scroll, "Center");
        add(panel, "South");
        setVisible(true);
    }
}