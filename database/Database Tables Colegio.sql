CREATE DATABASE `colegio`;
USE colegio;

CREATE TABLE `alumnos` (
  `id_alumno` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `direccion` varchar(250) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `curso_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id_alumno`),
  KEY `fk_alumno_curso` (`curso_id`),
  KEY `fk_alumno_usuario` (`usuario_id`),
  CONSTRAINT `fk_alumno_curso` FOREIGN KEY (`curso_id`) REFERENCES `cursos` (`id_curso`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_alumno_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB ;

CREATE TABLE `asistencias` (
  `id_asistencia` int NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `alumno_id` int NOT NULL,
  `estado_id` int NOT NULL,
  PRIMARY KEY (`id_asistencia`),
  KEY `fk_asistencia_alumno` (`alumno_id`),
  KEY `fk_estado_asistencia` (`estado_id`),
  CONSTRAINT `fk_asistencia_alumno` FOREIGN KEY (`alumno_id`) REFERENCES `alumnos` (`id_alumno`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_estado_asistencia` FOREIGN KEY (`estado_id`) REFERENCES `estadoAsistencia` (`id_estado_asistencia`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB ;

CREATE TABLE `cursos` (
  `id_curso` int NOT NULL AUTO_INCREMENT,
  `nivel` int NOT NULL,
  `paralelo` varchar(1) NOT NULL,
  `docente_id` int NOT NULL,
  PRIMARY KEY (`id_curso`),
  KEY `fk_curso_docente` (`docente_id`),
  CONSTRAINT `fk_curso_docente` FOREIGN KEY (`docente_id`) REFERENCES `docentes` (`id_docente`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE `docentes` (
  `id_docente` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `direccion` varchar(250) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id_docente`),
  KEY `fk_docente_usuario` (`usuario_id`),
  CONSTRAINT `fk_docente_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE `estadoAsistencia` (
  `id_estado_asistencia` int NOT NULL AUTO_INCREMENT,
  `estado` varchar(45) NOT NULL,
  PRIMARY KEY (`id_estado_asistencia`)
) ENGINE=InnoDB;

CREATE TABLE `tipos` (
  `id_tipo` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(45) NOT NULL,
  PRIMARY KEY (`id_tipo`)
) ENGINE=InnoDB;

CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `user` varchar(45) NOT NULL,
  `password` varchar(250) NOT NULL,
  `tipo_id` int NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UQ_user` (`user`),
  KEY `fk_tipo` (`tipo_id`),
  CONSTRAINT `fk_tipo` FOREIGN KEY (`tipo_id`) REFERENCES `tipos` (`id_tipo`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB ;
