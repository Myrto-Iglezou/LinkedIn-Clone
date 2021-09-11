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
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `chat_users`
--

LOCK TABLES `chat_users` WRITE;
/*!40000 ALTER TABLE `chat_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `connection`
--

LOCK TABLES `connection` WRITE;
/*!40000 ALTER TABLE `connection` DISABLE KEYS */;
/*!40000 ALTER TABLE `connection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (3);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `interest_reaction`
--

LOCK TABLES `interest_reaction` WRITE;
/*!40000 ALTER TABLE `interest_reaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `interest_reaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
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
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
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
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `post_users_interested`
--

LOCK TABLES `post_users_interested` WRITE;
/*!40000 ALTER TABLE `post_users_interested` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_users_interested` ENABLE KEYS */;
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
/*!40000 ALTER TABLE `skills_and_experience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,NULL,NULL,NULL,'admin','$2a$10$0MPXpbuZNYf2i0eh1/KOdOrJU/hn6J71zB31m8cKWtuSfTvlTfoGq',NULL,'admin',NULL,'admin@mail.com',NULL,NULL),(2,NULL,NULL,NULL,NULL,'name0','$2a$10$pjZ4ki5fckAv/Y0bycN/WeutmPeWY07NRZmiAWKhyvVXXB9/JJlJG',NULL,'surname0',NULL,'user0@mail.com',NULL,NULL),(3,NULL,NULL,NULL,NULL,'name1','$2a$10$Z0tHdwN1qyud21WC0LUB7umJ3C4m9BooQqcew98aIvP6.UEKpMZF2',NULL,'surname1',NULL,'user1@mail.com',NULL,NULL),(4,NULL,NULL,NULL,NULL,'name2','$2a$10$FKYlFUUEI/NF0gMutFIpcee4wbujNcHpooyggFThfaVyLoSoS7Q5u',NULL,'surname2',NULL,'user2@mail.com',NULL,NULL),(5,NULL,NULL,NULL,NULL,'name3','$2a$10$/Tj9svcF4zrBa/6sBHgAIevAZgfEHKKlqTqr5K/FfytRVgadmhAcS',NULL,'surname3',NULL,'user3@mail.com',NULL,NULL),(6,NULL,NULL,NULL,NULL,'name4','$2a$10$yOwJ3mq9iXg1H3.Obfp62OSaJdo4xrJL6pb73EGgJLY2pAhrvYepq',NULL,'surname4',NULL,'user4@mail.com',NULL,NULL),(7,NULL,NULL,NULL,NULL,'name5','$2a$10$cAUfriLCYMQGh0Y.1ps0feo6dPI/gEA9VyV3PYGyxpsdTrLh/b2gW',NULL,'surname5',NULL,'user5@mail.com',NULL,NULL),(8,NULL,NULL,NULL,NULL,'name6','$2a$10$pjrNw9e82lxvq6v//6.SB.JFV/WqoFzrypIi4UDUHbVitzZmRBkVy',NULL,'surname6',NULL,'user6@mail.com',NULL,NULL),(9,NULL,NULL,NULL,NULL,'name7','$2a$10$WLkjkoE99WoE7nBgovsmbO6wrVeBfm1a7ZpuyI8l8zCUZK270cLve',NULL,'surname7',NULL,'user7@mail.com',NULL,NULL),(10,NULL,NULL,NULL,NULL,'name8','$2a$10$6fzGi1stHS7y0z/wdNqEdOyCwlR3HQazw7k7KXjnBuln91t5zs0nu',NULL,'surname8',NULL,'user8@mail.com',NULL,NULL),(11,NULL,NULL,NULL,NULL,'name9','$2a$10$FOlBbhvrHBUEnD8GDrdBNOfrMRYg1SfVGkfRjtncemAt0lufWKzQu',NULL,'surname9',NULL,'user9@mail.com',NULL,NULL),(12,NULL,NULL,NULL,NULL,'name10','$2a$10$20J9Fh8RDDwHgrBVMysMAe.u/oRzOToDIefIMBqzCJSfv.aqKCv8a',NULL,'surname10',NULL,'user10@mail.com',NULL,NULL),(13,NULL,NULL,NULL,NULL,'name11','$2a$10$.GstliwzF2HU07KaYBovi.Zfr2GzpRmuG5US.E6JtLSv0SBx0rm1W',NULL,'surname11',NULL,'user11@mail.com',NULL,NULL),(14,NULL,NULL,NULL,NULL,'name12','$2a$10$LMoJNDNCOBLSM4Eq7Z3O8eKBCJtWVmVuxA5U.pCNekleNXWNdyfb2',NULL,'surname12',NULL,'user12@mail.com',NULL,NULL),(15,NULL,NULL,NULL,NULL,'name13','$2a$10$e2SMrrp.aWvIC/t57rClmeisZQPwJZNlRKqBZTkvEBxZiw6dHPzry',NULL,'surname13',NULL,'user13@mail.com',NULL,NULL),(16,NULL,NULL,NULL,NULL,'name14','$2a$10$T6URKERExiY883gQ0yuHH.Z8wvHjNKRrEgzwS1biyptDVJqemflvC',NULL,'surname14',NULL,'user14@mail.com',NULL,NULL),(17,NULL,NULL,NULL,NULL,'name15','$2a$10$GPaokhX4cR831laiBYlpoOY21v5Wwkd4HNDKjUVSACU/.Mm635SiG',NULL,'surname15',NULL,'user15@mail.com',NULL,NULL),(18,NULL,NULL,NULL,NULL,'name16','$2a$10$Ew1JOtI5gLUTbkehpTnyfeGt6EOv968OL94F8.lXfA7x9yWXYSvT6',NULL,'surname16',NULL,'user16@mail.com',NULL,NULL),(19,NULL,NULL,NULL,NULL,'name17','$2a$10$Txmg8XgvV1AtIrPFf41lMu2rZ5Xv5sy9sA7mMsCYui9RgTxaiCqau',NULL,'surname17',NULL,'user17@mail.com',NULL,NULL),(20,NULL,NULL,NULL,NULL,'name18','$2a$10$nk46JqLdLmCLB01vgaAGB.YJQKkUfKS5Y2N3HzWVDmc66AwcBjGYK',NULL,'surname18',NULL,'user18@mail.com',NULL,NULL),(21,NULL,NULL,NULL,NULL,'name19','$2a$10$2Zbv1aXqd4qvV.t5C4ITc.6mzQuyGSiS0tQrns87N6pVSgVxX4D4m',NULL,'surname19',NULL,'user19@mail.com',NULL,NULL);
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
-- Dumping data for table `user_posts_interested`
--

LOCK TABLES `user_posts_interested` WRITE;
/*!40000 ALTER TABLE `user_posts_interested` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_posts_interested` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_recommended_jobs`
--

LOCK TABLES `user_recommended_jobs` WRITE;
/*!40000 ALTER TABLE `user_recommended_jobs` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_recommended_jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_recommended_posts`
--

LOCK TABLES `user_recommended_posts` WRITE;
/*!40000 ALTER TABLE `user_recommended_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_recommended_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2),(11,2),(12,2),(13,2),(14,2),(15,2),(16,2),(17,2),(18,2),(19,2),(20,2),(21,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_users_connected_with`
--

LOCK TABLES `user_users_connected_with` WRITE;
/*!40000 ALTER TABLE `user_users_connected_with` DISABLE KEYS */;
INSERT INTO `user_users_connected_with` VALUES (3,5);
/*!40000 ALTER TABLE `user_users_connected_with` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-05 20:11:27
