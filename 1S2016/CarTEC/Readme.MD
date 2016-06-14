# CarTEC Repository

![alt tag](http://i.imgur.com/MjvDLiX.png)
![alt tag](http://i.imgur.com/nJfOPI1.png)

Este proyecto consiste en un carrito que puede ser manejado utilizando una app instalada sobre un teléfono con sistema operativo Android.

El carro tiene un sensor de temperatura y un sensor uv, que brindan información en tiempo real.

# Instrucciones de uso

Al abrir la aplicación, deberá mostrar una pantalla para seleccionar el dispositivo bluetooth. 

Active bluetooth en caso de no tenerlo activo.

Si el usuario intenta conectar con el dispositivo y el carrito está apagado, enviará un mensaje de error de conexión.

En el mejor de los casos el teléfono soportará bluetooth y el mismo estará activado, además el carrito estará encendido y con las baterías al máximo.

Una vez que se establezca conexión, la luz amarilla de la placa bluetooth deberá dejar de parpadear y quedarse iluminada.

Se abrirá una pantalla donde aparece un control similar a lo de los joystick y tres botones para regular la velocidad.

Si el carrito es apagado o se pierde la conexión, vaya hacia atrás y vuelva a seleccionar el dispositivo.

# Recomendaciones

Se recomienda una versión de Arduino IDE posterior a la 2.1.0.5.

# Carpetas

La carpeta Android contiene el proyecto para abrir con Android Studio y gradle.

La carpeta Arduino contiene el script para ejecutar en Arduino.

La carpeta Adicional contiene las librerías de Arduino necesarias para importar en Arduino.

La  carpeta Binarios tiene las últimas versiones del ejecutable en APK para teléfonos.

La carpeta documentación tiene la documentación del proyecto.

# Instalación

La aplicación se debe instalar mediante el archivo APK en la carpeta de Binarios.

El carrito debe tenerla instalada, cargando el programa en una Dagu V19 desde Arduino IDE.

Se debe seleccionar como tarjeta Arduino Pro / Mini.

En el caso de Linux hay que seleccionar específicamente la opción Arduino Pro / Mini (3.3V, 8MHz) w/ ATmega 168.

# Licencia

No tiene licencia específica.

# Versión

1.0.0.

# Colaboradores

- Giovanni G. Méndez Marín - geovanny.mendez.marin@gmail.com
- Mainor Andrey Gamboa Rodriguez - killone07@gmail.com                        		
- Kenneth Alonso Pérez Alfaro - kenn.perez@gmail.com
- Cristiam Guillermo Salas Salazar - cs.salas94@gmail.com
