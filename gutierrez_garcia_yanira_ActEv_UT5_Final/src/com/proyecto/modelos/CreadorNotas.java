package proyecto.modelos;

import proyecto.controladores.GestorArchivos;
import proyecto.app.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreadorNotas {
    private DefaultListModel<String> modeloNotas;
    private JList<String> listaNotas;
    private JTextField campoTitulo;
    private JTextArea areaContenido;
    private String correoUsuario;

    public CreadorNotas(String correoUsuario) {
        this.correoUsuario = correoUsuario;
        modeloNotas = new DefaultListModel<>();
        listaNotas = new JList<>(modeloNotas);

        cargarNotas();

        JFrame frame = new JFrame("Gestión de Notas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(240, 240, 240));

        // Encabezado
        JLabel etiquetaTituloVentana = new JLabel("Gestión de Notas", JLabel.CENTER);
        etiquetaTituloVentana.setFont(new Font("SansSerif", Font.BOLD, 28));
        etiquetaTituloVentana.setForeground(new Color(0, 123, 255)); 
        etiquetaTituloVentana.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panelPrincipal.add(etiquetaTituloVentana, BorderLayout.NORTH);

        // Panel central
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 30, 30)); 
        panelCentral.setBackground(new Color(240, 240, 240));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Panel izquierdo para título y contenido
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(240, 240, 240));

        JLabel etiquetaTituloNota = new JLabel("Título:");
        etiquetaTituloNota.setFont(new Font("SansSerif", Font.PLAIN, 18));
        etiquetaTituloNota.setAlignmentX(Component.CENTER_ALIGNMENT); 

        campoTitulo = new JTextField();
        campoTitulo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoTitulo.setMaximumSize(new Dimension(400, 40));
        campoTitulo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12) 
        ));

        JLabel etiquetaContenidoNota = new JLabel("Contenido:");
        etiquetaContenidoNota.setFont(new Font("SansSerif", Font.PLAIN, 18));
        etiquetaContenidoNota.setAlignmentX(Component.CENTER_ALIGNMENT);

        areaContenido = new JTextArea();
        areaContenido.setFont(new Font("SansSerif", Font.PLAIN, 16));
        areaContenido.setLineWrap(true);
        areaContenido.setWrapStyleWord(true);
        JScrollPane scrollContenido = new JScrollPane(areaContenido);
        scrollContenido.setPreferredSize(new Dimension(400, 200)); 
        scrollContenido.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        panelIzquierdo.add(etiquetaTituloNota);
        panelIzquierdo.add(Box.createVerticalStrut(15));
        panelIzquierdo.add(campoTitulo);
        panelIzquierdo.add(Box.createVerticalStrut(25));
        panelIzquierdo.add(etiquetaContenidoNota);
        panelIzquierdo.add(Box.createVerticalStrut(15));
        panelIzquierdo.add(scrollContenido);

        // Panel derecho para la lista de notas
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(new Color(240, 240, 240));

        JLabel etiquetaListaNotas = new JLabel("Tus Notas:");
        etiquetaListaNotas.setFont(new Font("SansSerif", Font.PLAIN, 18));
        etiquetaListaNotas.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollListaNotas = new JScrollPane(listaNotas);
        scrollListaNotas.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        panelDerecho.add(etiquetaListaNotas, BorderLayout.NORTH);
        panelDerecho.add(scrollListaNotas, BorderLayout.CENTER);

        panelCentral.add(panelIzquierdo);
        panelCentral.add(panelDerecho);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBackground(new Color(240, 240, 240));

        JButton botonGuardar = new JButton("Guardar Nota");
        botonGuardar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonGuardar.setBackground(new Color(0, 123, 255));
        botonGuardar.setForeground(Color.WHITE);
        botonGuardar.addActionListener(e -> guardarNota());

        JButton botonEditar = new JButton("Editar Nota");
        botonEditar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonEditar.setBackground(new Color(255, 165, 0)); // Naranja
        botonEditar.setForeground(Color.WHITE);
        botonEditar.addActionListener(e -> editarNota());

        JButton botonEliminar = new JButton("Eliminar Nota");
        botonEliminar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonEliminar.setBackground(new Color(255, 69, 0)); // Rojo
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.addActionListener(e -> eliminarNota());

        JButton botonLimpiar = new JButton("Limpiar Campos");
        botonLimpiar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonLimpiar.setBackground(new Color(0, 153, 76)); // Verde
        botonLimpiar.setForeground(Color.WHITE);
        botonLimpiar.addActionListener(e -> limpiarCampos());

        JButton botonCerrarSesion = new JButton("Cerrar Sesión");
        botonCerrarSesion.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonCerrarSesion.setBackground(new Color(33, 33, 33)); // Negro
        botonCerrarSesion.setForeground(Color.WHITE);
        botonCerrarSesion.addActionListener(e -> cerrarSesion(frame));

        panelBotones.add(botonGuardar);
        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonLimpiar);
        panelBotones.add(botonCerrarSesion);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        frame.add(panelPrincipal);
        frame.setVisible(true);
    }

    private void cargarNotas() {
        List<String> notas = GestorArchivos.leerNotas(correoUsuario);
        modeloNotas.clear();
        for (String nota : notas) {
            modeloNotas.addElement(nota);
        }
    }

    private void guardarNota() {
        String titulo = campoTitulo.getText().trim();
        String contenido = areaContenido.getText().trim();
        if (!titulo.isEmpty() && !contenido.isEmpty()) {
            modeloNotas.addElement("[" + titulo + "] " + contenido);
            guardarCambios();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "El título y el contenido no pueden estar vacíos.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editarNota() {
        int indice = listaNotas.getSelectedIndex();
        if (indice != -1) {
            String titulo = campoTitulo.getText().trim();
            String contenido = areaContenido.getText().trim();
            if (!titulo.isEmpty() && !contenido.isEmpty()) {
                modeloNotas.set(indice, "[" + titulo + "] " + contenido);
                guardarCambios();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "El título y el contenido no pueden estar vacíos.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una nota para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarNota() {
        int indice = listaNotas.getSelectedIndex();
        if (indice != -1) {
            modeloNotas.remove(indice);
            guardarCambios();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una nota para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarCampos() {
        campoTitulo.setText("");
        areaContenido.setText("");
    }

    private void cerrarSesion(JFrame frame) {
        guardarCambios();
        frame.dispose();
        new Main();
    }

    private void guardarCambios() {
        List<String> notas = new ArrayList<>();
        for (int i = 0; i < modeloNotas.size(); i++) {
            notas.add(modeloNotas.getElementAt(i));
        }
        GestorArchivos.guardarNotas(correoUsuario, notas);
    }
}