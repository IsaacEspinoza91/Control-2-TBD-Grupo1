--admin: Correo:admin@tareas.cl ; Contraseña:admin123
--trabajador1: Correo:usuario1@tareas.cl ; Contraseña:usuario123
--trabajador2: Correo:usuario2@tareas.cl ; Contraseña:usuario123

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



-- Sectores de Stgo
INSERT INTO Sector (nombre, categoria, geom)
VALUES 
('Usachita', 'estudio', 'POLYGON((-70.691901 -33.449412 , -70.691403 -33.451223 , -70.688108 -33.450753, -70.68716898007807 -33.45088307680278, -70.68692574283705 -33.45276617163064, -70.68523638791628 -33.45234896150979, -70.68568913599253 -33.45091127381412, -70.68477068268996  -33.45095355753359, -70.68463689616684 -33.4513442481911, -70.68421642275914 -33.45123062831517, -70.68422836928731 -33.450955550267246, -70.68032580232988 -33.450985504008685, -70.67959785147349 -33.450775304000175, -70.67978699159976  -33.448470799510254, -70.6809119965317 -33.448537854379765, -70.68105583798027 -33.446038903241806, -70.68359992719911 -33.44583570483628, -70.68384347732166 -33.4494263766006 , -70.691901 -33.449412 ))'),
('Parque Ohiggins', 'recreacion', 'POLYGON((-70.66390200318628 -33.458608495974566, -70.66328456189689 -33.47030399732882, -70.65674694823667 -33.470031322259, -70.65747334975393 -33.45812367429356, -70.6614322380263 -33.458456989490564, -70.66390200318628 -33.458608495974566))'),
('Aeropuero Comodoro Arturo Benitez', 'viaje', 'POLYGON((-70.80437253430804 -33.415760149762754, -70.78090930240226 -33.41260127368303, -70.78402091380184 -33.366328129803236, -70.80706365768094 -33.36660907295156, -70.80437253430804 -33.415760149762754))'),
('Cemeterio General', 'reparaciones', 'POLYGON(( -70.64373236828018 -33.40882822629869, -70.65331396603061 -33.406736675002016, -70.65298356610819 -33.41744672499053, -70.64844056717432 -33.41809020524517, -70.6438975682411 -33.4160218588858, -70.64373236828018 -33.40882822629869))'),
('Santiago Centro', 'mantencion', 'POLYGON((-70.65903070510221 -33.43215964703319, -70.65903070510221 -33.44757752962908, -70.64099585934638 -33.44757752962908, -70.64099585934638 -33.43215964703319,-70.65903070510221 -33.43215964703319))'),
('Estadio Monumental', 'futbol', 'POLYGON((-70.60560295030214  -33.501965381403515  ,-70.60662920241455  -33.50464671385915 ,-70.60856197722524 -33.50687158624082 , -70.6084080394085 -33.508297756400815,-70.6045253855844 -33.5086828183143,-70.6032254662426 -33.50220784573277,-70.60560295030214 -33.501965381403515))'),
('Estadio Santa Laura', 'futbol', 'POLYGON((-70.66034139019045 -33.403978581773806,-70.6595865875968 -33.405286748509575,-70.65814261746782 -33.405019637535474,-70.65861847126027 -33.40367722123367,-70.66034139019045 -33.403978581773806))'),
('Estadio Nacional', 'entretencion', 'POLYGON((-70.61482126075887 -33.46194019635161,-70.61358576233047 -33.47046037453423,-70.60685229589542 -33.47001377309235, -70.60627572996232  -33.462489910540015,-70.61482126075887 -33.46194019635161))'),
('Palafitos Chiloe', 'paisaje', 'POLYGON(( -73.76628847428198 -42.46939953980263,-73.76567481837061 -42.47175330648503, -73.7640676243171  -42.47181365831279,-73.76528324745568  -42.469196922049576,-73.76628847428198 -42.46939953980263))'),
('Mall Costanera Center', 'entretencion', 'POLYGON((-70.60716038369132 -33.41641294500882,-70.60872909166642 -33.418077370154904,-70.60605696013972  -33.41955241582441,-70.60510947127135  -33.41739315371645,-70.60716038369132 -33.41641294500882))'),
('Mall Parque Arauco', 'entretencion', 'POLYGON((-70.57654877650133 -33.400150597061014,-70.58408871914719 -33.403404315184524,-70.58113830678337 -33.4053047951331,-70.5744725603311 -33.40290258154119, -70.57654877650133 -33.400150597061014))'),
('Escuela de Ingenieria Beaucheff UCH', 'estudio', 'POLYGON((-70.66100882823994 -33.456941604897715,-70.66496680390426 -33.45725867593751,-70.66493830919126 -33.45847830038443,-70.66087897066083 -33.458221667406754,-70.66100882823994 -33.456941604897715))'),
('Faro La Serena', 'reparaciones', 'POLYGON((-71.27461563540966 -29.905288977417676,-71.27461563540966 -29.90582082054565,-71.2739120478745  -29.90582082054565,-71.2739120478745 -29.905288977417676,-71.27461563540966 -29.905288977417676))'),
('Memorial 11 Septiembre', 'reparaciones', 'POLYGON((-74.01393903773457 40.71283510361869,-74.01427606441122 40.71104900108736,-74.0127067672041 40.710217773546844,-74.01200976357738 40.712035610292645,-74.01393903773457 40.71283510361869))'),
('Torre Eifell', 'reparaciones','POLYGON((2.295101474231217 48.86001989359747,2.291098198388312 48.85749397304295,2.300745420786427 48.851254373495635,2.3052737314842773 48.85457934500681, 2.295101474231217 48.86001989359747))');



