-- Create the database
CREATE DATABASE IF NOT EXISTS elitecinema;

-- Use the database
USE elitecinema;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert a test user (password: test123)
INSERT INTO users (name, email, password, is_admin) 
VALUES ('Test User', 'test@example.com', 'test123', FALSE)
ON DUPLICATE KEY UPDATE name = 'Test User';

-- Insert an admin user (password: admin123)
INSERT INTO users (name, email, password, is_admin) 
VALUES ('Admin User', 'admin@example.com', 'admin123', TRUE)
ON DUPLICATE KEY UPDATE name = 'Admin User';
