package org.proyecto.Vista;

import javax.swing.*;
import java.awt.*;

public class HelpWindow extends JFrame {
    public HelpWindow() {

        setTitle("Acerca de");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);


        JTextArea helpText = new JTextArea();

        helpText.setText("Acerca de\n\n" +
                "Versión: 1.02\n" +
                "Fecha de compilación: 17/06/2024\n\n" +
                "Desarrollado por: Salvador López Criado\n\n" +
                "Este programa permite flashear binarios en un ESP32.\n\n" +
                "Para más información o soporte, por favor contacta con el desarrollador.\n\n" +
                "slopcri1608@g.educaand.es");

        helpText.setEditable(false);
        helpText.setFont(new Font("Arial", Font.PLAIN, 14));
        helpText.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(helpText), BorderLayout.CENTER);


        setVisible(true);
    }}