-- Tareas
INSERT INTO tarea (titulo, descripcion, fechacreacion, fechavencimiento, estado, ubicacion, eliminado, usuario_id, sector_id)
VALUES
-- Sector 1 - Construcción
('Reparación edificio', 'Obra en construcción', '2025-05-05', '2025-06-15', 'realizada', ST_SetSRID(ST_MakePoint(-70.6300, -33.4100), 4326), false, 2, 1),
('Obra nuevo edificio', 'Comienzo de obra en Las Condes', '2025-05-11', '2025-06-20', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6400, -33.4050), 4326), false, 2, 1),
('Demolición muro', 'Inicio de demolición en terreno fiscal', '2025-05-07', '2025-05-27', 'realizada', ST_SetSRID(ST_MakePoint(-70.5750, -33.4700), 4326), true, 3, 1),
('Inspección terreno', 'Revisión inicial para construcción', '2025-05-01', '2025-05-20', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6450, -33.4100), 4326), false, 2, 1),
('Entrega de materiales', 'Llegada de concreto al sitio', '2025-05-03', '2025-05-17', 'realizada', ST_SetSRID(ST_MakePoint(-70.6150, -33.4950), 4326), false, 3, 1),
('Montaje estructura', 'Estructura metálica en obra nueva', '2025-05-05', '2025-06-10', 'realizada', ST_SetSRID(ST_MakePoint(-70.6600, -33.4250), 4326), false, 2, 1),
('Supervisión seguridad', 'Revisión de normas en obra', '2025-05-06', '2025-05-26', 'realizada', ST_SetSRID(ST_MakePoint(-70.6430, -33.4090), 4326), false, 2, 1),
('Retiro de escombros', 'Limpieza final de zona', '2025-05-10', '2025-05-20', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6150, -33.4950), 4326), false, 3, 1),

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

--Notificaciones de prueba
INSERT INTO notificacion (mensaje, fechaenvio, tarea_id, usuario_id, visto) VALUES
(
  'Notificacion de prueba, si quieres generar notificaciones puedes editar una tarea y poner un mas de la fecha actual (Las notificaiones se actualizan cada 40 segundos)',
  CURRENT_DATE,
  1,
  2,
  false
),
(
  'Notificacion de prueba, si quieres generar notificaciones puedes editar una tarea y poner un mas de la fecha actual (Las notificaiones se actualizan cada 40 segundos)',
  CURRENT_DATE,
  3,
  3,
  false
);

-- Tareas adicionales para Sector 1 (Construcción)
INSERT INTO tarea (titulo, descripcion, fechacreacion, fechavencimiento, estado, ubicacion, eliminado, usuario_id, sector_id)
VALUES
('Cimentación edificio B', 'Preparación terreno para cimientos', '2025-05-16', '2025-06-05', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6320, -33.4080), 4326), false, 2, 1),
('Instalación andamios', 'Montaje andamios para fachada', '2025-05-17', '2025-06-07', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6410, -33.4070), 4326), false, 3, 1),
('Revisión planos', 'Verificación contra obra real', '2025-05-18', '2025-05-28', 'realizada', ST_SetSRID(ST_MakePoint(-70.6300, -33.4400), 4326), false, 2, 1),
('Pruebas de carga', 'Pruebas estructura principal', '2025-05-19', '2025-06-09', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6350, -33.4110), 4326), false, 3, 1),
('Instalación ventanas', 'Colocación de marco y vidrios', '2025-05-20', '2025-06-10', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6340, -33.4130), 4326), false, 2, 1),
('Impermeabilización', 'Aplicación en techos', '2025-05-21', '2025-06-01', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6370, -33.4140), 4326), false, 3, 1),
('Instalación eléctrica', 'Cableado primer piso', '2025-05-22', '2025-06-12', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6390, -33.4150), 4326), false, 2, 1),
('Pintura interior', 'Primera mano en departamentos', '2025-05-23', '2025-06-13', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6360, -33.4160), 4326), false, 3, 1),
('Instalación ascensor', 'Montaje sistema de elevación', '2025-05-24', '2025-06-14', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6330, -33.4170), 4326), false, 2, 1),
('Jardinería exterior', 'Preparación áreas verdes', '2025-05-25', '2025-06-15', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6320, -33.4180), 4326), false, 3, 1),

