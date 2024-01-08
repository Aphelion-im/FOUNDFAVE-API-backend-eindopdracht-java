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

INSERT INTO characters (character_id, character_alias_name, character_real_name, character_actor_name, character_title, character_gender, character_summary, character_description, character_image_url)
VALUES
    (1000, 'Hulk', 'Robert Bruce Banner', 'Mark Ruffalo', 'The incredible Hulk', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1001, 'Thor', 'Thor Odinson', 'Chris Hemsworth', 'The mighty Thor', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1002, 'Iron Man', 'Anthony Edward Stark', 'Robert Downey, Jr.', 'The brilliant Iron Man', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1003, 'Spider-Man', 'Peter Benjamin Parker', 'Tom Holland', 'The amazing Spider-Man', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1004, 'Captain America', 'Samuel Thomas Wilson', 'Anthony Mackie', 'The first avenger', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1005, 'Scarlet Witch', 'Wanda Maximoff', 'Elizabeth Olsen', '?', 'Female', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1006, 'Hawk Eye', 'Clinton Francis Barton', 'Jeremy Renner', '?', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1007, 'Black Panther', 'Shuri', 'Letitia Wright', '?', 'Female', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1008, 'Black Widow', 'Natalia Alianovna Romanoff', 'Scarlett Johansson', '?', 'Female', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1009, 'Ant-Man', 'Scott Edward Harris Lang', 'Paul Rudd', '?', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1010, 'Winter Soldier', 'James Buchanan Barnes', 'Sebastian Stan', '?', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1011, 'Dr. Strange', 'Stephen Vincent Strange', 'Benedict Cumberbatch', '?', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1012, 'Captain Marvel', 'Carol Susan Jane Danvers', 'Brie Larson', '?', 'Female', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1013, 'Nick Fury', 'Nicholas Joseph Fury', 'Samuel L. Jackson', '?', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image'),
    (1014, 'Vision', 'Vision', 'Paul Bettany', '?', 'Male', 'Lorem ipsum sit amet', 'Description', 'Unknown image');