package gui;

import javax.swing.*;
import java.awt.*;
import gui.VentanaLoginEstudiante;
import gui.VentanaLoginDocente;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("SGHLU - Sistema de Gestión de Horas Libres UNAB");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuInicio = new JMenu("Inicio de Sesión");

        JMenuItem itemEstudiante = new JMenuItem("Estudiante");
        itemEstudiante.addActionListener(e -> new VentanaLoginEstudiante().setVisible(true));

        JMenuItem itemDocente = new JMenuItem("Docente");
        itemDocente.addActionListener(e -> new VentanaLoginDocente().setVisible(true));

        menuInicio.add(itemEstudiante);
        menuInicio.add(itemDocente);
        menuBar.add(menuInicio);

        setJMenuBar(menuBar);

        JLabel bienvenida = new JLabel("Bienvenido a SGHLU", SwingConstants.CENTER);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        add(bienvenida, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}