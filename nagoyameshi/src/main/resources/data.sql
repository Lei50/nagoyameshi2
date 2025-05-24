 -- categoriesテーブル
INSERT IGNORE INTO categories (id, name) VALUES (1, '居酒屋');
INSERT IGNORE INTO categories (id, name) VALUES (2, '鉄板焼き');
INSERT IGNORE INTO categories (id, name) VALUES (3, '焼肉');
INSERT IGNORE INTO categories (id, name) VALUES (4, 'カレー');
INSERT IGNORE INTO categories (id, name) VALUES (5, 'パスタ');
INSERT IGNORE INTO categories (id, name) VALUES (6, 'カフェ');
INSERT IGNORE INTO categories (id, name) VALUES (7, '天ぷら');
INSERT IGNORE INTO categories (id, name) VALUES (8, '寿司');
INSERT IGNORE INTO categories (id, name) VALUES (9, '中華');
INSERT IGNORE INTO categories (id, name) VALUES (10, 'ラーメン');
INSERT IGNORE INTO categories (id, name) VALUES (11, 'ケーキ');

-- restaurantsテーブル
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (1, 2, '鉄板焼き　名古屋', 'restaurant01.jpg', '地鶏の王様“名古屋コーチン”を新鮮なお野菜と一緒に楽しむ専門店', 5000, 6000, '愛知県名古屋市中村区X-XX-X', '17:00:00', '23:00:00', '無し', 69);
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (2, 1, '居酒屋　愛知', 'restaurant02.jpg', 'コスパ最強の居酒屋', 2000, 3000, '愛知県名古屋市中村区X-XX-X', '17:00:00', '23:00:00', '不定休', 80);
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (3, 1, 'ダイニング　nagoya', 'restaurant03.jpg', '『全席完全個室』創作和食ダイニング', 4000, 5000, '愛知県名古屋市中村区X-XX-X', '17:00:00', '23:30:00', '無し', 50);
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (4, 3, '焼肉　aichi', 'restaurant04.jpg', '焼肉食べ放題2480円', 3000, 4000, '愛知県名古屋市中村区X-XX-X', '15:00:00', '23:30:00', '元日', 90);
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (5, 4, 'インド料理　Delhi', 'restaurant05.jpg', '本格インド料理のお店', 6000, 7000, '愛知県名古屋市中村区X-XX-X', '11:00:00', '23:30:00', '不定休', 30);
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (6, 7, '天ぷら酒場 tenten', 'restaurant06.jpg', '素材の旨みと香りを最大限に引き立てた天ぷら', 7000, 8000, '愛知県名古屋市中村区X-XX-X', '11:00:00', '23:00:00', '無し', 35);
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (7, 8, '鮨　銀次', 'restaurant07.jpg', '江戸前の真髄をいまに伝える江戸前鮨。', 8000, 9000, '愛知県名古屋市中村区X-XX-X', '12:00:00', '23:00:00', '火曜', 25);
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (8, 5, 'トラットリア　nagoyan', 'restaurant08.jpg', '上質なイタリアンをカジュアルにご堪能ください', 6000, 7000, '愛知県名古屋市中村区X-XX-X', '11:00:00', '22:00:00', '元旦', 20);
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (9, 5, 'イタリアンバル　milano', 'restaurant09.jpg', 'イタリアで修業を積んだシェフの絶品パスタ', 5000, 6000, '愛知県名古屋市中村区X-XX-X', '11:30:00', '22:00:00', '水曜', 28);
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (10,6, 'cafe nagomi', 'restaurant10.jpg', 'ランチから夜カフェまで♪隠れ家オープンテラスカフェ', 1000, 2000, '愛知県名古屋市中村区X-XX-X', '11:30:00', '15:00:00', '不定休', 24);
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, price_low, price_high, address, open_at, close_at, closed_on, capacity) VALUES (11,9, '中華飯店　菜々', 'restaurant11.jpg', '中国の国家資格を持つ料理人！', 4000, 5000, '愛知県名古屋市中村区X-XX-X', '11:30:00', '23:00:00', '12/31', 110);

-- rolesテーブル
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_FREE');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_PREMIUM');

