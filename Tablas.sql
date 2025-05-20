CREATE database GestorTareas;
\connect  gestortareas;

CREATE EXTENSION postgis;

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    rut VARCHAR(12) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    ubicacion GEOMETRY(POINT, 4326),
    nick VARCHAR(50) UNIQUE NOT NULL,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('admin', 'cliente'))
);

CREATE TABLE tarea (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(50),
    descripcion VARCHAR(50),
    fechacreacion DATE,
    fechavencimiento DATE,
    estado VARCHAR(50),
    sector VARCHAR(50),
    ubicacion GEOGRAPHY(POINT, 4326),
    eliminado BOOLEAN,
    usuario_id INTEGER REFERENCES usuario(id)
);

CREATE TABLE notificacion (
    id SERIAL PRIMARY KEY,
    mensaje VARCHAR(50),
    fechaenvio DATE,
    tarea_id INTEGER REFERENCES tarea(id),
    usuario_id INTEGER REFERENCES usuario(id)
);

-- Índice para búsquedas por email/nick (login)
CREATE INDEX idx_usuario_login ON usuario(email, nick);