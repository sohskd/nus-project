drop table if exists `user_account_tab`;
CREATE TABLE `user_account_tab`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
    `email`       VARCHAR(255) NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `create_time` BIGINT unsigned DEFAULT NULL,
    `update_time` BIGINT unsigned DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- INSERT INTO `user_account_tab` VALUES (1,'sohskd@gmail','Desmond','1648569692','1648569692');
-- INSERT INTO `user_account_tab` VALUES (2,'kianming@gmail.com','Kian Ming','1648569692','1648569692');

drop table if exists `position_summary_tab`;
CREATE TABLE `position_summary_tab`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT,
    `stock_ticker` VARCHAR(255) NOT NULL,
    `status`       VARCHAR(255) NOT NULL,
    `user_id`      VARCHAR(255) DEFAULT NULL,
    `create_time`  BIGINT unsigned DEFAULT NULL,
    `update_time`  BIGINT unsigned DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- INSERT INTO `position_summary_tab` VALUES (1,'MSFT','QUEUEING','1','1648569692','1648569692');
-- INSERT INTO `position_summary_tab` VALUES (2,'MSFT','QUEUEING','2','1648569692','1648569692');
