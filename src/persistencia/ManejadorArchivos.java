package persistencia;

import java.io.*;
import java.util.*;
import modelo.Evento;

public class ManejadorArchivos {

    public static void guardarLinea(String ruta, String contenido) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) {
            writer.write(contenido);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> leerLineas(String ruta) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineas;
    }

    public static List<Evento> cargarEventos(String ruta) {
        List<Evento> eventos = new ArrayList<>();
        for (String l : leerLineas(ruta)) {
            String[] p = l.split(",");
            eventos.add(new Evento(Integer.parseInt(p[0]), p[1], p[2], p[3], Integer.parseInt(p[4])));
        }
        return eventos;
    }
}