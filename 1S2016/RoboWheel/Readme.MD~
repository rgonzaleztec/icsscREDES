# CarTEC Repository

![alt tag](http://i.imgur.com/MjvDLiX.png)</p>

Este proyecto consiste en un carrito que puede ser manejado utilizando una app instalada sobre un teléfono con sistema operativo Android.

El carro tiene un sensor de temperatura y un sensor uv, que brindan información en tiempo real.

# Instrucciones de uso

![alt tag](http://i.imgur.com/nJfOPI1.png)

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

# Dillinger

Dillinger is a cloud-enabled, mobile-ready, offline-storage, AngularJS powered HTML5 Markdown editor.

  - Type some Markdown on the left
  - See HTML in the right
  - Magic

You can also:
  - Import and save files from GitHub, Dropbox, Google Drive and One Drive
  - Drag and drop files into Dillinger
  - Export documents as Markdown, HTML and PDF

Markdown is a lightweight markup language based on the formatting conventions that people naturally use in email.  As [John Gruber] writes on the [Markdown site][df1]

> The overriding design goal for Markdown's
> formatting syntax is to make it as readable
> as possible. The idea is that a
> Markdown-formatted document should be
> publishable as-is, as plain text, without
> looking like it's been marked up with tags
> or formatting instructions.

This text you see here is *actually* written in Markdown! To get a feel for Markdown's syntax, type some text into the left window and watch the results in the right.

### Version
3.2.7

### Tech

Dillinger uses a number of open source projects to work properly:

* [AngularJS] - HTML enhanced for web apps!
* [Ace Editor] - awesome web-based text editor
* [markdown-it] - Markdown parser done right. Fast and easy to extend.
* [Twitter Bootstrap] - great UI boilerplate for modern web apps
* [node.js] - evented I/O for the backend
* [Express] - fast node.js network app framework [@tjholowaychuk]
* [Gulp] - the streaming build system
* [keymaster.js] - awesome keyboard handler lib by [@thomasfuchs]
* [jQuery] - duh

And of course Dillinger itself is open source with a [public repository][dill]
 on GitHub.

### Installation

Dillinger requires [Node.js](https://nodejs.org/) v4+ to run.

You need Gulp installed globally:

```sh
$ npm i -g gulp
```

```sh
$ git clone [git-repo-url] dillinger
$ cd dillinger
$ npm i -d
$ NODE_ENV=production node app
```

### Plugins

Dillinger is currently extended with the following plugins

* Dropbox
* Github
* Google Drive
* OneDrive

Readmes, how to use them in your own application can be found here:

* [plugins/dropbox/README.md] [PlDb]
* [plugins/github/README.md] [PlGh]
* [plugins/googledrive/README.md] [PlGd]
* [plugins/onedrive/README.md] [PlOd]

### Development

Want to contribute? Great!

Dillinger uses Gulp + Webpack for fast developing.
Make a change in your file and instantanously see your updates!

Open your favorite Terminal and run these commands.

First Tab:
```sh
$ node app
```

Second Tab:
```sh
$ gulp watch
```

(optional) Third:
```sh
$ karma start
```

### Docker
Dillinger is very easy to install and deploy in a Docker container.

By default, the Docker will expose port 80, so change this within the Dockerfile if necessary. When ready, simply use the Dockerfile to build the image.

```sh
cd dillinger
docker build -t <youruser>/dillinger:latest .
```
This will create the dillinger image and pull in the necessary dependencies. Once done, run the Docker and map the port to whatever you wish on your host. In this example, we simply map port 80 of the host to port 80 of the Docker (or whatever port was exposed in the Dockerfile):

```sh
docker run -d -p 80:80 --restart="always" <youruser>/dillinger:latest
```

Verify the deployment by navigating to your server address in your preferred browser.

### N|Solid and NGINX

More details coming soon.

#### docker-compose.yml

Change the path for the nginx conf mounting path to your full path, not mine!

### Todos

 - Write Tests
 - Rethink Github Save
 - Add Code Comments
 - Add Night Mode

License
----

MIT


**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [@thomasfuchs]: <http://twitter.com/thomasfuchs>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [keymaster.js]: <https://github.com/madrobby/keymaster>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]:  <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>

