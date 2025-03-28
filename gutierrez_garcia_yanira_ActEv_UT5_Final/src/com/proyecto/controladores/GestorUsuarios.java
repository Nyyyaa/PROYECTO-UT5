package proyecto.controladores;

import java.io.*;
import java.security.MessageDigest;

public class GestorUsuarios {
    private static final String ARCHIVO_USUARIOS = "com/proyecto/data/users.txt";

    // Comprobación del correo
    public static boolean existeUsuario(String correo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(":"); // Divide cada línea en partes
                if (partes[0].equals(correo)) { // Compara el correo
                    return true; // El usuario existe
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Muestra errores de archivo
        }
        return false; // El usuario no existe
    }

    // Verificacion del correo y la contraseña
    public static boolean verificarCredenciales(String correo, String contrasena) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(":"); 
                if (partes[0].equals(correo)) { // Compara el correo
                    return partes[1].equals(generarHash(contrasena)); // Compara la contraseña
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return false;
    }

    // Registrar un nuevo usuario
    public static void registrarUsuario(String nombre, String correo, String contrasena) {
        String hashContrasena = generarHash(contrasena); 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            writer.write(correo + ":" + hashContrasena); // Guarda el correo y la contraseña
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace(); // Muestra errores de escritura
        }
    }

    // Crear un código seguro a la contraseña
    private static String generarHash(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Inicializa SHA-256
            byte[] hash = md.digest(contrasena.getBytes()); 
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b); // Convierte a hexadecimal
                if (hex.length() == 1) hexString.append('0'); // Asegura que tenga dos dígitos
                hexString.append(hex);
            }
            return hexString.toString(); 
        } catch (Exception e) {
            throw new RuntimeException(e); 
        }
    }
}