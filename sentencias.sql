-- [Isaac]
-- 1. ¿Cuál es la tarea más cercana al usuario (que esté pendiente)? CONSIDERA UBICACION X QUE INGRESA EL USUARIO
-- Ingresa id usuario y coordenadas (lng, lat)
SELECT 
    t.id,
    t.titulo,
    t.descripcion,
    s.nombre AS sector,
    ST_Distance(
        t.ubicacion::geography, 
        ST_SetSRID(ST_MakePoint(-70.6700, -33.4200), 4326)::geography
    ) AS distancia_metros
FROM tarea AS t
	JOIN usuario AS u ON t.usuario_id = u.id
	LEFT JOIN sector AS s ON t.sector_id = s.id
WHERE 
    u.id = 2
    AND t.estado = 'pendiente' AND t.eliminado = false
ORDER BY distancia_metros ASC LIMIT 1;


-- 2. ¿Cuál es la tarea pendiente más cercana a la ubicación del usuario? CONSIDERA LA UBICACION DE USUARIO EN TABLA
-- Ingresa el id usuario
SELECT 
    t.id,
    t.titulo,
    t.descripcion,
    s.nombre AS sector,
    ST_Distance(
        t.ubicacion::geography, 
        u.ubicacion::geography
    ) AS distancia_metros
FROM tarea AS t
	JOIN usuario AS u ON t.usuario_id = u.id 
	LEFT JOIN sector AS s ON t.sector_id = s.id
WHERE 
    u.id = 2 
	AND t.estado = 'pendiente' AND t.eliminado = false
ORDER BY distancia_metros ASC LIMIT 1;

