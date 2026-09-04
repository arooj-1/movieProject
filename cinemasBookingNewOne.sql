-- =====================================================
-- Cinema Booking System
-- Database: cinemaBookingDB
-- Entities:
--   1. Customer
--   2. Movie
--   3. Booking
--   4. CustomerBooking
-- =====================================================

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE;

SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- =====================================================
-- Create Database
-- =====================================================

DROP SCHEMA IF EXISTS `cinemaBookingDB`;

CREATE SCHEMA `cinemaBookingDB`
DEFAULT CHARACTER SET utf8mb4;

USE `cinemaBookingDB`;


-- =====================================================
-- CUSTOMER
-- =====================================================

CREATE TABLE `customers` (
    `customer_id` INT NOT NULL AUTO_INCREMENT,
    `full_name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `phone` VARCHAR(20) NULL,

    PRIMARY KEY (`customer_id`),

    UNIQUE INDEX `email_UNIQUE` (`email`)
)
ENGINE = InnoDB;


-- =====================================================
-- MOVIE
-- =====================================================

CREATE TABLE `movies` (
    `movie_id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(150) NOT NULL,

    PRIMARY KEY (`movie_id`)
)
ENGINE = InnoDB;


-- =====================================================
-- BOOKING
-- =====================================================

CREATE TABLE `bookings` (
    `booking_id` INT NOT NULL AUTO_INCREMENT,
    `movie_id` INT NOT NULL,
    `booking_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `total_price` DECIMAL(10,2) NOT NULL,

    PRIMARY KEY (`booking_id`),

    INDEX `fk_bookings_movies_idx` (`movie_id`),

    CONSTRAINT `fk_bookings_movies`
        FOREIGN KEY (`movie_id`)
        REFERENCES `movies` (`movie_id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)
ENGINE = InnoDB;


-- =====================================================
-- CUSTOMER BOOKING
-- =====================================================

CREATE TABLE `customer_booking` (
    `customer_id` INT NOT NULL,
    `booking_id` INT NOT NULL,

    PRIMARY KEY (`customer_id`, `booking_id`),

    INDEX `fk_customer_booking_booking_idx` (`booking_id`),

    CONSTRAINT `fk_customer_booking_customer`
        FOREIGN KEY (`customer_id`)
        REFERENCES `customers` (`customer_id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT `fk_customer_booking_booking`
        FOREIGN KEY (`booking_id`)
        REFERENCES `bookings` (`booking_id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)
ENGINE = InnoDB;


-- =====================================================
-- Restore MySQL settings
-- =====================================================

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;