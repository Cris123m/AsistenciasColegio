--
-- Base de datos: `colegio`
--

--
-- Volcado de datos para la tabla `tipos`
--

INSERT INTO `tipos` (`id_tipo`, `tipo`) VALUES
(1, 'Profesor'),
(2, 'Alumno');

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `user`, `password`, `tipo_id`) VALUES
(1, 'Henry', 'henry', 1),
(2, 'Luis', 'luis', 2),
(4, 'juan', 'juan', 1),
(5, 'felipe', 'felipe', 2),
(6, 'maria', 'maria', 1),
(7, 'dalma', 'dalma', 1),
(8, 'peter', 'peter', 1),
(15, 'juanp', 'juanp', 1),
(16, 'erickm', 'erickm', 2),
(18, 'santi', 'santi', 2),
(19, 'karina', 'karina', 2);

--
-- Volcado de datos para la tabla `docentes`
--

INSERT INTO `docentes` (`id_docente`, `nombre`, `apellido`, `direccion`, `telefono`, `usuario_id`) VALUES
(1, 'Dalma', 'Castro', 'La Roldos', '3381058', 7),
(2, 'Peter', 'Parker', 'New York', '0956345454', 8),
(7, 'Juan', 'Perez', 'Cotocollao', '0874357346', 15);

--
-- Volcado de datos para la tabla `cursos`
--

INSERT INTO `cursos` (`id_curso`, `nivel`, `paralelo`, `docente_id`) VALUES
(1, 1, 'A', 7),
(2, 2, 'A', 1);

--
-- Volcado de datos para la tabla `alumnos`
--

INSERT INTO `alumnos` (`id_alumno`, `nombre`, `apellido`, `direccion`, `telefono`, `curso_id`, `usuario_id`) VALUES
(1, 'Erick', 'Matinez', 'Av. Colón', '0967886656', 2, 16),
(3, 'Santiago', 'Silva', 'Las Casas', '0962482342', 1, 18),
(4, 'Karina', 'Velez', 'Condado', '0964688345', 1, 19);

--
-- Volcado de datos para la tabla `estadoAsistencia`
--

INSERT INTO `estadoAsistencia` (`id_estado_asistencia`, `estado`) VALUES
(1, 'Presente'),
(2, 'Falta'),
(3, 'Fuga');

--
-- Volcado de datos para la tabla `asistencias`
--

INSERT INTO `asistencias` (`id_asistencia`, `fecha`, `alumno_id`, `estado_id`) VALUES
(1, '2021-01-11', 3, 1),
(2, '2021-01-11', 4, 3),
(5, '2021-01-11', 1, 2),
(6, '2021-01-12', 3, 1),
(7, '2021-01-12', 4, 1);
