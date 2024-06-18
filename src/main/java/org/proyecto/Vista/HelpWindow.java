package org.proyecto.Vista;

import javax.swing.*;
import java.awt.*;

public class HelpWindow extends JFrame {
    public HelpWindow() {
        // Configurar la ventana
        setTitle("Acerca de");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Añadir contenido de ayuda
        JTextArea helpText = new JTextArea();

        helpText.setText("Acerca de\n\n" +
                "Versión: 1.02\n" +
                "Fecha de compilación: 17/06/2024\n\n" +
                "Desarrollado por: Salvador López Criado\n\n" +
                "Este programa permite flashear binarios en un ESP32.\n\n" +
                "Para más información o soporte, por favor contacta con el desarrollador.\n\n" +
                "slopcri1608@g.educaand.es");
        //helpText.setFont(new Font("Serif",Font.BOLD,12));
        helpText.setEditable(false);
        helpText.setFont(new Font("Arial", Font.PLAIN, 14));
        helpText.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(helpText), BorderLayout.CENTER);

        // Hacer visible la ventana
        setVisible(true);
    }}