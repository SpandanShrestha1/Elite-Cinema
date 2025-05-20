-- Add status column to movies table
ALTER TABLE movies ADD COLUMN IF NOT EXISTS status VARCHAR(20) DEFAULT 'now_showing';

-- Update existing movies to have 'now_showing' status
UPDATE movies SET status = 'now_showing' WHERE status IS NULL;

-- Sample upcoming movies
INSERT INTO movies (title, genre, description, duration, release_date, image_path, status) VALUES
('The Future Awaits', 'Sci-Fi', 'A journey into the unknown future where humanity faces its greatest challenge yet.', 135, DATE(NOW() + INTERVAL 30 DAY), 'uploads/movies/future_awaits.jpg', 'upcoming');

INSERT INTO movies (title, genre, description, duration, release_date, image_path, status) VALUES
('Midnight Mystery', 'Thriller', 'A detective must solve a complex case before the clock strikes midnight.', 120, DATE(NOW() + INTERVAL 15 DAY), 'uploads/movies/midnight_mystery.jpg', 'upcoming');

INSERT INTO movies (title, genre, description, duration, release_date, image_path, status) VALUES
('Love in Paris', 'Romance', 'Two strangers meet in the city of love and their lives change forever.', 110, DATE(NOW() + INTERVAL 45 DAY), 'uploads/movies/love_paris.jpg', 'upcoming');

INSERT INTO movies (title, genre, description, duration, release_date, image_path, status) VALUES
('Jungle Adventure', 'Action', 'An explorer embarks on a dangerous journey through the Amazon rainforest.', 140, DATE(NOW() + INTERVAL 20 DAY), 'uploads/movies/jungle_adventure.jpg', 'upcoming');
