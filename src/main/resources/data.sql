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
VALUES (1000, 'Hulk', 'Robert Bruce Banner', 'Mark Ruffalo', 'The incredible Hulk', 'Male', 'Exposed to heavy doses of gamma radiation, scientist Bruce Banner transforms into the mean, green rage machine called the Hulk.', 'Dr. Bruce Banner lives a life caught between the soft-spoken scientist he’s always been and the uncontrollable green monster powered by his rage.', 'hulk.jpg'),
    (1001, 'Thor', 'Thor Odinson', 'Chris Hemsworth', 'The mighty Thor', 'Male', 'Thor Odinson wields the power of the ancient Asgardians to fight evil throughout the Nine Realms and beyond.', 'The son of Odin uses his mighty abilities as the God of Thunder to protect his home Asgard and planet Earth alike.', 'thor.jpg'),
    (1002, 'Iron Man', 'Anthony Edward Stark', 'Robert Downey, Jr.', 'The brilliant Iron Man', 'Male', 'Inventor Tony Stark applies his genius for high-tech solutions to problems as Iron Man, the armored Avenger.', 'Genius. Billionaire. Philanthropist. Tony Stark''s confidence is only matched by his high-flying abilities as the hero called Iron Man.', 'iron-man.jpg'),
    (1003, 'Spider-Man', 'Peter Benjamin Parker', 'Tom Holland', 'The amazing Spider-Man', 'Male', 'With amazing spider-like abilities, teenage science whiz Peter Parker fights crime and dreams of becoming an Avenger as Spider-Man.', 'Bitten by a radioactive spider, Peter Parker’s arachnid abilities give him amazing powers he uses to help others, while his personal life continues to offer plenty of obstacles.', 'spider-man.jpg'),
    (1004, 'Captain America', 'Steve Rogers', 'Chris Evans', 'The first avenger', 'Male', 'America’s World War II Super-Soldier continues his fight in the present as an Avenger and untiring sentinel of liberty.', 'Recipient of the Super Soldier serum, World War II hero Steve Rogers fights for American ideals as one of the world’s mightiest heroes and the leader of the Avengers.', 'captain-america.jpg'),
    (1005, 'Scarlet Witch', 'Wanda Maximoff', 'Elizabeth Olsen', 'The powerful Scarlet Witch', 'Female', 'Wanda Maximoff aimed her anger and grief over loss of her parents at Tony Stark, which drove her to HYDRA—where she gained powers—which drove her further to Ultron, where she gained perspective. Seeing the error of her ways, Wanda switched sides.', 'Notably powerful, Wanda Maximoff has fought both against and with the Avengers, attempting to hone her abilities and do what she believes is right to help the world', 'scarlet-witch.jpg'),
    (1006, 'Hawkeye', 'Clinton Francis Barton', 'Jeremy Renner', 'The never missing Hawkeye', 'Male', 'A master marksman and longtime friend of Black Widow, Clint Barton serves as the Avengers’ amazing archer.', 'An expert marksman and fighter, Clint Barton puts his talents to good use by working for S.H.I.E.L.D. as a special agent. The archer known as Hawkeye also boasts a strong moral compass that at times leads him astray from his direct orders.', 'hawkeye.jpg'),
    (1007, 'Black Panther', 'T’Challa', 'Chadwick Boseman', 'The wise T’Challa', 'Male', 'As the king of the African nation of Wakanda, T’Challa protects his people as the latest in a legacy line of Black Panther warriors.', 'T’Challa is the king of the secretive and highly advanced African nation of Wakanda - as well as the powerful warrior known as the Black Panther.', 'black-panther.jpg'),
    (1008, 'Black Widow', 'Natalia Alianovna Romanoff', 'Scarlett Johansson', 'Deadly Black Widow', 'Female', 'A deadly assassin is closing in on Natasha Romanoff. Now Natasha must reunite with an unlikely group of spies from her past in order to survive and stop a lethal force from being unleashed on the world.', 'Natasha Romanoff, separated from the now-fractured Avengers, confronts the dark path she took to becoming a spy and assassin, as well as events that followed.', 'black-widow.jpg'),
    (1009, 'Ant-Man', 'Scott Edward Harris Lang', 'Paul Rudd', 'Ant-Man', 'Male', 'Thief turned hero Scott Lang uses size manipulation tech to infiltrate bases and intimidate bad guys. Whether he’s commanding an insect army or standing tall at 65 feet, Scott’s always trying to do the right thing now that he’s Ant-Man.', 'Ex-con Scott Lang finds a new lease on life, and a chance to redeem himself in the eyes of his daughter, after taking over the mantle of Ant-Man.', 'ant-man.jpg'),
    (1010, 'Winter Soldier', 'James Buchanan Barnes', 'Sebastian Stan', 'Winter Soldier', 'Male', 'Bucky Barnes was brought back from his supposed death to be a brainwashed assassin called the Winter Soldier. Reformed by his friends, he now fights alongside the Avengers.', 'A brave soldier, Barnes fights valiantly, but eventually literally falls in battle. Unfortunately, it is the evil Arnim Zola who recovers him, erases his memory and turns him into a highly-trained, but mindless assassin called the Winter Soldier.', 'winter-soldier.jpg'),
    (1011, 'Doctor Strange', 'Stephen Vincent Strange', 'Benedict Cumberbatch', 'Doctor Strange', 'Male', 'Formerly a renowned surgeon, Doctor Stephen Strange now serves as the Sorcerer Supreme—Earth’s foremost protector against magical and mystical threats.', 'Once a highly successful, yet notably egotistical, surgeon, Doctor Stephen Strange endured a terrible accident that led him to evolve in ways he could have never foreseen.', 'doctor-strange.jpg'),
    (1012, 'Captain Marvel', 'Carol Susan Jane Danvers', 'Brie Larson', 'Captain Marvel', 'Female', 'Carol Danvers becomes one of the universe''s most powerful heroes when Earth is caught in the middle of a galactic war between two alien races.', 'Rediscovering her human identity and past, Carol Danvers learned to channel her powers for good, becoming the Super Hero Captain Marvel. Now an ally to Earth''s mightiest heroes, the Avengers, she travels across the stars to give aid to those in need.', 'captain-marvel'),
    (1013, 'Nick Fury', 'Nicholas Joseph Fury', 'Samuel L. Jackson', 'Nick Fury', 'Male', 'A veteran S.H.I.E.L.D. operative, Nick Fury continues the legacy as one of the greatest super spies in the world.', 'Nick Fury leads S.H.I.E.L.D. through hell and high water to combat the forces that threaten Earth from within and without.', 'nick-fury.jpg'),
    (1014, 'Vision', 'Vision', 'Paul Bettany', 'Vision', 'Male', 'The android called Vision defies physics and fights as an Avenger with the power of density manipulation and his flawless computer brain.', 'Upon gaining sentience, Vision offered to help fight Ultron. Since then, he has been doing his best to understand and control the Mind Stone in his forehead and prove himself as an Avenger, all while learning what it means to be human.', 'vision.jpg');

