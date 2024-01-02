

INSERT INTO users (username, password, email, enabled)
VALUES ('user', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'user@foundfave.online', TRUE),
       ('admin', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'admin@foundfave.online', TRUE),
       ('andre', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'andre@foundfave.online', TRUE);

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER'),
       ('admin', 'ROLE_USER'),
       ('admin', 'ROLE_ADMIN'),
       ('andre', 'ROLE_USER'),
       ('andre', 'ROLE_ADMIN');