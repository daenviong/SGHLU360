package modelo;

import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private String nombre;
    private String codigo;
    private int horasAcumuladas;
    private List<Evento> eventosInscritos;

    public Estudiante(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.horasAcumuladas = 0;
        this.eventosInscritos = new ArrayList<>();
    }

    public void inscribirEvento(Evento evento) {
        eventosInscritos.add(evento);
        horasAcumuladas += evento.getHoras();
    }

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public int getHorasAcumuladas() { return horasAcumuladas; }
    public List<Evento> getEventosInscritos() { return eventosInscritos; }

    public String getInfoCSV() {
        return nombre + "," + codigo + "," + horasAcumuladas;
    }
}