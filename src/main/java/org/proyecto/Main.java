package org.proyecto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new Pruebas();


        // Crear combobox con nombres de programas
        String[] programas = {"C:\\Users\\Salva\\Documents\\Arduino\\sketch_may25a\\sketch_may25a.ino.bin"};
        JComboBox<String> comboBox = new JComboBox<>(programas);

        // Crear botón para flashear
        JButton flashButton = new JButton("Flashear");

        // Panel para organizar componentes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(comboBox);
        panel.add(flashButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        // Añadir acción al botón
        flashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProgram = (String) comboBox.getSelectedItem();
                flashESP32(selectedProgram);
            }
        });
    }

    private static void flashESP32(String program) {
        try {
            // Ruta al archivo .bin y puerto del ESP32
            String command = "esptool --port COM3 write_flash 0x1000 " + program;

            // Ejecutar el comando
            Process process = Runtime.getRuntime().exec(command);

            // Leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
            System.out.println("Proceso terminado con código: " + exitCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
