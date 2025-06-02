CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS usuario (
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

CREATE TABLE IF NOT EXISTS sector (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    categoria VARCHAR(50) NOT NULL, -- construcción, semáforos, etc.
    geom GEOGRAPHY(POLYGON, 4326)
);

CREATE TABLE IF NOT EXISTS tarea (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(50),
    descripcion VARCHAR(50),
    fechacreacion DATE,
    fechavencimiento DATE,
    estado VARCHAR(50),
    ubicacion GEOMETRY(POINT, 4326),
    eliminado BOOLEAN,
    usuario_id INTEGER REFERENCES usuario(id),
    sector_id INTEGER REFERENCES sector(id)  -- asociación con sector
);

CREATE TABLE IF NOT EXISTS notificacion (
    id SERIAL PRIMARY KEY,
    mensaje VARCHAR(250),
    fechaenvio DATE,
    tarea_id INTEGER REFERENCES tarea(id),
    usuario_id INTEGER REFERENCES usuario(id),
    visto BOOLEAN

);

--Proceso almacenado para Crear la notificación a tareas que vencen en 1 dia.
CREATE OR REPLACE PROCEDURE generar_notificaciones_por_vencer()
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO notificacion (mensaje, fechaenvio, tarea_id, usuario_id, visto)
    SELECT 
        'La tarea "' || t.titulo || '" está por vencer.',
        CURRENT_DATE,
        t.id,
        t.usuario_id,
        FALSE
    FROM tarea t
    WHERE 
        t.fechavencimiento = CURRENT_DATE + INTERVAL '1 day'
        AND t.eliminado IS DISTINCT FROM TRUE
        AND NOT EXISTS (
            SELECT 1 
            FROM notificacion n 
            WHERE n.tarea_id = t.id
              AND n.mensaje LIKE '%por vencer%'
        );
END;
$$;


-- Índice para búsquedas por email/nick (login)
CREATE INDEX IF NOT EXISTS idx_usuario_login ON usuario(email, nick);

-- Índices búsqueda de tareas segun ubicacion
CREATE INDEX IF NOT EXISTS idx_tarea_ubicacion ON tarea USING GIST(ubicacion);
CREATE INDEX IF NOT EXISTS idx_usuario_ubicacion ON usuario USING GIST(ubicacion);
