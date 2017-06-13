-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2+deb7u8
-- http://www.phpmyadmin.net
--
-- Servidor: mississippi.ic-itcr.ac.cr:3306
-- Tiempo de generación: 13-06-2017 a las 19:50:18
-- Versión del servidor: 5.5.50
-- Versión de PHP: 5.6.30-1~dotdeb+7.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `alecastillo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Sensors`
--

CREATE TABLE IF NOT EXISTS `Sensors` (
  `SensorID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  PRIMARY KEY (`SensorID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Volcado de datos para la tabla `Sensors`
--

INSERT INTO `Sensors` (`SensorID`, `Name`) VALUES
(8, 'Sensor_Calor'),
(9, 'Sensor_Proximidad'),
(11, 'Sensor_Temperatura');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Valores`
--

CREATE TABLE IF NOT EXISTS `Valores` (
  `ValueID` int(11) NOT NULL AUTO_INCREMENT,
  `Value` double NOT NULL,
  `Date_Time` datetime NOT NULL,
  PRIMARY KEY (`ValueID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=34 ;

--
-- Volcado de datos para la tabla `Valores`
--

INSERT INTO `Valores` (`ValueID`, `Value`, `Date_Time`) VALUES
(21, 11.455667, '2017-06-04 13:23:18'),
(22, 13.9988, '2017-06-05 17:18:17'),
(23, 14.9988, '2017-07-05 01:18:17'),
(24, 0.9988, '2017-07-05 01:18:17'),
(25, 1.088, '2017-07-05 01:18:17'),
(26, 1.188, '2017-07-05 01:18:18'),
(28, 0.67, '2017-06-10 09:37:24'),
(29, 0.35, '2017-06-10 09:46:52'),
(30, 0, '2017-06-10 03:54:36'),
(31, 0.31, '2017-06-10 03:54:48'),
(32, 1.4, '2017-06-10 03:55:11'),
(33, 27, '2017-06-10 04:36:33');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ValuesXSensor`
--

CREATE TABLE IF NOT EXISTS `ValuesXSensor` (
  `SensorID` int(11) NOT NULL,
  `ValueID` int(11) NOT NULL,
  KEY `fk_SensorID1_idx` (`SensorID`),
  KEY `fk_ValueID1_idx` (`ValueID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ValuesXSensor`
--

INSERT INTO `ValuesXSensor` (`SensorID`, `ValueID`) VALUES
(8, 21),
(8, 22),
(8, 23),
(9, 24),
(9, 25),
(9, 26),
(8, 28),
(9, 29),
(8, 31),
(9, 32),
(11, 33);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ValuesXSensor`
--
ALTER TABLE `ValuesXSensor`
  ADD CONSTRAINT `ValuesXSensor_ibfk_2` FOREIGN KEY (`ValueID`) REFERENCES `Valores` (`ValueID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ValuesXSensor_ibfk_1` FOREIGN KEY (`SensorID`) REFERENCES `Sensors` (`SensorID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
