package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VentanaListaDocentes extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaListaDocentes() {
        setTitle("Lista de Docentes");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Correo"});

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarDocentes();
    }

    private void cargarDocentes() {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT id, nombre, correo FROM docentes";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar docentes.");
            e.printStackTrace();
        }
    }
}