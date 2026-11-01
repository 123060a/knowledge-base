-- ============================================================
-- 个人知识库 v1：建库 + 建表 + 测试数据
-- 用法：在 MySQL Workbench 里打开这个文件，按 Ctrl+Shift+Enter 全部执行
-- ============================================================

-- 1. 建库（kb = knowledge base）
DROP DATABASE IF EXISTS kb;
CREATE DATABASE kb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE kb;

-- 2. 用户表（v1 阶段只插一条测试数据，登录留到 v2）
CREATE TABLE `user` (
  `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username`      VARCHAR(50)     NOT NULL COMMENT '登录名',
  `password_hash` VARCHAR(100)    NOT NULL COMMENT 'BCrypt 结果，绝不存明文',
  `nickname`      VARCHAR(50)     DEFAULT NULL,
  `created_at`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- 3. 笔记表
CREATE TABLE `note` (
  `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`    BIGINT UNSIGNED NOT NULL,
  `title`      VARCHAR(200)    NOT NULL,
  `content`    MEDIUMTEXT      NOT NULL COMMENT 'MEDIUMTEXT 而不是 TEXT，TEXT 只有 64KB',
  `is_deleted` TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '0 正常 1 已删（软删除）',
  `created_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_updated` (`user_id`, `updated_at`),
  KEY `idx_user_deleted` (`user_id`, `is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='笔记';

-- 4. 标签表
CREATE TABLE `tag` (
  `id`      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL,
  `name`    VARCHAR(50)     NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_name` (`user_id`, `name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签';

-- 5. 笔记-标签关联表（多对多）
CREATE TABLE `note_tag` (
  `note_id` BIGINT UNSIGNED NOT NULL,
  `tag_id`  BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`note_id`, `tag_id`),
  KEY `idx_tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='笔记与标签多对多';

-- ============================================================
-- 6. 测试数据（跑完就能直接查，先看到数据长什么样）
-- ============================================================
INSERT INTO `user` (`id`, `username`, `password_hash`, `nickname`)
VALUES (1, 'me', '$2a$10$abcdefghijklmnopqrstuv', '我');

INSERT INTO `tag` (`id`, `user_id`, `name`) VALUES
 (1, 1, '数据库'),
 (2, 1, 'Java'),
 (3, 1, '面试');

INSERT INTO `note` (`user_id`, `title`, `content`) VALUES
 (1, 'MySQL 联合索引笔记', '最左前缀原则：联合索引 (a,b,c) 能支撑 a、a+b、a+b+c 的查询，但单独查 b 用不上索引。'),
 (1, 'ArrayList 扩容机制', '默认容量 10，扩容为原来的 1.5 倍，底层是 Arrays.copyOf。中间插入是 O(n)，随机访问是 O(1)。');

INSERT INTO `note_tag` (`note_id`, `tag_id`) VALUES (1, 1), (1, 3), (2, 2);

-- ============================================================
-- 7. 验证：执行完这几条，能看到结果就说明建表成功
-- ============================================================
SELECT COUNT(*) AS 用户数 FROM `user`;
SELECT COUNT(*) AS 笔记数 FROM `note`;
SELECT COUNT(*) AS 标签数 FROM `tag`;
SELECT id, title, LEFT(content, 20) AS 正文前20字, updated_at FROM `note`;
