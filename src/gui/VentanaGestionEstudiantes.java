package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VentanaGestionEstudiantes extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField campoBusqueda;
    private JTextField campoHorasMin;
    private JTextField campoHorasMax;

    public VentanaGestionEstudiantes() {
        setTitle("Gestión de Estudiantes");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Administrar Estudiantes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(0, 102, 153));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Correo", "Horas Completadas"});

        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelFiltros = new JPanel(new GridLayout(2, 4, 10, 5));
        campoBusqueda = new JTextField();
        campoHorasMin = new JTextField();
        campoHorasMax = new JTextField();
        JButton btnBuscar = new JButton("Buscar");

        panelFiltros.add(new JLabel("Buscar por nombre o correo:"));
        panelFiltros.add(campoBusqueda);
        panelFiltros.add(new JLabel("Horas mínimas:"));
        panelFiltros.add(campoHorasMin);
        panelFiltros.add(new JLabel("Horas máximas:"));
        panelFiltros.add(campoHorasMax);
        panelFiltros.add(btnBuscar);

        add(panelFiltros, BorderLayout.SOUTH);

        JPanel panelBotones = new JPanel();
        JButton btnCargar = new JButton("Cargar Todo");
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnCargar);
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.NORTH);

        btnCargar.addActionListener(e -> cargarEstudiantes("", "", ""));
        btnBuscar.addActionListener(e -> {
            String texto = campoBusqueda.getText().trim();
            String minHoras = campoHorasMin.getText().trim();
            String maxHoras = campoHorasMax.getText().trim();
            cargarEstudiantes(texto, minHoras, maxHoras);
        });
        btnNuevo.addActionListener(e -> crearEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());

        cargarEstudiantes("", "", "");
    }

    private void crearEstudiante() {
        JTextField nombre = new JTextField();
        JTextField correo = new JTextField();
        JTextField contrasena = new JTextField();
        Object[] campos = {
            "Nombre:", nombre,
            "Correo:", correo,
            "Contraseña:", contrasena
        };
        int opcion = JOptionPane.showConfirmDialog(this, campos, "Nuevo Estudiante", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try (Connection conn = ConexionBD.obtenerConexion()) {
                String consulta = "SELECT COUNT(*) FROM estudiantes WHERE correo = ?";
                PreparedStatement check = conn.prepareStatement(consulta);
                check.setString(1, correo.getText().trim());
                ResultSet rs = check.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "Ya existe un estudiante con ese correo.");
                    return;
                }

                String sql = "INSERT INTO estudiantes (nombre, correo, contrasena, horas_completadas) VALUES (?, ?, ?, 0)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, nombre.getText().trim());
                ps.setString(2, correo.getText().trim());
                ps.setString(3, contrasena.getText().trim());
                ps.executeUpdate();
                cargarEstudiantes("", "", "");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al insertar estudiante.");
            }
        }
    }

    private void eliminarEstudiante() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelo.getValueAt(fila, 0);
            try (Connection conn = ConexionBD.obtenerConexion()) {
                String sql = "DELETE FROM estudiantes WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                cargarEstudiantes("", "", "");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar estudiante.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un estudiante para eliminar.");
        }
    }

    private void cargarEstudiantes(String filtro, String minHoras, String maxHoras) {
        modelo.setRowCount(0);
        try (Connection conn = ConexionBD.obtenerConexion()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM estudiantes WHERE 1=1");
            if (!filtro.isEmpty()) {
                sql.append(" AND (LOWER(nombre) LIKE ? OR LOWER(correo) LIKE ?)");
            }
            if (!minHoras.isEmpty()) {
                sql.append(" AND horas_completadas >= ?");
            }
            if (!maxHoras.isEmpty()) {
                sql.append(" AND horas_completadas <= ?");
            }
            sql.append(" ORDER BY id");

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int index = 1;
            if (!filtro.isEmpty()) {
                String f = "%" + filtro.toLowerCase() + "%";
                ps.setString(index++, f);
                ps.setString(index++, f);
            }
            if (!minHoras.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(minHoras));
            }
            if (!maxHoras.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(maxHoras));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getInt("horas_completadas")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar estudiantes.");
        }
    }
}