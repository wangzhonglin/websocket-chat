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
-- Table structure for table `websocket_chat_user`
--

DROP TABLE IF EXISTS `websocket_chat_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `websocket_chat_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(45) NOT NULL DEFAULT '' COMMENT '用户名称',
  `user_nickname` varchar(45) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `password` varchar(45) NOT NULL DEFAULT '',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别：0 女 1 男',
  `signature` varchar(128) NOT NULL DEFAULT '' COMMENT '个性签名',
  `avatar` varchar(128) NOT NULL DEFAULT '' COMMENT '用户头像地址',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标志: 0 正常 1 删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `websocket_chat_user`
--

LOCK TABLES `websocket_chat_user` WRITE;
/*!40000 ALTER TABLE `websocket_chat_user` DISABLE KEYS */;
INSERT INTO `websocket_chat_user` VALUES (1,'11111','11111','111111',0,'','images/demo/av3.jpg','2016-04-06 10:02:50','2016-04-20 07:14:21',0),(2,'12312','12312','111111',0,'','images/demo/av3.jpg','2016-04-06 13:40:47','2016-04-20 07:14:21',0),(3,'12312','12312','111111',0,'','images/demo/av3.jpg','2016-04-06 13:41:03','2016-04-20 07:14:21',0),(4,'12312','12312','111111',0,'','images/demo/av3.jpg','2016-04-06 13:41:05','2016-04-20 07:14:21',0),(5,'12312','12312','111111',0,'','images/demo/av3.jpg','2016-04-06 13:41:11','2016-04-20 07:14:21',0),(6,'12312','12312','111111',0,'','images/demo/av3.jpg','2016-04-06 13:51:21','2016-04-20 07:14:21',0),(7,'12312','12312','111111',0,'','images/demo/av3.jpg','2016-04-06 13:51:34','2016-04-20 07:14:21',0),(8,'12312','12312','111111',0,'','images/demo/av3.jpg','2016-04-06 13:51:51','2016-04-20 07:14:21',0),(9,'12312','12312','111111',0,'','images/demo/av3.jpg','2016-04-06 13:52:08','2016-04-20 07:14:21',0),(10,'12312','12312','111111',0,'','images/demo/av3.jpg','2016-04-06 13:52:57','2016-04-20 07:14:21',0),(11,'Eason Chen','Eason Chen','111111',0,'','images/demo/d2Vic29ja2V0dXNlcmlkLTIw.png','2016-04-06 13:55:15','2016-04-22 07:47:09',0),(12,'蝙蝠侠','蝙蝠侠','111111',0,'','images/demo/av2.jpg','2016-04-06 14:03:28','2016-04-21 01:58:37',0),(13,'12312','12312','123123',0,'','images/demo/av3.jpg','2016-04-06 14:16:53','2016-04-20 07:14:21',0),(14,'12312','12312','222222',0,'','images/demo/av3.jpg','2016-04-06 16:19:36','2016-04-20 07:14:21',0),(15,'超人','超人','111111',0,'','images/demo/av3.jpg','2016-04-06 16:42:41','2016-04-20 07:14:21',0),(16,'??123','??123','111111',0,'','images/demo/av3.jpg','2016-04-18 11:49:43','2016-04-20 07:14:21',0),(17,'??123','??123','111111',0,'','images/demo/av3.jpg','2016-04-18 13:00:08','2016-04-20 07:14:21',0),(18,'??123','??123','111111',0,'','images/demo/av3.jpg','2016-04-18 13:01:25','2016-04-20 07:14:21',0),(19,'快速123','快速123','111111',0,'','images/demo/av3.jpg','2016-04-18 13:02:46','2016-04-20 07:14:21',0),(20,'莱昂纳多','莱昂纳多','111111',1,'丫丫','images/demo/d2Vic29ja2V0dXNlcmlkLTIw.png','2016-04-18 17:51:25','2016-04-22 08:36:29',0);
/*!40000 ALTER TABLE `websocket_chat_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-22 17:05:32
