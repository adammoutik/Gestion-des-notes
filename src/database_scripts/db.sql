-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 04, 2024 at 11:31 PM
-- Server version: 8.0.35-0ubuntu0.22.04.1
-- PHP Version: 8.1.2-1ubuntu2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `GestionNotes`
--

-- --------------------------------------------------------

--
-- Table structure for table `Class`
--

CREATE TABLE `Class` (
  `class_id` int NOT NULL,
  `pf_id` int NOT NULL,
  `className` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Class`
--

INSERT INTO `Class` (`class_id`, `pf_id`, `className`) VALUES
(1, 8, 'Java'),
(2, 0, 'Maths'),
(3, 9, 'Web Dev'),
(4, 0, 'Network'),
(5, 0, 'UML');

-- --------------------------------------------------------

--
-- Table structure for table `Etudiant`
--

CREATE TABLE `Etudiant` (
  `etudiant_id` int NOT NULL,
  `class_id` int NOT NULL,
  `user_id` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Etudiant`
--

INSERT INTO `Etudiant` (`etudiant_id`, `class_id`, `user_id`, `created_at`) VALUES
(39, 1, 54, '2024-01-04 22:10:09'),
(40, 1, 55, '2024-01-04 22:10:33'),
(41, 1, 56, '2024-01-04 22:10:56'),
(42, 1, 57, '2024-01-04 22:11:15'),
(43, 1, 58, '2024-01-04 22:11:36');

-- --------------------------------------------------------

--
-- Table structure for table `Note`
--

CREATE TABLE `Note` (
  `note_id` int NOT NULL,
  `etudiant_id` int NOT NULL,
  `type` varchar(30) NOT NULL,
  `note` double NOT NULL,
  `status` varchar(20) NOT NULL,
  `class_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Note`
--

INSERT INTO `Note` (`note_id`, `etudiant_id`, `type`, `note`, `status`, `class_id`) VALUES
(11, 39, 'DS', 12, 'Validé', 1),
(12, 40, 'DS', 5, 'Non validé', 1),
(13, 41, 'DS', 8, 'Rattrapage', 1),
(14, 42, 'DS', 19, 'Validé', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Professeur`
--

CREATE TABLE `Professeur` (
  `pf_id` int NOT NULL,
  `user_id` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Professeur`
--

INSERT INTO `Professeur` (`pf_id`, `user_id`, `created_at`) VALUES
(8, 59, '2024-01-04 22:12:07'),
(9, 60, '2024-01-04 22:26:03');

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `user_id` int NOT NULL,
  `username` varchar(40) NOT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `role` varchar(25) NOT NULL,
  `password` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`user_id`, `username`, `first_name`, `last_name`, `role`, `password`, `created_at`) VALUES
(54, 'javastudent1', 'Mohammed', 'Ait Benhaddou', 'Student', 'javastudent1', '2024-01-04 22:10:08'),
(55, 'javastudent2', 'Fatima', 'Zahra', 'Student', 'javastudent2', '2024-01-04 22:10:32'),
(56, 'javastudent3', 'Youssef', 'El Amrani', 'Student', 'javastudent3', '2024-01-04 22:10:55'),
(57, 'javastudent4', 'Nora', 'Ouazzani', 'Student', 'javastudent4', '2024-01-04 22:11:13'),
(58, 'javastudent5', 'Adil', 'Ezzahraoui', 'Student', 'javastudent5', '2024-01-04 22:11:34'),
(59, 'profjava', 'Khalid', 'Bouzid', 'Professeur', 'profjava', '2024-01-04 22:12:05'),
(60, 'profweb', 'Ahmed', 'Raiss', 'Professeur', 'profweb', '2024-01-04 22:26:01');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Class`
--
ALTER TABLE `Class`
  ADD PRIMARY KEY (`class_id`);

--
-- Indexes for table `Etudiant`
--
ALTER TABLE `Etudiant`
  ADD PRIMARY KEY (`etudiant_id`);

--
-- Indexes for table `Note`
--
ALTER TABLE `Note`
  ADD PRIMARY KEY (`note_id`);

--
-- Indexes for table `Professeur`
--
ALTER TABLE `Professeur`
  ADD PRIMARY KEY (`pf_id`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Class`
--
ALTER TABLE `Class`
  MODIFY `class_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `Etudiant`
--
ALTER TABLE `Etudiant`
  MODIFY `etudiant_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `Note`
--
ALTER TABLE `Note`
  MODIFY `note_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `Professeur`
--
ALTER TABLE `Professeur`
  MODIFY `pf_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
