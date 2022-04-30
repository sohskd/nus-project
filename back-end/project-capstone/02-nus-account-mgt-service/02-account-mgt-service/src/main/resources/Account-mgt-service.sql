drop table if exists `user`;
CREATE TABLE `user`
(   
    `username`    VARCHAR(255) NOT NULL,
    `email`       VARCHAR(255) NOT NULL,
    `password`    VARCHAR(255) NOT NULL,
    `loggon_i`    bit(1),
       PRIMARY KEY (`username`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
