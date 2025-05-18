package gui;

import javax.swing.*;
import java.awt.*;

public class VentanaEstudiante extends JFrame {

    public VentanaEstudiante(String correoEstudiante) {
        setTitle("Panel del Estudiante");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel etiqueta = new JLabel("¡Bienvenido estudiante: " + correoEstudiante + "!", SwingConstants.CENTER);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 18));
        add(etiqueta, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuOpciones = new JMenu("Opciones");

        JMenuItem itemCerrar = new JMenuItem("Cerrar sesión");
        itemCerrar.addActionListener(e -> dispose());

        menuOpciones.add(itemCerrar);
        menuBar.add(menuOpciones);
        setJMenuBar(menuBar);
    }
}