-- Tareas adicionales para Sector 2 (Semáforos)
('Reemplazo poste', 'Poste semáforo accidentado', '2025-05-16', '2025-05-31', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6540, -33.5210), 4326), false, 3, 2),
('Actualización software', 'Actualización controladores', '2025-05-17', '2025-06-01', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6530, -33.5220), 4326), false, 2, 2),
('Instalación cámaras', 'Cámaras para monitoreo tráfico', '2025-05-18', '2025-06-02', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6520, -33.5230), 4326), false, 3, 2),
('Reparación cableado', 'Cables expuestos en cruce', '2025-05-19', '2025-06-03', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6510, -33.5240), 4326), false, 2, 2),
('Calibración sensores', 'Ajuste sensores vehiculares', '2025-05-20', '2025-06-04', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6500, -33.5250), 4326), false, 3, 2),
('Limpieza lentes', 'Limpieza de lentes semáforos', '2025-05-21', '2025-06-05', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6490, -33.5260), 4326), false, 2, 2),
('Instalación señalética', 'Señales informativas nuevas', '2025-05-22', '2025-06-06', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6480, -33.5270), 4326), false, 3, 2),
('Reposición viseras', 'Viseras dañadas por vandalismo', '2025-05-23', '2025-06-07', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6470, -33.5280), 4326), false, 2, 2),
('Verificación alimentación', 'Revisión fuente de poder', '2025-05-24', '2025-06-08', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6460, -33.5290), 4326), false, 3, 2),
('Instalación semáforo peatonal', 'Nuevo cruce peatonal', '2025-05-25', '2025-06-09', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6450, -33.5300), 4326), false, 2, 2),

-- Tareas adicionales para Sector 3 (Calles)
('Reparación brocal', 'Brocal de alcantarilla roto', '2025-05-16', '2025-05-31', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6190, -33.4160), 4326), false, 2, 3),
('Poda de árboles', 'Ramas obstruyen calzada', '2025-05-17', '2025-06-01', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6200, -33.4170), 4326), false, 3, 3),
('Reparación cordón', 'Cordón de vereda dañado', '2025-05-18', '2025-06-02', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6210, -33.4180), 4326), false, 2, 3),
('Limpieza sumideros', 'Sumideros obstruidos', '2025-05-19', '2025-06-03', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6220, -33.4190), 4326), false, 3, 3),
('Reposición baldosas', 'Baldosas rotas en paseo', '2025-05-20', '2025-06-04', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6230, -33.4200), 4326), false, 2, 3),
('Pintado mobiliario', 'Bancas y basureros', '2025-05-21', '2025-06-05', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6240, -33.4210), 4326), false, 3, 3),
('Reparación escaleras', 'Escaleras de acceso dañadas', '2025-05-22', '2025-06-06', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6250, -33.4220), 4326), false, 2, 3),
('Instalación barandas', 'Barandas de seguridad nuevas', '2025-05-23', '2025-06-07', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6260, -33.4230), 4326), false, 3, 3),
('Reparación luminarias', 'Bases de postes dañadas', '2025-05-24', '2025-06-08', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6270, -33.4240), 4326), false, 2, 3),
('Nivelación acceso', 'Acceso para discapacitados', '2025-05-25', '2025-06-09', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6280, -33.4250), 4326), false, 3, 3);

-- Tareas para Usachita (estudio)
INSERT INTO tarea (titulo, descripcion, fechacreacion, fechavencimiento, estado, ubicacion, eliminado, usuario_id, sector_id)
VALUES
('Reparación mobiliario', 'Arreglo de sillas en biblioteca', '2025-05-20', '2025-06-05', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6850, -33.4500), 4326), false, 2, 4),
('Mantención laboratorios', 'Revisión equipos computación', '2025-05-21', '2025-06-06', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6830, -33.4510), 4326), false, 3, 4),
('Pintura aulas', 'Renovación pintura salas de clase', '2025-05-22', '2025-06-07', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6820, -33.4490), 4326), false, 2, 4),

