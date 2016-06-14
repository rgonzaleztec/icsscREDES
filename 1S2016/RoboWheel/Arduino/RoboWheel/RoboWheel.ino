/*
  Carro Inalambrico Bluetooth
 Written by Giovanni Mendez Marin
 */

#include <microM.h>
#include <dht.h>
#include <LiquidCrystal.h>
#include <Wire.h> // Requerido para adafruit
#include <Adafruit_SI1145.h>


dht DHT;

#define DHT11_PIN 3

static int estado_recibido = 'y';
static int velocidad_carro = 255;

static int estado = 'g';         // inicia detenido

LiquidCrystal lcd(3, 4, 5, 6, 7 , 8);

Adafruit_SI1145 uv = Adafruit_SI1145();


void setup()  {
  // microM.Setup();
  Serial.begin(9600);    // inicia el puerto serial para comunicacion con el Bluetooth
  //Serial.begin(115200);  // inicial el puerto serial para comunicacin con el sensor DHT11 (Humedad y temperatura)


  if (! uv.begin()) {
    Serial.println("Didn't find Si1145");
    // while (1);
  }


  lcd.begin(16, 2);
  lcd.setCursor(0,1);
  lcd.write("gmendezm ");

}

void loop()  {

  if (Serial.available() > 0) {    // lee el bluetooth y almacena en estado

    estado_recibido = Serial.read();

  }

  mover_carrito();

  enviar_temperatura();

  enviar_uv();

  enviar_pantalla();

}

void enviar_pantalla(){
  // TODO
}

void enviar_uv(){
  //Serial.println("===================");
  //Serial.print("Vis: ");
  Serial.print(".P"); 
  Serial.println(uv.readVisible());
  //Serial.print("IR: "); 
  Serial.print(".Q"); 
  Serial.println(uv.readIR());

  // Uncomment if you have an IR LED attached to LED pin!
  //Serial.print("Prox: "); Serial.println(uv.readProx());

  float UVindex = uv.readUV();
  // the index is multiplied by 100 so to get the
  // integer index, divide by 100!
  UVindex /= 100.0;  
  //Serial.print("UV: ");
  Serial.print(".R");    
  Serial.println(UVindex);

  //delay(1000);
}

void enviar_temperatura(){

  int chk = DHT.read11(DHT11_PIN);


  switch (chk)
  {
  case DHTLIB_OK:  
    // Serial.print("OK,\t"); 
    Serial.print("");
  case DHTLIB_ERROR_CHECKSUM: 
    //Serial.print("Checksum error,\t"); 
    Serial.print("");

  case DHTLIB_ERROR_TIMEOUT: 
    // Serial.print("Time out error,\t"); 
    Serial.print("");

  case DHTLIB_ERROR_CONNECT:
    // Serial.print("Connect error,\t");
    Serial.print("");

  case DHTLIB_ERROR_ACK_L:
    // Serial.print("Ack Low error,\t");
    Serial.print("");

  case DHTLIB_ERROR_ACK_H:
    // Serial.print("Ack High error,\t");
    Serial.print("");

  default: 
    //Serial.print("Unknown error,\t"); 
    Serial.print("");
  }

  // DISPLAY DATA
  //Serial.print("H ");
  // Serial.print(DHT.humidity, 1);
  //Serial.print(",\t");
  Serial.print(".H");
  Serial.println(DHT.humidity);
  Serial.print(".T");
  Serial.println(DHT.temperature);
}

void mover_carrito(){

  // Marchas
  if(estado_recibido == 'x' || estado_recibido == 'y' || estado_recibido == 'z' ){
    if(estado_recibido == 'x'){
      velocidad_carro = 80;
    } 
    else if(estado_recibido == 'y'){
      velocidad_carro = 125;
    } 
    else if(estado_recibido == 'z'){
      velocidad_carro = 255;
    }
  }

  // Direccion
  else if(estado_recibido == 'a' || estado_recibido == 'b' || estado_recibido == 'c' || estado_recibido == 'd' || estado_recibido == 'e' || estado_recibido == 'f' || estado_recibido == 'g' ){
    if(estado != estado_recibido){
      microM.Motors(0, 0, 0, 0);
      estado = estado_recibido;
    }
  }

  if (estado == 'a') {       // Boton desplazar al Frente
    microM.Motors(-velocidad_carro, velocidad_carro, 0, 0); // update motor controller
  }
  if (estado == 'b') {      // Boton IZQ
    microM.Motors(0, velocidad_carro, 0, 0); // update motor controller

  }
  if (estado == 'c') {     // Boton Parar
    microM.Motors(0, 0, 1, 1); // update motor controller
  }
  if (estado == 'd') {      // Boton DER
    microM.Motors(-velocidad_carro, 0, 0, 0); // update motor controller
  }

  if (estado == 'e') {      // Boton Reversa
    microM.Motors(velocidad_carro, -velocidad_carro, 0, 0); // update motor controller
  }
  if (estado == 'f') {        // Boton ON se mueve sensando distancia

  }
  if  (estado == 'g') {       // Boton OFF, detiene los motores no hace nada
    microM.Motors(0, 0, 0, 0); // update motor controller
  }


}











