# 💊 Proyecto Farmacia - Sistema de Gestión

Este es un proyecto básico de sistema de gestión para una farmacia, desarrollado con **Spring Boot**, **JPA**, y **MySQL** como base de datos. Forma parte de una entrega académica de programación orientada a objetos y sistemas web con Java.

---

## ⚙️ Tecnologías Utilizadas

- Java 17
- Spring Boot 3.1+
- Spring Data JPA
- MySQL
- Maven

---

## 📄 Configuración

En el archivo `application.properties` la configuración debe ser la siguiente:

```properties
spring.application.name=farmacia

spring.datasource.url=jdbc:mysql://localhost:3306/farmacia
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8085
```

> ⚠️ Reemplaza `TU_CONTRASEÑA` por la contraseña real del usuario `root` de MySQL.

---

## 🚀 ¿Cómo ejecutar el proyecto?

1. Asegurarse de tener MySQL corriendo localmente y crear una base de datos llamada `farmacia`.
2. Clonar el repositorio y abrirlo en el IDE
3. Configura correctamente el archivo `application.properties`.
4. Ejecuta `FarmaciaApplication.java` como una aplicación Spring Boot.
5. Accede a los endpoints desde Postman o navegador en:  
   `http://localhost:8085`
