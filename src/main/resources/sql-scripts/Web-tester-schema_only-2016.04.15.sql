-- MySQL dump 10.13  Distrib 5.7.10, for Win32 (AMD64)
--
-- Host: 52.29.239.198    Database: sql7111307
-- ------------------------------------------------------
-- Server version	5.5.47-0ubuntu0.14.04.1

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
-- Table structure for table `Application`
--

DROP TABLE IF EXISTS `Application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Application` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(75) COLLATE utf8_bin NOT NULL,
  `description` varchar(255) COLLATE utf8_bin NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BuildVersion`
--

DROP TABLE IF EXISTS `BuildVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BuildVersion` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(75) COLLATE utf8_bin NOT NULL,
  `description` varchar(255) COLLATE utf8_bin NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DbValidation`
--

DROP TABLE IF EXISTS `DbValidation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DbValidation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sqlQuery` text COLLATE utf8_bin NOT NULL,
  `expectedValue` varchar(255) COLLATE utf8_bin NOT NULL,
  `requestId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DbValidation_Request_id` (`requestId`),
  CONSTRAINT `FK_DbValidation_Request_id` FOREIGN KEY (`requestId`) REFERENCES `Request` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DbValidationHistory`
--

DROP TABLE IF EXISTS `DbValidationHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DbValidationHistory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sqlQuery` text COLLATE utf8_bin NOT NULL,
  `expectedValue` varchar(255) COLLATE utf8_bin NOT NULL,
  `actualValue` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `resultHistoryId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DbValidationHistory_ResultHistory_id` (`resultHistoryId`),
  CONSTRAINT `FK_DbValidationHistory_ResultHistory_id` FOREIGN KEY (`resultHistoryId`) REFERENCES `ResultHistory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Environment`
--

DROP TABLE IF EXISTS `Environment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Environment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(75) COLLATE utf8_bin NOT NULL,
  `baseUrl` varchar(75) COLLATE utf8_bin NOT NULL,
  `dbType` varchar(10) COLLATE utf8_bin NOT NULL,
  `dbUrl` varchar(100) COLLATE utf8_bin NOT NULL,
  `dbPort` int(5) NOT NULL,
  `dbName` varchar(75) COLLATE utf8_bin NOT NULL,
  `dbUsername` varchar(75) COLLATE utf8_bin NOT NULL,
  `dbPassword` varchar(50) COLLATE utf8_bin NOT NULL,
  `timeMultiplier` double NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EnvironmentHistory`
--

DROP TABLE IF EXISTS `EnvironmentHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EnvironmentHistory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `resultHistoryId` int(11) unsigned NOT NULL,
  `name` varchar(75) COLLATE utf8_bin NOT NULL,
  `baseUrl` varchar(75) COLLATE utf8_bin NOT NULL,
  `dbUrl` varchar(100) COLLATE utf8_bin NOT NULL,
  `dbPort` varchar(5) COLLATE utf8_bin NOT NULL,
  `dbName` varchar(75) COLLATE utf8_bin NOT NULL,
  `environmentId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`resultHistoryId`),
  KEY `FK_EnvironmentHistory_Environment_id` (`environmentId`),
  CONSTRAINT `FK_EnvironmentHistory_Environment_id` FOREIGN KEY (`environmentId`) REFERENCES `Environment` (`id`),
  CONSTRAINT `FK_EnvironmentHistory_ResultHistory_id` FOREIGN KEY (`resultHistoryId`) REFERENCES `ResultHistory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=263 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Header`
--

DROP TABLE IF EXISTS `Header`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Header` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `value` varchar(255) COLLATE utf8_bin NOT NULL,
  `requestId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Header_Request_id` (`requestId`),
  CONSTRAINT `FK_Header_Request_id` FOREIGN KEY (`requestId`) REFERENCES `Request` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=548 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `HeaderHistory`
--

DROP TABLE IF EXISTS `HeaderHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HeaderHistory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `value` varchar(255) COLLATE utf8_bin NOT NULL,
  `resultHistoryId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_HeaderHisory_ResultHistory_id` (`resultHistoryId`),
  CONSTRAINT `FK_HeaderHisory_ResultHistory_id` FOREIGN KEY (`resultHistoryId`) REFERENCES `ResultHistory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=590 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Label`
--

DROP TABLE IF EXISTS `Label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Label` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(75) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Request`
--

DROP TABLE IF EXISTS `Request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Request` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(75) COLLATE utf8_bin NOT NULL,
  `description` varchar(255) COLLATE utf8_bin NOT NULL,
  `requestMethod` varchar(7) COLLATE utf8_bin NOT NULL,
  `applicationId` int(11) unsigned NOT NULL,
  `serviceId` int(11) unsigned NOT NULL,
  `endpoint` varchar(255) COLLATE utf8_bin NOT NULL,
  `requestBody` text COLLATE utf8_bin,
  `responseType` varchar(10) COLLATE utf8_bin NOT NULL,
  `expectedResponse` text COLLATE utf8_bin,
  `timeout` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FK_Request_Application_id` (`applicationId`),
  KEY `FK_Request_Service_id` (`serviceId`),
  CONSTRAINT `FK_Request_Application_id` FOREIGN KEY (`applicationId`) REFERENCES `Application` (`id`),
  CONSTRAINT `FK_Request_Service_id` FOREIGN KEY (`serviceId`) REFERENCES `Service` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RequestCollection`
--

