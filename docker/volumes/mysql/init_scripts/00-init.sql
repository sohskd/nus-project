-- create databases
CREATE DATABASE IF NOT EXISTS `omnitrade_orders`;
CREATE DATABASE IF NOT EXISTS `omnitrade_accounts`;
CREATE DATABASE IF NOT EXISTS `omnitrade_forums`;
-- create root user and grant rights
-- CREATE USER 'root' @'localhost' IDENTIFIED BY 'local';
-- GRANT ALL PRIVILEGES ON *.* TO 'root' @'%';