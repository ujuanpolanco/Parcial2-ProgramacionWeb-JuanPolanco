# Consultas medicas

Aplicacion web para gestionar consultas medicas con roles de administrador, medico y paciente. Hecha con Spring Boot, JPA, JSP y PostgreSQL.

## Como ejecutarlo

1. Tener PostgreSQL con usuario postgres y contraseña 1234 (si es otra se cambia en application.properties).
2. Ejecutar los scripts de la carpeta consultas_medicas/sql en orden: 00, 01 y 02.
3. Abrir una terminal en la carpeta consultas_medicas y ejecutar: mvnw spring-boot:run
4. Entrar a http://localhost:8081

La documentacion de los servicios esta en http://localhost:8081/swagger-ui.html

## Usuarios

Todos tienen la contraseña 1234.

- Administrador: admin
- Medicos: jpolanco, sramos, amayor
- Pacientes: pablo, samuel
