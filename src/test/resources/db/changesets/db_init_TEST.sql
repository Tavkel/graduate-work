--liquibase formatted sql
--changeset tav:0 runAlways:true
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS ads CASCADE;
DROP TABLE IF EXISTS users CASCADE;