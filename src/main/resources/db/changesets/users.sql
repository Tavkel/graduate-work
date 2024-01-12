--liquibase formatted sql

--changeset tav:users_1
CREATE TABLE users(
id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
email VARCHAR(32) NOT NULL,
first_name VARCHAR(16),
last_name VARCHAR(16),
phone VARCHAR(31),
image_url VARCHAR(255),
user_role VARCHAR(31) NOT NULL,
password_hash VARCHAR(255) NOT NULL,
CONSTRAINT "PK_users_id" PRIMARY KEY (id),
CONSTRAINT "users_email_length_min" CHECK (char_length(email) > 3),
CONSTRAINT "users_first_name_min" CHECK (char_length(first_name) >1),
CONSTRAINT "users_last_name_min" CHECK (char_length(last_name) >1)
);