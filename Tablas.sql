

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    rut VARCHAR(12) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    -- ubicacion GEOMETRY(POINT, 4326),
    nick VARCHAR(50) UNIQUE NOT NULL,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('admin', 'cliente'))
);


-- Índice para búsquedas por email/nick (login)
CREATE INDEX idx_usuario_login ON usuario(email, nick);