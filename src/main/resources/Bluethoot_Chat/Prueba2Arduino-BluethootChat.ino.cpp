#include <Arduino.h>

void setup()

{
  
    Serial.begin(9600);
   SerialBT.begin("SalvaESP32.V2");
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
