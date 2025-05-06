package gui;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("SGHLU - Sistema de Gestión de Horas Libres UNAB");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JMenuBar barraMenu = new JMenuBar();

        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemInicio = new JMenuItem("Inicio");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemInicio);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        // Menú Estudiantes
        JMenu menuEstudiantes = new JMenu("Estudiantes");
        JMenuItem itemRegistrarEst = new JMenuItem("➕ Registrar estudiante");
        itemRegistrarEst.addActionListener(e -> new VentanaRegistroEstudiante());
        JMenuItem itemVerEst = new JMenuItem("📋 Ver estudiantes");
        itemVerEst.addActionListener(e -> new VentanaVerEstudiantes());
        JMenuItem itemHistorial = new JMenuItem("🧾 Ver historial de inscripciones");
        itemHistorial.addActionListener(e -> new VentanaVerHistorial());
        menuEstudiantes.add(itemRegistrarEst);
        menuEstudiantes.add(itemVerEst);
        menuEstudiantes.addSeparator();
        menuEstudiantes.add(itemHistorial);

        // Menú Eventos
        JMenu menuEventos = new JMenu("Eventos");
        JMenuItem itemCrearEvento = new JMenuItem("➕ Crear evento");
        itemCrearEvento.addActionListener(e -> new VentanaCrearEvento());
        JMenuItem itemVerEventos = new JMenuItem("📅 Ver e inscribirse");
        itemVerEventos.addActionListener(e -> new VentanaEventos());
        JMenuItem itemInscritos = new JMenuItem("👥 Ver inscritos por evento");
        itemInscritos.addActionListener(e -> new VentanaVerInscritos());
        menuEventos.add(itemCrearEvento);
        menuEventos.add(itemVerEventos);
        menuEventos.addSeparator();
        menuEventos.add(itemInscritos);

        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemExportar = new JMenuItem("⬇️ Exportar reporte a CSV");
        itemExportar.addActionListener(e -> new VentanaExportarCSV());
        menuReportes.add(itemExportar);

        barraMenu.add(menuArchivo);
        barraMenu.add(menuEstudiantes);
        barraMenu.add(menuEventos);
        barraMenu.add(menuReportes);
        setJMenuBar(barraMenu);

        // Logo y bienvenida
        ImageIcon icono = new ImageIcon("resources/logo.png");
        JLabel logo = new JLabel(icono);
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel mensaje = new JLabel("Bienvenido al Sistema SGHLU", SwingConstants.CENTER);
        mensaje.setFont(new Font("SansSerif", Font.BOLD, 18));
        mensaje.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(logo, BorderLayout.NORTH);
        panelCentro.add(mensaje, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        setVisible(true);
    }
}