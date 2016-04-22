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
-- Table structure for table `websocket_chat_login`
--

DROP TABLE IF EXISTS `websocket_chat_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `websocket_chat_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '登录id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `session_id` varchar(128) NOT NULL DEFAULT '' COMMENT '登录sessionId',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '在线状态: 0 在线 1 忙碌 2 离线',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标志: 0 正常 1 删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_session_id` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='登录信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `websocket_chat_login`
--

LOCK TABLES `websocket_chat_login` WRITE;
/*!40000 ALTER TABLE `websocket_chat_login` DISABLE KEYS */;
INSERT INTO `websocket_chat_login` VALUES (2,12,'PO4uQL9LznzjtkM0VP9H',2,'2016-04-06 14:03:39','2016-04-22 05:32:25',0),(3,1,'6aLQhnDesAkNLUH7kSu6',2,'2016-04-06 14:14:28','2016-04-12 10:13:05',0),(4,15,'Nt2KYxpEu67zDmoEjIPh',2,'2016-04-06 16:42:51','2016-04-15 10:05:49',0),(5,11,'H9YgXdptRADyV1PUk27e',0,'2016-04-15 17:33:32','2016-04-18 03:41:50',0),(6,20,'k27Ry8K2dpJ6N3CoTx8W',0,'2016-04-18 17:52:24','2016-04-22 08:56:47',0);
/*!40000 ALTER TABLE `websocket_chat_login` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-22 17:05:33
