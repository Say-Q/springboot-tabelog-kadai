--company（会社概要）テーブル
INSERT IGNORE INTO company (id, name, postal_code , location, representative, establishment, capital, content) VALUES (1, 'NAGOYAMESHI株式会社', '〒101-0022', '東京都千代田区神田練塀町300番地 住友不動産秋葉原駅前ビル5F', '侍　名古屋','2024-03-01', '1億円','飲食店等の情報提供サービス');

--shops（店舗）テーブル
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (1, 1, '味噌煮込みうどんの山本屋　大久手店', 'shop01.jpg', '大正14年創業の味噌煮込みうどんの老舗。初代からの「伝統製法」「手作りへのこだわり」「アットホームな温もり」を感じられる店。', '464-0854', '名古屋市千種区大久手5-9-2', '052-733-7413', '11:00', '21:00', '月曜日', 4500, 50, 'http://a-yamamotoya.co.jp/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (2, 2, '世界の山ちゃん　本店', 'shop02.jpg', '辛さと風味が際立つ「幻のコショウ」が効いた幻の手羽先は、「もう１本！」と思わず手が伸びます。', '460-0008', '名古屋市中区栄4-9-6', '052-242-1342', '17:00', '23:00', '無休（年末年始は除く）', 600, 20, 'https://www.yamachan.co.jp/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (3, 3, '名古屋コーチン 弌鳥　グローバルゲート店', 'shop03.jpg', '名古屋コーチン料理をはじめ様々な鶏料理を提供させて頂いております。鶏料理専門店の鶏の旨味を是非お楽しみ下さい。', '453-6101', '名古屋市中村区平池町4丁目60-12　グローバルゲート1階', '052-485-8551', '17:00', '23:00', '不定休', 1500, 30, 'https://www.shunsai-icchou.com/gg/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (4, 1, '宮きしめん　神宮東店', 'shop04.jpg', '名鉄神宮前直結。各種きしめんメニューはもちろん「ちょいのみ」メニューがあります。', '456-0032', '名古屋市熱田区三本松町18番4号　ミュープラット神宮前3階', '052-618-9633', '11:00', '21:30', '月曜日', 350, 15, '	https://www.miyakishimen.co.jp/jinguhigashi/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (5, 4, 'うなぎしら河　浄心本店', 'shop05.jpg', '創業以来継ぎ足しの今に伝えるたれで、身はふっくら、皮はカリッと焼き上げた鰻がぎっしりのったひつまぶしを是非ご堪能下さい。', '451-0031', '名古屋市西区城西4-20-12', '052-524-1415', '17:00', '21:30', '木曜日', 2160, 25, 'https://hitsumabushi.jp/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (6, 4, 'ひつまぶし名古屋備長　大名古屋ビルヂング店', 'shop06.jpg', '素材、焼き、技にこだわる本格ひつまぶし専門店の備長。職人の技により外はパリッと中はふっくら香ばしく焼き上げた鰻をご堪能下さい。', '450-0002', '名古屋市中村区名駅3-28-12　大名古屋ビルヂング3F', '052-564-5756', '17:00', '23:00', '無休（大名古屋ビルヂングに準ずる）', 3000, 10, 'http://hitsumabushi.co.jp/dai-nagoya-bldg/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (7, 4, '宮健', 'shop07.jpg', '明治32年創業。受け継がれた伝統の味を大切にする鳥とうなぎの専門店です。', '450-0003', '名古屋市中村区名駅南1-2-13', '052-541-0760', '11:30', '21:30', '土曜日', 1000, 25, '');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (8, 4, '炭焼 ひつまぶし割烹　うな善', 'shop08.jpg', '創業昭和32年の当店のひつまぶしは、ふんわり柔らかな食感。あえて細かくすることなく、鰻本来の旨みを感じて、食して頂けます。', '450-0003', '名古屋市中村区名駅南1-17-26', '052-551-5235', '11:30', '22:00', '月曜日', 6000, 150, 'http://www.meieki-unazen.com/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (9, 4, 'あつた蓬莱軒　松坂屋店', 'shop09.jpg', '創業明治六年。四季折々の日本料理とつぎ足し守り続けた秘伝のタレで焼き上げた鰻料理をお楽しみいただけます。', '460-0008', '名古屋市中区栄3-30-8　松坂屋名古屋店南館10F', '052-264-3825', '11:00', '20:30', '無休（松坂屋名古屋店に準ずる）', 5500, 60, 'http://www.houraiken.com/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (10, 5, 'めいふつ天むす千寿', 'shop10.jpg', '昭和55年よりの天むすの専門店。厳選したお米に小えびの天ぷらを入れのりで巻いた手軽にお召し上がりいただける小ぶりのおむすび。', '460-0011', '名古屋市中区大須4-10-82', '052-262-0466', '08:30', '14:00', '火曜日,水曜日', 400, 5, '');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (11, 6, '喫茶リッチ', 'shop11.jpg', '代々味を受け継いできたエビフライやハンバーグといった王道の洋食に、アツアツの鉄板ナポリタンなど、懐かしい味に出会えます。', '453-0015', '名古屋市中村区椿町6-9号先　エスカ地下街内', '052-452-3456', '07:00', '20:30', '無休（エスカ地下街に準ずる）', 2500, 60, 'http://www.esca-sc.com/rest_cafe_0039.html');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (12, 1, '中国台湾料理 味仙　本店', 'shop12.jpg', '1960年創業の元祖台湾ラーメンの店。 唐辛子とニンニクの旨味を効かせた、たっぷりの豚肉ミンチと鶏ガラスープの味わいは絶品！', '464-0850', '名古屋市千種区今池1-12-10', '052-733-7670', '17:30', '02:00', '無休', 550, 40, 'http://www.misen.ne.jp/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (13, 4, '伊勢神宮外宮奉納うなぎ うなぎのしろむら', 'ffc800e5-a13b-40d6-b2d1-c8f7f50b0bf2.jpg', '縁起の良い伊勢神宮外宮奉納うなぎを和の趣がある店内でお楽しみ頂けます。卵やとろろなど独自の食べ方が人気のひつまぶしは必食です。', '461-0001', '名古屋市東区泉1-18-41', '052-971-3122', '10:30', '20:00', '不定休', 9000, 50, 'http://unagi-shiromura.nagoya/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (14, 1, '手打うどん 五城', '0077cae0-e256-4173-81aa-b4ee2750dead.jpg', '国産小麦で毎日手打しています。二種類の味噌をブレンドし出汁と合わせまろやかで見た目よりもあっさり。鉄鍋でボリューム満点です。', '460-0008', '名古屋市中区栄1-10-10', '052-204-1995','11:00', '15:30', '日曜日', 600, 30, '');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (15, 3, '鶏鉄板焼　お好み焼き　かしわ', 'b1b09af9-dbb6-4b28-977b-7e5232bc72a9.jpg', '厳選した”純鶏”名古屋コーチンと銘柄鶏の様々な部位を新鮮野菜と一緒にお召し上がり頂ける鶏料理専門店', '450-0002', '名古屋市中村区名駅1丁目1番3号　　　　　ＪＲゲートタワー12Ｆ', '052-756-2831', '11:00', '23:00', '無休', 1200, 50, 'http://salt-group.jp/shop/kashiwa-nagoya');

--categories（カテゴリ）テーブル
INSERT IGNORE INTO categories (id, category_name) VALUES (1, 'めん類');
INSERT IGNORE INTO categories (id, category_name) VALUES (2, '手羽先');
INSERT IGNORE INTO categories (id, category_name) VALUES (3, '名古屋コーチン');
INSERT IGNORE INTO categories (id, category_name) VALUES (4, 'ひつまぶし');
INSERT IGNORE INTO categories (id, category_name) VALUES (5, '天むす');
INSERT IGNORE INTO categories (id, category_name) VALUES (6, '鉄板スパ');

--rolesテーブル
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_FREEMEMBER');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_PAYMEMBER');

