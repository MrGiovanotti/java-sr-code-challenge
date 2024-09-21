# Proyecto: Sistema de Gestión de Clientes y Cuentas

Este proyecto consiste en dos microservicios desarrollados en Java con Spring Boot, junto con una base de datos MySQL. Además, se incluyen archivos para realizar el despliegue del proyecto utilizando Docker, y una colección Postman para probar los endpoints de los microservicios.

## Estructura del proyecto

El proyecto está organizado en las siguientes carpetas:

- **code/**: Contiene el código fuente de los dos microservicios en Java/Spring Boot:
  - `customer-management`: Microservicio para la gestión de clientes.
  - `account-management`: Microservicio para la gestión de cuentas y movimientos.
  
- **database/**: Contiene el script SQL para la creación de la base de datos que utilizan los microservicios.

- **docker/**: Contiene todos los archivos necesarios para levantar los microservicios y la base de datos utilizando Docker y Docker Compose.

- **postman/**: Incluye la colección Postman para probar los endpoints de los microservicios.

## Requisitos

Para levantar este proyecto, asegúrate de tener instalado:

- **Docker**: [Instalar Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Instalar Docker Compose](https://docs.docker.com/compose/install/)

## Instrucciones para levantar el proyecto con Docker

1. Clonar el repositorio y situarse en la carpeta `docker/` desde la terminal.

2. Levantar la base de datos:
   En una consola, ejecutar el siguiente comando y esperar a que la base de datos esté lista:

   ```bash
   docker compose -f deploy_database.yml up --build
   ```

3. Levantar los microservicios: En una nueva consola, también situada en la carpeta docker/, ejecutar el siguiente comando para levantar los microservicios:

   ```bash
   docker compose -f deploy_microservices.yml up --build
   ```

   Esto levantará los dos microservicios: customer-management y account-management, conectados a la base de datos previamente creada.


4. Probar los servicios: Puedes utilizar la colección de Postman que se encuentra en la carpeta postman/ para probar los diferentes endpoints de los microservicios.