DROP TABLE IF EXISTS `RequestCollection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RequestCollection` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(75) COLLATE utf8_bin NOT NULL,
  `description` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RequestCollection_Label`
--

DROP TABLE IF EXISTS `RequestCollection_Label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RequestCollection_Label` (
  `requestCollectionId` int(11) unsigned NOT NULL,
  `labelId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`requestCollectionId`,`labelId`),
  KEY `FK_RequestCollection_Label_Label_id` (`labelId`),
  CONSTRAINT `FK_RequestCollection_Label_Label_id` FOREIGN KEY (`labelId`) REFERENCES `Label` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_RequestCollection_Label_RequestCollection_id` FOREIGN KEY (`requestCollectionId`) REFERENCES `RequestCollection` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RequestCollection_Request`
--

DROP TABLE IF EXISTS `RequestCollection_Request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RequestCollection_Request` (
  `requestCollectionId` int(11) unsigned NOT NULL,
  `requestId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`requestCollectionId`,`requestId`),
  KEY `FK_RequestCollection_Request_Request_id` (`requestId`),
  CONSTRAINT `FK_RequestCollection_Request_Request_id` FOREIGN KEY (`requestId`) REFERENCES `Request` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_RequestCollection_Request_RequestCollection_id` FOREIGN KEY (`requestCollectionId`) REFERENCES `RequestCollection` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Request_Label`
--

DROP TABLE IF EXISTS `Request_Label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Request_Label` (
  `requestId` int(11) unsigned NOT NULL,
  `labelId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`requestId`,`labelId`),
  KEY `FK_Request_Label_Label_id` (`labelId`),
  CONSTRAINT `FK_Request_Label_Label_id` FOREIGN KEY (`labelId`) REFERENCES `Label` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Request_Label_Request_id` FOREIGN KEY (`requestId`) REFERENCES `Request` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ResultHistory`
--

DROP TABLE IF EXISTS `ResultHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResultHistory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `status` bit(1) NOT NULL,
  `applicationId` int(11) unsigned NOT NULL,
  `serviceId` int(11) unsigned NOT NULL,
  `requestId` int(11) unsigned NOT NULL,
  `requestName` varchar(75) COLLATE utf8_bin NOT NULL,
  `requestDescription` varchar(255) COLLATE utf8_bin NOT NULL,
  `url` varchar(255) COLLATE utf8_bin NOT NULL,
  `responseType` text COLLATE utf8_bin NOT NULL,
  `requestBody` text COLLATE utf8_bin,
  `statusLine` text COLLATE utf8_bin NOT NULL,
  `timeStart` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `expectedResponseTime` int(11) NOT NULL,
  `responseTime` int(11) NOT NULL,
  `expectedResponse` text COLLATE utf8_bin NOT NULL,
  `actualResponse` text COLLATE utf8_bin,
  `message` text COLLATE utf8_bin NOT NULL,
  `runId` int(11) unsigned NOT NULL,
  `requestCollectionId` int(11) unsigned DEFAULT NULL,
  `buildVersionId` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ResultHistory_Application_id` (`applicationId`),
  KEY `FK_ResultHistory_Service_id` (`serviceId`),
  KEY `FK_ResultHistory_Request_id` (`requestId`),
  KEY `FK_ResultHistory_RequestCollection_id` (`requestCollectionId`),
  KEY `FK_ResultHistory_BuildVersion_id` (`buildVersionId`),
  CONSTRAINT `FK_ResultHistory_Application_id` FOREIGN KEY (`applicationId`) REFERENCES `Application` (`id`),
  CONSTRAINT `FK_ResultHistory_BuildVersion_id` FOREIGN KEY (`buildVersionId`) REFERENCES `BuildVersion` (`id`),
  CONSTRAINT `FK_ResultHistory_RequestCollection_id` FOREIGN KEY (`requestCollectionId`) REFERENCES `RequestCollection` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ResultHistory_Request_id` FOREIGN KEY (`requestId`) REFERENCES `Request` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ResultHistory_Service_id` FOREIGN KEY (`serviceId`) REFERENCES `Service` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=415 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ResultHistory_Label`
--

DROP TABLE IF EXISTS `ResultHistory_Label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResultHistory_Label` (
  `resultHistoryId` int(11) unsigned NOT NULL,
  `labelId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`resultHistoryId`,`labelId`),
  KEY `FK_Result_Label_Label_id` (`labelId`),
  CONSTRAINT `FK_Result_Label_ResultHistory_id` FOREIGN KEY (`resultHistoryId`) REFERENCES `ResultHistory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Result_Label_Label_id` FOREIGN KEY (`labelId`) REFERENCES `Label` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Service`
--

DROP TABLE IF EXISTS `Service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Service` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(75) COLLATE utf8_bin NOT NULL,
  `description` varchar(255) COLLATE utf8_bin NOT NULL,
  `sla` int(10) DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(75) COLLATE utf8_bin NOT NULL,
  `password` char(32) COLLATE utf8_bin NOT NULL,
  `firstName` varchar(75) COLLATE utf8_bin DEFAULT NULL,
  `lastName` varchar(75) COLLATE utf8_bin DEFAULT NULL,
  `role` varchar(15) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Variable`
--

DROP TABLE IF EXISTS `Variable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Variable` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `value` text COLLATE utf8_bin NOT NULL,
  `isSql` bit(1) DEFAULT NULL,
  `isRandom` bit(1) DEFAULT NULL,
  `dataType` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `requestId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Variable_Request_id` (`requestId`),
  CONSTRAINT `FK_Variable_Request_id` FOREIGN KEY (`requestId`) REFERENCES `Request` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=263 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-15 12:20:41
