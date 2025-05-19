package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VentanaGestionDocentes extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField campoBusqueda;

    public VentanaGestionDocentes() {
        setTitle("Gestión de Docentes");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Administrar Docentes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(0, 102, 153));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Correo"});

        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBusqueda = new JPanel(new BorderLayout());
        campoBusqueda = new JTextField();
        JButton btnBuscar = new JButton("Buscar");

        panelBusqueda.add(new JLabel("Buscar por nombre o correo:"), BorderLayout.WEST);
        panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
        panelBusqueda.add(btnBuscar, BorderLayout.EAST);

        add(panelBusqueda, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        JButton btnCargar = new JButton("Cargar Todo");
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnCargar);
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        btnCargar.addActionListener(e -> cargarDocentes(""));
        btnBuscar.addActionListener(e -> cargarDocentes(campoBusqueda.getText().trim()));
        btnNuevo.addActionListener(e -> crearDocente());
        btnEliminar.addActionListener(e -> eliminarDocente());

        cargarDocentes("");
    }

    private void crearDocente() {
        JTextField nombre = new JTextField();
        JTextField correo = new JTextField();
        JTextField contrasena = new JTextField();
        Object[] campos = {
            "Nombre:", nombre,
            "Correo:", correo,
            "Contraseña:", contrasena
        };
        int opcion = JOptionPane.showConfirmDialog(this, campos, "Nuevo Docente", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try (Connection conn = ConexionBD.obtenerConexion()) {
                String consulta = "SELECT COUNT(*) FROM docentes WHERE correo = ?";
                PreparedStatement check = conn.prepareStatement(consulta);
                check.setString(1, correo.getText().trim());
                ResultSet rs = check.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "Ya existe un docente con ese correo.");
                    return;
                }

                String sql = "INSERT INTO docentes (nombre, correo, contrasena) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, nombre.getText().trim());
                ps.setString(2, correo.getText().trim());
                ps.setString(3, contrasena.getText().trim());
                ps.executeUpdate();
                cargarDocentes("");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al insertar docente.");
            }
        }
    }

    private void eliminarDocente() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelo.getValueAt(fila, 0);
            try (Connection conn = ConexionBD.obtenerConexion()) {
                String sql = "DELETE FROM docentes WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                cargarDocentes("");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar docente.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un docente para eliminar.");
        }
    }

    private void cargarDocentes(String filtro) {
        modelo.setRowCount(0);
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT id, nombre, correo FROM docentes";
            if (!filtro.isEmpty()) {
                sql += " WHERE LOWER(nombre) LIKE ? OR LOWER(correo) LIKE ?";
            }
            sql += " ORDER BY id";

            PreparedStatement ps = conn.prepareStatement(sql);
            if (!filtro.isEmpty()) {
                String f = "%" + filtro.toLowerCase() + "%";
                ps.setString(1, f);
                ps.setString(2, f);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar docentes.");
        }
    }
}