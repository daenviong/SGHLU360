package gui;

import javax.swing.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    public LoginGUI() {
        setTitle("Login SGHLU");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JTextField campoUsuario = new JTextField(15);
        JButton botonLogin = new JButton("Entrar");

        botonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuPrincipal().setVisible(true);
            }
        });

        panel.add(new JLabel("Usuario:"));
        panel.add(campoUsuario);
        panel.add(botonLogin);
        add(panel);
        setVisible(true);
    }
}