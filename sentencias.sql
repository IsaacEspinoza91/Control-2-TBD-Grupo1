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

-- [Bastián]
-- ¿Cuál es el sector con más tareas completadas en un radio de 2 kilómetros del usuario?

WITH usuario_referencia AS (
    SELECT
        id,
        ubicacion
    FROM
        usuario
    WHERE
        id = :usuarioId  -- Cambiar por el ID del usuario que interesa
),
     tareas_cercanas AS (
         SELECT
             t.sector_id,
             COUNT(*) AS tareas_completadas
         FROM
             tarea t
                 JOIN
             usuario_referencia u ON ST_DWithin(
                     t.ubicacion::geography,
                     u.ubicacion::geography,
                     2000  -- 2000 metros = 2 km
                                     )
         WHERE
             t.estado = 'realizada'
         GROUP BY
             t.sector_id
     )
SELECT
    s.id AS sector_id,
    s.nombre AS sector_nombre,
    tc.tareas_completadas
FROM
    sector s
        JOIN
    tareas_cercanas tc ON s.id = tc.sector_id
ORDER BY
    tc.tareas_completadas DESC
    LIMIT 1;

-- ¿Cuál es el sector con más tareas completadas dentro de un radio de 5 km desde la ubicación del usuario?

WITH usuario_referencia AS (
    SELECT
        id,
        ubicacion
    FROM
        usuario
    WHERE
        id = :usuarioId  -- Cambia por el ID del usuario que interesa
),
     tareas_cercanas AS (
         SELECT
             t.sector_id,
             COUNT(*) AS tareas_completadas
         FROM
             tarea t
                 JOIN
             usuario_referencia u ON ST_DWithin(
                     t.ubicacion::geography,
                     u.ubicacion::geography,
                     5000  -- 5000 metros = 5 km
                                     )
         WHERE
             t.estado = 'realizada'
         GROUP BY
             t.sector_id
     )
SELECT
    s.id AS sector_id,
    s.nombre AS sector_nombre,
    tc.tareas_completadas
FROM
    sector s
        JOIN
    tareas_cercanas tc ON s.id = tc.sector_id
ORDER BY
    tc.tareas_completadas DESC
    LIMIT 1;