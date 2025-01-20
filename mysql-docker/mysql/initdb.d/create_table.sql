SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;
SET character_set_server = utf8mb4;
SET collation_connection = utf8mb4_unicode_ci;
SET collation_server = utf8mb4_unicode_ci;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` varchar(255),
  `password` varchar(255),
  `name` varchar(255),
  `phone` varchar(255),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint,
  `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_by` bigint,
  `is_active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `Article` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` varchar(255),
  `content` TEXT,
  -- `created_at` datetime NOT NULL,
  -- `updated_at` datetime NOT NULL
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO user (username, password, name, phone, created_at, created_by, modified_at, modified_by, is_active) VALUES ('admin', '1234', '관리자', '010-1234-5678', NOW(), 1, NOW(), 1, 1);

INSERT INTO Article (title, content) VALUES ('제목 1', '내용 1');
INSERT INTO Article (title, content) VALUES ('제목 2', '내용 2');
INSERT INTO Article (title, content) VALUES ('제목 3', '내용 3');