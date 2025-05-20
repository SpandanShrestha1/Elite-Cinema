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
-- Password hash for 'test123' using SHA-256: ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae
INSERT INTO users (name, email, password, is_admin) 
VALUES ('Test User', 'test@example.com', 'ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae', FALSE)
ON DUPLICATE KEY UPDATE name = 'Test User';

-- Insert an admin user (password: admin123)
-- Password hash for 'admin123' using SHA-256: 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
INSERT INTO users (name, email, password, is_admin) 
VALUES ('Admin User', 'admin@example.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', TRUE)
ON DUPLICATE KEY UPDATE name = 'Admin User';
