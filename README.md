# Aplicación CRUD con Vaadin y PostgreSQL

## Descripción
Este proyecto es una aplicación educativa para el jug de guatemala  CRUD (Crear, Leer, Actualizar, Eliminar) simple, construida con Vaadin utilizando el componente add-crud, respaldada por una base de datos PostgreSQL. Permite a los usuarios gestionar información de clientes, como nombre, detalles de contacto, fecha de nacimiento, profesión, género y estado.

## Tecnologías Utilizadas
Frontend: Vaadin 24
Backend: Java (Spring Boot)
Componente CRUD: @add-crud (Vaadin Add-on)
Base de datos: PostgreSQL
Herramienta de construcción: Maven
IDE: IntelliJ IDEA
Docker (opcional): Configuración de PostgreSQL y despliegue del backend

Requisitos Previos
JDK 21
Maven instalado
PostgreSQL 12+ instalado
Docker (opcional)
Instrucciones de Instalación

## Clonar el Repositorio
Inicialmente, deberá obtener una copia del código fuente alojado en GitHub. Abra una terminal en su sistema y ejecute el siguiente comando para clonar el repositorio:

```bash
git clone https://github.com/crud-guate.git
```

## Cambiar al Directorio del Proyecto
Una vez clonado el repositorio, cambie al directorio del proyecto ejecutando:
```bash
cd crud-guate
```

2. Ejecutar la Aplicación
```bash 
mvn clean install
mvn spring-boot:run
```

3. Configuración con Docker (Opcional)
Si prefieres ejecutar la base de datos PostgreSQL con Docker:

Ejecutar Docker Compose:
```bash
docker-compose up
```

## Ejecutar la Aplicación
Una vez que la aplicación esté en ejecución, se puede acceder a ella a través de http://localhost:8081 para gestionar los datos de los clientes.

 ## Demostración de la API

Esta animación muestra el funcionamiento en vivo de nuestra API, destacando cómo los usuarios pueden registrarse, crear sus perfiles y consultar su información de manera segura y eficiente.

![Ejemplo](https://github.com/geovannymcode/crud-guate/blob/main/video/Crud_Vaadin.gif)



