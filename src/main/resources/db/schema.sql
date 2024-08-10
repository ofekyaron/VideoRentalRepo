-- Drop the 'movies' table if it exists
DROP TABLE IF EXISTS movies;
-- Create a new 'movies' table

CREATE TABLE movies (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,  -- SQLite uses INTEGER for autoincrement
                        title TEXT NOT NULL,
                        release_year INTEGER NOT NULL,
                        genre TEXT NOT NULL,
                        description TEXT NOT NULL
);

-- Drop the 'users' table if it exists
DROP TABLE IF EXISTS users;

-- Create a new 'users' table
CREATE TABLE users (
                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                         username TEXT UNIQUE NOT NULL,
                         password TEXT NOT NULL,
                         email TEXT UNIQUE NOT NULL,
                         roles TEXT NOT NULL -- This can store roles as a comma-separated string or use a separate table for roles
);





