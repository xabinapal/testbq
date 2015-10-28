*NOTA*: la memoria sobre la escalabilidad se encuentra en el fichero ESCALABILIDAD.md

Aplicación web desarrollada en Java y que corre bajo un servidor Tomcat.

Requiere el driver de conexión MySQL y un servidor al que conectar, donde se almacenarán todos los datos de usuarios y mensajes. El script de creación de tablas es testbq.sql

Para el envío de correos electrónicos requiere la api JavaMail y de un servidor de correo saliente SMTP.

Está compuesta de una página estática inicial con un formulario para crear usuarios y otro para enviar mensajes.

El resto de la aplicación se compone de varios servlets para cada una de las acciones a realizar.

- User.java: en el método doPost permite la creación de usuarios. Además, posee métodos estáticos accesibles desde el resto de servlets para obtener el listado de usuarios, comprobar la validez de un identificador y obtener el nombre de un usuario en base a su identificador.

- Message.java: en el método doPost se realiza el envío de mensajes por parte de usuarios. Posee métodos privados para almacenar los mensajes válidos y un log de los inválidos en la base de datos.

- MessageList.java: en el método doGet muestra el listado de mensajes y, dependiendo de si recibe un parámetro con el identificador de un usuario, se filtran los mensajes mostrándo los de dicho usuario.

- MessageExport.java: exporta un listado de mensajes en formato CSV, siguiendo las mismas directrices que el servlet anterior.

- MessageModel.java: es el modelado de un mensaje extraído de la base de datos.

- DatabaseHelper.java: clase utilizada para facilitar la comunicación con la base de datos.

La principal cuestión a mejorar sería las comprobaciones de seguridad sobre todos los inputs del usuario, evitando inyecciones HTML, XSS o SQL.