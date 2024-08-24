-- Sample data for movies table
INSERT INTO movies (title, genre, release_year, description, available) VALUES
('Inception', 'Sci-Fi', 2010, 'A thief who enters the dreams of others to steal secrets from their subconscious.', TRUE),
('The Shawshank Redemption', 'Drama', 1994, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', TRUE),
('The Dark Knight', 'Action', 2008, 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', TRUE),
('Pulp Fiction', 'Crime', 1994, 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', FALSE),
('Forrest Gump', 'Drama', 1994, 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.', TRUE),
('The Matrix', 'Sci-Fi', 1999, 'A computer programmer discovers that reality as he knows it is a simulation created by machines to subjugate humanity.', TRUE),
('Goodfellas', 'Crime', 1990, 'The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate.', FALSE),
('The Silence of the Lambs', 'Thriller', 1991, 'A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.', TRUE),
('Schindler''s List', 'Biography', 1993, 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', TRUE),
('The Lord of the Rings: The Fellowship of the Ring', 'Fantasy', 2001, 'A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.', FALSE);

-- Insert initial data into users table with encoded passwords
INSERT INTO users (id, username, password, email, roles) VALUES
(1, 'ofek', 'a1a2a3', 'ofek@example.com', 'ROLE_USER'),
(2, 'matan', 'password123', 'matan@example.com', 'ROLE_USER'),
(3, 'admin', 'admin2020', 'admin@example.com', 'ROLE_ADMIN');

-- Sample data for orders table
INSERT INTO orders (user_id, movie_id, order_date, return_date) VALUES
(1, 1, '2024-08-01 10:00:00', '2024-08-03 15:30:00'),
(1, 3, '2024-08-05 14:00:00', NULL),
(2, 2, '2024-08-02 09:30:00', '2024-08-04 18:00:00'),
(2, 5, '2024-08-07 11:00:00', NULL),
(1, 6, '2024-08-08 16:00:00', '2024-08-10 12:00:00'),
(2, 8, '2024-08-09 13:00:00', NULL),
(1, 9, '2024-08-11 10:30:00', '2024-08-13 19:00:00');

INSERT INTO reviews (user_id, movie_id, content, rating, created_at) VALUES
                                                                         (1, 1, 'Mind-bending plot with amazing visuals. Christopher Nolan at his best!', 5, '2024-08-02 09:15:00'),
                                                                         (2, 1, 'Confusing at times, but overall a great sci-fi experience.', 4, '2024-08-03 14:30:00'),
                                                                         (1, 2, 'A timeless classic. Morgan Freeman''s narration is perfect.', 5, '2024-08-04 11:45:00'),
                                                                         (2, 2, 'Powerful story of hope and friendship. Absolutely loved it!', 5, '2024-08-05 16:20:00'),
                                                                         (1, 3, 'Heath Ledger''s Joker is unforgettable. Best superhero movie ever.', 5, '2024-08-06 10:00:00'),
                                                                         (2, 3, 'Dark, gritty, and intense. A masterpiece of the genre.', 5, '2024-08-07 13:10:00'),
                                                                         (1, 4, 'Tarantino''s storytelling is brilliant. Love the non-linear narrative.', 5, '2024-08-08 17:30:00'),
                                                                         (2, 5, 'Tom Hanks delivers an incredible performance. Heartwarming and funny.', 4, '2024-08-09 12:45:00'),
                                                                         (1, 6, 'Revolutionary for its time. The effects still hold up today.', 4, '2024-08-10 15:00:00'),
                                                                         (2, 6, 'A sci-fi classic that makes you question reality. Loved it!', 5, '2024-08-11 09:20:00'),
                                                                         (1, 7, 'Scorsese''s direction is impeccable. A must-watch crime film.', 5, '2024-08-12 14:15:00'),
                                                                         (2, 8, 'Thrilling and chilling. Jodie Foster and Anthony Hopkins are fantastic.', 5, '2024-08-13 11:30:00'),
                                                                         (1, 9, 'Heartbreaking and powerful. Spielberg''s masterpiece.', 5, '2024-08-14 16:45:00'),
                                                                         (2, 10, 'Epic start to an amazing trilogy. The world-building is incredible.', 5, '2024-08-15 10:10:00'),
                                                                         (1, 10, 'Beautiful cinematography and a gripping story. Can''t wait to watch the sequels!', 4, '2024-08-16 13:25:00');
