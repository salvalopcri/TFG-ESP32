package org.proyecto;
import org.proyecto.HelpWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Pruebas extends JFrame {
    private JComboBox<String> programList;
    private JTextArea outputArea;
    private JButton flashButton;
    private JButton helpButton;

    public Pruebas() {
        // Configurar la ventana
        setTitle("Flashear ESP32");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        createMenu();  // Añadir el menú

        // Crear y añadir componentes
        JLabel label = new JLabel("Selecciona el programa:");
        label.setBounds(10, 20, 200, 25);
        add(label);

        String[] programas = {"web_server", "led_blink", "sensor_data", "wifi_scan"};
        programList = new JComboBox<>(programas);
        programList.setBounds(220, 20, 200, 25);
        add(programList);

        flashButton = new JButton("Flashear");
        flashButton.setBounds(10, 60, 150, 25);
        add(flashButton);

        helpButton = new JButton("Ayuda");
        helpButton.setBounds(170, 60, 150, 25);
        add(helpButton);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 100, 460, 250);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(10, 100, 460, 250);
        add(scrollPane);

        // Añadir el listener al botón de flasheo
        flashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProgram = (String) programList.getSelectedItem();
                flashearPrograma(selectedProgram);
            }
        });

        // Añadir el listener al botón de ayuda
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HelpWindow();
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }

    private void flashearPrograma(String programa) {
        String command = "esptool.py --chip esp32 --port /dev/ttyUSB0 --baud 115200 write_flash -z 0x1000 ./binarios/" + programa + ".bin";
        outputArea.setText(""); // Limpiar el área de salida
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                outputArea.append(line + "\n");
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                JOptionPane.showMessageDialog(this, "Programa " + programa + " flasheado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al flashear el programa " + programa + ".", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Ayuda");
        JMenuItem aboutItem = new JMenuItem("Acerca de");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HelpWindow();
            }
        });
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }
}
