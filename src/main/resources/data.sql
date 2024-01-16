INSERT INTO users (username, password, email, enabled)
VALUES ('user', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'user@foundfave.online', TRUE),
       ('admin', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'admin@foundfave.online', TRUE),
       ('andre', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'andre@foundfave.online', TRUE),
       ('adam', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'adam@hotmail.com', TRUE),
       ('bruce', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'bruce@avengershq.com', TRUE),
       ('priscilla', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'priscilla@protonmail.com', TRUE),
       ('caroline', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'caroline@protonmail.com', TRUE),
       ('sandy', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'sandy@hotmail.live', TRUE),
       ('peter', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'depeters@depeters.nl', TRUE),
       ('pieter', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'pieter@uwv.nl', TRUE),
       ('ellen1970', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'ellen@uwv.nl', TRUE),
       ('mike', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'mike@tiscali.nl', TRUE),
       ('michael', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'michael@zonnet.nl', TRUE),
       ('kim', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'kim@xs4all.nl', TRUE),
       ('kees', '$2a$10$u.POz3wbmJd6OMuGkZkGlelUefX81Wv9qZ3mUKW.Q/4uNEZSiEHIm', 'kees@xs4all.nl', TRUE);

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER'),
       ('admin', 'ROLE_USER'),
       ('admin', 'ROLE_ADMIN'),
       ('andre', 'ROLE_USER'),
       ('andre', 'ROLE_ADMIN'),
       ('adam', 'ROLE_USER'),
       ('bruce', 'ROLE_USER'),
       ('priscilla', 'ROLE_USER'),
       ('caroline', 'ROLE_USER'),
       ('sandy', 'ROLE_USER'),
       ('peter', 'ROLE_USER'),
       ('pieter', 'ROLE_USER'),
       ('ellen1970', 'ROLE_USER'),
       ('mike', 'ROLE_USER'),
       ('michael', 'ROLE_USER'),
       ('kim', 'ROLE_USER'),
       ('kees', 'ROLE_USER');

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
       (1002, 'Adam', 'de Vries', 'Male', '21-01-1974', 'Image url'),
       (1003, 'Bruce', 'Li', 'Male', '16-01-2014', 'Image url'),
       (1004, 'Priscilla', 'Groothoek', 'Female', '07-01-1984', 'Image url'),
       (1005, 'Caroline', 'de Groot', 'Female', '21-01-1999', 'Image url'),
       (1006, 'Sandy', 'Kemp', 'Female', '22-01-2011', 'Image url'),
       (1007, 'Peter', 'de Groot', 'Male', '31-01-2002', 'Image url'),
       (1008, 'Pieter', 'Groot', 'Male', '14-01-1987', 'Image url'),
       (1009, 'Ellen', 'Koning', 'Female', '01-01-1983', 'Image url'),
       (1010, 'Mike', 'de Groot', 'Male', '01-01-1983', 'Image url'),
       (1011, 'Michael', 'Grootfaam', 'Male', '01-01-1983', 'Image url'),
       (1012, 'Kim', 'de Vries', 'Female', '01-01-1983', 'Image url'),
       (1013, 'Kees', 'Poort', 'Male', '01-01-1983', 'Image url');

