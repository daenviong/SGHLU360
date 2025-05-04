package modelo;

public class Estudiante {
    private String nombre;
    private String codigo;
    private int horasAcumuladas;

    public Estudiante(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.horasAcumuladas = 0;
    }

    public void agregarHoras(int horas) {
        this.horasAcumuladas += horas;
    }

    public String getInfo() {
        return nombre + "," + codigo + "," + horasAcumuladas;
    }

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public int getHorasAcumuladas() { return horasAcumuladas; }
}