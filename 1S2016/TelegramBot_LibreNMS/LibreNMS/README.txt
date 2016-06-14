Introducción 

LibreNMS es un sistema de supervisión de red con autodescubrimiento PHP / MySQL / SNMP que incluye soporte para una amplia gama de sistemas operativos y hardware de red como Cisco, Linux, FreeBSD, Juniper, Brocade, Fundición, HP y muchos más. LibreNMS es una bifurcación  Observium desarrollada por la comunidad.

Estructura

El proyecto hace uso de este framework para proveer una solución Open Source al Instituto Tecnológico de Costa Rica capaz de mapear y monitorear constantemente los dispositivos de red de las instalaciones. Las configuraciones realizadas se detallan en la documentación, al igual que los pasos para posibles modificaciones futuras. 

La estructura del proyecto es básicamente una máquina virtual con Ubuntu 14.04.1 que cumple el papel de servidor para el sistema, la página principal puede ser accesada desde localhost en la máquina virtual o desde la dirección ip que esta tiene asignada en la máquina anfitrión, al ser un framework diseñado para su ejecución en servidores virtuales no tiene otra interfaz a parte de la web y todas las opciones de configuración que esta no cubre deben ser realizadas vía terminal.

La carpeta contenedora del proyecto se puede encontrar en Google Drive por medio del enlace: https://drive.google.com/folderview?id=0B8_WsUeuEk3VZDJNenpKajBsZUE&usp=sharing_eid&ts=575b79d5

Funcionamiento

Una vez levantado el servidor, el sistema ejecuta el poller, que se encarga de recopilar información desde los dispositivos agregados y posteriormente se muestran en la página, este proceso es automático y se realiza cada 300 segundos, sin embargo el proceso de escaneo de redes debe hacerse manualmente cómo se describe a continuación:
El comando de escaneo automático tiene el siguiente formato,  “sudo php snmp-scan.php -r 192.168.1.1/24” donde el parámetro “-r” indica que hará uso del 24 para el rango de búsqueda dentro de la red, en este caso buscaria en las 256 direcciones del cuarto octeto. 

También es posible agregar dispositivos específicos individualmente desde la interfaz web, pero la acción de refrescar los datos de un equipo en un tiempo dado, es decir sin esperar por el auto refresh,  también debe realizarse desde la terminal con el siguiente comando:
sudo php poller.php -h 192.168.1.114, donde el parámetro “-h” indica que la dirección ip dada es el dispositivo a actualizar.
   
Es importante mencionar que todos estos archivos se encuentran en la carpeta de instalación de Librenms por lo que para ejecutarlos se debe estar posicionado en su dirección, lo cuál se logra con el siguiente comando:
cd /opt/librenms

La configuración de alertas y dispositivos se pueden realizar desde la interfaz web cómo se detalla en el video adjunto, el sistema es configurado para que las notificaciones pueden ser enviadas vía email o Telegram, el detalle de esta configuración se amplía en la documentación pues la opción de Telegram funciona con identificador estático para usuario del mismo.

Licencia 

Copyright (C) 2013-2016 por los contribuyentes individuales LibreNMS

Este programa está basado software libre: usted puede redistribuirlo y / o modificarlo bajo los términos de la Licencia Pública General de GNU según es publicada por la Free Software Foundation, bien de la versión 3 de la Licencia, o (a su elección) cualquier versión posterior.

Dada esta licencia no se ofrece ninguna garantía, ver Licencia Pública General de GNU para más detalles.

Esta configuración fue realizada por estudiantes de Instituto Tecnológico de Costa Rica cómo asignación del curso de redes, su distribución queda a criterio del profesor mientras se sigan los lineamientos de GNU GPL v3.

Juan Carlos Abarca Segura 
pat01822@gmail.com

Osvaldo Barrantes Paniagua
barrantesosvaldo@gmail.com

Melvin Salazar Sánchez
msalazars89@gmail.com

Brian Zumbado Huertas
brianeladiozumbado@gmail.com

