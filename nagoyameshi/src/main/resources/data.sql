--company（会社概要）テーブル
INSERT IGNORE INTO company (id, name, location, representative, establishment, capital, content) VALUES (1, 'NAGOYAMESHI株式会社', '〒101-0022 東京都千代田区神田練塀町300番地 住友不動産秋葉原駅前ビル5F', '侍　名古屋','2024年3月1日', '1億円','飲食店等の情報提供サービス');

--shops（店舗）テーブル
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (1, 1, '味噌煮込みうどんの山本屋　大久手店', 'shop01.jpg', '大正14年創業の味噌煮込みうどんの老舗。初代からの「伝統製法」「手作りへのこだわり」「アットホームな温もり」を感じられる店。', '464-0854', '名古屋市千種区大久手5-9-2', '052-733-7413', '11:00', '21:00', '月曜(月曜が祝日の場合は営業、翌日が振替休日)', 4500, 50, 'http://a-yamamotoya.co.jp/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (2, 2, '世界の山ちゃん　本店', 'shop02.jpg', '辛さと風味が際立つ「幻のコショウ」が効いた幻の手羽先は、「もう１本！」と思わず手が伸びます。', '460-0008', '名古屋市中区栄4-9-6', '052-242-1342', '17:00', '23:00', '無休（年末年始は除く）', 600, 20, 'https://www.yamachan.co.jp/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (3, 3, '名古屋コーチン 弌鳥　グローバルゲート店', 'shop03.jpg', '名古屋コーチン料理をはじめ様々な鶏料理を提供させて頂いております。鶏料理専門店の鶏の旨味を是非お楽しみ下さい。', '453-6101', '名古屋市中村区平池町4丁目60-12　グローバルゲート1階', '052-485-8551', '17:00', '23:00', '不定休', 1500, 30, 'https://www.shunsai-icchou.com/gg/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (4, 1, '宮きしめん　神宮東店', 'shop04.jpg', '名鉄神宮前直結。各種きしめんメニューはもちろん「ちょいのみ」メニューがあります。', '456-0032', '名古屋市熱田区三本松町18番4号　ミュープラット神宮前3階', '052-618-9633', '11:00', '21:30', '月曜(月曜が祝日の場合は営業、翌日が振替休日)', 350, 15, '	https://www.miyakishimen.co.jp/jinguhigashi/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (5, 4, 'うなぎしら河　浄心本店', 'shop05.jpg', '創業以来継ぎ足しの今に伝えるたれで、身はふっくら、皮はカリッと焼き上げた鰻がぎっしりのったひつまぶしを是非ご堪能下さい。', '451-0031', '名古屋市西区城西4-20-12', '052-524-1415', '17:00', '21:30', '木曜（祝日の場合営業）', 2160, 25, 'https://hitsumabushi.jp/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (6, 4, 'ひつまぶし名古屋備長　大名古屋ビルヂング店', 'shop06.jpg', '素材、焼き、技にこだわる本格ひつまぶし専門店の備長。職人の技により外はパリッと中はふっくら香ばしく焼き上げた鰻をご堪能下さい。', '450-0002', '名古屋市中村区名駅3-28-12　大名古屋ビルヂング3F', '052-564-5756', '17:00', '23:00', '無休（大名古屋ビルヂングに準ずる）', 3000, 10, 'http://hitsumabushi.co.jp/dai-nagoya-bldg/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (7, 4, '宮健', 'shop07.jpg', '明治32年創業。受け継がれた伝統の味を大切にする鳥とうなぎの専門店です。', '450-0003', '名古屋市中村区名駅南1-2-13', '052-541-0760', '11:30', '21:30', '土曜、第４水曜', 1000, 25, '');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (8, 4, '炭焼 ひつまぶし割烹　うな善', 'shop08.jpg', '創業昭和32年の当店のひつまぶしは、ふんわり柔らかな食感。あえて細かくすることなく、鰻本来の旨みを感じて、食して頂けます。', '450-0003', '名古屋市中村区名駅南1-17-26', '052-551-5235', '11:30', '22:00', '月曜、日曜（夜）', 6000, 150, 'http://www.meieki-unazen.com/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (9, 4, 'あつた蓬莱軒　松坂屋店', 'shop09.jpg', '創業明治六年。四季折々の日本料理とつぎ足し守り続けた秘伝のタレで焼き上げた鰻料理をお楽しみいただけます。', '460-0008', '名古屋市中区栄3-30-8　松坂屋名古屋店南館10F', '052-264-3825', '11:00', '20:30', '無休（松坂屋名古屋店に準ずる）', 5500, 60, 'http://www.houraiken.com/');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (10, 5, 'めいふつ天むす千寿', 'shop10.jpg', '昭和55年よりの天むすの専門店。厳選したお米に小えびの天ぷらを入れのりで巻いた手軽にお召し上がりいただける小ぶりのおむすび。', '460-0011', '名古屋市中区大須4-10-82', '052-262-0466', '8:30', '14:00', '火曜、水曜', 400, 5, '');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (11, 1, '喫茶リッチ', 'shop11.jpg', '代々味を受け継いできたエビフライやハンバーグといった王道の洋食に、アツアツの鉄板ナポリタンなど、懐かしい味に出会えます。', '453-0015', '名古屋市中村区椿町6-9号先　エスカ地下街内', '052-452-3456', '07:00', '20:30', '無休（エスカ地下街に準ずる）', 2500, 60, 'http://www.esca-sc.com/rest_cafe_0039.html');
INSERT IGNORE INTO shops (id, categories_id, name, image_name, description, postal_code, address, phone_number, open_time, close_time, regular_holiday, price, seats, shop_site) VALUES (12, 1, '中国台湾料理 味仙　本店', 'shop12.jpg', '1960年創業の元祖台湾ラーメンの店。 唐辛子とニンニクの旨味を効かせた、たっぷりの豚肉ミンチと鶏ガラスープの味わいは絶品！', '464-0850', '名古屋市千種区今池1-12-10', '052-733-7670', '17:30', '2:00', '無休', 550, 40, 'http://www.misen.ne.jp/');

--categoriesテーブル
INSERT IGNORE INTO categories (id, category_name) VALUES (1, 'めん類');
INSERT IGNORE INTO categories (id, category_name) VALUES (2, '手羽先');
INSERT IGNORE INTO categories (id, category_name) VALUES (3, '名古屋コーチン');
INSERT IGNORE INTO categories (id, category_name) VALUES (4, 'ひつまぶし');
INSERT IGNORE INTO categories (id, category_name) VALUES (5, '天むす');
INSERT IGNORE INTO categories (id, category_name) VALUES (6, '鉄板スパ');

-- rolesテーブル
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_FREEMEMBER');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_PAYMEMBER');

-- usersテーブル
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (1, 1, '名古屋 飯主', 'ナゴヤハンシュ', '1984/03/01', '080-1234-5678', 'エンジニア', 'nagoya.hansyu@example.com', 'admin', false);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (2, 2, '名古屋　飯太', 'ナゴヤハンタ', '2010/01/01', '080-1234-5678', '営業', 'nagoya.hanta@example.com', 'password', false);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (3, 2, '名古屋　飯子', 'ナゴヤハンコ', '2015/02/02', '080-1234-5678', '学生', 'nagoya.hanko@example.com', 'password', false);
INSERT IGNORE INTO users (id, roles_id, name, furigana, birthday, phone_number, profession, mail, password, enabled) VALUES (4, 3, '名古屋　飯副', 'ナゴヤハンプク', '1982/05/29', '080-1234-5678', '主婦', 'nagoya.hanpuku@example.com', 'password', false);
