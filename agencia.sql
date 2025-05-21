-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-05-2025 a las 12:56:19
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `agencia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `agente`
--

CREATE TABLE `agente` (
  `ID_Usuario` int(11) NOT NULL,
  `Codigo_Empleado` varchar(20) NOT NULL,
  `Oficina` varchar(100) NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `agente`
--

INSERT INTO `agente` (`ID_Usuario`, `Codigo_Empleado`, `Oficina`, `activo`) VALUES
(2, '0002', 'la rambla', 0),
(3, '0003', 'montalban', 1),
(4, '0004', 'Santaella', 1),
(6, '0015', 'Logroño', 1),
(18, '0020', 'Burgos', 1),
(19, '0009', 'Lucena', 1),
(20, '0010', 'Sevilla', 1),
(21, '0012', 'Caceres', 0),
(24, '0016', 'Lugo', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `ID_Usuario` int(11) NOT NULL,
  `DNI` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`ID_Usuario`, `DNI`) VALUES
(5, '12345678P'),
(7, '78857645R'),
(8, '78857075R'),
(9, '12346789O'),
(10, '12376548H'),
(11, '31415041U'),
(22, '60048751K'),
(25, '78950231P');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
  `ID_Reserva` int(11) NOT NULL,
  `ID_Cliente` int(11) NOT NULL,
  `ID_Viaje` int(11) NOT NULL,
  `ID_Agente` int(11) NOT NULL,
  `Estado` varchar(20) NOT NULL,
  `Fecha_salida` date DEFAULT NULL,
  `Fecha_regreso` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reservas`
--

INSERT INTO `reservas` (`ID_Reserva`, `ID_Cliente`, `ID_Viaje`, `ID_Agente`, `Estado`, `Fecha_salida`, `Fecha_regreso`) VALUES
(2, 10, 1, 20, 'Aceptada', '2025-05-16', '2025-05-24'),
(4, 8, 3, 4, 'Pendiente', '2025-05-17', '2025-05-20');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `ID_Usuario` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Contraseña` varchar(100) NOT NULL,
  `Fecha_Registro` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`ID_Usuario`, `Nombre`, `Email`, `Contraseña`, `Fecha_Registro`) VALUES
(2, 'David', 'david@gmail.com', '1234', '2025-05-12'),
(3, 'Juan', 'juan@gmail.com', '1234', '2025-05-12'),
(4, 'Antonio', 'antonio@gmail.com', 'usuarip', '2025-05-13'),
(5, 'Jesus', 'Jesus@gmail.com', 'usuariop', '2025-05-13'),
(6, 'Juan Rafael', 'jaunrafa@gmail.com', 'juanracule76', '2025-05-13'),
(7, 'Pedro', 'pedro@gmail.com', 'UsUaRiO-1', '2025-05-13'),
(8, 'Ana', 'ana3@gmail.com', 'usuario1234', '2025-05-13'),
(9, 'Gabriel', 'gabri@gmail.com', 'esteban12', '2025-05-14'),
(10, 'Jacobo', 'jacob30@gmail.com', 'jacob93', '2025-05-14'),
(11, 'Carlos', 'carlos21@gmail.com', 'carl12', '2025-05-14'),
(18, 'David', 'david32@gmail.com', 'davicicito11', '2025-05-14'),
(19, 'Lucas', 'lucasvz@gmail.com', 'realmadrid', '2025-05-14'),
(20, 'Raul', 'raul12@gmail.com', 'raul01', '2025-05-14'),
(21, 'Federico', 'fede1998@gmail.com', 'fedelegusta456', '2025-05-14'),
(22, 'Antonio', 'antonio45@gmail.com', 'guiti02', '2025-05-15'),
(23, 'Amelia Gonzalez', 'amegonza@gmail.com', '123456Y', '2025-05-21'),
(24, 'Amelia Fernandez', 'ameferna@gmail.com', '123456Y', '2025-05-21'),
(25, 'Omar', 'omarcz@gmail.com', '78950231P', '2025-05-21');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `viajes`
--

CREATE TABLE `viajes` (
  `ID_Viaje` int(11) NOT NULL,
  `Destino` varchar(100) NOT NULL,
  `Fecha_salida` date NOT NULL,
  `Fecha_regreso` date NOT NULL,
  `Precio` decimal(10,2) NOT NULL,
  `Plazas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `viajes`
--

INSERT INTO `viajes` (`ID_Viaje`, `Destino`, `Fecha_salida`, `Fecha_regreso`, `Precio`, `Plazas`) VALUES
(1, 'Cancun', '2025-05-15', '2025-05-20', 990.00, 2),
(2, 'Paris', '2025-05-16', '2025-05-23', 880.00, 4),
(3, 'Barcelona', '2025-05-16', '2025-05-17', 100.00, 2),
(5, 'Sevilla', '2025-05-24', '2025-05-31', 40.00, 2),
(6, 'Fuengirola', '2025-05-24', '2025-05-25', 30.00, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `agente`
--
ALTER TABLE `agente`
  ADD PRIMARY KEY (`ID_Usuario`),
  ADD UNIQUE KEY `Codigo_Empleado` (`Codigo_Empleado`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`ID_Usuario`);

--
-- Indices de la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`ID_Reserva`),
  ADD KEY `fk_clientes_usuario` (`ID_Cliente`),
  ADD KEY `fkViaje` (`ID_Viaje`),
  ADD KEY `fkAgente` (`ID_Agente`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`ID_Usuario`);

--
-- Indices de la tabla `viajes`
--
ALTER TABLE `viajes`
  ADD PRIMARY KEY (`ID_Viaje`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `reservas`
--
ALTER TABLE `reservas`
  MODIFY `ID_Reserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `ID_Usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `viajes`
--
ALTER TABLE `viajes`
  MODIFY `ID_Viaje` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `agente`
--
ALTER TABLE `agente`
  ADD CONSTRAINT `fk_agente_usuario` FOREIGN KEY (`ID_Usuario`) REFERENCES `usuario` (`ID_Usuario`) ON DELETE CASCADE;

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `fk_cliente_usuario` FOREIGN KEY (`ID_Usuario`) REFERENCES `usuario` (`ID_Usuario`) ON DELETE CASCADE;

--
-- Filtros para la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `fkAgente` FOREIGN KEY (`ID_Agente`) REFERENCES `agente` (`ID_Usuario`) ON DELETE CASCADE,
  ADD CONSTRAINT `fkViaje` FOREIGN KEY (`ID_Viaje`) REFERENCES `viajes` (`ID_Viaje`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_clientes_usuario` FOREIGN KEY (`ID_Cliente`) REFERENCES `clientes` (`ID_Usuario`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
