package modelo;

public class Evento {
    private int id;
    private String nombre;
    private String tipo;
    private String fecha;
    private int horas;

    public Evento(int id, String nombre, String tipo, String fecha, int horas) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
        this.horas = horas;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getFecha() { return fecha; }
    public int getHoras() { return horas; }

    public String getInfoCSV() {
        return id + "," + nombre + "," + tipo + "," + fecha + "," + horas;
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " (" + horas + "h)";
    }
}