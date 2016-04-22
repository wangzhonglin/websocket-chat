-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: websocket_chat
-- ------------------------------------------------------
-- Server version	5.5.47

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
-- Table structure for table `websocket_chat_recent_sessions`
--

DROP TABLE IF EXISTS `websocket_chat_recent_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `websocket_chat_recent_sessions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `session_id` int(11) NOT NULL DEFAULT '0' COMMENT '会话id',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标志: 0 正常 1 删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户最近会话表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `websocket_chat_recent_sessions`
--

LOCK TABLES `websocket_chat_recent_sessions` WRITE;
/*!40000 ALTER TABLE `websocket_chat_recent_sessions` DISABLE KEYS */;
INSERT INTO `websocket_chat_recent_sessions` VALUES (1,12,1,'2016-04-21 16:05:29','2016-04-21 08:05:36',0),(2,12,3,'2016-04-21 16:05:29','2016-04-21 08:05:36',0),(3,12,31,'2016-04-21 16:05:29','2016-04-21 08:05:36',0),(4,15,1,'2016-04-21 16:05:29','2016-04-21 08:05:36',0),(5,11,3,'2016-04-21 16:05:29','2016-04-21 08:05:36',0),(6,20,31,'2016-04-21 16:05:29','2016-04-21 08:51:12',1),(7,20,31,'2016-04-21 16:51:22','2016-04-21 08:51:35',1),(8,20,31,'2016-04-21 16:51:39','2016-04-21 08:51:39',0);
/*!40000 ALTER TABLE `websocket_chat_recent_sessions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-22 17:05:36
