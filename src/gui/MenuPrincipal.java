package gui;

import javax.swing.*;
import modelo.Estudiante;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal(Estudiante est) {
        setTitle("SGHLU - Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setText("Bienvenido " + est.getNombre() + "\n\n"
                   + "Código: " + est.getCodigo() + "\n"
                   + "Horas acumuladas: " + est.getHorasAcumuladas());
        add(area);
    }
}