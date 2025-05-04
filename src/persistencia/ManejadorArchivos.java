package persistencia;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ManejadorArchivos {
    public static void guardar(String ruta, String contenido) throws IOException {
        Files.write(Paths.get(ruta), contenido.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static List<String> leer(String ruta) throws IOException {
        return Files.readAllLines(Paths.get(ruta));
    }
}