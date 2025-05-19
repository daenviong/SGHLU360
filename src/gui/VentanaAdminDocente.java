package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.PrintWriter;
import java.sql.*;

public class VentanaAdminDocente extends JFrame {
    private JTable tablaEventos;
    private DefaultTableModel modelo;
    private JTextField campoBusqueda;
    private JTextField campoFechaDesde;
    private JTextField campoFechaHasta;

    public VentanaAdminDocente() {
        setTitle("Panel de Administración - Docentes");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel logo = new JLabel();
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icono = new ImageIcon("resources/logo.png");
            Image imagen = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(imagen));
        } catch (Exception e) {
            logo.setText("SGHLU");
        }
        add(logo, BorderLayout.NORTH);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Descripción", "Tipo", "Fecha", "Horas"});
        tablaEventos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaEventos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelFiltros = new JPanel(new GridLayout(2, 4, 10, 5));
        campoBusqueda = new JTextField();
        campoFechaDesde = new JTextField();
        campoFechaHasta = new JTextField();
        JButton btnBuscar = new JButton("Buscar");

        panelFiltros.add(new JLabel("Buscar nombre o tipo:"));
        panelFiltros.add(campoBusqueda);
        panelFiltros.add(new JLabel("Fecha desde (YYYY-MM-DD):"));
        panelFiltros.add(campoFechaDesde);
        panelFiltros.add(new JLabel("Fecha hasta (YYYY-MM-DD):"));
        panelFiltros.add(campoFechaHasta);
        panelFiltros.add(btnBuscar);
        add(panelFiltros, BorderLayout.SOUTH);

        JPanel panelBotones = new JPanel();
        JButton btnCargar = new JButton("Cargar Eventos");
        JButton btnCrear = new JButton("Crear Evento");
        JButton btnEliminar = new JButton("Eliminar Evento");
        JButton btnInscritos = new JButton("Ver Inscritos");
        JButton btnEstadisticas = new JButton("Estadísticas");
        JButton btnDocentes = new JButton("Gestionar Docentes");
        JButton btnEstudiantes = new JButton("Gestionar Estudiantes");

        panelBotones.add(btnCargar);
        panelBotones.add(btnCrear);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnInscritos);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnDocentes);
        panelBotones.add(btnEstudiantes);
