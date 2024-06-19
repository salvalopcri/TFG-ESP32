#include <Arduino.h>
#line 1 "C:\\Users\\Usuario\\Desktop\\Repositorios\\Arduino-basics\\Prueba2Arduino-BluethootChat\\Prueba2Arduino-BluethootChat.ino"
#include "BluetoothSerial.h"
BluetoothSerial SerialBT;
#line 3 "C:\\Users\\Usuario\\Desktop\\Repositorios\\Arduino-basics\\Prueba2Arduino-BluethootChat\\Prueba2Arduino-BluethootChat.ino"
void setup();
#line 13 "C:\\Users\\Usuario\\Desktop\\Repositorios\\Arduino-basics\\Prueba2Arduino-BluethootChat\\Prueba2Arduino-BluethootChat.ino"
void loop();
#line 3 "C:\\Users\\Usuario\\Desktop\\Repositorios\\Arduino-basics\\Prueba2Arduino-BluethootChat\\Prueba2Arduino-BluethootChat.ino"
void setup()

{
  
    Serial.begin(9600);
   SerialBT.begin("SalvaESP32.V2"); //Bluetooth device name
   //Comprobar que se esté conectado por Bluetooth el ordenador al ESP32
   Serial.println("The device started, now you can pair it with bluetooth!");
}

void loop()
{ 
  
  if ( Serial.available())
        SerialBT.write(Serial.read());
  if (SerialBT.available())
     Serial.write(SerialBT.read());
  
  
}
