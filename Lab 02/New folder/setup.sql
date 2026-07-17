-- ============================================================
-- EC7020 Lab 2 - Task (a) & (b)
-- Run this ONCE in MySQL Workbench or the MySQL command line.
-- ============================================================

-- (a) SQL Database Schema
CREATE DATABASE IF NOT EXISTS lab2_security;
USE lab2_security;

-- (b) UserTable + sample data
DROP TABLE IF EXISTS UserTable;
CREATE TABLE UserTable (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    email    VARCHAR(100),
    role     VARCHAR(20) DEFAULT 'user'
);

INSERT INTO UserTable (username, password, email, role) VALUES
('alice',   'alice@123',        'alice@corp.lk',   'user'),
('bob',     'bobPass',          'bob@corp.lk',     'user'),
('charlie', 'ch4rlie!',         'charlie@corp.lk', 'user'),
('admin',   'SuperSecret#2022', 'admin@corp.lk',   'admin');

-- Verify
SELECT * FROM UserTable;
