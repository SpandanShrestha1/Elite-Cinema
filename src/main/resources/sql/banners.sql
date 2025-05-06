-- Create banners table
CREATE TABLE IF NOT EXISTS banners (
    banner_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    image_path VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample banners
INSERT INTO banners (title, description, image_path, active) VALUES
('Welcome to Elite Cinema', 'Experience the best movies in town', 'uploads/banners/banner1.jpg', true),
('New Releases Every Week', 'Check out our latest movies', 'uploads/banners/banner2.jpg', true),
('Special Discount on Weekdays', 'Get 20% off on all tickets from Monday to Thursday', 'uploads/banners/banner3.jpg', true);
