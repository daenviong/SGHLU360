package gui;

import javax.swing.*;
import modelo.*;
import persistencia.ManejadorArchivos;
import java.awt.event.*;
import java.util.List;

public class InterfazPrincipal extends JFrame {
    private Estudiante estudiante;
    private List<Evento> eventosDisponibles;

    public InterfazPrincipal(Estudiante est) {
        this.estudiante = est;
        this.eventosDisponibles = ManejadorArchivos.cargarEventos("resources/eventos.csv");

        setTitle("SGHLU - Panel de Eventos");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        DefaultListModel<Evento> model = new DefaultListModel<>();
        for (Evento e : eventosDisponibles) model.addElement(e);
        JList<Evento> lista = new JList<>(model);
        JScrollPane scroll = new JScrollPane(lista);

        JButton btnInscribir = new JButton("Inscribirse");
        JTextArea info = new JTextArea();
        info.setEditable(false);

        btnInscribir.addActionListener(e -> {
            Evento seleccionado = lista.getSelectedValue();
            if (seleccionado != null) {
                estudiante.inscribirEvento(seleccionado);
                info.setText("Inscrito en: " + seleccionado.getNombre() +
                             "\nTotal horas: " + estudiante.getHorasAcumuladas());
            }
        });

        add(scroll, "North");
        add(btnInscribir, "Center");
        add(info, "South");

        setVisible(true);
    }
}