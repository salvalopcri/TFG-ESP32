package org.proyecto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pruebas extends JFrame {
    private JTextField filePathField;
    private JTextField serialPortField;
    private JTextArea logArea;

    public Pruebas() {
        setTitle("ESP32 Flasher");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel fileLabel = new JLabel("Binary File:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(fileLabel, gbc);

        filePathField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(filePathField, gbc);

        JButton browseButton = new JButton("Browse...");
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(browseButton, gbc);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        JLabel portLabel = new JLabel("Serial Port:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(portLabel, gbc);

        serialPortField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(serialPortField, gbc);

        JButton flashButton = new JButton("Flash");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(flashButton, gbc);
        flashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flashESP32();
            }
        });

        logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        add(panel);
    }

    private void flashESP32() {
        String filePath = filePathField.getText();
        String serialPort = serialPortField.getText();
        logArea.append("Flashing " + filePath + " to " + serialPort + "\n");

        // Aquí es donde se llamará a esptool.py
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "python", "-m", "esptool", "--chip", "esp32", "--port", serialPort, "write_flash", "0x1000", filePath
            );
            builder.redirectErrorStream(true);
            Process process = builder.start();
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                logArea.append(line + "\n");
            }
            process.waitFor();
        } catch (Exception e) {
            logArea.append("Error: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Pruebas().setVisible(true);
            }
        });
    }
}
