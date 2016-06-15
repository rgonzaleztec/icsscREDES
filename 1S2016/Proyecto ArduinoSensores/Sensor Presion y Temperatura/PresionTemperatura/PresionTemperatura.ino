/*
 MPL3115A2 Barometric Pressure Sensor Library Example Code
 By: Nathan Seidle
 SparkFun Electronics
 Date: September 24th, 2013
 License: This code is public domain but you buy me a beer if you use this and we meet someday (Beerware license).
 
 Uses the MPL3115A2 library to display the current altitude and temperature
 
 Hardware Connections (Breakoutboard to Arduino):
 -VCC = 3.3V
 -SDA = A4 (use inline 10k resistor if your board is 5V)
 -SCL = A5 (use inline 10k resistor if your board is 5V)
 -INT pins can be left unconnected for this demo
 
 During testing, GPS with 9 satellites reported 5393ft, sensor reported 5360ft (delta of 33ft). Very close!
 During testing, GPS with 8 satellites reported 1031ft, sensor reported 1021ft (delta of 10ft).
 
 Available functions:
 .begin() Gets sensor on the I2C bus.
 .readAltitude() Returns float with meters above sealevel. Ex: 1638.94
 .readAltitudeFt() Returns float with feet above sealevel. Ex: 5376.68
 .readPressure() Returns float with barometric pressure in Pa. Ex: 83351.25
 .readTemp() Returns float with current temperature in Celsius. Ex: 23.37
 .readTempF() Returns float with current temperature in Fahrenheit. Ex: 73.96
 .setModeBarometer() Puts the sensor into Pascal measurement mode.
 .setModeAltimeter() Puts the sensor into altimetery mode.
 .setModeStandy() Puts the sensor into Standby mode. Required when changing CTRL1 register.
 .setModeActive() Start taking measurements!
 .setOversampleRate(byte) Sets the # of samples from 1 to 128. See datasheet.
 .enableEventFlags() Sets the fundamental event flags. Required during setup.
 
*/

#include <UbidotsEthernet.h>
#include <SPI.h>
#include "SparkFunMPL3115A2.h"
#include <SoftwareSerial.h>
#include <Ethernet.h>
#include <Wire.h>
 
#define TOKEN  "5prdtbu0yTx4rYYd8Hu4VVz7KwM1zW"
#define tempVarId "5758c88f762542555ca41687"
#define pressureVarId "5758c8a3762542566b31c1a3"

byte mac[] = { 0x90, 0xA2, 0xDA, 0x0F, 0x4C, 0xF0 };
char server[]="things.ubidots.com";
IPAddress ip(172,24,181,104); // Arduino IP Add
Ubidots client(TOKEN);
MPL3115A2 myPressure;

void setup() {
  Wire.begin();
  Serial.begin(9600);
  Serial.print("Here");
  
  myPressure.begin();
  myPressure.setModeBarometer(); // Measure pressure in Pascals from 20 to 110 kPa
  myPressure.setOversampleRate(7); // Set Oversample to the recommended 128
  myPressure.enableEventFlags(); // Enable all three pressure and temp event flags
  
  // start the Ethernet connection:
  
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // try to congifure using IP address instead of DHCP:
    //Ethernet.begin(mac,ip,myDns,myGateway);
    Ethernet.begin(mac,ip);
  }
  Ethernet.begin(mac);
  
}
 
void loop() {
  
  float pressure = myPressure.readPressure();
  Serial.print("Pressure(Pa):");
  Serial.println(pressure, 2);

  float temperature = myPressure.readTemp();
  Serial.print("Temp(c):");
  Serial.println(temperature, 2);
  Serial.println();
  
  client.add(tempVarId, temperature);
  client.add(pressureVarId, pressure);
  client.sendAll();
  delay(8000);
}
