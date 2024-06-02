-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 02, 2024 at 05:26 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `psrentalsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `pss`
--

CREATE TABLE `pss` (
  `ID` int(11) NOT NULL,
  `Brand` text NOT NULL,
  `Model` text NOT NULL,
  `Color` text NOT NULL,
  `Year` int(11) NOT NULL,
  `Price` double NOT NULL,
  `Available` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pss`
--

INSERT INTO `pss` (`ID`, `Brand`, `Model`, `Color`, `Year`, `Price`, `Available`) VALUES
(0, 'Playstation', '5', 'White', 2023, 10000, 0),
(1, 'Playstation', '4', 'Black', 2013, 5000, 0),
(2, 'Playstation', '3', 'Blue', 2009, 3000, 0),
(3, 'Playstation', '2', 'Silver', 2000, 2000, 0),
(4, 'XBOX', 'Series X', 'Black', 2016, 5000, 0),
(5, 'XBOX', 'Series S', 'White', 2019, 6000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `rents`
--

CREATE TABLE `rents` (
  `ID` int(11) NOT NULL,
  `User` int(11) NOT NULL,
  `ps` int(11) NOT NULL,
  `DateTime` text NOT NULL,
  `Hours` int(11) NOT NULL,
  `Total` double NOT NULL,
  `Status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rents`
--

INSERT INTO `rents` (`ID`, `User`, `ps`, `DateTime`, `Hours`, `Total`, `Status`) VALUES
(0, 1, 0, '2024-02-06 20:23', 2, 4000, 1),
(1, 1, 3, '2024-02-06 22:09', 3, 6000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `FirstName` text NOT NULL,
  `LastName` text NOT NULL,
  `Email` text NOT NULL,
  `PhoneNumber` text NOT NULL,
  `Password` text NOT NULL,
  `Type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `FirstName`, `LastName`, `Email`, `PhoneNumber`, `Password`, `Type`) VALUES
(0, 'admin', 'admin', 'admin', '123', '123', 1),
(1, 'gege1', 'gege1', 'gege1', '123', '123', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
