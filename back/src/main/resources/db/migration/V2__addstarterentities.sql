INSERT INTO user (id, wallet, confirmed, email, first_name, last_name, password, role, username) values
(1,0,1,'tibi.alex@yahoo.com', 'tibi', 'alex', '$2a$10$W2isItDMcZqcNezYQC4jcuF4JwdSO94NPLMqmnr0aytelRDiJKIpG', 'ADMIN', 'TibiAlex');
INSERT INTO user (id, wallet, confirmed, email, first_name, last_name, password, role, username) values
    (2,0,1,'tibi.alex@yahoo.com', 'tibi', 'alex', '$2a$10$W2isItDMcZqcNezYQC4jcuF4JwdSO94NPLMqmnr0aytelRDiJKIpG', 'ADMIN', 'Martin');
INSERT INTO user (id, wallet, confirmed, email, first_name, last_name, password, role, username) values
    (3,0,1,'tibi.alex@yahoo.com', 'tibi', 'alex', '$2a$10$W2isItDMcZqcNezYQC4jcuF4JwdSO94NPLMqmnr0aytelRDiJKIpG', 'ADMIN', 'Stefa');

INSERT INTO team (id,country, name) values
    (4,'Romania', 'FCSB');
INSERT INTO team (id,country, name) values
    (5,'Romania', 'FC U Craiova');
INSERT INTO team (id,country, name) values
    (6,'Spnia', 'FC Barcelona');
INSERT INTO team (id,country, name) values
    (7,'Franta', 'PSG');
INSERT INTO team (id,country, name) values
    (8,'Ungaria', 'FC Kurtoskolack');
INSERT INTO team (id,country, name) values
    (9,'Spania', 'Atletico Madrid');

INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id ) values
    (10, 'super', 1, '???', LOCALTIMESTAMP(), 4, 5);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id ) values
    (11, 'super', 0, '1-1', DATE_SUB(LOCALTIMESTAMP(), INTERVAL 1 DAY), 7, 8);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id ) values
    (12, 'super', 1, '???', DATE_ADD(LOCALTIMESTAMP(), INTERVAL 1 DAY), 5, 7);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id ) values
    (13, 'super', 0, '2-3', DATE_SUB(DATE_SUB(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), 9, 4);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id ) values
    (14, 'super', 1, '???', DATE_ADD(DATE_ADD(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), 5, 6);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id ) values
    (15, 'super', 0, '0-0', DATE_SUB(DATE_SUB(DATE_SUB(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), INTERVAL 1 DAY), 6, 8);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id ) values
    (16, 'super', 1, '???', DATE_ADD(DATE_ADD(DATE_ADD(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), INTERVAL 1 DAY), 6, 9);

INSERT INTO league (country, name, sport_id) VALUES ('Romania', 'Liga 1', 1);
INSERT INTO league (country, name, sport_id) VALUES ('Spain', 'La Liga', 1);
INSERT INTO league (country, name, sport_id) VALUES ('England', 'EFL', 1);
INSERT INTO league (country, name, sport_id) VALUES ('Europe', 'Champions League', 1);