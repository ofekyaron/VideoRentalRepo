CREATE TABLE IF NOT EXISTS movies (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        title TEXT NOT NULL,
                        director TEXT NOT NULL,
                        releaseYear INTEGER NOT NULL,
                        genre TEXT NOT NULL,
                        description TEXT
);

CREATE TABLE IF NOT EXISTS users (
                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                       username TEXT UNIQUE NOT NULL,
                       password TEXT NOT NULL,
                       email TEXT UNIQUE NOT NULL,
                       roles TEXT NOT NULL -- This can store roles as a comma-separated string or use a separate table for roles
);
