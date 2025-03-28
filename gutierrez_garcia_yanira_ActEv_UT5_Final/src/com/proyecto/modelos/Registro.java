package proyecto.modelos;

import proyecto.controladores.GestorUsuarios;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Registro {
    private JTextField campoNombre, campoCorreo;
    private JPasswordField campoContrasena;

    public Registro() {
        JFrame frame = new JFrame("Registro de Usuario");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(250, 250, 210));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel etiquetaTitulo = new JLabel("Registrar Nuevo Usuario", JLabel.CENTER);
        etiquetaTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        etiquetaTitulo.setForeground(new Color(0, 102, 102));
        panelPrincipal.add(etiquetaTitulo, BorderLayout.NORTH);

        // Campos centrados
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        panelCampos.setBackground(new Color(250, 250, 210));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campo de nombre
        JLabel etiquetaNombre = new JLabel("Nombre:");
        etiquetaNombre.setFont(new Font("SansSerif", Font.PLAIN, 16));
        etiquetaNombre.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar etiqueta
        campoNombre = new JTextField();
        campoNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoNombre.setPreferredSize(new Dimension(300, 35));
        campoNombre.setMaximumSize(new Dimension(300, 35)); // Limitar tamaño
        campoNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Campo de correo
        JLabel etiquetaCorreo = new JLabel("Correo:");
        etiquetaCorreo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        etiquetaCorreo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar etiqueta
        campoCorreo = new JTextField();
        campoCorreo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoCorreo.setPreferredSize(new Dimension(300, 35));
        campoCorreo.setMaximumSize(new Dimension(300, 35));
        campoCorreo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Campo de contraseña
        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        etiquetaContrasena.setFont(new Font("SansSerif", Font.PLAIN, 16));
        etiquetaContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);
        campoContrasena = new JPasswordField();
        campoContrasena.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoContrasena.setPreferredSize(new Dimension(300, 35));
        campoContrasena.setMaximumSize(new Dimension(300, 35));
        campoContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panelCampos.add(etiquetaNombre);
        panelCampos.add(Box.createVerticalStrut(10));
        panelCampos.add(campoNombre);
        panelCampos.add(Box.createVerticalStrut(20));
        panelCampos.add(etiquetaCorreo);
        panelCampos.add(Box.createVerticalStrut(10));
        panelCampos.add(campoCorreo);
        panelCampos.add(Box.createVerticalStrut(20));
        panelCampos.add(etiquetaContrasena);
        panelCampos.add(Box.createVerticalStrut(10));
        panelCampos.add(campoContrasena);

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(250, 250, 210));

        JButton botonRegistrar = new JButton("Registrar");
        botonRegistrar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonRegistrar.setBackground(new Color(0, 102, 102));
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusPainted(false);
        botonRegistrar.setPreferredSize(new Dimension(150, 40));
        botonRegistrar.addActionListener(e -> registrarUsuario());

        panelBotones.add(botonRegistrar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Configurar frame
        frame.add(panelPrincipal);
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }

    private void registrarUsuario() {
        // Validar campos
        String nombre = campoNombre.getText().trim();
        String correo = campoCorreo.getText().trim();
        String contrasena = new String(campoContrasena.getPassword()).trim();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(null, "Correo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (GestorUsuarios.existeUsuario(correo)) {
            JOptionPane.showMessageDialog(null, "El correo ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Registrar usuario
        GestorUsuarios.registrarUsuario(nombre, correo, contrasena);

        // Crear carpeta y archivo
        File carpetaUsuario = new File("data/usuarios/" + correo);
        if (!carpetaUsuario.exists()) {
            carpetaUsuario.mkdirs();
            try {
                new File(carpetaUsuario, "notas.txt").createNewFile();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al crear la carpeta del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}