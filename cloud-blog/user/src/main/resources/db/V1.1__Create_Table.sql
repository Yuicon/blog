DROP TABLE IF EXISTS saabisu.user;

CREATE TABLE saabisu.user (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(255),
    `sex` INT(11) DEFAULT 0,
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `state` INT(11) DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC),
    UNIQUE INDEX `phone_UNIQUE` (`phone` ASC)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 AUTO_INCREMENT=10086;