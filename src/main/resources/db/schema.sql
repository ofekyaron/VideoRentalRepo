DROP TABLE IF EXISTS users;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    roles VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS movies;

-- Movies table
CREATE TABLE IF NOT EXISTS movies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(100) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    release_year INTEGER NOT NULL,
    description TEXT,
    available BOOLEAN NOT NULL DEFAULT TRUE
);

-- Drop the existing orders table if it exists
DROP TABLE IF EXISTS orders;

-- Create the orders table with the correct schema
CREATE TABLE orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

-- Drop the existing reviews table if it exists
DROP TABLE IF EXISTS reviews;

CREATE TABLE reviews (
                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                         user_id INTEGER NOT NULL,
                         movie_id INTEGER NOT NULL,
                         content TEXT NOT NULL,
                         rating INTEGER NOT NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES users(id),
                         FOREIGN KEY (movie_id) REFERENCES movies(id)
);

-- Insert sample data into the orders table (if needed)
INSERT INTO orders (user_id, movie_id, order_date, return_date) VALUES
(1, 1, '2024-08-01 10:00:00', '2024-08-03 15:30:00'),
(1, 3, '2024-08-05 14:00:00', NULL),
(2, 2, '2024-08-02 09:30:00', '2024-08-04 18:00:00'),
(2, 5, '2024-08-07 11:00:00', NULL);

-- Index for faster queries on user_id and movie_id
CREATE INDEX IF NOT EXISTS idx_orders_user_id ON orders(user_id);
CREATE INDEX IF NOT EXISTS idx_orders_movie_id ON orders(movie_id);
