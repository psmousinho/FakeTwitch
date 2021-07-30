INSERT INTO channel(name, password) VALUES('alpha','$2a$10$UbfwqBzv.VZcrViS7pVd1.NDWvZc0DLCo6VshRJ.nEt18CaYajZC2');
INSERT INTO channel(name, password) VALUES('beta','$2a$10$UbfwqBzv.VZcrViS7pVd1.NDWvZc0DLCo6VshRJ.nEt18CaYajZC2');
INSERT INTO channel(name, password) VALUES('teta','$2a$10$UbfwqBzv.VZcrViS7pVd1.NDWvZc0DLCo6VshRJ.nEt18CaYajZC2');
INSERT INTO channel(name, password) VALUES('gama','$2a$10$UbfwqBzv.VZcrViS7pVd1.NDWvZc0DLCo6VshRJ.nEt18CaYajZC2');


INSERT INTO connection(owner_id, target_id,status) VALUES(1,2,'FOLLOW');
INSERT INTO connection(owner_id, target_id,status) VALUES(1,3,'TIER_3');

INSERT INTO category(name, genre) VALUES ('game 1', 'Action');

INSERT INTO channel_categories(followers_id, categories_id) VALUES(1,1);

INSERT INTO live_stream(title,game_id,owner_id,active) VALUES('STREAM 1', 1, 2, false);
INSERT INTO live_stream(title,game_id,owner_id,active) VALUES('STREAM 2', 1, 3, false);
INSERT INTO live_stream(title,game_id,owner_id,active) VALUES('STREAM 3', 1, 3, true);
