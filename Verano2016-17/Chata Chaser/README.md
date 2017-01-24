# Proyecto de Redes Verano 2016-2017

* [Team Members](#team-members)

# <a name="team-members"></a>Team Members
* "Carlos Arturo Solis Solis" <carlos.art.solis.s@gmail.com>
* "Daryn Gerardo Soto Soto" <carlos.art.solis.s@gmail.com>
* "Juan Miguel Arce Rodriguez" <carlos.art.solis.s@gmail.com>
* "Rafael Quesada Alpízar" <carlos.art.solis.s@gmail.com>

# Proyecto a desarrollar
Desarrollar un sistema de reconocimiento de sonidos de altos desiveles que permita identificar un vehículo que esta produciendo alto ruido,
este sistemas debe venir acompañado del sensor que permita identificar esos niveles y generar los datos.
El sitio permite ver una foto del infractor.

# Descripción del Proyecto 
El dispositivo se encarga de tomar una fotografía si un vehículo cercano supera el límite de volumen permitido, la fotografía se manda a través de la red hacia un correo por defecto.

# Partes físicas del dispositivo 
* Raspberry Pi
* Microfono
* Cámara compatible con Raspberry Pi

# Archivos programados 

go.py: Es el encargado de interactuar directamente con el micrófono y la cámara, esto debido a que es el encargado de activar el micrófono para que empiece a capturar los sonidos de su al rededor, de igual manera es el archivo que se encarga de activar la cámara y tomar la fotografía.

SWHear.py: Se encarga de controlar los datos obtenidos por el micrófono. 

ServerTCPFilles.py: Es el encargado de levantar un socket para esperar una inagen tomada por la aplicación . 

clientTCPFilles.py: Este archivo toma la imagen capturada por el go.py y luego la manda al ServerTCPFilles.py.


# Entregables para calificar
Se debe entregar el equipo funcionando con lo solicitado y el software operando
Se debe entregar una documentacón detallada de como configurar todo desde cero (como armarlo), incluidas todas las caracteristicas solicitadas
Se deben hacer pruebas


# Fecha de Entrega completa y FINAL
<b> Lunes 23 de Enero de 2017 </b>
