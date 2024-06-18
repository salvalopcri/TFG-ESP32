package org.proyecto.Vista;

import javax.swing.*;
import java.awt.*;

public class MencionesWindow extends JFrame {

    public MencionesWindow() {
        // Configurar la ventana
        setTitle("Menciones");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Añadir contenido de menciones
        JTextArea textArea = new JTextArea();
        textArea.setText("Menciones honoríficas:\n\n" +
                "Empresa formadora: IKOSTECH S.L.\n\n" +
                "Instituto: I.E.S Celia Viñas\n\n" +
                "Programación: Alfonso Martínez\n\n" +
                "Diseño de interfaz: Evaristo Romero\n\n" +
                "Gestión de procesos e hilos: Diego Gay\n\n");
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Hacer visible la ventana
        setVisible(true);
    }}