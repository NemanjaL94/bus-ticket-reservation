-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: bus_ticket_reservation
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `bus_company`
--

DROP TABLE IF EXISTS `bus_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bus_company` (
  `bus_company_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `bus_company_name` varchar(64) NOT NULL,
  PRIMARY KEY (`bus_company_id`),
  UNIQUE KEY `uq_bus_company_bus_company_name` (`bus_company_name`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bus_company`
--

LOCK TABLES `bus_company` WRITE;
/*!40000 ALTER TABLE `bus_company` DISABLE KEYS */;
INSERT INTO `bus_company` VALUES (7,'Banbus'),(6,'Dampex'),(5,'Delix Tours'),(3,'Joe Travel'),(4,'Kavim Jedinstvo'),(1,'Kolasin Prevoz'),(19,'Kosmet prevoz'),(12,'Lasta'),(9,'Lotus Travel'),(2,'Maxi Prevoz'),(27,'Nis Express'),(28,'PUB Internacional');
/*!40000 ALTER TABLE `bus_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservation` (
  `reservation_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `route_id` int(10) unsigned NOT NULL,
  `number_of_tickets` int(10) unsigned NOT NULL,
  `price` double unsigned NOT NULL,
  PRIMARY KEY (`reservation_id`),
  KEY `fk_reservation_user_id_idx` (`user_id`),
  KEY `fk_reservation_route_id_idx` (`route_id`),
  CONSTRAINT `fk_reservation_route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_reservation_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (25,42,4,14,7000),(27,47,65,35,31500),(28,49,1,3,2700);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `role_name` varchar(64) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `fk_role_user_id_idx` (`user_id`),
  CONSTRAINT `fk_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (37,42,'USER'),(38,43,'ADMIN'),(39,45,'ADMIN'),(41,47,'USER'),(43,48,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `route_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `bus_company_id` int(10) unsigned NOT NULL,
  `available_tickets` int(10) unsigned NOT NULL,
  PRIMARY KEY (`route_id`),
  KEY `fk_route_bus_company_id_idx` (`bus_company_id`),
  CONSTRAINT `fk_route_bus_company_id` FOREIGN KEY (`bus_company_id`) REFERENCES `bus_company` (`bus_company_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (1,1,7),(2,1,21),(3,1,48),(4,2,61),(5,5,99),(6,5,999),(7,1,17),(8,2,13),(9,4,18),(10,4,16),(11,1,11),(12,1,0),(13,1,1),(14,1,54),(15,1,11),(16,1,11),(17,4,7),(18,2,15),(19,2,666),(20,1,565),(21,2,77),(22,1,7171),(23,4,11),(24,4,0),(25,5,99),(26,5,111),(27,1,78),(28,2,7),(29,4,99),(30,1,66),(31,4,666),(32,1,45151),(33,1,78),(34,2,5151),(35,4,45),(36,2,15),(37,2,67),(38,6,50),(39,6,50),(40,6,11),(41,1,88),(42,4,99),(43,4,999),(44,7,55),(45,9,89),(46,9,90),(47,9,77),(48,7,21),(49,3,55),(50,7,55),(51,4,898),(52,9,8),(53,9,9),(54,7,22),(55,9,22),(56,9,88),(57,7,244),(58,1,2225111),(59,9,565),(60,9,788),(61,6,120),(62,4,50),(63,27,80),(64,1,77),(65,28,0),(66,2,50);
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_stop`
--

DROP TABLE IF EXISTS `route_stop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route_stop` (
  `route_stop_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `route_id` int(10) unsigned NOT NULL,
  `stop_id` int(10) unsigned NOT NULL,
  `arrival_time` time NOT NULL,
  `arrival_date` date NOT NULL,
  `fare` double NOT NULL,
  PRIMARY KEY (`route_stop_id`),
  UNIQUE KEY `uq_route_stop` (`route_id`,`stop_id`),
  KEY `fk_route_stop_route_id_idx` (`route_id`),
  KEY `fk_route_stop_stop_id_idx` (`stop_id`),
  CONSTRAINT `fk_route_stop_route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_route_stop_stop_id` FOREIGN KEY (`stop_id`) REFERENCES `stop` (`stop_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_stop`
--

LOCK TABLES `route_stop` WRITE;
/*!40000 ALTER TABLE `route_stop` DISABLE KEYS */;
INSERT INTO `route_stop` VALUES (1,1,1,'16:00:00','2017-06-17',0),(2,1,2,'17:00:00','2017-06-17',500),(3,1,3,'18:00:00','2017-06-17',650),(4,1,4,'19:00:00','2017-06-17',900),(5,2,7,'03:00:00','2017-06-18',0),(6,2,6,'04:00:00','2017-06-18',300),(7,2,5,'05:00:00','2017-06-18',470),(8,2,4,'19:00:00','2017-06-19',333),(12,46,1,'05:00:00','2017-08-10',0),(13,46,9,'05:00:00','2017-08-10',250),(14,46,7,'05:00:00','2017-08-10',350),(15,46,3,'05:00:00','2017-08-10',450),(16,46,4,'05:00:00','2017-08-10',550),(19,60,3,'03:03:00','2017-08-11',888),(21,61,4,'04:04:00','2017-08-16',0),(22,61,2,'19:00:00','2017-08-16',800),(23,62,1,'05:00:00','2017-08-16',0),(28,65,1,'06:00:00','2017-08-17',0),(29,65,3,'08:30:00','2017-08-17',700),(30,65,4,'09:30:00','2017-08-17',900),(31,65,5,'11:17:00','2017-08-17',1200);
/*!40000 ALTER TABLE `route_stop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stop`
--

DROP TABLE IF EXISTS `stop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stop` (
  `stop_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `city_name` varchar(64) NOT NULL,
  PRIMARY KEY (`stop_id`),
  UNIQUE KEY `city_name_UNIQUE` (`city_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stop`
--

LOCK TABLES `stop` WRITE;
/*!40000 ALTER TABLE `stop` DISABLE KEYS */;
INSERT INTO `stop` VALUES (1,'Beograd'),(7,'Kosovska Mitrovica'),(3,'Kragujevac'),(4,'Kraljevo'),(6,'Novi Pazar'),(9,'Subotica'),(2,'Topola'),(5,'Uzice');
/*!40000 ALTER TABLE `stop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(88) NOT NULL,
  `email` varchar(255) NOT NULL,
  `active` bit(1) NOT NULL DEFAULT b'0',
  `token` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uq_user_username` (`username`),
  UNIQUE KEY `uq_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (42,'test','$2a$10$c3qklMW9pn9LSuAqweG5M.7l.kGkrpofCJm8NBaT4mdVw7PIoS3s.','test@gmail.com','','$2a$10$juPlhO4nEPl8ImZ0syKlp.9r33Y76HEw640zYKOGIeA5ZvAxvUVH2'),(43,'Nemanja','$2a$10$0QLO0culieW0iNhwTUF.VurWSEOfitmDFuRDDTsQ3NLzE0WYAxDRm','nemanja4@gmail.com','','$2a$10$mbGwkX/8d3ar9Q3L/JUzh.1IpdurlVp.8TuFhx9CD9KB7lFGtJuxO'),(45,'Nemanja94','$2a$10$3hqYjw1N5HZ.HMj2sQbS.e.l7CzQSUg9KOnQPN7aSAg7oB8gw7/fm','nemanja@gmail.com','\0','$2a$10$9PXAu41dXvfn1R6rbx1MMurU0WzeZOTHgKUW.To97OVDP.iVxnW56'),(46,'Milos','$2a$10$v.6FnQBFVv25Xsraulcoz.9jwtegKtpAJErzw0mwVthL8fbMC4Eg2','milos@gmail.com','\0','$2a$10$N7d4Ee4t0hQu1NezxV2obe3WNNfjroD5z59RGWR4cj4bIef6akYz2'),(47,'Galiks','$2a$10$VjQaAflMfDqjYkC6ebahee6Oorb7ZkvMVhqi2SD4RjeuB9n4da/AG','galiks@gmail.com','','$2a$10$FDP/NHQDIjyHqw/zwh4as.u01AU1J8DRizYhjOX300IBb/1gUbKPK'),(48,'Marko94','$2a$10$e8sNEkrNp/Atq4jbOzY2EORJqNZy9Xcnk27s2/k1niggTzUfPHuoG','marko94@gmail.com','','$2a$10$Cen4PtoE6ZFO63xEowPb7Oa3lhWVJOkWopIqD//G.h0gYetuYeEMO'),(49,'Alex','$2a$10$bRU8OyLCa.BlmT8TPtXCYOYc3nfdnel0Q2lWtFTu9aWAmzpcO.y5G','alex@gmail.com','','$2a$10$r5DmphoDdQTcZ55ap6Bw3.ebOpvBjjKKabij9GvGY/jrdioOwdmdm');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-21 10:17:50
