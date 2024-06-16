package org.proyecto;

import javax.swing.*;
import java.awt.*;

public class HelpWindow extends JFrame {
    public HelpWindow() {
        // Configurar la ventana
        setTitle("Ayuda");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Añadir contenido de ayuda
        JTextArea helpText = new JTextArea();
        helpText.setText("Este programa permite flashear binarios en un ESP32.\n\n" +
                "Pasos para usar:\n" +
                "1. Selecciona un programa de la lista desplegable.\n" +
                "2. Pulsa el botón 'Flashear'.\n" +
                "3. El resultado del flasheo aparecerá en el área de texto.");
        helpText.setEditable(false);
        add(new JScrollPane(helpText), BorderLayout.CENTER);

        // Hacer visible la ventana
        setVisible(true);
    }
}
