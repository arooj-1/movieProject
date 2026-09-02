-- MySQL Script: Cinema Booking System
-- Tables: customers, movies, bookings and customer_booking

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

DROP SCHEMA IF EXISTS `cinemaDB`;
CREATE SCHEMA IF NOT EXISTS `cinemaDB` DEFAULT CHARACTER SET utf8mb4;
USE `cinemaDB`;

-- -----------------------------------------------------
-- Table `customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `customers` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(20) NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movies` (
  `movie_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(150) NOT NULL,
  `genre` VARCHAR(50) NULL,
  `duration_minutes` INT NULL,
  `release_date` DATE NULL,
  `rating` VARCHAR(10) NULL,
  PRIMARY KEY (`movie_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `bookings`
-- Stores the booking itself
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookings` (
  `booking_id` INT NOT NULL AUTO_INCREMENT,
  `movie_id` INT NOT NULL,
  `booking_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `show_date` DATE NOT NULL,
  `show_time` TIME NOT NULL,
  `number_of_tickets` INT NOT NULL DEFAULT 1,
  `seat_number` VARCHAR(20) NULL,
  `total_price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`booking_id`),
  INDEX `fk_bookings_movies_idx` (`movie_id` ASC) VISIBLE,
  CONSTRAINT `fk_bookings_movies`
    FOREIGN KEY (`movie_id`)
    REFERENCES `cinemaDB`.`movies` (`movie_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `customer_booking`
-- Links customers to their bookings
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `customer_booking` (
  `customer_id` INT NOT NULL,
  `booking_id` INT NOT NULL,
  PRIMARY KEY (`customer_id`, `booking_id`),
  INDEX `fk_customer_booking_booking_idx` (`booking_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_booking_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `cinemaDB`.`customers` (`customer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_booking_booking`
    FOREIGN KEY (`booking_id`)
    REFERENCES `cinemaDB`.`bookings` (`booking_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