INSERT INTO movies (movie_id, movie_title, movie_summary, movie_year_of_release, movie_image_url)
VALUES (1000, 'The Incredible Hulk', 'Bruce Banner, a scientist on the run from the U.S. Government, must find a cure for the monster he turns into whenever he loses his temper.', 2008, 'image-url-hulk'),
       (1001, 'The Avengers', 'Earths mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.', 2012, 'image-url-the-avengers'),
       (1002, 'Avengers: Age of Ultron', 'When Tony Stark and Bruce Banner try to jump-start a dormant peacekeeping program called Ultron, things go horribly wrong and it''s up to Earths mightiest heroes to stop the villainous Ultron from enacting his terrible plan.', 2015, 'image-url-age-of-ultron'),
       (1003, 'Thor: Ragnarok', 'Imprisoned on the planet Sakaar, Thor must race against time to return to Asgard and stop Ragnarök, the destruction of his world, at the hands of the powerful and ruthless villain Hela.', 2017, 'image-url-thor-ragnarok'),
       (1004, 'Avengers: Infinity War', 'The Avengers and their allies must be willing to sacrifice all in an attempt to defeat the powerful Thanos before his blitz of devastation and ruin puts an end to the universe.', 2018, 'image-url-avengers-infinity-war'),
       (1005, 'Avengers: Endgame', 'After the devastating events of Avengers: Infinity War the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos his actions and restore balance to the universe.', 2019, 'image-url-avengers-endgame'),
       (1006, 'Thor', 'The powerful but arrogant god Thor is cast out of Asgard to live amongst humans in Midgard (Earth), where he soon becomes one of their finest defenders.', 2011, 'image-url-thor'),
       (1007, 'Thor: The Dark World', 'When the Dark Elves attempt to plunge the universe into darkness, Thor must embark on a perilous and personal journey that will reunite him with doctor Jane Foster.', 2013, 'image-url-thor-dark-world'),
       (1008, 'Thor: Love and Thunder', 'Thor enlists the help of Valkyrie, Korg and ex-girlfriend Jane Foster to fight Gorr the God Butcher, who intends to make the gods extinct.', 2022, 'image-url-thor-love-thunder'),
       (1009, 'Iron Man', 'After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.', 2008, 'image-url-iron-man'),
       (1010, 'Iron Man 2', 'With the world now aware of his identity as Iron Man, Tony Stark must contend with both his declining health and a vengeful mad man with ties to his father''s legacy.', 2010, 'image-url-iron-man-2'),
       (1011, 'Iron Man 3', 'When Tony Stark''s world is torn apart by a formidable terrorist called the Mandarin, he starts an odyssey of rebuilding and retribution.', 2013, 'image-url-iron-man-3'),
       (1012, 'Captain America: Civil War', 'Political involvement in the Avengers'' affairs causes a rift between Captain America and Iron Man.', 2016, 'image-url-captain-america-civil-war'),
       (1013, 'Spider-Man: Homecoming', 'Peter Parker balances his life as an ordinary high school student in Queens with his superhero alter-ego Spider-Man, and finds himself on the trail of a new menace prowling the skies of New York City.', 2017, 'image-url-spider-man-homecoming'),
       (1014, 'Spider-Man: Far From Home', 'Following the events of Avengers: Endgame, Spider-Man must step up to take on new threats in a world that has changed forever.', 2019, 'image-url-spider-man-far-from-home'),
       (1015, 'Spider-Man: No Way Home', 'With Spider-Mans identity now revealed, Peter asks Doctor Strange for help. When a spell goes wrong, dangerous foes from other worlds start to appear, forcing Peter to discover what it truly means to be Spider-Man.', 2021, 'image-url-spider-man-no-way-home'),
       (1016, 'Spider-Man 4', 'Unknown', 0000, 'image-url-spider-man-4'),
       (1017, 'Captain America: The first avenger', 'Steve Rogers, a rejected military soldier, transforms into Captain America after taking a dose of a "Super-Soldier serum". But being Captain America comes at a price as he attempts to take down a warmonger and a terrorist organization.', 2011, 'image-url-captain-america-the-first-avenger'),
       (1018, 'Captain America: The Winter Soldier', 'As Steve Rogers struggles to embrace his role in the modern world, he teams up with a fellow Avenger and S.H.I.E.L.D agent, Black Widow, to battle a new threat from history: an assassin known as the Winter Soldier.', 2014, 'image-url-captain-america-the-winter-soldier'),
       (1019, 'Ant-Man', 'Armed with a super-suit with the astonishing ability to shrink in scale but increase in strength, cat burglar Scott Lang must embrace his inner hero and help his mentor, Dr. Hank Pym, pull off a plan that will save the world.', 2015, 'image-url-ant-man'),
       (1020, 'Captain America: Brave New World', 'Plot kept under wraps. Fourth movie in the Captain America franchise.', 2025, 'image-url-captain-america-brave-new-world'),
       (1021, 'Doctor Strange in the Multiverse of Madness', 'Doctor Strange teams up with a mysterious girl from his dreams who can travel across multiverses, to battle multiple threats which threaten to wipe out millions across the multiverse. They seek help from Wanda the Scarlet Witch, Wong and others.', 2022, 'image-url-doctor-strange-multiverse-of-madness'),
       (1022, 'Black Panther', 'TChalla, heir to the hidden but advanced kingdom of Wakanda, must step forward to lead his people into a new future and must confront a challenger from his country''s past.', 2018, 'image-url-black-panther'),
       (1023, 'Black Panther: Wakanda Forever', 'The people of Wakanda fight to protect their home from intervening world powers as they mourn the death of King TChalla.', 2022, 'image-url-black-panther-wakanda-forever'),
       (1024, 'Black Widow', 'Natasha Romanoff confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises.', 2021, 'image-url-black-widow'),
       (1025, 'Ant-Man and the Wasp', 'As Scott Lang balances being both a superhero and a father, Hope van Dyne and Dr. Hank Pym present an urgent new mission that finds the Ant-Man fighting alongside The Wasp to uncover secrets from their past.', 2018, 'image-url-ant-man-and-the-wasp'),
       (1026, 'Ant-Man and the Wasp: Quantumania', 'Scott Lang and Hope Van Dyne are dragged into the Quantum Realm, along with Hope''s parents and Scott''s daughter Cassie. Together they must find a way to escape, but what secrets is Hopes mother hiding? And who is the mysterious Kang?', 2023, 'image-url-ant-man-and-the-wasp-quantumania'),
       (1027, 'Thunderbolts', 'A group of supervillains are recruited to go on missions for the government.', 2025, 'image-url-thunderbolts'),
       (1028, 'Doctor Strange', 'While on a journey of physical and spiritual healing, a brilliant neurosurgeon is drawn into the world of the mystic arts.', 2016, 'image-url-doctor-strange'),
       (1029, 'Captain Marvel', 'Carol Danvers becomes one of the universe''s most powerful heroes when Earth is caught in the middle of a galactic war between two alien races.', 2019, 'image-url-captain-marvel'),
       (1030, 'The Marvels', 'Carol Danvers gets her powers entangled with those of Kamala Khan and Monica Rambeau, forcing them to work together to save the universe.', 2023, 'image-url-the-marvels');








