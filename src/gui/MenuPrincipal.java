package gui;

import javax.swing.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("SGHLU - Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea("Bienvenido a SGHLU\nAquí podrás gestionar tus horas libres.");
        add(area);
    }
}