--users（会員）テーブル
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (1, 1, '名古屋　飯主', 'ナゴヤハンシュ', '1984/03/01', '080-1234-5678', 'エンジニア', 'nagoya.hansyu@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (2, 2, '名古屋　飯太', 'ナゴヤハンタ', '2010/01/01', '080-1234-5678', '大学生', 'nagoya.hanta@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (3, 2, '名古屋　飯子', 'ナゴヤハンコ', '2015/02/02', '080-1234-5678', '学生', 'nagoya.hanko@example.com', '$2a$10$qKT4WD93t8H80zsbSLCrTucwZ6b6PEr20tzGgZqpMMArn497.E0Vq', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (4, 2, '名古屋　飯副', 'ナゴヤハンプク', '1982/05/29', '080-1234-5678', '主婦', 'nagoya.hanpuku@example.com', '$2a$10$8hTTwZXDLqin7MdzRLSShef/3kG8c6JP8ha/jXSmbWy.7mzTNfQv2', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (5, 2, '名古屋　風太郎', 'ナゴヤフウタロウ', '2007/04/15', '080-1234-5678', '学生', 'nagoya.futaro@example.com', 'password', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (6, 2, '名古屋　次郎', 'ナゴヤジロウ', '1988/06/02', '080-1234-5678', '公務員', 'nagoya.jiro@example.com', 'password', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (7, 2, '名古屋　三郎', 'ナゴヤサブロウ', '1994/07/03', '080-1234-5678', '警察官', 'nagoya.saburo@example.com', 'password', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (8, 2, '名古屋　四郎', 'ナゴヤシロウ', '2000/08/04', '080-1234-5678', '美容師', 'nagoya.shiro@example.com', 'password', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (9, 2, '名古屋　一花', 'ナゴヤイチカ', '2007/05/05', '080-1234-5678', '学生', 'nagoya.ichika@example.com', 'password', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (10, 2, '名古屋　ニ乃', 'ナゴヤニノ', '2007/05/05', '080-1234-5678', '学生', 'nagoya.nino@example.com', 'password', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (11, 2, '名古屋　三玖', 'ナゴヤミク', '2007/05/05', '080-1234-5678', '学生', 'nagoya.miku@example.com', 'password', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (12, 2, '名古屋　四葉', 'ナゴヤヨツバ', '2007/05/05', '080-1234-5678', '学生', 'nagoya.yotsuba@example.com', 'password', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (13, 2, '名古屋　五月', 'ナゴヤイツキ', '2007/05/06', '080-1234-5678', '学生', 'nagoya.itsuki@example.com', 'password', true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (14, 3, '久保　誠和', 'クボセイカズ', '2000-01-01', '012-012-1234','エンジニア','say19840702@gmail.com','$2a$10$MT9rQRWKg8uwtUS2X6RyvuZQ4KHU0RgwjY18fBHcpRVFb9mXqhPjG',true);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (16, 1, '名古屋　管理者', 'ナゴヤ管理者', '2024/03/01', '080-1234-5678', 'エンジニア', 'nagoya.admin@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', true);

--reservations（予約）テーブル
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (1, 2, 1, '2024-01-01', '18:00', 10);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (2, 2, 2, '2024-02-02', '18:30', 9);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (3, 2, 3, '2024-03-03', '18:00', 8);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (4, 2, 6, '2024-04-04', '19:00', 7);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (5, 2, 4, '2024-05-05', '19:30', 6);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (6, 2, 5, '2024-06-06', '10:00', 5);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (7, 2, 10, '2024-07-07', '18:30', 4);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (8, 2, 8, '2024-10-08', '18:00', 3);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (9, 2, 7, '2024-09-09', '19:00', 2);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (10, 2, 9, '2024-10-10', '19:30', 1);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (11, 2, 15, '2024-11-11', '18:00', 2);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (12, 2, 12, '2024-12-12', '18:30', 3);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (13, 2, 13, '2025-01-13', '18:00', 4);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (14, 2, 11, '2024-02-14', '19:00', 5);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (15, 2, 14, '2024-03-15', '19:30', 6);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (16, 14, 1, '2024-01-01', '18:00', 10);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (17, 14, 2, '2024-02-02', '18:30', 9);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (18, 14, 3, '2024-03-03', '18:00', 8);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (19, 14, 6, '2024-04-04', '19:00', 7);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (20, 14, 4, '2024-05-05', '19:30', 6);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (21, 14, 5, '2024-06-06', '10:00', 5);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (22, 14, 10, '2024-07-07', '18:30', 4);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (23, 14, 8, '2024-10-08', '18:00', 3);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (24, 14, 7, '2024-09-09', '19:00', 2);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (25, 14, 9, '2024-10-10', '19:30', 1);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (26, 14, 15, '2024-11-11', '18:00', 2);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (27, 14, 12, '2024-12-12', '18:30', 3);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (28, 14, 13, '2025-01-13', '18:00', 4);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (29, 14, 11, '2024-02-14', '19:00', 5);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (30, 14, 14, '2024-03-15', '19:30', 6);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (31, 3, 4, '2024-07-04', '11:30', 2);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (32, 3, 10, '2024-07-06', '13:00', 3);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (33, 14, 14, '2024-07-23', '13:30', 4);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (34, 14, 3, '2024-09-18', '18:30', 3);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (35, 14, 11, '2024-10-15', '13:00', 9);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (36, 14, 13, '2024-12-25', '18:30', 2);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (37, 14, 5, '2024-07-20', '19:30', 12);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (38, 14, 2, '2024-07-27', '20:00', 3);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (39, 14, 15, '2024-09-06', '12:30', 4);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (40, 14, 15, '2024-08-06', '12:30', 4);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (41, 14, 4, '2024-09-09', '16:30', 4);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (42, 14, 15, '2024-12-29', '18:00', 10);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (43, 2, 14, '2024-07-20', '13:30', 4);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (44, 2, 15, '2024-07-28', '15:30', 2);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (45, 2, 11, '2024-07-26', '10:00', 7);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (46, 2, 3, '2024-07-24', '20:30', 6);
INSERT IGNORE INTO reservations(id, users_id, shops_id, reservation_date, reservation_time, reservation_count) VALUES (47, 2, 7, '2024-07-18', '13:00', 6);
