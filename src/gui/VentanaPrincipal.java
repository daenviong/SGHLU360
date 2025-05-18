package gui;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("SGHLU - Sistema de Gestión de Horas Libres UNAB");
        setSize(900, 600);
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
        JMenuItem itemRegistrarEst = new JMenuItem("Registrar estudiante");
        itemRegistrarEst.addActionListener(e -> new VentanaRegistroEstudiante());
        JMenuItem itemVerEst = new JMenuItem("Ver estudiantes");
        itemVerEst.addActionListener(e -> new VentanaVerEstudiantes());
        JMenuItem itemHistorial = new JMenuItem("Ver historial de inscripciones");
        itemHistorial.addActionListener(e -> new VentanaVerHistorial());
        menuEstudiantes.add(itemRegistrarEst);
        menuEstudiantes.add(itemVerEst);
        menuEstudiantes.addSeparator();
        menuEstudiantes.add(itemHistorial);

        // Menú Eventos
        JMenu menuEventos = new JMenu("Eventos");
        JMenuItem itemCrearEvento = new JMenuItem("Crear evento");
        itemCrearEvento.addActionListener(e -> new VentanaCrearEvento());
        JMenuItem itemVerEventos = new JMenuItem("Ver e inscribirse");
        itemVerEventos.addActionListener(e -> new VentanaEventos());
        JMenuItem itemInscritos = new JMenuItem("Ver inscritos por evento");
        itemInscritos.addActionListener(e -> new VentanaVerInscritos());
        menuEventos.add(itemCrearEvento);
        menuEventos.add(itemVerEventos);
        menuEventos.addSeparator();
        menuEventos.add(itemInscritos);

        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReporteCSV = new JMenuItem("Exportar a CSV");
        itemReporteCSV.addActionListener(e -> new VentanaExportarCSV());
        JMenuItem itemReporteFechas = new JMenuItem("Ver inscripciones por fechas");
        itemReporteFechas.addActionListener(e -> new VentanaReportePorFechas());
        JMenuItem itemHorasAcumuladas = new JMenuItem("Horas acumuladas por estudiante");
        itemHorasAcumuladas.addActionListener(e -> new VentanaHorasAcumuladas());
        menuReportes.add(itemReporteCSV);
        menuReportes.addSeparator();
        menuReportes.add(itemReporteFechas);
        menuReportes.add(itemHorasAcumuladas);

        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de SGHLU");
        itemAcerca.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "SGHLU - Sistema de Gestión de Horas Libres UNAB\n" +
                "Versión: 1.0\n" +
                "Desarrollado por SGHLU Corporation",
                "Acerca de SGHLU",
                JOptionPane.INFORMATION_MESSAGE);
        });
        menuAyuda.add(itemAcerca);

        barraMenu.add(menuArchivo);
        barraMenu.add(menuEstudiantes);
        barraMenu.add(menuEventos);
        barraMenu.add(menuReportes);
        barraMenu.add(menuAyuda);
        setJMenuBar(barraMenu);

        ImageIcon icono = new ImageIcon("resources/logo.png");
        JLabel logo = new JLabel(icono);
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel mensaje = new JLabel("BIenvenido al Sistema SGHLU", SwingConstants.CENTER);
        mensaje.setFont(new Font("SansSerif", Font.BOLD, 18));
        mensaje.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(logo, BorderLayout.NORTH);
        panelCentro.add(mensaje, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        setVisible(true);
    }
}