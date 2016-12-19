# Proyecto de Redes Verano 2016-2017

* [Team Members](#team-members)

# <a name="team-members"></a>Team Members
* "XXXXXXXX" <xxxxx@dominio.com>

# Descripción del Proyecto a desarrollar
Instalar configurar y documentar los siguientes servicios de RED: Central Telefónica IP (Elastix 5.0), NTOPNG (Analizador de Tráfico de Red con paquetes de NetFlow) y un router para un red pequeña basado en ZeroShell.

# Características básicas para Elastix(5.0)
Debe tener la capacidad de registrar teléfonos IP mediante SIP, asignarles una extensión que tengan al menos: Contestadora de llamadas activida, Identificador de llamadas, llamada en espera, aviso de mensajes de voz grabados.
La Central debe tener una Contestadora de llamadas automatica, que indique los departamentos disponibles de la Sede Regional, para que los usuarios al llamar puedan digitar el número de extensión y sean comunicados sin necesidad de intervención humana.
La Central debe poder crear troncales con otras centrales telefónicas SIP como lo son Huawei y debe poderse conectar con una central Cisco.

# Características básicas para NTOPNG (versión estable reciente)
Debe quedar configuración funcionando para todas las vlans del TEC en la Sede Regional, con un almacenamiento historico en base de datos no menor a 200 megas inicialmente.

# Características básicas para Zeroshell(versión estable reciente)
Montado sobre una máquina virtual para las demos, con dos tarjetas de red activas, una para la LAN y otra para Internet.
Se deben de crear almenos 4 redes virtuales o VLAN utilizando el protocolo 802.1q.
Se debe dejar corriendo el Squid para todas las Redes.
Se debe dejar corriendo la caracteristica de ancho de banda por red, donde no se pueda descargar a más de 1 Mbps por red.
Se debe de poder navegar entre redes, osea se debe activar el routing entre vlans.


# Entregables para calificar
Se debe entregar el demo funcionando en MV
Se debe entregar una documentacón detallada de como configurar todo desde cero, incluidas todas las caracteristicas solicitadas
Se deben hacer pruebas con la oficina de soporte en tiempo real


# Fecha de Entrega completa y FINAL
<b> Lunes 23 de Enero de 2017 </b>

