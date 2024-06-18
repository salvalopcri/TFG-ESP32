package org.proyecto;
import org.proyecto.Vista.*;

import javax.swing.*;
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
    private JComboBox<String> placasList;
    private static final String RUTA = "C:\\Users\\Salva\\IdeaProjects\\ESP32_Flasher\\src\\main\\resources\\";

    public Pruebas() {
        // Configurar la ventana
        setTitle("ESP32 Flasher");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        createMenu();  // Añadir el menú

        // Crear y añadir componentes
        JLabel labelPlaca = new JLabel("Selecciona el dispositivo:");
        labelPlaca.setBounds(10, 20, 200, 25);
        add(labelPlaca);

        String[] placas = {"esp32", "esp32-Wroover", "esp32-Wroom", "DWM3001CDK"};
        placasList = new JComboBox<>(placas);
        placasList.setBounds(220, 20, 200, 25);
        add(placasList);

        JLabel label = new JLabel("Selecciona el programa:");
        label.setBounds(10, 50, 200, 25);
        add(label);

        String[] programas = {"Web_Server", "Blink_Fade", "Sensor_Data", "WiFi_Scan", "Bluethoot_Chat"};
        programList = new JComboBox<>(programas);
        programList.setBounds(220, 50, 200, 25);
        add(programList);

        flashButton = new JButton("Flashear");
        flashButton.setBounds(10, 100, 150, 25);
        add(flashButton);

        helpButton = new JButton("Ayuda");
        helpButton.setBounds(170, 100, 150, 25);
        add(helpButton);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 120, 660, 250);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(10, 150, 660, 250);
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

        programList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }

    private void flashearPrograma(String programa) {
        String rutaFinal = RUTA + programList.getSelectedItem()+"\\";
        String command = "esptool --chip esp32 -p COM4 -b 460800 --before=default_reset --after=hard_reset write_flash --flash_mode dio --flash_freq 40m --flash_size 2MB 0x1000 "+rutaFinal+"bootloader.bin 0x10000 "+rutaFinal+"app-template.bin 0x8000 "+rutaFinal+"partition-table.bin";
        outputArea.setText("Cargando...\n\n"); // Limpiar el área de salida
        
        try {
            Thread.sleep(200);
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
        JMenu helpMenu = new JMenu("Detalles");
        JMenuItem menciones = new JMenuItem("Menciones honoríficas");
        JMenuItem aboutItem = new JMenuItem("Acerca de");
        menciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MencionesWindow();
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HelpWindow();
            }
        });
        helpMenu.add(aboutItem);
        helpMenu.add(menciones);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }
}
