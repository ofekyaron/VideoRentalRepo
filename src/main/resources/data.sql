-- Insert initial data into the movies table
INSERT INTO movies (title, director, releaseYear, genre, description) VALUES
                                                                          ('Inception', 'Christopher Nolan', 2010, 'Science Fiction', 'A mind-bending thriller about dreams within dreams.'),
                                                                          ('The Matrix', 'Wachowski Sisters', 1999, 'Action', 'A computer hacker learns about the true nature of reality and his role in the war against its controllers.'),
                                                                          ('The Shawshank Redemption', 'Frank Darabont', 1994, 'Drama', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.');

-- -- Insert initial data into the users table
-- INSERT INTO users (username, password, email, roles) VALUES ('ofek', '123', 'ofek@example.com', 'ROLE_USER');
-- INSERT INTO users (username, password, email, roles) VALUES ('matan', 'aaa', 'matan@example.com', 'ROLE_USER');
-- INSERT INTO users (username, password, email, roles) VALUES ('admin', 'password123', 'admin@example.com', 'ROLE_ADMIN');
