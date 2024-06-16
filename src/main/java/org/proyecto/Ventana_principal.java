package org.proyecto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Ventana_principal extends JFrame{

    private JPanel jpanel1;
    private JButton salirButton;
    private JButton ayudaButton;
    private JButton button1;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;

    public Ventana_principal() {
        super("ESP32 Flasher");
        setContentPane(jpanel1);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flasheo.flashESP32(null);
            }
        });
    }
}
