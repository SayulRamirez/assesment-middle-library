# Inventory System for Digital Library :computer: :books:

El proyecto fue realizado en el booster de Metaphorce Java Middle como reto final del entrenamiento, es 
un sistema de inventario de los prestamos realizados para una librería digita.

El proyecto fue realizado mediante una arquitectura de microservicios.

* Cada microservicio tiene su base de datos.
* Se agregó un microservicio gateway que se encargó de realizar el enrutamiento además de manejar la 
seguridad de los accesos a los demás microservicios.
* Se agregó el microservicio book :closed_book: para el manejo de lo relacionado a los libros
* Se agregó el microsercvicio loan para el manejo de lo relacionado a los prestamos

## Herramientas y tecnologías utilizadas:

* Java  17	:coffee:
* Spring Boot 3.2.0
    * Spring Security
    * Data Jpa 
    * Open API - Swagger 
    * Validation
    * Data MongoDB
* Json Web Token 0.11.5
* MySQL
* MongoDB :leaves:
* Docker :whale2:
* Lombok 1.18.38
* Postman
* Git

## Configuración :construction:
Para la ejecución del proyecto se usó docker para poder crear las imágenes y contenedores
fácilmente, solo necesitas seguir los pasos mencionados a continuación:

1. Instalar docker desde su sitio oficial https://www.docker.com/ y ejecutarlo una vez instalado.
2. Descargar o clonar este repositorio.
3. Configuración del archivo docker-compose: se agrego un archivo en la raíz del directorio
llamado docker-compose-example.yml el cual debe de ser renombrado a docker-compose.yml.
4. Dentro del archivo se deberá de remplazar los campos indicados, como las credenciales
de las bases de datos, los nombres de las bases de datos entre otros mencionados en el archivo.
5. :wrench: Para facilitar la configuración los microservicios contienen el DockerFile configurado.
6. Deberás ejecutar los comandos de Maven si no se tiene instalado, podrás realizarlo desde
los accesos de tu editor o IDE:
   * Limpiar el proyecto
     > mvn clean
   * Crear los .jar
     > mvn install -DskipTests
7. Una vez configurado el docker-compose.yml deberás abrir una terminal y situarte en la raiz del
proyecto y ejecutar los siguientes comandos:
   * Construir la imagenes de los microservicios:
     > docker-compose build 
   * Crear los contenedores y ejecutarlos para iniciar la aplicación:
     > docker-compose up

Una vez levantados los contenedores se podrá realizar las peticiones a los microservicios

### Seguridad

La aplicación cuenta con autenticación basada en tokens, estos los cuales son necesarios para
poder realizar con éxito las peticiones a los servicios, se debe de agregar en la petición 
la siguiente cabecera:

> Authorization: Bearer token_generado

> [!NOTE]
> Solo hay un endpoint que es público el cual no necesita de la cabecera de Authorization  
> URL: http://localhost:8083/api/v1/book/all

Para poder obtener el token debes de:
1. Estar registrado.
2. Realizar el login de la aplicación.

## Microservicio Gateway
##### Registro

URL: http://localhost:8083/auth/register  
Método: POST  
Cuerpo en formato json:
````
{
  "names": "string",
  "username": "string",
  "password": "string"
}
````

Respuestas:
- 200 si el registro fue exitoso
- 409 si el username ya está registrado

##### Login

URL: http://localhost:8083/auth/login  
Método: POST  
Cuerpo en formato json:
````
{
  "username": "string",
  "password": "string"
}
````

Respuestas:
- 200 conteniendo el token generado si el registro fue exitoso
- Un código 401 si no se autentico correctamente

## Microservicio Prestamos

#### Creación del préstamo
URL: http://localhost:8083/api/v1/loan 
Método: POST  
Cuerpo en formato json:
````
{
  "idBook": 0,
  "idUser": 0
}
````

Respuestas:
- 200:
  ````
  {
    "idLoan": "string",
    "idBook": 0,
    "loanDueDate": "2025-08-08",
    "borrowed": true
  }
  ````

##### Actualizar el estado del prestamó

URL: http://localhost:8083/api/v1/loan/{idLoan}
Método: PUT  
Sin cuerpo:
Parámetro de la url: id del prestamó

Respuestas:
- 200 si se actualizo correctamente
- 404 si no se encontró el préstamo a actualizar

##### Obtener todos los préstamos de un usuario por su id

URL: http://localhost:8083/api/v1/loan/{idUser}
Método: GET  
Sin cuerpo:
Parámetro de la url: id del usuario

Respuestas:
- 200 conteniendo una lista de los préstamos del usuario o una lista vacía si no se encontraron
  ````
  [
    {
      "idLoan": "string",
      "idBook": 0,
      "loanDueDate": "2025-08-08",
      "borrowed": true
    }
  ]
  ````
## Microservicio Libros

#### Obtener el libro por su id
URL: http://localhost:8083/api/v1/book/{id}
Método: GET
Sin cuerpo:
Parámetro de la url: id del libro

Respuestas:
- 200 conteniendo el contenido del libro
- 404 si no se encontró el libro buscado

##### Obtener la portada del libro por su id

URL: http://localhost:8083/api/v1/book/{id}/cover
Método: GET
Sin cuerpo:
Parámetro de la url: id del libro

Respuestas:
- 200 conteniendo la portada del libro
- 404 si no se encontró el libro

##### Obtener todos los libros disponibles

URL: http://localhost:8083/api/v1/book/all
Método: GET
Sin cuerpo:

Respuestas:
- 200 conteniendo una lista de los libros o una lista vacía si no se encontraron
  ````
  [
    {
      "id": 0,
      "title": "string",
      "summary": "string",
      "names": "string",
      "genere": "string"
    },
  ]
  ````
