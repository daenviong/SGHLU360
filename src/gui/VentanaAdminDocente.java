import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class VentanaAdminDocente extends JFrame {
    private JTable tablaEventos;
    private DefaultTableModel modelo;

    public VentanaAdminDocente() {
        setTitle("Panel de Administración - Docentes");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Descripción", "Tipo", "Fecha", "Horas"});

        tablaEventos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaEventos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnCargar = new JButton("Cargar Eventos");
        JButton btnCrear = new JButton("Crear Evento");
        JButton btnEliminar = new JButton("Eliminar Evento");
        JButton btnInscritos = new JButton("Ver Inscritos");
        JButton btnEstadisticas = new JButton("Estadísticas");

        panelBotones.add(btnCargar);
        panelBotones.add(btnCrear);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnInscritos);
        panelBotones.add(btnEstadisticas);

        add(panelBotones, BorderLayout.SOUTH);

        btnCargar.addActionListener(e -> cargarEventos());
        btnCrear.addActionListener(e -> crearEvento());
        btnEliminar.addActionListener(e -> eliminarEvento());
        btnInscritos.addActionListener(e -> verInscritos());
        btnEstadisticas.addActionListener(e -> verEstadisticas());
    }

    private void cargarEventos() {
        modelo.setRowCount(0);
        try (Connection conn = ConexionBD.obtenerConexion()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM eventos ORDER BY fecha");
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("tipo"),
                    rs.getDate("fecha"),
                    rs.getInt("horas")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void crearEvento() {
        JTextField nombre = new JTextField();
        JTextField descripcion = new JTextField();
        JTextField tipo = new JTextField();
        JTextField fecha = new JTextField();
        JTextField horas = new JTextField();
        Object[] campos = {
            "Nombre:", nombre,
            "Descripción:", descripcion,
            "Tipo:", tipo,
            "Fecha (yyyy-mm-dd):", fecha,
            "Horas:", horas
        };
        int opcion = JOptionPane.showConfirmDialog(this, campos, "Nuevo Evento", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try (Connection conn = ConexionBD.obtenerConexion()) {
                String sql = "INSERT INTO eventos (nombre, descripcion, tipo, fecha, horas) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, nombre.getText());
                ps.setString(2, descripcion.getText());
                ps.setString(3, tipo.getText());
                ps.setDate(4, Date.valueOf(fecha.getText()));
                ps.setInt(5, Integer.parseInt(horas.getText()));
                ps.executeUpdate();
                cargarEventos();
                JOptionPane.showMessageDialog(this, "Evento creado correctamente.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al crear el evento.");
            }
        }
    }

    private void eliminarEvento() {
        int fila = tablaEventos.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelo.getValueAt(fila, 0);
            try (Connection conn = ConexionBD.obtenerConexion()) {
                String sql = "DELETE FROM eventos WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                cargarEventos();
                JOptionPane.showMessageDialog(this, "Evento eliminado.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar evento.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un evento para eliminar.");
        }
    }

    private void verInscritos() {
        int fila = tablaEventos.getSelectedRow();
        if (fila >= 0) {
            int idEvento = (int) modelo.getValueAt(fila, 0);
            StringBuilder inscritos = new StringBuilder("Inscritos al evento:\n");
            try (Connection conn = ConexionBD.obtenerConexion()) {
                String sql = "SELECT e.nombre, e.correo FROM inscripciones i JOIN estudiantes e ON i.estudiante_id = e.id WHERE i.evento_id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idEvento);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    inscritos.append("- ").append(rs.getString("nombre")).append(" (").append(rs.getString("correo")).append(")\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                inscritos.append("Error al obtener inscritos.");
            }
            JOptionPane.showMessageDialog(this, inscritos.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un evento.");
        }
    }

    private void verEstadisticas() {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT COUNT(*) AS total_eventos FROM eventos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int totalEventos = rs.next() ? rs.getInt("total_eventos") : 0;

            sql = "SELECT COUNT(*) AS total_inscripciones FROM inscripciones";
            rs = stmt.executeQuery(sql);
            int totalInscritos = rs.next() ? rs.getInt("total_inscripciones") : 0;

            sql = "SELECT SUM(ev.horas) AS total_horas FROM eventos ev JOIN inscripciones ins ON ev.id = ins.evento_id";
            rs = stmt.executeQuery(sql);
            int totalHoras = rs.next() ? rs.getInt("total_horas") : 0;

            JOptionPane.showMessageDialog(this,
                "Estadísticas generales:\n" +
                "- Total de eventos: " + totalEventos + "\n" +
                "- Total de inscripciones: " + totalInscritos + "\n" +
                "- Total de horas otorgadas: " + totalHoras
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener estadísticas.");
        }
    }
}