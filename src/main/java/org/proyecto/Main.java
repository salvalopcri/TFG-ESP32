package org.proyecto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("ESP32 Flasher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Crear combobox con nombres de programas
        String[] programas = {"C:\\Users\\Salva\\Desktop\\Repositorios\\Arduino-Basics\\TFG\\Binaries\\1_Blink.bin"};
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
            String command = "esptool --chip esp32 --port COM3 write_flash 0x1000 " + program;
            /**
             * Vale esto hay que comprobarlo mas a fondo pero desde espressif, este es el comando que me ha permitido flashear el programaa en el ESP32 WROOM.
             * 
             * esptool --chip esp32 -p COM5 -b 460800 --before=default_reset --after=hard_reset write_flash 
             * --flash_mode dio --flash_freq 40m --flash_size 2MB 0x1000 bootloader.bin 0x10000 app-template.bin 0x8000 partition-table.bin
             * 
             * Al parecer son necesarios los ficheros bootloader.bin, app-template.bin y partition-table.bin.
             * Todos ellos se pueden encontrar en la ruta C:\Espressif\frameworks\esp-idf-v5.1.2\workspace\Pruebas\build
             * 
             * Los he metido , almenos los del primer ejemplo, en la carpeta resources
             * 
             * Seria interesante tener alomejor 2 ventanas y que cada una sea para un esp32 (wroom o wrover) y con un COM conocido, de manera que al pulsar al boton, el COM se 
             * introduzca en función de que botón se ha pulsado, pero no Hard codeado.
             */


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
