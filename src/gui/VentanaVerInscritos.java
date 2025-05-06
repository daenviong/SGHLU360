package gui;

import javax.swing.*;

public class VentanaVerInscritos extends JFrame {
    public VentanaVerInscritos() {
        setTitle("Lista de inscritos por evento");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new JLabel("Lista de inscritos por evento", SwingConstants.CENTER));
        setVisible(true);
    }
}