-- Tareas para Parque Ohiggins (recreacion)
('Podar árboles', 'Poda de mantención en áreas verdes', '2025-05-20', '2025-06-10', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6600, -33.4650), 4326), false, 3, 5),
('Reparar juegos infantiles', 'Arreglo de columpios dañados', '2025-05-21', '2025-06-11', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6620, -33.4630), 4326), false, 2, 5),
('Limpieza laguna', 'Retiro de algas y desechos', '2025-05-22', '2025-06-12', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6580, -33.4640), 4326), false, 3, 5),

-- Tareas para Aeropuerto (viaje)
('Mantención pista', 'Revisión superficie de aterrizaje', '2025-05-20', '2025-06-15', 'pendiente', ST_SetSRID(ST_MakePoint(-70.7900, -33.3900), 4326), false, 2, 6),
('Señalización taxiways', 'Repintado de señalética horizontal', '2025-05-21', '2025-06-16', 'pendiente', ST_SetSRID(ST_MakePoint(-70.7950, -33.3950), 4326), false, 3, 6),
('Limpieza terminal', 'Aseo profundo área de check-in', '2025-05-22', '2025-06-17', 'pendiente', ST_SetSRID(ST_MakePoint(-70.7850, -33.4000), 4326), false, 2, 6),

-- Tareas para Cementerio General (reparaciones)
('Reparación mausoleos', 'Arreglo de estructuras dañadas', '2025-05-20', '2025-06-18', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6480, -33.4100), 4326), false, 3, 7),
('Poda de árboles', 'Mantención de vegetación', '2025-05-21', '2025-06-19', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6500, -33.4120), 4326), false, 2, 7),
('Limpieza general', 'Retiro de hojas y desechos', '2025-05-22', '2025-06-20', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6450, -33.4150), 4326), false, 3, 7),

-- Tareas para Santiago Centro (mantencion)
('Reparación veredas', 'Arreglo de aceras dañadas', '2025-05-20', '2025-06-21', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6500, -33.4400), 4326), false, 2, 8),
('Mantención luminarias', 'Cambio de ampolletas quemadas', '2025-05-21', '2025-06-22', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6550, -33.4350), 4326), false, 3, 8),
('Limpieza calles', 'Barrido y retiro de basura', '2025-05-22', '2025-06-23', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6450, -33.4450), 4326), false, 2, 8),

-- Tareas para Estadio Monumental (futbol)
('Reparación graderías', 'Arreglo de asientos dañados', '2025-05-20', '2025-06-24', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6060, -33.5050), 4326), false, 3, 9),
('Mantención césped', 'Riego y fertilización cancha', '2025-05-21', '2025-06-25', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6070, -33.5040), 4326), false, 2, 9),
('Limpieza baños', 'Aseo profundo instalaciones', '2025-05-22', '2025-06-26', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6050, -33.5060), 4326), false, 3, 9),

-- Tareas para Estadio Santa Laura (futbol)
('Pintura tribunas', 'Renovación pintura sector norte', '2025-05-20', '2025-06-27', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6590, -33.4040), 4326), false, 2, 10),
('Reparación iluminación', 'Cambio de reflectores dañados', '2025-05-21', '2025-06-28', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6580, -33.4050), 4326), false, 3, 10),
('Mantención marcador', 'Reparación pantalla electrónica', '2025-05-22', '2025-06-29', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6600, -33.4045), 4326), false, 2, 10),

-- Tareas para Estadio Nacional (entretencion)
('Reparación pista atlética', 'Arreglo superficie de carrera', '2025-05-20', '2025-06-30', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6100, -33.4650), 4326), false, 3, 11),
('Mantención camerinos', 'Reparación duchas y lockers', '2025-05-21', '2025-07-01', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6120, -33.4660), 4326), false, 2, 11),
('Limpieza general', 'Aseo completo instalaciones', '2025-05-22', '2025-07-02', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6110, -33.4640), 4326), false, 3, 11),

