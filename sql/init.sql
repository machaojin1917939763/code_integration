CREATE TABLE `developer` (
                             `id` INT NOT NULL AUTO_INCREMENT,
                             `name` VARCHAR(100) NOT NULL COMMENT '开发人员姓名',
                             `gender` ENUM('male', 'female', 'other') NOT NULL COMMENT '性别',
                             `department` VARCHAR(100) NOT NULL COMMENT '部门',
                             `position` VARCHAR(100) NOT NULL COMMENT '职位',
                             `phone` VARCHAR(20) NOT NULL COMMENT '电话',
                             `email` VARCHAR(100) NOT NULL COMMENT '邮件',
                             `project_id` INT COMMENT '项目ID',
                             `bug_id` INT COMMENT 'BUG ID',
                             `score` INT COMMENT '积分',
                             `creator` VARCHAR(100) NOT NULL COMMENT '创建者',
                             `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `updater` VARCHAR(100) NOT NULL COMMENT '更新者',
                             `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开发人员表';

CREATE TABLE `project` (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `name` VARCHAR(255) NOT NULL COMMENT '项目名称',
                           `url` VARCHAR(255) NOT NULL COMMENT '项目地址',
                           `type` VARCHAR(100) NOT NULL COMMENT '项目类型',
                           `responsible_person` VARCHAR(100) NOT NULL COMMENT '项目负责人',
                           `issue_count` INT COMMENT '项目问题数',
                           `issue_id` INT COMMENT '问题ID',
                           `developer_id` INT COMMENT '开发人员表ID',
                           `creator` VARCHAR(100) NOT NULL COMMENT '创建者',
                           `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updater` VARCHAR(100) NOT NULL COMMENT '更新者',
                           `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

CREATE TABLE `developer_project_relation` (
                                              `id` INT NOT NULL AUTO_INCREMENT,
                                              `developer_id` INT NOT NULL COMMENT '开发人员ID',
                                              `project_id` INT NOT NULL COMMENT '项目ID',
                                              `creator` VARCHAR(100) NOT NULL COMMENT '创建者',
                                              `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                              `updater` VARCHAR(100) NOT NULL COMMENT '更新者',
                                              `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开发人员项目关系表';

CREATE TABLE `issue` (
                         `id` INT NOT NULL AUTO_INCREMENT,
                         `issue_type` VARCHAR(100) NOT NULL COMMENT '问题类型',
                         `description` TEXT NOT NULL COMMENT '问题描述',
                         `resolver` VARCHAR(100) COMMENT '问题解决人',
                         `score` INT COMMENT '问题分数',
                         `is_resolved` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否解决',
                         `creator` VARCHAR(100) NOT NULL COMMENT '创建者',
                         `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `updater` VARCHAR(100) NOT NULL COMMENT '更新者',
                         `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='问题表';


CREATE TABLE `developer_issue_relation` (
                                            `id` INT NOT NULL AUTO_INCREMENT,
                                            `issue_id` INT NOT NULL COMMENT '问题ID',
                                            `developer_id` INT NOT NULL COMMENT '开发人员ID',
                                            `creator` VARCHAR(100) NOT NULL COMMENT '创建者',
                                            `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                            `updater` VARCHAR(100) NOT NULL COMMENT '更新者',
                                            `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开发人员问题关系表';


CREATE TABLE `project_issue_relation` (
                                          `id` INT NOT NULL AUTO_INCREMENT,
                                          `project_id` INT NOT NULL COMMENT '项目ID',
                                          `issue_id` INT NOT NULL COMMENT '问题ID',
                                          `creator` VARCHAR(100) NOT NULL COMMENT '创建者',
                                          `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `updater` VARCHAR(100) NOT NULL COMMENT '更新者',
                                          `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目问题关系表';