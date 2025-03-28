package proyecto.app;

import proyecto.controladores.GestorUsuarios;
import proyecto.modelos.CreadorNotas;
import proyecto.modelos.Registro;
import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;

    public Main() {
        // Configuración de la ventana
        JFrame frame = new JFrame("Inicio de Sesión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 400);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(240, 248, 255)); // Fondo azul claro

        // Encabezado
        JLabel etiquetaTitulo = new JLabel("Inicio de Sesión", JLabel.CENTER);
        etiquetaTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        etiquetaTitulo.setForeground(new Color(0, 102, 153));
        panelPrincipal.add(etiquetaTitulo, BorderLayout.NORTH);

        // Panel central para los campos
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        panelCampos.setBackground(new Color(240, 248, 255));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Campo de correo
        JLabel etiquetaCorreo = new JLabel("Correo:");
        etiquetaCorreo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoCorreo = new JTextField();
        campoCorreo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoCorreo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Campo de contraseña
        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        etiquetaContrasena.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoContrasena = new JPasswordField();
        campoContrasena.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Añadir campos 
        panelCampos.add(etiquetaCorreo);
        panelCampos.add(Box.createVerticalStrut(10)); // Espaciado
        panelCampos.add(campoCorreo);
        panelCampos.add(Box.createVerticalStrut(20)); // Espaciado
        panelCampos.add(etiquetaContrasena);
        panelCampos.add(Box.createVerticalStrut(10)); // Espaciado
        panelCampos.add(campoContrasena);

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBackground(new Color(240, 248, 255));

        JButton botonLogin = new JButton("Iniciar Sesión");
        botonLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonLogin.setBackground(new Color(0, 102, 153));
        botonLogin.setForeground(Color.WHITE);
        botonLogin.addActionListener(e -> iniciarSesion());

        JButton botonNuevoUsuario = new JButton("Crear Cuenta");
        botonNuevoUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonNuevoUsuario.setBackground(new Color(0, 153, 76));
        botonNuevoUsuario.setForeground(Color.WHITE);
        botonNuevoUsuario.addActionListener(e -> nuevoUsuario());

        panelBotones.add(botonLogin);
        panelBotones.add(botonNuevoUsuario);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar ventana
        frame.add(panelPrincipal);
        frame.setVisible(true);
    }

    private void iniciarSesion() {
        String correo = campoCorreo.getText().trim();
        String contrasena = new String(campoContrasena.getPassword()).trim();

        if (GestorUsuarios.verificarCredenciales(correo, contrasena)) {
            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            new CreadorNotas(correo);
        } else {
            JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void nuevoUsuario() {
        new Registro();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}