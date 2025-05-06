package gui;

import javax.swing.*;

public class VentanaVerHistorial extends JFrame {
    public VentanaVerHistorial() {
        setTitle("Historial de eventos por estudiante");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new JLabel("Historial de eventos por estudiante", SwingConstants.CENTER));
        setVisible(true);
    }
}