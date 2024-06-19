package org.proyecto.Controlador;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Flasheo_NO_UTILIZAR {

    public static void flashESP32(String program) {
        try {
            // Ruta al archivo .bin y puerto del ESP32
            String command = "esptool --chip esp32 --port COM4 write_flash 0x1000 " + program;

            String comando_2 = "cmd.exe esptool --chip esp32 -p COM4 -b 460800 --before=default_reset --after=hard_reset write_flash --flash_mode dio --flash_freq 40m --flash_size 2MB 0x1000 resources/Blink_Fade/bootloader.bin 0x10000 resources/Blink_Fade/app-template.bin 0x8000 resources/Blink_Fade/partition-table.bin" ;
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



            Process process = Runtime.getRuntime().exec(comando_2);


            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }


            int exitCode = process.waitFor();
            System.out.println("Proceso terminado con código: " + exitCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