-- usersテーブル
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (1, '侍 太郎', 'サムライ タロウ', 20, '東京都千代田区神田練塀町300番地', '090-1234-5678', 'taro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', '学生', 2, true, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (2, '侍 花子', 'サムライ ハナコ', 28, '東京都千代田区神田練塀町300番地', '090-1234-5678', 'hanako.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', '会社員', 1, true, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (3, '侍 義勝', 'サムライ ヨシカツ', 52, '奈良県五條市西吉野町湯川X-XX-XX', '090-1234-5678', 'yoshikatsu.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', '会社員', 2, true, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (4, '侍 幸美', 'サムライ サチミ', 63, '埼玉県吉川市南広島X-XX-XX', '090-1234-5678', 'sachimi.samurai@example.com', 'password', '会社員', 3, true, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (5, '侍 雅', 'サムライ ミヤビ', 19, '滋賀県東近江市佐目町X-XX-XX', '090-1234-5678', 'miyabi.samurai@example.com', 'password', '学生', 3, false, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (6, '侍 正保', 'サムライ マサヤス', 32, '宮城県柴田郡大河原町旭町X-XX-XX', '090-1234-5678', 'masayasu.samurai@example.com', 'password', '会社員', 3, false, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (7, '侍 真由美', 'サムライ マユミ', 35, '新潟県新潟市松岡町X-XX-XX', '090-1234-5678', 'mayumi.samurai@example.com', 'password', '会社員', 3, false, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (8, '侍 安民', 'サムライ ヤスタミ', 41, '神奈川県横浜市旭区今川町X-XX-XX', '090-1234-5678', 'yasutami.samurai@example.com', 'password', '会社員', 3, false, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (9, '侍 章緒', 'サムライ アキオ', 37, '広島県東広島市高屋町宮領X-XX-XX', '090-1234-5678', 'akio.samurai@example.com', 'password', '会社員', 3, false, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (10, '侍 祐子', 'サムライ ユウコ', 26, '京都府南丹市美山町高野X-XX-XX', '090-1234-5678', 'yuko.samurai@example.com', 'password', '会社員', 3, false, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (11, '侍 秋美', 'サムライ アキミ', 46, '京都府京都市左京区田中西春菜町X-XX-XX', '090-1234-5678', 'akimi.samurai@example.com', 'password', '主婦', 3, false, NULL);
INSERT IGNORE INTO users (id, name, furigana, age, address, phone_number, email, password, occupation, role_id, enabled, stripe_customer_id) VALUES (12, '侍 信平', 'サムライ シンペイ', 55, '兵庫県加東市新定X-XX-XX', '090-1234-5678', 'shinpei.samurai@example.com', 'password', '会社員', 3, false, NULL);

-- reservationsテーブル
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_date, reservation_time, number_of_people) VALUES (1, 1, 1, '2023-04-01', '18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_date, reservation_time, number_of_people) VALUES (2, 2, 1, '2023-04-02', '19:00', 3);

-- reviewsテーブル
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (1, 1, 1, 4, 'お好み焼きは思っていた味と違ったけど、全体的にどれも美味しかったです。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (2, 1, 2, 5, '名古屋コーチンが堪能できました。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (3, 1, 3, 4, 'おいしかったです。ご馳走様でした。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (4, 1, 4, 5, '内装の雰囲気も大変良かったです。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (5, 1, 5, 3, '私には少し味付けが濃かったです。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (6, 1, 6, 5, '店員さんも皆感じが良いお店でした。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (7, 1, 7, 4, 'コストパフォーマンス抜群で、お得に名古屋コーチンがいただけます。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (8, 1, 8, 3, '料理が出てくるまで少し時間がかかりました。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (9, 1, 9, 4, 'なかなか予約が取れないので、やっと行けてよかったです。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (10, 1, 10, 5, 'いろんな鶏肉料理が楽しめました。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (11, 1, 11, 4, 'ご馳走様でした。また行きます。');
 INSERT IGNORE INTO reviews (id, restaurant_id, user_id, score, content) VALUES (12, 1, 12, 5, 'おすすめのお店です。');

 -- favoritesテーブル
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (1, 1, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (2, 2, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (3, 3, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (4, 4, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (5, 5, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (6, 6, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (7, 7, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (8, 8, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (9, 9, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (10, 10, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (11, 11, 1);
 INSERT IGNORE INTO favorites (id, restaurant_id, user_id) VALUES (12, 12, 1);
 
