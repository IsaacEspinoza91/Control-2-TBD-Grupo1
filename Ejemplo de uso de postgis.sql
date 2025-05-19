
CREATE EXTENSION postgis; -- agrego primero la extension (debe estar instalada)

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    ubicacion GEOMETRY(POINT, 4326) -- Definir correctamente el tipo de dato
);

---Ejemplos de ususarios

-- Insertar un usuario con ubicación en la Universidad de Santiago de Chile
INSERT INTO usuario (nombre, password, ubicacion)
VALUES (
    'Pedro Fernández',
    'hash_seguro',
    ST_SetSRID(ST_MakePoint(-70.682766, -33.448942), 4326)  -- 
    -- ST_MakePoint(longitud, latitud) crea un punto geográfico con las coordenadas dadas
    -- -70.682766 representa la longitud, y -33.448942 la latitud de la Universidad de Santiago de Chile
    -- ST_SetSRID(geometría, srid) asigna el sistema de referencia espacial (SRID)
    -- El valor 4326 corresponde al sistema WGS 84, comúnmente usado para coordenadas geográficas
);

-- Insertar un usuario con ubicación (longitud, latitud)
INSERT INTO usuario (nombre, password, ubicacion)
VALUES (
    'Juan Pérez',
    'hash_de_contraseña_segura',
    ST_SetSRID(ST_MakePoint(-99.1332, 19.4326), 4326)  -- Coordenadas de Ciudad de México
);

-- Otro ejemplo
INSERT INTO usuario (nombre, password, ubicacion)
VALUES (
    'María García',
    'otro_hash_seguro',
    ST_SetSRID(ST_MakePoint(-58.3816, -34.6037), 4326)  -- Coordenadas de Buenos Aires
);
