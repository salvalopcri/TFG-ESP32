#include <Arduino.h>
#line 1 "C:\\Users\\Salva\\AppData\\Local\\Temp\\.arduinoIDE-unsaved2024519-3908-ems7bl.lujjb\\Basic\\Basic.ino"
#include <WiFiManager.h> // https://github.com/tzapu/WiFiManager


#line 4 "C:\\Users\\Salva\\AppData\\Local\\Temp\\.arduinoIDE-unsaved2024519-3908-ems7bl.lujjb\\Basic\\Basic.ino"
void setup();
#line 39 "C:\\Users\\Salva\\AppData\\Local\\Temp\\.arduinoIDE-unsaved2024519-3908-ems7bl.lujjb\\Basic\\Basic.ino"
void loop();
#line 4 "C:\\Users\\Salva\\AppData\\Local\\Temp\\.arduinoIDE-unsaved2024519-3908-ems7bl.lujjb\\Basic\\Basic.ino"
void setup() {
    WiFi.mode(WIFI_STA); // explicitly set mode, esp defaults to STA+AP
    // it is a good practice to make sure your code sets wifi mode how you want it.

    // put your setup code here, to run once:
    Serial.begin(115200);
    
    //WiFiManager, Local intialization. Once its business is done, there is no need to keep it around
    WiFiManager wm;

    // reset settings - wipe stored credentials for testing
    // these are stored by the esp library
    wm.resetSettings();

    // Automatically connect using saved credentials,
    // if connection fails, it starts an access point with the specified name ( "AutoConnectAP"),
    // if empty will auto generate SSID, if password is blank it will be anonymous AP (wm.autoConnect())
    // then goes into a blocking loop awaiting configuration and will return success result

    bool res;
    // res = wm.autoConnect(); // auto generated AP name from chipid
    // res = wm.autoConnect("AutoConnectAP"); // anonymous ap
    res = wm.autoConnect("AutoConnectAP","password"); // password protected ap

    if(!res) {
        Serial.println("Failed to connect");
        // ESP.restart();
    } 
    else {
        //if you get here you have connected to the WiFi    
        Serial.println("connected...yeey :)");
    }

}

void loop() {
    // put your main code here, to run repeatedly:   
}

