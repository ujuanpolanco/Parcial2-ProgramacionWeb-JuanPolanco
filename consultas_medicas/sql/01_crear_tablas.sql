-- Paso 2: crear las tablas (ejecutar conectado a la base "consultas_medicas").
-- Se borran primero por si el script se vuelve a ejecutar.
DROP TABLE IF EXISTS consulta;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS medico;

-- Médicos: tabla independiente que alimenta la lista desplegable del formulario de consultas.
CREATE TABLE medico (
    id           SERIAL PRIMARY KEY,
    nombre       VARCHAR(60) NOT NULL,
    especialidad VARCHAR(60) NOT NULL
);

-- Usuarios que inician sesión. El rol define qué vistas y servicios puede usar.
-- medico_id solo se llena cuando el rol es MEDICO.
-- Para el rol PACIENTE, el campo nombre debe ser igual al "nombre del paciente" de sus consultas.
CREATE TABLE usuario (
    id        SERIAL PRIMARY KEY,
    username  VARCHAR(30) NOT NULL UNIQUE,
    password  VARCHAR(60) NOT NULL,
    nombre    VARCHAR(60) NOT NULL,
    rol       VARCHAR(15) NOT NULL CHECK (rol IN ('ADMINISTRADOR', 'MEDICO', 'PACIENTE')),
    medico_id INT REFERENCES medico (id)
);

-- Consultas (asignación de consultorio).
CREATE TABLE consulta (
    id                 SERIAL PRIMARY KEY,
    nombre_paciente    VARCHAR(40)  NOT NULL,
    motivo             VARCHAR(100) NOT NULL,
    numero_consultorio INT          NOT NULL CHECK (numero_consultorio > 0),
    hora_inicio        TIME         NOT NULL,
    hora_fin           TIME         NOT NULL,
    medico_id          INT          NOT NULL REFERENCES medico (id),
    CHECK (hora_fin > hora_inicio)
);
