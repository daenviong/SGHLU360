package gui;

import javax.swing.*;
import java.awt.event.*;
import modelo.Estudiante;
import persistencia.ManejadorArchivos;

public class LoginGUI extends JFrame {
    public LoginGUI() {
        setTitle("Login SGHLU");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JTextField campoNombre = new JTextField(15);
        JTextField campoCodigo = new JTextField(15);
        JButton botonLogin = new JButton("Entrar");

        botonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText();
                String codigo = campoCodigo.getText();
                if (!nombre.isEmpty() && !codigo.isEmpty()) {
                    Estudiante est = new Estudiante(nombre, codigo);
                    ManejadorArchivos.guardarEstudiante("resources/estudiantes.csv", est.getInfo());
                    dispose();
                    new MenuPrincipal(est).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Campos vacíos.");
                }
            }
        });

        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Código:"));
        panel.add(campoCodigo);
        panel.add(botonLogin);
        add(panel);
        setVisible(true);
    }
}