-- Tareas para Palafitos Chiloe (paisaje)
('Reparación estructuras', 'Refuerzo pilotes palafitos', '2025-05-20', '2025-07-03', 'pendiente', ST_SetSRID(ST_MakePoint(-73.7650, -42.4700), 4326), false, 2, 12),
('Pintura fachadas', 'Renovación pintura exterior', '2025-05-21', '2025-07-04', 'pendiente', ST_SetSRID(ST_MakePoint(-73.7655, -42.4705), 4326), false, 3, 12),
('Mantención pasarelas', 'Reparación puentes peatonales', '2025-05-22', '2025-07-05', 'pendiente', ST_SetSRID(ST_MakePoint(-73.7660, -42.4710), 4326), false, 2, 12),

-- Tareas para Mall Costanera Center (entretencion)
('Limpieza vidrieras', 'Lavado de fachadas de cristal', '2025-05-20', '2025-07-06', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6075, -33.4170), 4326), false, 3, 13),
('Mantención ascensores', 'Revisión técnica mensual', '2025-05-21', '2025-07-07', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6080, -33.4175), 4326), false, 2, 13),
('Reparación pisos', 'Arreglo de losetas dañadas', '2025-05-22', '2025-07-08', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6065, -33.4180), 4326), false, 3, 13),

-- Tareas para Mall Parque Arauco (entretencion)
('Podar áreas verdes', 'Mantención de jardines interiores', '2025-05-20', '2025-07-09', 'pendiente', ST_SetSRID(ST_MakePoint(-70.5800, -33.4020), 4326), false, 2, 14),
('Reparación estacionamiento', 'Arreglo de badenes dañados', '2025-05-21', '2025-07-10', 'pendiente', ST_SetSRID(ST_MakePoint(-70.5790, -33.4030), 4326), false, 3, 14),
('Limpieza fuentes', 'Aseo y mantención de fuentes', '2025-05-22', '2025-07-11', 'pendiente', ST_SetSRID(ST_MakePoint(-70.5810, -33.4010), 4326), false, 2, 14),

-- Tareas para Escuela de Ingenieria Beaucheff UCH (estudio)
('Mantención laboratorios', 'Calibración equipos electrónicos', '2025-05-20', '2025-07-12', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6620, -33.4570), 4326), false, 3, 15),
('Reparación mobiliario', 'Arreglo de mesas y sillas', '2025-05-21', '2025-07-13', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6630, -33.4580), 4326), false, 2, 15),
('Pintura aulas', 'Renovación pintura salas', '2025-05-22', '2025-07-14', 'pendiente', ST_SetSRID(ST_MakePoint(-70.6610, -33.4575), 4326), false, 3, 15),

-- Tareas para Faro La Serena (reparaciones)
('Pintura estructura', 'Renovación pintura exterior', '2025-05-20', '2025-07-15', 'pendiente', ST_SetSRID(ST_MakePoint(-71.2740, -29.9055), 4326), false, 2, 16),
('Reparación escalera', 'Arreglo de escalones dañados', '2025-05-21', '2025-07-16', 'pendiente', ST_SetSRID(ST_MakePoint(-71.2742, -29.9056), 4326), false, 3, 16),
('Mantención lente', 'Limpieza y calibración óptica', '2025-05-22', '2025-07-17', 'pendiente', ST_SetSRID(ST_MakePoint(-71.2741, -29.9057), 4326), false, 2, 16),

-- Tareas para Memorial 11 Septiembre (reparaciones)
('Limpieza placas', 'Pulido de placas conmemorativas', '2025-05-20', '2025-07-18', 'pendiente', ST_SetSRID(ST_MakePoint(-74.0130, 40.7120), 4326), false, 3, 17),
('Reparación fuentes', 'Arreglo sistema de agua', '2025-05-21', '2025-07-19', 'pendiente', ST_SetSRID(ST_MakePoint(-74.0135, 40.7125), 4326), false, 2, 17),
('Mantención iluminación', 'Cambio de focos quemados', '2025-05-22', '2025-07-20', 'pendiente', ST_SetSRID(ST_MakePoint(-74.0140, 40.7115), 4326), false, 3, 17),

-- Tareas para Torre Eiffel (reparaciones)
('Pintura estructura', 'Renovación pintura metalica', '2025-05-20', '2025-07-21', 'pendiente', ST_SetSRID(ST_MakePoint(2.2940, 48.8580), 4326), false, 2, 18),
('Mantención ascensores', 'Revisión técnica mensual', '2025-05-21', '2025-07-22', 'pendiente', ST_SetSRID(ST_MakePoint(2.2950, 48.8590), 4326), false, 3, 18),
('Limpieza miradores', 'Aseo de plataformas de observación', '2025-05-22', '2025-07-23', 'pendiente', ST_SetSRID(ST_MakePoint(2.2960, 48.8570), 4326), false, 2, 18);