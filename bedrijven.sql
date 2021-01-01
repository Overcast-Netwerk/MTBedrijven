-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 01, 2021 at 08:40 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bedrijven`
--

-- --------------------------------------------------------

--
-- Table structure for table `bedrijven`
--

DROP TABLE IF EXISTS `bedrijven`;
CREATE TABLE `bedrijven` (
  `id` int(11) NOT NULL,
  `owner_uuid` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `balance` int(11) NOT NULL,
  `creation_date` timestamp NOT NULL,
  `disabled` int(11) NOT NULL,
  `type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `facturen`
--

DROP TABLE IF EXISTS `facturen`;
CREATE TABLE `facturen` (
  `id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `client_uuid` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `description` longtext NOT NULL,
  `creation_time` timestamp NOT NULL,
  `disabled` int(11) NOT NULL,
  `creator_uuid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `werknemers`
--

DROP TABLE IF EXISTS `werknemers`;
CREATE TABLE `werknemers` (
  `id` int(11) NOT NULL,
  `user_uuid` varchar(255) NOT NULL,
  `company_id` int(11) NOT NULL,
  `role` int(11) NOT NULL,
  `pay` int(11) NOT NULL,
  `hiring_date` timestamp NOT NULL,
  `disabled` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bedrijven`
--
ALTER TABLE `bedrijven`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `facturen`
--
ALTER TABLE `facturen`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `werknemers`
--
ALTER TABLE `werknemers`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bedrijven`
--
ALTER TABLE `bedrijven`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `facturen`
--
ALTER TABLE `facturen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `werknemers`
--
ALTER TABLE `werknemers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
