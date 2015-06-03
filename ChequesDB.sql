CREATE DATABASE  IF NOT EXISTS `chequesmg` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ChequesMG`;
-- MySQL dump 10.13  Distrib 5.5.24, for osx10.5 (i386)
--
-- Host: localhost    Database: ChequesMG
-- ------------------------------------------------------
-- Server version	5.5.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Actualizacion`
--

DROP TABLE IF EXISTS `Actualizacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Actualizacion` (
  `fecha` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`fecha`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Actualizacion`
--

LOCK TABLES `Actualizacion` WRITE;
/*!40000 ALTER TABLE `Actualizacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `Actualizacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cheques`
--

DROP TABLE IF EXISTS `Cheques`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cheques` (
  `Numero` varchar(45) NOT NULL,
  `Importe` varchar(45) DEFAULT NULL,
  `Chequera` varchar(45) DEFAULT NULL,
  `Factura` varchar(45) DEFAULT NULL,
  `Beneficiario` varchar(45) DEFAULT NULL,
  `FechaEmision` date DEFAULT NULL,
  `FechaPago` date DEFAULT NULL,
  `Pagado` int(11) DEFAULT '0',
  `FechaOriginal` date DEFAULT NULL,
  PRIMARY KEY (`Numero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cheques`
--

LOCK TABLES `Cheques` WRITE;
/*!40000 ALTER TABLE `Cheques` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cheques` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Feriados`
--

DROP TABLE IF EXISTS `Feriados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Feriados` (
  `Feriado` date NOT NULL,
  PRIMARY KEY (`Feriado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Feriados`
--

LOCK TABLES `Feriados` WRITE;
/*!40000 ALTER TABLE `Feriados` DISABLE KEYS */;
/*!40000 ALTER TABLE `Feriados` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-10 19:04:31
