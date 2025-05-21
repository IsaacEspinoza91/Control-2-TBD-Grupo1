-- Usuarios
INSERT INTO usuario (rut, nombre, apellido, email, password, ubicacion, nick, tipo)
VALUES 
('11111111-1', 'Admin', 'Uno', 'admin@tareas.cl', 'admin123', ST_SetSRID(ST_MakePoint(-70.6506, -33.4372), 4326), 'admin1', 'admin'),
('22222222-2', 'Cliente', 'Dos', 'cliente1@tareas.cl', 'cliente123', ST_SetSRID(ST_MakePoint(-70.6700, -33.4200), 4326), 'cliente1', 'cliente'),
('33333333-3', 'Cliente', 'Tres', 'cliente2@tareas.cl', 'cliente123', ST_SetSRID(ST_MakePoint(-70.6100, -33.4900), 4326), 'cliente2', 'cliente');

-- Sectores
INSERT INTO sector (nombre, categoria, geom)
VALUES
('Zona Norte - Construcción', 'construcción',
 ST_GeogFromText('POLYGON((
    -70.6500 -33.4200,
    -70.6500 -33.4000,
    -70.6200 -33.4000,
    -70.6200 -33.4200,
    -70.6500 -33.4200
 ))')),
('Zona Sur - Semáforos', 'reparación de semáforos',
 ST_GeogFromText('POLYGON((
    -70.6600 -33.5300,
    -70.6600 -33.5100,
    -70.6300 -33.5100,
    -70.6300 -33.5300,
    -70.6600 -33.5300
 ))')),
('Zona Norte - Calles', 'reparación de calles',
 ST_GeogFromText('POLYGON((
    -70.6400 -33.4300,
    -70.6400 -33.4100,
    -70.6100 -33.4100,
    -70.6100 -33.4300,
    -70.6400 -33.4300
 ))'));

-- Tareas
INSERT INTO tarea (titulo, descripcion, fechacreacion, fechavencimiento, estado, ubicacion, eliminado, usuario_id, sector_id)
VALUES
('Reparar vereda', 'Vereda dañada en zona norte', '2025-05-15', '2025-06-01', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6350, -33.4150), 4326), false, 2, 3),
('Instalar semáforo', 'Semáforo en mal estado', '2025-05-10', '2025-05-30', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6450, -33.5200), 4326), false, 3, 2),
('Reparación edificio', 'Obra en construcción', '2025-05-05', '2025-06-15', 'realizada', ST_SetSRID(ST_MakePoint(-70.6300, -33.4100), 4326), false, 2, 1),
('Obra nuevo edificio', 'Comienzo de obra en Las Condes', '2025-05-11', '2025-06-20', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6400, -33.4050), 4326), false, 2, 1),
('Revisión semáforo', 'Semáforo parpadea intermitente', '2025-05-14', '2025-05-28', 'realizada', ST_SetSRID(ST_MakePoint(-70.6550, -33.5250), 4326), false, 3, 2),
('Pintado de paso peatonal', 'Paso desgastado frente a colegio', '2025-05-13', '2025-06-01', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6150, -33.4200), 4326), false, 2, 3),
('Demolición muro', 'Inicio de demolición en terreno fiscal', '2025-05-07', '2025-05-27', 'realizada', ST_SetSRID(ST_MakePoint(-70.6250, -33.4100), 4326), false, 3, 1),
('Cambiar ampolletas', 'Semáforo con luz baja', '2025-05-09', '2025-05-23', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6320, -33.5150), 4326), false, 2, 2),
('Nivelación de calzada', 'Desnivel cerca de plaza', '2025-05-08', '2025-06-02', 'realizada', ST_SetSRID(ST_MakePoint(-70.6180, -33.4250), 4326), false, 3, 3),
('Bache calle', 'Bache en calle principal', '2025-05-12', '2025-05-25', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6250, -33.4180), 4326), false, 3, 3);
