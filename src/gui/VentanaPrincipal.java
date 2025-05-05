package gui;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("SGHLU - Sistema de Gestión de Horas Libres UNAB");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar barraMenu = new JMenuBar();

        // Menú Estudiantes
        JMenu menuEstudiante = new JMenu("Estudiantes");
        JMenuItem itemRegistrarEst = new JMenuItem("Registrar estudiante");
        itemRegistrarEst.addActionListener(e -> new VentanaRegistroEstudiante());
        JMenuItem itemVerEst = new JMenuItem("Ver estudiantes");
        itemVerEst.addActionListener(e -> new VentanaVerEstudiantes());
        menuEstudiante.add(itemRegistrarEst);
        menuEstudiante.add(itemVerEst);

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
        menuEventos.add(itemInscritos);

        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemVerHistorial = new JMenuItem("Ver inscripciones por estudiante");
        itemVerHistorial.addActionListener(e -> new VentanaVerHistorial());
        JMenuItem itemExportarCSV = new JMenuItem("Exportar a CSV");
        itemExportarCSV.addActionListener(e -> new VentanaExportarCSV());
        menuReportes.add(itemVerHistorial);
        menuReportes.add(itemExportarCSV);

        // Menú Sistema
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuSistema.add(itemSalir);

        // Agregar todos los menús
        barraMenu.add(menuEstudiante);
        barraMenu.add(menuEventos);
        barraMenu.add(menuReportes);
        barraMenu.add(menuSistema);
        setJMenuBar(barraMenu);

        JLabel bienvenida = new JLabel("Bienvenido a SGHLU", SwingConstants.CENTER);
        add(bienvenida);

        setVisible(true);
    }
}