INSERT INTO profiles (profile_id, first_name, last_name, gender, date_of_birth, profile_image_url)
VALUES (1000, 'André', 'de Groot', 'Male', '28-07-2000', 'Image url'),
       (1001, 'Michael', 'de Boer', 'Male', '28-01-1999', 'Image url'),
       (1002, 'André', 'de Groot', 'Male', '21-01-2000', 'Image url');

INSERT INTO movies (movie_id, movie_title)
VALUES (1001, 'The Incredible Hulk'),
       (1002, 'The Avengers'),
       (1003, 'Avengers: Age of Ultron'),
       (1004, 'Thor: Ragnarok'),
       (1005, 'Avengers: Infinity War'),
       (1006, 'Avengers: Endgame'),
       (1007, 'Thor'),
       (1008, 'Thor: The Dark World'),
       (1009, 'Thor: Love and Thunder'),
       (1010, 'Iron Man'),
       (1011, 'Iron Man 2'),
       (1012, 'Iron Man 3'),
       (1013, 'Captain America: Civil War'),
       (1014, 'Spider-Man: Homecoming'),
       (1015, 'Spider-Man: Far From Home'),
       (1016, 'Spider-Man: No Way Home'),
       (1017, 'Spider-Man 4'),
       (1018, 'Captain America: The first avenger'),
       (1019, 'Captain America: The Winter Soldier'),
       (1020, 'Ant-Man'),
       (1021, 'Captain America: Brave New World'),
       (1022, 'Doctor Strange in the Multiverse of Madness'),
       (1023, 'Black Panther'),
       (1024, 'Black Panther: Wakanda Forever'),
       (1025, 'Black Widow'),
       (1026, 'Ant-Man and the Wasp'),
       (1027, 'Ant-Man and the Wasp: Quantumania'),
       (1028, 'Thunderbolts'),
       (1029, 'Doctor Strange'),
       (1030, 'Captain Marvel'),
       (1031, 'The Marvels');








