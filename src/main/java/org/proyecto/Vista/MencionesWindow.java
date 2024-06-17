package org.proyecto.Vista;

import javax.swing.*;
import java.awt.*;

public class MencionesWindow extends JFrame{


        public MencionesWindow() {
            // Configurar la ventana
            setTitle("Menciones");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            // Añadir contenido de ayuda
            JTextArea textArea = new JTextArea();
            textArea.setText("Este programa permite flashear binarios en un ESP32.\n\n" +
                    "Pasos para usar:\n" +
                    "1. Selecciona un programa de la lista desplegable.\n" +
                    "2. Pulsa el botón 'Flashear'.\n" +
                    "3. El resultado del flasheo aparecerá en el área de texto.");
            textArea.setEditable(false);
            add(new JScrollPane(textArea), BorderLayout.CENTER);

            // Hacer visible la ventana
            setVisible(true);
        }
    }


