--- !Ups
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    oauth_id VARCHAR UNIQUE NOT NULL
);
CREATE INDEX idx_oauth_id ON users(oauth_id);

CREATE TABLE tokens (
  token UUID PRIMARY KEY,
  user_id INT REFERENCES users(user_id) ON DELETE CASCADE
);

--- !Downs
DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS users;