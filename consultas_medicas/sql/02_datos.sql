-- Paso 3: datos de prueba (ejecutar después de 01_crear_tablas.sql).
INSERT INTO medico (nombre, especialidad) VALUES
('Juan Polanco', 'Medicina general'),
('Sofía Ramos', 'Pediatría'),
('Ana Mayor', 'Cardiología');

-- Todos los usuarios tienen la contraseña 1234
INSERT INTO usuario (username, password, nombre, rol, medico_id) VALUES
('admin',    '1234', 'Administrador', 'ADMINISTRADOR', NULL),
('jpolanco', '1234', 'Juan Polanco',  'MEDICO',        1),
('sramos',   '1234', 'Sofía Ramos',   'MEDICO',        2),
('amayor',   '1234', 'Ana Mayor',     'MEDICO',        3),
('pablo',    '1234', 'Pablo Torres',  'PACIENTE',      NULL),
('samuel',   '1234', 'Samuel Méndez', 'PACIENTE',      NULL);

INSERT INTO consulta (nombre_paciente, motivo, numero_consultorio, hora_inicio, hora_fin, medico_id) VALUES
('Pablo Torres',  'Dolor de cabeza frecuente',     101, '08:00', '08:30', 1),
('Samuel Méndez', 'Control de crecimiento',        102, '09:00', '09:30', 2),
('Pablo Torres',  'Revisión de presión arterial',  201, '10:00', '10:45', 3),
('Samuel Méndez', 'Fiebre y malestar general',     101, '11:00', '11:20', 1);
