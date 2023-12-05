 -- changeset tav:dev_users_1
 -- comment: default user. To be removed
 INSERT INTO users
 (id, email, first_name, last_name, phone, image_url, user_role, password_hash)
 VALUES(1, 'test@gmail.com', 'Qwe', 'Asd', '+7903 1234567', NULL, 'USER', '$2a$10$5zsYpmdWZU64YX/hXbl/buIdlB/tegPjpkXAAFFbJ17CruyVBwGGG');