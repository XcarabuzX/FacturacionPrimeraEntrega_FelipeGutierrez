# 💊 Proyecto Farmacia - Sistema de Gestión

Este es un sistema de gestión básico para una farmacia, desarrollado con **Java 17**, **Spring Boot** y **MySQL**. Permite administrar clientes, productos y ventas de forma eficiente. Es parte de una entrega académica de desarrollo web y programación orientada a objetos.

---

## ⚙️ Tecnologías Utilizadas

- Java 17
- Spring Boot 3.1+
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Postman (para pruebas de API)

---

## 🧩 Módulos Implementados

### Clientes
- Registro, listado, edición y eliminación de clientes.

### Productos
- Registro, listado, edición y eliminación de productos.

### Ventas
- Registrar ventas asociadas a un cliente y uno o varios productos.
- Obtener lista de ventas.
- Obtener venta por ID.
- Eliminar venta.

---

## 🗃️ Entidades Relacionales

- `Cliente` ←→ `Venta` (1:N)
- `Producto` ←→ `Venta` (N:M)

Las relaciones se manejan automáticamente mediante JPA, y las operaciones CRUD están expuestas a través de controladores REST.

---

## 🔌 Configuración del proyecto

Asegúrate de tener tu base de datos MySQL corriendo y crea una base llamada `farmacia`.

### 📄 `application.properties`
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

> ⚠️ Reemplaza `TU_CONTRASEÑA` por la contraseña real del usuario `root` de MySQL.

---

## 🚀 ¿Cómo ejecutar el proyecto?

1. Asegurarse de tener MySQL corriendo localmente y crear una base de datos llamada `farmacia`.
2. Clonar el repositorio y abrirlo en el IDE
3. Configura correctamente el archivo `application.properties`.
4. Ejecuta `FarmaciaApplication.java` como una aplicación Spring Boot.
5. Accede a los endpoints desde Postman o navegador en:  
   `http://localhost:8085`
