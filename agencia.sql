-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-06-2025 a las 12:31:03
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
  `Nombre` varchar(100) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Codigo_Empleado` varchar(20) NOT NULL,
  `Contraseña` varchar(100) NOT NULL,
  `Fecha_registro` date NOT NULL DEFAULT '2024-01-01',
  `Oficina` varchar(100) NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `agente`
--

INSERT INTO `agente` (`Nombre`, `Email`, `Codigo_Empleado`, `Contraseña`, `Fecha_registro`, `Oficina`, `activo`) VALUES
('Antonio', 'antonio@gmail.com', '0001', 'anto12', '2025-06-04', 'Córdoba', 1),
('Sergio', 'sergio@gmail.com', '0002', 'sergiroca4', '2025-06-04', 'Caceres', 0),
('Jesús', 'jesusito@gmail.com', '0003', 'memesito12', '2025-06-04', 'Logroño', 1),
('Joaquín', 'joaqui@gmail.com', '0004', '1234joaqui', '2025-06-05', 'Malaga', 1),
('Mari Carmen', 'maricarmen@gmail.com', '0006', '1234maricarmen', '2025-06-05', 'Fernan Nuñez', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `DNI` varchar(20) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Contraseña` varchar(100) NOT NULL,
  `Fecha_registro` date NOT NULL DEFAULT '2024-01-01',
  `Vip` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`DNI`, `Nombre`, `Email`, `Contraseña`, `Fecha_registro`, `Vip`) VALUES
('12345678P', 'Alfredo', 'alfiler@gmail.com', 'alfi1234.', '2025-06-04', 1),
('78840356T', 'Jesus', 'memesito45@gmail.com', 'elmeme12', '2025-06-04', 1),
('98865012N', 'Alfonso', 'alfonsosoler45@gmail.com', '1234alfiler', '2025-06-05', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
  `ID_Reserva` int(11) NOT NULL,
  `DNI` varchar(20) NOT NULL,
  `ID_Viaje` int(11) NOT NULL,
  `Codigo_Empleado` varchar(20) NOT NULL,
  `Estado` varchar(20) NOT NULL,
  `Fecha_salida` date DEFAULT NULL,
  `Fecha_regreso` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reservas`
--

INSERT INTO `reservas` (`ID_Reserva`, `DNI`, `ID_Viaje`, `Codigo_Empleado`, `Estado`, `Fecha_salida`, `Fecha_regreso`) VALUES
(6, '98865012N', 8, '0004', 'Aceptada', '2025-06-06', '2025-06-10');

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
(6, 'Fuengirola', '2025-06-13', '2025-06-15', 30.00, 2),
(8, 'Nueva York', '2025-05-31', '2025-06-07', 1000.00, 3);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `agente`
--
ALTER TABLE `agente`
  ADD PRIMARY KEY (`Codigo_Empleado`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`DNI`);

--
-- Indices de la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`ID_Reserva`),
  ADD KEY `fkViaje` (`ID_Viaje`),
  ADD KEY `CPclientes` (`DNI`),
  ADD KEY `fkAgente` (`Codigo_Empleado`);

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
  MODIFY `ID_Reserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `viajes`
--
ALTER TABLE `viajes`
  MODIFY `ID_Viaje` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `CPclientes` FOREIGN KEY (`DNI`) REFERENCES `clientes` (`DNI`) ON DELETE CASCADE,
  ADD CONSTRAINT `fkAgente` FOREIGN KEY (`Codigo_Empleado`) REFERENCES `agente` (`Codigo_Empleado`) ON DELETE CASCADE,
  ADD CONSTRAINT `fkViaje` FOREIGN KEY (`ID_Viaje`) REFERENCES `viajes` (`ID_Viaje`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
