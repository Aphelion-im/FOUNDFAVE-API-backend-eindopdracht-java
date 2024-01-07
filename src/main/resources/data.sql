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

INSERT INTO contact_forms (contact_form_id, name, email, comments, time_stamp)
VALUES (1000, 'André de Groot', 'andre.de.groot@novi.nl', 'Leuke site!', '2024-01-01 14:20:16.203216'),
       (1001, 'Michael de Boer', 'michael.de.boer@novi.nl', 'Ik zou graag meer willen zien van Fantastic four', '2024-01-02 22:26:05.483236'),
       (1002, 'Irene de Visser', 'irene.de.visser@novi.nl', 'Wolverine graag!', '2024-01-03 20:30:45.404232'),
       (1003, 'Gerard de Vries', 'gerard_devries@protonmail.com', 'Vinden jullie Phase 4 ook wat minder?', '2024-01-03 21:22:02.201236'),
       (1004, 'Anton', 'anton@hotmail.nl', 'Krijgen jullie ook QuickSilver en X-men binnenkort?', '2024-01-06 22:26:05.403236');

