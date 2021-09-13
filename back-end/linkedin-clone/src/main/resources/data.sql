CREATE DATABASE  IF NOT EXISTS `linkedin_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `linkedin_db`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: linkedin_db
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` VALUES (1,'2021-09-12 16:24:25'),(2,'2021-09-12 16:28:35'),(3,'2021-09-12 16:32:17'),(4,'2021-09-12 16:35:27'),(5,'2021-09-12 16:35:28'),(6,'2021-09-13 10:57:41'),(7,'2021-09-13 10:57:42');
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `chat_users`
--

LOCK TABLES `chat_users` WRITE;
/*!40000 ALTER TABLE `chat_users` DISABLE KEYS */;
INSERT INTO `chat_users` VALUES (1,2),(1,3),(2,2),(2,5),(3,2),(3,4),(4,3),(4,6),(5,2),(5,6),(6,3),(6,5),(7,3),(7,4);
/*!40000 ALTER TABLE `chat_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'I totally agree','2021-09-13 11:01:44',NULL,2,6),(2,'Nerdd','2021-09-13 11:03:15',NULL,3,6),(3,'Totaly nerd','2021-09-13 11:09:55',NULL,3,5),(4,'DevOps is the future!','2021-09-13 11:59:57',NULL,2,5),(5,'Nikos see my posted jobs if you\'re interested','2021-09-13 12:03:31',NULL,2,5),(6,'Microsoft is the best','2021-09-13 12:10:13',NULL,5,2);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `connection`
--

LOCK TABLES `connection` WRITE;
/*!40000 ALTER TABLE `connection` DISABLE KEYS */;
INSERT INTO `connection` VALUES (1,_binary '',NULL,6,2),(2,_binary '',NULL,4,2),(3,_binary '',NULL,3,2),(4,_binary '',NULL,5,2),(5,_binary '',NULL,6,3),(6,_binary '',NULL,3,5),(7,_binary '',NULL,3,4);
/*!40000 ALTER TABLE `connection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(19),(19);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `interest_reaction`
--

LOCK TABLES `interest_reaction` WRITE;
/*!40000 ALTER TABLE `interest_reaction` DISABLE KEYS */;
INSERT INTO `interest_reaction` VALUES (1,NULL,NULL,1,2),(2,NULL,NULL,3,6),(3,NULL,NULL,2,6),(4,NULL,NULL,2,5),(5,NULL,NULL,5,2);
/*!40000 ALTER TABLE `interest_reaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,'Looking for senior ML engineer.','2021-09-12 16:37:09','Machine Learning Engineer',5),(2,'Looking for Junior ML engineer. Please sen me a message.','2021-09-12 16:38:08','Machine Learning Job',5),(3,'Our company is looking for a devOps engineer. Please contact me for more info.','2021-09-12 16:42:03','DevOps Engineer',4),(4,'Looking for a Software Engineer .','2021-09-12 16:43:38','HTML, Javascript Engineer',4),(5,'I am looking a cyber security engineer. Please send me a message if you are interested.','2021-09-13 11:11:47','Cyber Security Job',5);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `job_users_applied`
--

LOCK TABLES `job_users_applied` WRITE;
/*!40000 ALTER TABLE `job_users_applied` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_users_applied` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'Hi friend!','2021-09-12 16:32:46',3,4),(2,'Hi Nick!','2021-09-12 16:35:50',4,6),(3,'Hello','2021-09-12 16:45:19',3,2),(4,'How are you?','2021-09-12 16:45:33',3,2),(5,'Hi friend!','2021-09-12 16:45:46',1,2),(6,'Hello!','2021-09-13 12:13:52',1,3),(7,'Did you see the posted jobs??','2021-09-13 12:14:06',1,3),(8,'Hi Manolis!','2021-09-13 12:14:18',4,3),(9,'I am fine. Thanks','2021-09-13 12:17:55',3,4),(10,'You?','2021-09-13 12:17:59',3,4);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,_binary '',2,1,NULL,NULL,6),(2,_binary '',2,2,NULL,NULL,4),(3,_binary '',2,3,NULL,NULL,3),(4,_binary '',2,4,NULL,NULL,5),(5,_binary '',2,5,NULL,NULL,6),(6,_binary '',2,6,NULL,NULL,3),(7,_binary '',2,7,NULL,NULL,3),(8,_binary '\0',1,NULL,NULL,1,6),(9,_binary '\0',0,NULL,1,NULL,3),(10,_binary '\0',0,NULL,2,NULL,3),(11,_binary '\0',1,NULL,NULL,2,3),(12,_binary '\0',1,NULL,NULL,3,3),(13,_binary '\0',0,NULL,3,NULL,3),(14,_binary '\0',1,NULL,NULL,4,3),(15,_binary '\0',0,NULL,4,NULL,3),(16,_binary '\0',0,NULL,5,NULL,3),(17,_binary '\0',1,NULL,NULL,5,5),(18,_binary '\0',0,NULL,6,NULL,5);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'Lessons starting tomorrow. ','2021-09-12 16:35:18',6),(2,'DevOps is interesting now.','2021-09-13 10:58:21',3),(3,'Machine Learning <3','2021-09-13 10:58:56',3),(4,'Being a teacher is the best!','2021-09-13 11:04:18',6),(5,'Google is paying well.','2021-09-13 11:06:55',5),(6,'DevOps+MachineLearning=<3','2021-09-13 11:08:20',5);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'PROFESSIONAL');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `skills_and_experience`
--

LOCK TABLES `skills_and_experience` WRITE;
/*!40000 ALTER TABLE `skills_and_experience` DISABLE KEYS */;
INSERT INTO `skills_and_experience` VALUES (1,'Software Engineer intern at Intel',1,2,NULL,2,NULL),(2,'2nd High School of Thesaloniki',1,0,2,NULL,NULL),(3,'Computer Science at AUTH',1,0,2,NULL,NULL),(4,'c++',1,1,NULL,NULL,2),(5,'English',1,1,NULL,NULL,2),(6,'Javascript',1,1,NULL,NULL,2),(7,'HTML',1,1,NULL,NULL,2),(8,'Lab Assistant at UOA ',1,2,NULL,3,NULL),(9,'2nd High School of Athens',1,0,3,NULL,NULL),(10,'machine learning',1,1,NULL,NULL,3),(11,'Software Engineer ntern at IBM',1,2,NULL,5,NULL),(12,'2nd High School of Thesaloniki',1,0,5,NULL,NULL),(13,'DevOps',1,1,NULL,NULL,5),(14,'3nd High School of Petralona',1,0,4,NULL,NULL),(15,'cyber sequrity',1,1,NULL,NULL,4),(16,'Private Tutoring',1,2,NULL,6,NULL),(17,'8nd High School of Athens',1,0,6,NULL,NULL),(18,'maths',1,1,NULL,NULL,6);
/*!40000 ALTER TABLE `skills_and_experience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,NULL,NULL,NULL,'admin','$2a$10$GONFMdmEDjH04aay1pSDxucXV/u9Ea7YPqAvv9X/5GQMj.sWBQ/fG',NULL,'admin',NULL,'admin@mail.com',NULL,NULL),(2,'London','Microsoft','Software Engineer',NULL,'Anna','$2a$10$Y23Gt5ghgLNjPmjBzzck8.e8VkhM5uejpiArLXTDirh6BbrymaX/G','6972455425','Papadopoulou',NULL,'papadopoulou@mail.com','anna.com',NULL),(3,'Athens','Athena Research Center ','Machine Learning Engineer',NULL,'Nikos','$2a$10$lEDA1RR/fDN/46Wc8aRBwORTVnq2tgz/xvS6JpyUDcUpZmNs2M8YC','6975969333','Venetis',NULL,'venetis@mail.com',NULL,NULL),(4,'Athens',' Motorola Solutions','Cyber Threat Intelligence Product Manager',NULL,'Katerina','$2a$10$bimnB9t3i.bEwV8cjsrMhOrcp6ZEYh777E57..pAVwKxWZWqBUNLG','6989807306','Anastasiou',NULL,'anastasiou@mail.com',NULL,NULL),(5,'London','Google','DevOps Engineer',NULL,'Panagiotis ','$2a$10$P.3WAGsU0oSXNPjcin40v.tilhpRXqvZ.zPVgEWghelJVL0Tsu7DO','6989800983','Evangeliou',NULL,'evangeliou@mail.com',NULL,NULL),(6,'Alimos','3rd high school of Alimos','High School Teacher',NULL,'Manolis','$2a$10$6aqKmNdq7Ddxj5Z3IvilR.sndHwTr3tkc1mQuZOtvqoU92YwJpz2S','69778345260','Germanos',NULL,'germanos@mail.com',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_interest_reactions`
--

LOCK TABLES `user_interest_reactions` WRITE;
/*!40000 ALTER TABLE `user_interest_reactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_interest_reactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_job_applied`
--

LOCK TABLES `user_job_applied` WRITE;
/*!40000 ALTER TABLE `user_job_applied` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_job_applied` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_recommended_jobs`
--

LOCK TABLES `user_recommended_jobs` WRITE;
/*!40000 ALTER TABLE `user_recommended_jobs` DISABLE KEYS */;
INSERT INTO `user_recommended_jobs` VALUES (6,3),(6,5),(6,2),(6,1),(5,3),(5,5),(5,4),(6,4),(4,3),(4,5),(5,2),(5,1),(3,1),(4,2),(4,1),(4,4),(3,2),(3,5),(3,4),(3,3),(2,3),(2,5),(2,2),(2,4),(2,1);
/*!40000 ALTER TABLE `user_recommended_jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_recommended_posts`
--

LOCK TABLES `user_recommended_posts` WRITE;
/*!40000 ALTER TABLE `user_recommended_posts` DISABLE KEYS */;
INSERT INTO `user_recommended_posts` VALUES (2,3),(2,5),(4,3),(4,5),(3,3),(2,6),(2,2),(3,5),(3,6),(3,4),(2,4),(6,3),(5,6),(5,3),(5,2),(2,1),(5,5),(4,6),(3,2),(3,1),(5,1),(4,2),(4,4),(4,1),(5,4),(6,5),(6,6),(6,4),(6,2),(6,1);
/*!40000 ALTER TABLE `user_recommended_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(1,2),(2,2),(3,2),(4,2),(5,2),(6,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-13 12:21:18
