--会社概要
CREATE TABLE IF NOT EXISTS company
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR (100) NOT NULL,
   postal_code VARCHAR (50) NOT NULL,
   location VARCHAR (100) NOT NULL,
   --所在地
   representative VARCHAR (50) NOT NULL,
   --代表者
   establishment DATE NOT NULL,
   --設立
   capital VARCHAR (10) NOT NULL,
   --資本金
   content VARCHAR (255) NOT NULL,
   --内容
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
--カテゴリ
CREATE TABLE IF NOT EXISTS categories
(
   id INT NOT NULL PRIMARY KEY,
   category_name VARCHAR (10) NOT NULL,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
--店舗
CREATE TABLE IF NOT EXISTS shops
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   categories_id INT NOT NULL,
   name VARCHAR (50) NOT NULL,
   image_name VARCHAR (255),
   description VARCHAR (500),
   --説明
   postal_code VARCHAR (50) NOT NULL,
   address VARCHAR (255) NOT NULL,
   phone_number VARCHAR (50) NOT NULL,
   open_time VARCHAR (10) NOT NULL,
   close_time VARCHAR (10) NOT NULL,
   regular_holiday VARCHAR (100) NOT NULL,
   price INT NOT NULL,
   seats INT NOT NULL,
   --座席数
   shop_site VARCHAR (100),
   --店舗URL
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (categories_id) REFERENCES categories (id)
);
--ロール
CREATE TABLE IF NOT EXISTS roles
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR (50) NOT NULL
);
--会員
CREATE TABLE IF NOT EXISTS users
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   roles_id INT NOT NULL,
   name VARCHAR (50) NOT NULL,
   furigana VARCHAR (100) NOT NULL,
   birthday DATE NOT NULL,
   phone_number VARCHAR (50) NOT NULL,
   profession VARCHAR (100) NOT NULL, --職業
   mail VARCHAR (255) NOT NULL UNIQUE,
   password VARCHAR (255) NOT NULL,
   enabled BOOLEAN NOT NULL,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (roles_id) REFERENCES roles (id)
);
--トークン
CREATE TABLE IF NOT EXISTS verification_tokens
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   user_id INT NOT NULL UNIQUE,
   token VARCHAR (255) NOT NULL,
   expiry_date timestamp NULL,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (user_id) REFERENCES users (id)
);
--予約
CREATE TABLE IF NOT EXISTS reservations
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   users_id INT NOT NULL,
   shops_id INT NOT NULL,
   reservation_date DATE NOT NULL,
   reservation_time VARCHAR (10) NOT NULL,
   reservation_count INT NOT NULL,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (users_id) REFERENCES users (id),
   FOREIGN KEY (shops_id) REFERENCES shops (id)
);
--お気に入り
CREATE TABLE IF NOT EXISTS favorites
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   users_id INT NOT NULL,
   shops_id INT NOT NULL,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (users_id) REFERENCES users (id),
   FOREIGN KEY (shops_id) REFERENCES shops (id)
);