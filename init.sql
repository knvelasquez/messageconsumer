CREATE DATABASE satellite_db;
\c satellite_db

CREATE TABLE satellite(
  id SERIAL PRIMARY KEY,
  name VARCHAR(10),
  x INTEGER,
  y INTEGER,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO satellite (name, x, y) VALUES ('kenobi', 10, 20);
INSERT INTO satellite (name, x, y) VALUES ('skywalker', 15, 25);
INSERT INTO satellite (name, x, y) VALUES ('sato', 5, 15);

CREATE TABLE message (
  id SERIAL PRIMARY KEY,
  id_satellite INTEGER,
  content TEXT,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_message_satellite FOREIGN KEY (id_satellite) REFERENCES satellite(id)
);

