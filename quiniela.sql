start transaction;

CREATE DATABASE  IF NOT EXISTS `Quiniela` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `Quiniela`;


create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
		on `Quiniela`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
	        create temporary tables, lock tables, create view, create routine,
	        alter routine, execute, trigger, show view
	    on `Quiniela`.* to 'acme-manager'@'%';


-- MySQL dump 10.13  Distrib 5.5.41, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: Quiniela
-- ------------------------------------------------------
-- Server version	5.5.41-0ubuntu0.14.04.1

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
-- Table structure for table `Actor`
--

DROP TABLE IF EXISTS `Actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Actor` (
  `emailAddress` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_b88pmjby6mc0hms0vb18apeyk` FOREIGN KEY (`id`) REFERENCES `DomainEntity` (`id`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Actor`
--

LOCK TABLES `Actor` WRITE;
/*!40000 ALTER TABLE `Actor` DISABLE KEYS */;
INSERT INTO `Actor` VALUES ('luidia@gmail.com','Luis','Diaz',2,1);
/*!40000 ALTER TABLE `Actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Administrator`
--

DROP TABLE IF EXISTS `Administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Administrator` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_jd0370oy76ntbbd6qi0ofdqjs` FOREIGN KEY (`id`) REFERENCES `Actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Administrator`
--

LOCK TABLES `Administrator` WRITE;
/*!40000 ALTER TABLE `Administrator` DISABLE KEYS */;
INSERT INTO `Administrator` VALUES (2);
/*!40000 ALTER TABLE `Administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DomainEntity`
--

DROP TABLE IF EXISTS `DomainEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DomainEntity` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DomainEntity`
--

LOCK TABLES `DomainEntity` WRITE;
/*!40000 ALTER TABLE `DomainEntity` DISABLE KEYS */;
INSERT INTO `DomainEntity` VALUES (1,0),(2,0);
/*!40000 ALTER TABLE `DomainEntity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Partido`
--

DROP TABLE IF EXISTS `Partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Partido` (
  `equipo1` varchar(255) DEFAULT NULL,
  `equipo2` varchar(255) DEFAULT NULL,
  `resultado` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `quiniela_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9gbcykpmiy7lxf7ggqqdotvtw` (`quiniela_id`),
  CONSTRAINT `FK_9ikdbb737wxmyp05074obxd36` FOREIGN KEY (`id`) REFERENCES `DomainEntity` (`id`),
  CONSTRAINT `FK_9gbcykpmiy7lxf7ggqqdotvtw` FOREIGN KEY (`quiniela_id`) REFERENCES `Quiniela` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Partido`
--

LOCK TABLES `Partido` WRITE;
/*!40000 ALTER TABLE `Partido` DISABLE KEYS */;
/*!40000 ALTER TABLE `Partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Quiniela`
--

DROP TABLE IF EXISTS `Quiniela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Quiniela` (
  `fechaLimite` date DEFAULT NULL,
  `jornada` varchar(255) DEFAULT NULL,
  `numAciertos` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `administrator_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_j2gcl4rxyj1ir5smmg99lh4an` (`administrator_id`),
  KEY `FK_nahsouyfks1r8xklutiqc4hkb` (`user_id`),
  CONSTRAINT `FK_6qu1uqexj2cwohm81gmi65dlp` FOREIGN KEY (`id`) REFERENCES `DomainEntity` (`id`),
  CONSTRAINT `FK_j2gcl4rxyj1ir5smmg99lh4an` FOREIGN KEY (`administrator_id`) REFERENCES `Administrator` (`id`),
  CONSTRAINT `FK_nahsouyfks1r8xklutiqc4hkb` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Quiniela`
--

LOCK TABLES `Quiniela` WRITE;
/*!40000 ALTER TABLE `Quiniela` DISABLE KEYS */;
/*!40000 ALTER TABLE `Quiniela` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `telefono` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_omjl8ijwi58yx57d9ejn4c0jt` FOREIGN KEY (`id`) REFERENCES `Actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserAccount`
--

DROP TABLE IF EXISTS `UserAccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserAccount` (
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`),
  CONSTRAINT `FK_ld5o32pooojx5q0qcunqqfs2u` FOREIGN KEY (`id`) REFERENCES `DomainEntity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserAccount`
--

LOCK TABLES `UserAccount` WRITE;
/*!40000 ALTER TABLE `UserAccount` DISABLE KEYS */;
INSERT INTO `UserAccount` VALUES ('25151b2a5f3d138eb8c6b1408c52b4f2','foriinformatica',1);
/*!40000 ALTER TABLE `UserAccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserAccount_authorities`
--

DROP TABLE IF EXISTS `UserAccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserAccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserAccount_authorities`
--

LOCK TABLES `UserAccount_authorities` WRITE;
/*!40000 ALTER TABLE `UserAccount_authorities` DISABLE KEYS */;
INSERT INTO `UserAccount_authorities` VALUES (1,'ADMINISTRATOR');
/*!40000 ALTER TABLE `UserAccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-09 18:37:34

commit;
