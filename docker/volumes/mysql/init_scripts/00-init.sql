-- NOTE This script will only be executed ONCE on the first container start
-- create databases
CREATE DATABASE IF NOT EXISTS `omnitrade_orders`;
CREATE DATABASE IF NOT EXISTS `omnitrade_accounts`;
CREATE DATABASE IF NOT EXISTS `omnitrade_forums`;
-- create root user and grant rights
-- CREATE USER 'root' @'localhost' IDENTIFIED BY 'local';
-- GRANT ALL PRIVILEGES ON *.* TO 'root' @'%';
-- init live stock table
USE `omnitrade_orders`;
CREATE TABLE `stock_live_tab` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `stock_ticker` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `price` float DEFAULT NULL,
    `price_fix_around` float DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 3 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
INSERT INTO `stock_live_tab` (`stock_ticker`, `price`, `price_fix_around`) VALUES ('AAPL', '100', '100');
INSERT INTO `stock_live_tab` (`stock_ticker`, `price`, `price_fix_around`) VALUES ('MSFT', '200', '200');
INSERT INTO `stock_live_tab` (`stock_ticker`, `price`, `price_fix_around`) VALUES ('GOOGL', '300', '300');
