-- Usuarios
INSERT INTO usuario (rut, nombre, apellido, email, password, ubicacion, nick, tipo)
VALUES 
('11111111-1', 'Admin', 'Uno', 'admin@tareas.cl', 'admin123', ST_SetSRID(ST_MakePoint(-70.6506, -33.4372), 4326), 'admin1', 'admin'),
('22222222-2', 'Trabajador', 'Uno', 'usuario1@tareas.cl', 'usuario123', ST_SetSRID(ST_MakePoint(-70.6700, -33.4200), 4326), 'Bob El Contructor', 'cliente'),
('33333333-3', 'Trabajador', 'Dos', 'usuario2@tareas.cl', 'usuario123', ST_SetSRID(ST_MakePoint(-70.6100, -33.4900), 4326), 'Felix', 'cliente');

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
-- Sector 1 - Construcción
('Reparación edificio', 'Obra en construcción', '2025-05-05', '2025-06-15', 'realizada', ST_SetSRID(ST_MakePoint(-70.6300, -33.4100), 4326), false, 2, 1),
('Obra nuevo edificio', 'Comienzo de obra en Las Condes', '2025-05-11', '2025-06-20', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6400, -33.4050), 4326), false, 2, 1),
('Demolición muro', 'Inicio de demolición en terreno fiscal', '2025-05-07', '2025-05-27', 'realizada', ST_SetSRID(ST_MakePoint(-70.6250, -33.4100), 4326), true, 3, 1),
('Inspección terreno', 'Revisión inicial para construcción', '2025-05-01', '2025-05-20', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6450, -33.4100), 4326), false, 2, 1),
('Entrega de materiales', 'Llegada de concreto al sitio', '2025-05-03', '2025-05-17', 'realizada', ST_SetSRID(ST_MakePoint(-70.6480, -33.4180), 4326), false, 3, 1),
('Montaje estructura', 'Estructura metálica en obra nueva', '2025-05-05', '2025-06-10', 'realizada', ST_SetSRID(ST_MakePoint(-70.6420, -33.4120), 4326), false, 2, 1),
('Supervisión seguridad', 'Revisión de normas en obra', '2025-05-06', '2025-05-26', 'realizada', ST_SetSRID(ST_MakePoint(-70.6430, -33.4090), 4326), false, 2, 1),
('Retiro de escombros', 'Limpieza final de zona', '2025-05-10', '2025-05-20', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6460, -33.4160), 4326), false, 3, 1),

-- Sector 2 - Semáforos
('Instalar semáforo', 'Semáforo en mal estado', '2025-05-10', '2025-05-30', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6450, -33.5200), 4326), false, 3, 2),
('Revisión semáforo', 'Semáforo parpadea intermitente', '2025-05-14', '2025-05-28', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6550, -33.5250), 4326), false, 3, 2),
('Cambiar ampolletas', 'Semáforo con luz baja', '2025-05-09', '2025-05-23', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6320, -33.5150), 4326), true, 2, 2),
('Verificación sensores', 'Sensores de cruce peatonal', '2025-05-01', '2025-05-18', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6350, -33.5280), 4326), false, 2, 2),
('Instalación botón peatón', 'Nuevo botón de cruce solicitado', '2025-05-02', '2025-05-22', 'realizada', ST_SetSRID(ST_MakePoint(-70.6380, -33.5230), 4326), false, 3, 2),
('Reprogramación semáforo', 'Ajuste de tiempos en cruce', '2025-05-04', '2025-05-24', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6500, -33.5140), 4326), false, 2, 2),
('Cambio gabinete', 'Gabinete controlador dañado', '2025-05-07', '2025-05-27', 'realizada', ST_SetSRID(ST_MakePoint(-70.6450, -33.5190), 4326), false, 2, 2),
('Pintado señalización', 'Señalización en cruce desgastada', '2025-05-09', '2025-05-28', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6550, -33.5110), 4326), false, 3, 2),

-- Sector 3 - Calles
('Reparar vereda', 'Vereda dañada en zona norte', '2025-05-15', '2025-06-01', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6350, -33.4150), 4326), false, 2, 3),
('Pintado de paso peatonal', 'Paso desgastado frente a colegio', '2025-05-13', '2025-06-01', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6150, -33.4200), 4326), false, 2, 3),
('Nivelación de calzada', 'Desnivel cerca de plaza', '2025-05-08', '2025-06-02', 'realizada', ST_SetSRID(ST_MakePoint(-70.6180, -33.4250), 4326), false, 3, 3),
('Bache calle', 'Bache en calle principal', '2025-05-12', '2025-05-25', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6250, -33.4180), 4326), false, 3, 3),
('Ajuste tapas alcantarilla', 'Tapa mal posicionada', '2025-05-01', '2025-05-19', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6200, -33.4170), 4326), true, 2, 3),
('Corte de raíces', 'Raíces elevan vereda', '2025-05-02', '2025-05-22', 'realizada', ST_SetSRID(ST_MakePoint(-70.6120, -33.4270), 4326), false, 3, 3),
('Nivelación cunetas', 'Agua estancada por desnivel', '2025-05-03', '2025-05-23', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6140, -33.4210), 4326), false, 2, 3),
('Reemplazo adoquines', 'Adoquines rotos en zona peatonal', '2025-05-05', '2025-06-05', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6170, -33.4120), 4326), true, 3, 3),
('Reparación reja desagüe', 'Reja oxidada cede al paso', '2025-05-06', '2025-05-29', 'realizada', ST_SetSRID(ST_MakePoint(-70.6110, -33.4150), 4326), false, 2, 3);
