drop table if exists `transaction_history_tab`;
CREATE TABLE `transaction_history_tab` (
                                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                           `user_id` bigint(20) DEFAULT NULL,
                                           `stock_ticker` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                           `side` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL,
                                           `price` float NOT NULL,
                                           `quantity` bigint(20) NOT NULL,
                                           `status` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
                                           `transaction_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                           `transaction_id_after_match` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `create_time` bigint(20) unsigned DEFAULT NULL,
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `transaction_history_tab` VALUES (1,3,'MSFT','BUY',101,2,'UNMATCHED','oweyf9923',NULL,1649238459);
INSERT INTO `transaction_history_tab` VALUES (2,1,'MSFT','SELL',100,3,'UNMATCHED','hdgf732772',NULL,1649238460);
INSERT INTO `transaction_history_tab` VALUES (3,3,'MSFT','BUY',101,2,'MATCHED','oweyf9923','wds87f68w',1649238459);
INSERT INTO `transaction_history_tab` VALUES (4,1,'MSFT','SELL',101,2,'MATCHED','hdgf732772','wds87f68w',1649238460);
INSERT INTO `transaction_history_tab` VALUES (5,1,'MSFT','SELL',101,1,'UNMATCHED','hdgf732772',NULL,1649238460);
INSERT INTO `transaction_history_tab` VALUES (6, 3, 'MSFT', 'SELL', 110, 2, 'MATCHED','des',NULL,1649238460);
INSERT INTO `transaction_history_tab` VALUES (7, 3, 'MSFT', 'BUY', 102, 3, 'MATCHED','des',NULL,1649238460);
INSERT INTO `transaction_history_tab` VALUES (8, 3, 'SE', 'BUY', 150, 2, 'MATCHED','des2',NULL,1649238460);
INSERT INTO `transaction_history_tab` VALUES (9, 3, 'SE', 'SELL', 120, 1, 'MATCHED','des2',NULL,1649238460);

drop table if exists `stock_live_tab`;
CREATE TABLE `stock_live_tab` (
                                  `stock_ticker` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                  `price` float NOT NULL,
                                  `price_fix_around` float DEFAULT NULL,
                                  `create_time` bigint(20) unsigned DEFAULT NULL,
                                  `update_time` bigint(20) unsigned DEFAULT NULL,
                                  PRIMARY KEY (`stock_ticker`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `stock_live_tab` VALUES ('MSFT',108,105,1649238460,1649238460);
INSERT INTO `stock_live_tab` VALUES ('SE',181,190,1649238460,1649238460);


drop table if exists `user_account_tab`;
CREATE TABLE `user_account_tab` (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                    `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                    `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                    `create_time` bigint(20) unsigned DEFAULT NULL,
                                    `update_time` bigint(20) unsigned DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `user_account_tab` VALUES (1,'sohskd@gmail','Desmond',1648569692,1648569692);
INSERT INTO `user_account_tab` VALUES (2,'kianming@gmail.com','Kian Ming',1648569692,1648569692);
INSERT INTO `user_account_tab` VALUES (3,'siyuan@gmail.com','Vincent',1648569692,1648569692);
