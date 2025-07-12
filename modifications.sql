
-- 05/07/2025
-- Creation of t_user table

CREATE TABLE `t_user` (
                            `lg_user_id`  varchar(150) NOT NULL ,
                            `str_fname`  varchar(150) NOT NULL ,
                            `str_lname`  varchar(150) NULL ,
                            `str_phone_number`  varchar(30) NOT NULL ,
                            `str_password`  varchar(150) NULL ,
                            `str_id_card_number`  varchar(150) NULL ,
                            `str_email`  varchar(200) NULL ,
                            `str_roles`  varchar(200) NULL ,
                            `em_status`  enum('DISABLED','ENABLED') NOT NULL DEFAULT 'ENABLED' ,
                            `em_sex`  enum('F','M') NOT NULL ,
                            `dt_created`  datetime NOT NULL ,
                            `dt_updated`  datetime NULL ,
                            PRIMARY KEY (`lg_user_id`)
);

ALTER TABLE `t_user`
ADD UNIQUE INDEX `UQ_str_email` (`str_email`) ;

CREATE TABLE `t_role` (
                            `lg_id`  bigint NOT NULL AUTO_INCREMENT,
                            `str_name`  varchar(50) NOT NULL ,
                            `bl_active`  tinyint(1) NOT NULL DEFAULT 1 ,
                            `dt_created`  datetime NOT NULL ,
                            `dt_updated`  datetime NULL ,
                            PRIMARY KEY (`lg_id`)
);


CREATE TABLE `t_user_role` (
                            `lg_id`  bigint NOT NULL AUTO_INCREMENT,
                            `lg_user_id`  varchar(150) NOT NULL ,
                            `lg_role_id`  bigint NOT NULL ,
                            `em_status`  enum('DISABLED','ENABLED') NOT NULL DEFAULT 'ENABLED' ,
                            `dt_created`  datetime NOT NULL ,
                            `dt_updated`  datetime NULL ,
                            PRIMARY KEY (`lg_id`),
                            CONSTRAINT `fk_t_user_t_user_role` FOREIGN KEY (`lg_user_id`) REFERENCES `t_user` (`lg_user_id`),
                            CONSTRAINT `fk_t_role_t_user_role` FOREIGN KEY (`lg_role_id`) REFERENCES `t_role` (`lg_id`)
);

ALTER TABLE `t_user_role` DROP FOREIGN KEY `fk_t_role_t_user_role`
;

ALTER TABLE `t_role`
DROP COLUMN `lg_id`,
CHANGE COLUMN `str_name` `str_key`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL FIRST ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`str_key`);

ALTER TABLE `t_user_role`
    MODIFY COLUMN `lg_role_id`  varchar(50) NOT NULL AFTER `lg_user_id`;

ALTER TABLE `t_user_role` ADD CONSTRAINT `fk_t_role_t_user_role` FOREIGN KEY (`lg_role_id`) REFERENCES `t_role` (`str_key`);

