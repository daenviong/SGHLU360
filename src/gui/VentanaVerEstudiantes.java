package gui;

import javax.swing.*;

public class VentanaVerEstudiantes extends JFrame {
    public VentanaVerEstudiantes() {
        setTitle("Ver estudiantes con JTextArea");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new JLabel("Ver estudiantes con JTextArea", SwingConstants.CENTER));
        setVisible(true);
    }
}