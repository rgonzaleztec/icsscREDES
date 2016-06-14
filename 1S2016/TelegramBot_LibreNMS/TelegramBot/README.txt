Introducción 

Los bots son las aplicaciones de terceros que se ejecutan dentro del telegram. Los usuarios pueden interactuar con los robots mediante el envío de mensajes, comandos y peticiones en línea.

Estructura

Para nuestro caso, se crearon 2 bots para mostrar diferentes formas de realizarlo, uno mediante comandos configurados, en el cual se inician una serie de preguntas (trivias). Y otro bot que se creó de manera programada el cual a la hora de recibir una petición da una respuesta previamente configurada desde nuestro sistema programado.

Los bots, se encuentra estructurado de la siguiente manera.
	1. Se utiliza la API de telegram (@BotFather) para crear el nuevo bot en el cual podremos establecer un nombre, una descripción y más datos sobre nuestro bot.
	2. Esto nos genera una clave de la API la cual hace referencia a nuestro BOT y con la cual vamos a identificarlo en el entorno de Telegram.
	3. En nuestro primer bot, con clave anterior del API, podemos utilizar otra API llamada ManyBot para modificar ciertas propiedades de nuestro bot creado y que nos facilitaría la edicion de los comandos y crear nuestro sistema de trivia. En nuestro segundo BOT, debemos tener en cuenta que se necesitan descargar diferentes librerías con sus respectivas dependencias, que para nuestro caso se utilizó el lenguaje de JAVA, y dentro de las configuraciones de nuestra aplicación debemos incluir la clave de la API de nuestro bot.
	4. Al tener configurado y programado, según sea el caso de nuestros bots, es posible acceder a ellos y buscarlos desde Telegram con el nombre que les dimos para comenzar a utilizarlos según como lo hayamos programado.

Para acceder al proyecto se debe hacer desde el siguiente enlace en Github: https://github.com/barrantesosvaldo/TriviaRedesBot
Dentro de la carpeta TriviaRedesBot se encuentra el bot programado, ahí también se encuentra una carpeta con el nombre guía donde se puede visualizar paso a paso como se realizó la creación del bot @TriviaRedesBot, pasos con los cuales también se creó el bot @TEC_RedesBot.

Funcionamiento

En el núcleo de Telegram los bots son cuentas especiales que no requieren un número telefónico adicional de configurar. 

Los usuarios pueden interactuar con los bots de dos maneras:

	Enviar mensajes y órdenes a los robots mediante la apertura de una charla con ellos o mediante su inclusión en grupos.

	Enviar solicitudes directamente desde el campo de entrada escribiendo @ nombre de usuario del bot y una consulta. Esto permite el envío de contenido de los robots en línea directamente en cualquier charla, grupo o canal.

Mensajes, comandos y las solicitudes enviadas por los usuarios se pasan al software que se ejecuta en los servidores. El servidor intermediario se encarga de toda la encriptación y la comunicación con la API de telegramas. Al comunicarse con este servidor a través de una sencilla interfaz HTTPS que ofrece una versión simplificada de la API de telegram. Llamamos a esa interfaz nuestra API Bot.

Ejecución

	1. Para ejecutar alguno de los bots es necesario tener la aplicación de telegram y escribir el nombre de nuestro bot por ejemplo en el caso del Bot#1 escribir @TEC_RedesBot, para el segundo caso @TriviaRedesBot.
	2. Presiona Iniciar o con su comando equivalente /start
	3. En breve te mostrará toda la descripción sobre el bot y la instrucción para comenzar la trivia, además de una pequeña ayuda e indicaciones proporcionadas por el programa manybot al cual asociamos para este caso nuestro bot configurado, para el caso del bot programado a falta de funcionalidad solo se muestra una descripción y para utilizarlo basta con enviar cualquier mensaje y el bot enviará una respuesta, y ahí termina la funcionalidad de este bot.
	4. Continuando con el bot configurado, te puede aparecer el menú donde das en Iniciar o su comando equivalente /iniciar.
	5. Comienzas a contescar cada una de las preguntas que el bot te va suministrando hasta terminar, en donde te mostrará tus respuestas seleccionadas.

Licencia 

Este programa está basado software libre y disponible para los desarrolladores y usuarios que deseen utilizarlos.

Esta configuración fue realizada por estudiantes de Instituto Tecnológico de Costa Rica cómo asignación del curso de redes, su distribución queda a criterio del profesor.

Juan Carlos Abarca Segura 
pat01822@gmail.com

Osvaldo Barrantes Paniagua
barrantesosvaldo@gmail.com

Melvin Salazar Sánchez
msalazars89@gmail.com

Brian Zumbado Huertas
brianeladiozumbado@gmail.com

