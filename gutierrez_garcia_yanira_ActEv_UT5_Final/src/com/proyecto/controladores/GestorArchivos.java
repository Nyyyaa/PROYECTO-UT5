package proyecto.controladores;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {
    // Leer notas
    public static List<String> leerNotas(String correo) {
        List<String> notas = new ArrayList<>();
        File archivoNotas = new File("com/proyecto/data/usuarios/" + correo + "/notas.txt");
        if (archivoNotas.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivoNotas))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    notas.add(linea);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return notas;
    }

    // Guardar notas
    public static void guardarNotas(String correo, List<String> notas) {
        File archivoNotas = new File("com/proyecto/data/usuarios/" + correo + "/notas.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoNotas))) {
            for (String nota : notas) {
                writer.write(nota);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}