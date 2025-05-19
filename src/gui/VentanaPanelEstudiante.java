package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.PrintWriter;
import java.sql.*;

public class VentanaPanelEstudiante extends JFrame {
    private JTable tablaEventos;
    private DefaultTableModel modelo;
    private String correoEstudiante;

    public VentanaPanelEstudiante(String correo) {
        this.correoEstudiante = correo;
        setTitle("Panel del Estudiante");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");

        JMenuItem itemMisInscripciones = new JMenuItem("Mis inscripciones");
        JMenuItem itemExportar = new JMenuItem("Exportar mis eventos");
        JMenuItem itemCerrar = new JMenuItem("Cerrar sesión");

        itemMisInscripciones.addActionListener(e -> mostrarInscripciones());
        itemExportar.addActionListener(e -> exportarMisEventos());
        itemCerrar.addActionListener(e -> dispose());

        menu.add(itemMisInscripciones);
        menu.add(itemExportar);
        menu.add(itemCerrar);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JLabel bienvenida = new JLabel("¡Bienvenido estudiante: " + correo + "!", SwingConstants.CENTER);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        bienvenida.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(bienvenida, BorderLayout.NORTH);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Fecha", "Horas", "Tipo"});
        tablaEventos = new JTable(modelo);
        add(new JScrollPane(tablaEventos), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnInscribirse = new JButton("Inscribirse");
        JButton btnActualizar = new JButton("Actualizar");
        panelBotones.add(btnActualizar);
        panelBotones.add(btnInscribirse);
        add(panelBotones, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> cargarEventosDisponibles());
        btnInscribirse.addActionListener(e -> inscribirseEnEvento());

        cargarEventosDisponibles();
    }

    private void cargarEventosDisponibles() {
        modelo.setRowCount(0);
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT * FROM eventos";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDate("fecha"),
                    rs.getInt("horas"),
                    rs.getString("tipo")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar eventos.");
        }
    }

    private void inscribirseEnEvento() {
        int fila = tablaEventos.getSelectedRow();
        if (fila >= 0) {
            int idEvento = (int) modelo.getValueAt(fila, 0);
            try (Connection conn = ConexionBD.obtenerConexion()) {
                String sqlEst = "SELECT id FROM estudiantes WHERE correo = ?";
                PreparedStatement pstEst = conn.prepareStatement(sqlEst);
                pstEst.setString(1, correoEstudiante);
                ResultSet rsEst = pstEst.executeQuery();
                if (rsEst.next()) {
                    int idEstudiante = rsEst.getInt("id");

                    String checkSql = "SELECT COUNT(*) FROM inscripciones WHERE estudiante_id = ? AND evento_id = ?";
                    PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                    checkStmt.setInt(1, idEstudiante);
                    checkStmt.setInt(2, idEvento);
                    ResultSet rsCheck = checkStmt.executeQuery();
                    rsCheck.next();
                    if (rsCheck.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this, "Ya estás inscrito en este evento.");
                        return;
                    }

                    String insSql = "INSERT INTO inscripciones (estudiante_id, evento_id) VALUES (?, ?)";
                    PreparedStatement ps = conn.prepareStatement(insSql);
                    ps.setInt(1, idEstudiante);
                    ps.setInt(2, idEvento);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Inscripción exitosa.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al inscribirse.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un evento para inscribirte.");
        }
    }

    private void mostrarInscripciones() {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT e.nombre, e.fecha, e.horas, e.tipo FROM inscripciones i JOIN eventos e ON i.evento_id = e.id JOIN estudiantes s ON i.estudiante_id = s.id WHERE s.correo = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correoEstudiante);
            ResultSet rs = ps.executeQuery();
            StringBuilder sb = new StringBuilder("Tus eventos inscritos:\n");
            while (rs.next()) {
                sb.append("- ").append(rs.getString("nombre"))
                  .append(" | ").append(rs.getDate("fecha"))
                  .append(" | ").append(rs.getInt("horas")).append(" horas")
                  .append(" | ").append(rs.getString("tipo")).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar tus inscripciones.");
        }
    }

    private void exportarMisEventos() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Exportar a CSV");
        int opcion = fc.showSaveDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            try (Connection conn = ConexionBD.obtenerConexion();
                 PrintWriter writer = new PrintWriter(fc.getSelectedFile())) {
                String sql = "SELECT e.nombre, e.fecha, e.horas, e.tipo FROM inscripciones i JOIN eventos e ON i.evento_id = e.id JOIN estudiantes s ON i.estudiante_id = s.id WHERE s.correo = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, correoEstudiante);
                ResultSet rs = ps.executeQuery();

                writer.println("Nombre,Fecha,Horas,Tipo");
                while (rs.next()) {
                    writer.printf("%s,%s,%d,%s%n",
                            rs.getString("nombre"),
                            rs.getDate("fecha").toString(),
                            rs.getInt("horas"),
                            rs.getString("tipo"));
                }
                JOptionPane.showMessageDialog(this, "Eventos exportados con éxito.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al exportar.");
            }
        }
    }
}