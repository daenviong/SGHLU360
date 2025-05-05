package gui;

import javax.swing.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("SGHLU - Sistema de Gestión de Horas Libres UNAB");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar barraMenu = new JMenuBar();

        JMenu menuEstudiante = new JMenu("Estudiante");
        JMenuItem itemRegistrar = new JMenuItem("Registrar estudiante");
        itemRegistrar.addActionListener(e -> new VentanaRegistroEstudiante());
        menuEstudiante.add(itemRegistrar);

        JMenu menuEventos = new JMenu("Eventos");
        JMenuItem itemVerEventos = new JMenuItem("Ver e inscribirse");
        itemVerEventos.addActionListener(e -> new VentanaEventos());
        menuEventos.add(itemVerEventos);

        barraMenu.add(menuEstudiante);
        barraMenu.add(menuEventos);
        setJMenuBar(barraMenu);

        JLabel bienvenida = new JLabel("Bienvenido a SGHLU", SwingConstants.CENTER);
        add(bienvenida);

        setVisible(true);
    }
}