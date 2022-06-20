-- MariaDB dump 10.19  Distrib 10.8.3-MariaDB, for Win64 (AMD64)
--
-- Host: 127.0.0.1    Database: comic_store_api_test
-- ------------------------------------------------------
-- Server version	10.7.4-MariaDB-1:10.7.4+maria~focal

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `collection`
--

DROP TABLE IF EXISTS `collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_collection_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection`
--

LOCK TABLES `collection` WRITE;
/*!40000 ALTER TABLE `collection` DISABLE KEYS */;
INSERT INTO `collection` (`id`, `description`, `name`, `publisher`) VALUES (1,'The DC Universe','DC 1','DC Comics'),
(2,'The Marvel Universe','Marvel 1','Marvel Comics');
/*!40000 ALTER TABLE `collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comic`
--

DROP TABLE IF EXISTS `comic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(512) DEFAULT NULL,
  `issue_number` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `collection_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_comic_collection_issue_number` (`collection_id`,`issue_number`),
  CONSTRAINT `fk_comic_collection` FOREIGN KEY (`collection_id`) REFERENCES `collection` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comic`
--

LOCK TABLES `comic` WRITE;
/*!40000 ALTER TABLE `comic` DISABLE KEYS */;
INSERT INTO `comic` (`id`, `description`, `issue_number`, `title`, `collection_id`) VALUES (1,'The Amazing Spider',1,'The Amazing Spider',2);
/*!40000 ALTER TABLE `comic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comic_copy`
--

DROP TABLE IF EXISTS `comic_copy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comic_copy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cover` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `comic_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_comic_copy_comic` (`comic_id`),
  CONSTRAINT `fk_comic_copy_comic` FOREIGN KEY (`comic_id`) REFERENCES `comic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comic_copy`
--

LOCK TABLES `comic_copy` WRITE;
/*!40000 ALTER TABLE `comic_copy` DISABLE KEYS */;
INSERT INTO `comic_copy` (`id`, `cover`, `price`, `purchase_date`, `state`, `comic_id`) VALUES (1,'Blanda',850.00,'2022-06-20','Nuevo',1);
/*!40000 ALTER TABLE `comic_copy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comic_creator`
--

DROP TABLE IF EXISTS `comic_creator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comic_creator` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `comic_id` bigint(20) DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_comic_creator_role_creator_comic` (`role`,`creator_id`,`comic_id`),
  KEY `fk_comic_creator_comic_id` (`comic_id`),
  KEY `fk_comic_creator_creator_id` (`creator_id`),
  CONSTRAINT `fk_comic_creator_comic_id` FOREIGN KEY (`comic_id`) REFERENCES `comic` (`id`),
  CONSTRAINT `fk_comic_creator_creator_id` FOREIGN KEY (`creator_id`) REFERENCES `creator` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comic_creator`
--

LOCK TABLES `comic_creator` WRITE;
/*!40000 ALTER TABLE `comic_creator` DISABLE KEYS */;
INSERT INTO `comic_creator` (`id`, `role`, `comic_id`, `creator_id`) VALUES (2,'dibujante',1,2),
(1,'Escritor',1,1);
/*!40000 ALTER TABLE `comic_creator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creator`
--

DROP TABLE IF EXISTS `creator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creator` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creator`
--

LOCK TABLES `creator` WRITE;
/*!40000 ALTER TABLE `creator` DISABLE KEYS */;
INSERT INTO `creator` (`id`, `last_name`, `name`) VALUES (1,'Lee','Lee'),
(2,'Kirby','Jack');
/*!40000 ALTER TABLE `creator` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-20  6:52:59