// ... (código de la clase)
        JButton btnExportarCSV = new JButton("Exportar a CSV");
        panelBotones.add(btnExportarCSV);

        btnExportarCSV.addActionListener(e -> exportarEventosCSV());
        add(panelBotones, BorderLayout.NORTH);

        btnCargar.addActionListener(e -> cargarEventos("", "", ""));
        btnBuscar.addActionListener(e -> {
            String texto = campoBusqueda.getText().trim();
            String desde = campoFechaDesde.getText().trim();
            String hasta = campoFechaHasta.getText().trim();
            cargarEventos(texto, desde, hasta);
        });

        btnCrear.addActionListener(e -> crearEvento());
        btnEliminar.addActionListener(e -> eliminarEvento());
        btnInscritos.addActionListener(e -> verInscritos());
        btnEstadisticas.addActionListener(e -> verEstadisticas());
        btnDocentes.addActionListener(e -> new VentanaGestionDocentes().setVisible(true));
        btnEstudiantes.addActionListener(e -> new VentanaGestionEstudiantes().setVisible(true));

        cargarEventos("", "", "");
    }

    private void cargarEventos(String filtro, String desde, String hasta) {
        modelo.setRowCount(0);
        try (Connection conn = ConexionBD.obtenerConexion()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM eventos WHERE 1=1");
            if (!filtro.isEmpty()) {
                sql.append(" AND (LOWER(nombre) LIKE ? OR LOWER(tipo) LIKE ?)");
            }
            if (!desde.isEmpty()) {
                sql.append(" AND fecha >= ?");
            }
            if (!hasta.isEmpty()) {
                sql.append(" AND fecha <= ?");
            }
            sql.append(" ORDER BY fecha");

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int index = 1;
            if (!filtro.isEmpty()) {
                String f = "%" + filtro.toLowerCase() + "%";
                ps.setString(index++, f);
                ps.setString(index++, f);
            }
            if (!desde.isEmpty()) {
                ps.setDate(index++, Date.valueOf(desde));
            }
            if (!hasta.isEmpty()) {
                ps.setDate(index++, Date.valueOf(hasta));
            }

            ResultSet rs = ps.executeQuery();
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
            JOptionPane.showMessageDialog(this, "Error al cargar eventos.");
        }
    }

    private void crearEvento() {
        JTextField nombre = new JTextField();
        JTextField descripcion = new JTextField();
        JTextField tipo = new JTextField();
        JTextField fecha = new JTextField("2025-06-01");
        JTextField horas = new JTextField();

        Object[] campos = {
            "Nombre:", nombre,
            "Descripción:", descripcion,
            "Tipo:", tipo,
            "Fecha (YYYY-MM-DD):", fecha,
            "Horas:", horas
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Nuevo Evento", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try (Connection conn = ConexionBD.obtenerConexion()) {
                String sql = "INSERT INTO eventos (nombre, descripcion, tipo, fecha, horas) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, nombre.getText().trim());
                ps.setString(2, descripcion.getText().trim());
                ps.setString(3, tipo.getText().trim());
                ps.setDate(4, Date.valueOf(fecha.getText().trim()));
                ps.setInt(5, Integer.parseInt(horas.getText().trim()));
                ps.executeUpdate();
                cargarEventos("", "", "");
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
                cargarEventos("", "", "");
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
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT e.nombre, e.correo FROM inscripciones i JOIN estudiantes e ON i.estudiante_id = e.id WHERE i.evento_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idEvento);
            ResultSet rs = ps.executeQuery();

            StringBuilder resultado = new StringBuilder("Estudiantes inscritos:\n");
            while (rs.next()) {
                resultado.append("- ")
                         .append(rs.getString("nombre"))
                         .append(" (")
                         .append(rs.getString("correo"))
                         .append(")\n");
            }

            JOptionPane.showMessageDialog(this, resultado.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar inscritos.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecciona un evento.");
    }
}
private void verEstadisticas() {
    try (Connection conn = ConexionBD.obtenerConexion()) {
        Statement stmt = conn.createStatement();
        ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM eventos");
        rs1.next();
        int totalEventos = rs1.getInt(1);

        ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) FROM inscripciones");
        rs2.next();
        int totalInscripciones = rs2.getInt(1);

        ResultSet rs3 = stmt.executeQuery("SELECT SUM(horas) FROM eventos");
        rs3.next();
        int totalHoras = rs3.getInt(1);

        String mensaje = "Total de eventos: " + totalEventos +
                         "\\nTotal de inscripciones: " + totalInscripciones +
                         "\\nHoras totales acumuladas: " + totalHoras;
        JOptionPane.showMessageDialog(this, mensaje, "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener estadísticas.");
    }
}
private void exportarEventosCSV() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar como CSV");
    int seleccion = fileChooser.showSaveDialog(this);
    if (seleccion == JFileChooser.APPROVE_OPTION) {
        try (PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile())) {
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                writer.print(modelo.getColumnName(i));
                if (i < modelo.getColumnCount() - 1) writer.print(",");
            }
            writer.println();
            for (int fila = 0; fila < modelo.getRowCount(); fila++) {
                for (int col = 0; col < modelo.getColumnCount(); col++) {
                    writer.print(modelo.getValueAt(fila, col));
                    if (col < modelo.getColumnCount() - 1) writer.print(",");
                }
                writer.println();
            }
            JOptionPane.showMessageDialog(this, "Exportación exitosa.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al exportar CSV.");
        }
    }
}

}