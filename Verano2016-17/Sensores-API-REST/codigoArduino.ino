#include <Parse.h>
#include <Bridge.h>
#include <DHT.h>
//#include <DHT_U.h>

#define API_KEY "xcVaGfSaBv"
#define DHTPIN 2         //define as DHTPIN the Pin 3 used to connect the Sensor
#define DHTTYPE DHT11    //define the sensor used(DHT11)

int PIN_GAS = 1;



DHT dht(DHTPIN, DHTTYPE);//create an instance of DHT


///librerias del sensor de humedad

void setup() {
  
  //Initialize digital pin 13 as an output.
  pinMode(13, OUTPUT);
   
  // Initialize Bridge
  Bridge.begin();
   
  // Initialize Serial
  Serial.begin(9600);
   
  while (!Serial); // wait for a serial connection
  Serial.println("Parse Starter Project");
   
  // Initialize Parse
  Parse.begin("rsqUFx4jmfzlr3IOsxUmcz8XsrAg0qcLJRTpr2Q1", "Pos0LEjbrfCEv5NfNRCkdOikWiyImX2QUlrs09dM");

  Parse.setServerURL("https://rsqUFx4jmfzlr3IOsxUmcz8XsrAg0qcLJRTpr2Q1:Im7YLtyYUV3ND1GNhv70ZfrPHswhrqalqAR1VvwU@proyectoredes2.back4app.io/");
}

void loop() {
  
  ParseObjectCreate create; 
  float h = dht.readHumidity();    // reading Humidity 
  float t = dht.readTemperature(); // read Temperature as Celsius (the default)
  // check if any reads failed and exit early (to try again).
  
  if (isnan(h) || isnan(t)) {    
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  //analogRead(PIN_GAS); // Read Gas value from analog 0

  // Inserting Temperature
  
  create.setClassName("Temperature");
  create.add("_ApplicationId", "rsqUFx4jmfzlr3IOsxUmcz8XsrAg0qcLJRTpr2Q1");
  create.add("_MasterKey", "Im7YLtyYUV3ND1GNhv70ZfrPHswhrqalqAR1VvwU");
  create.add("value", String(t));
  create.add("tokenId", API_KEY);
  ParseResponse response = create.send();

  // Inserting Humidity

  create.setClassName("Humidity");
  create.add("_ApplicationId", "rsqUFx4jmfzlr3IOsxUmcz8XsrAg0qcLJRTpr2Q1");
  create.add("_MasterKey", "Im7YLtyYUV3ND1GNhv70ZfrPHswhrqalqAR1VvwU");
  create.add("value", String(h));
  create.add("tokenId", API_KEY);
  ParseResponse response = create.send();

  // Inserting CO2

  create.setClassName("CO2");
  create.add("_ApplicationId", "rsqUFx4jmfzlr3IOsxUmcz8XsrAg0qcLJRTpr2Q1");
  create.add("_MasterKey", "Im7YLtyYUV3ND1GNhv70ZfrPHswhrqalqAR1VvwU");
  create.add("value", String(analogRead(PIN_GAS)));
  create.add("tokenId", API_KEY);
  ParseResponse response = create.send();
  
  
  if (!response.getErrorCode()) {
    // The object has been saved 
    Serial.print(API_KEY);
    Serial.print("\t");   
    Serial.print(h, 2);  //print the humidity
    Serial.print("\t");
    Serial.print(t, 2);    //print the temperature
    Serial.print("\t");
    Serial.println(analogRead(PIN_GAS),DEC);//Print the value to serial port
  } else {
    // There was a problem, check response.
    //getErrorCode();
    Serial.println("Error al insertar datos");
  }
  response.close(); // Free the resource
 
  // wait 2 seconds 
  // if there are incoming bytes available
  // from the server, read them and print them:
   delay(2000); 
}
