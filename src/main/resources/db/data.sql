-- Insert initial data into movies table
INSERT INTO movies (id, title, release_year, genre, description) VALUES
(1, 'Inception', 2010, 'Science Fiction', 'A mind-bending thriller about dreams within dreams.'),
(2, 'The Matrix', 1999, 'Action', 'A computer hacker learns about the true nature of reality and his role in the war against its controllers.'),
(3, 'The Shawshank Redemption', 1994, 'Drama', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.');

-- Insert initial data into users table with encoded passwords
INSERT INTO users (id, username, password, email, roles) VALUES
(1, 'ofek', '$2a$10$abcdefghijklmnopqrstuvwxyz123456', 'ofek@example.com', 'USER'),
(2, 'matan', '$2a$10$abcdefghijklmnopqrstuvwxyz789012', 'matan@example.com', 'USER'),
(3, 'admin', '$2a$10$abcdefghijklmnopqrstuvwxyz345678', 'admin@example.com', 'ADMIN');