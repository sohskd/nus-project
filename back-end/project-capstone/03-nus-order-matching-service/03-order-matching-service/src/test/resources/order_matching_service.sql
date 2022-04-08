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

drop table if exists `transaction_history_tab`;
CREATE TABLE `transaction_history_tab`
(
    `user`         VARCHAR(255) NOT NULL,
    `stock_ticker` VARCHAR(255) NOT NULL,
    `side`         VARCHAR(5) NOT NULL,
    `price`        FLOAT(8) NOT NULL,
    `quantity`     BIGINT(20) NOT NULL,
    `status`       VARCHAR(10) NOT NULL,
    `transaction_id` VARCHAR(255) NOT NULL,
    `transaction_id_after_match` VARCHAR(255) DEFAULT NULL,
    `create_time`  BIGINT unsigned DEFAULT NULL,
    PRIMARY KEY (`user`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

--INSERT INTO `transaction_history_tab` VALUES ('vincent','MSFT','BUY', 101.0, 2,'UNMATCHED','oweyf9923',NULL,1649238459);
--INSERT INTO `transaction_history_tab` VALUES ('desmond','MSFT','SELL', 100.0, 2,'UNMATCHED','hdgf732772',NULL,1649238460);