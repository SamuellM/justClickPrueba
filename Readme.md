<h1> README.MD </h1>

• La base de datos de MySQL y Elastic Search se desplegaron en un servidor linode utilizando docker.
- http://li1155-138.members.linode.com:9200/user_agent/_search
- mysql://35.192.84.67:3306/clickdb

• max_value se definio en 3. pasado de esto o en caso que el usuario no exista será redirigido a la pagina not found 404.

• En el navegador escribir la url "http://localhost:8089/link/{username}"

• Remplazar username por los usuarios de pruebas que se inserto a la base.
-samuell5
-jose5
-carlosc
-jackI

• Al ejecutar el codigo dentro del archivo JustClickService cambiar el path por el path local donde se va a guardar el archivo
  pruebaClick.json


samuelmoreira@hotmail.es