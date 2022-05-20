INSERT INTO user (id, wallet, confirmed, email, first_name, last_name, password, role, username) values
(1,0,1,'tibi.alex@yahoo.com', 'tibi', 'alex', '$2a$10$W2isItDMcZqcNezYQC4jcuF4JwdSO94NPLMqmnr0aytelRDiJKIpG', 'ADMIN', 'TibiAlex');
INSERT INTO user (id, wallet, confirmed, email, first_name, last_name, password, role, username) values
    (2,0,1,'tibi.alex@yahoo.com', 'tibi', 'alex', '$2a$10$W2isItDMcZqcNezYQC4jcuF4JwdSO94NPLMqmnr0aytelRDiJKIpG', 'ADMIN', 'Martin');
INSERT INTO user (id, wallet, confirmed, email, first_name, last_name, password, role, username) values
    (3,0,1,'tibi.alex@yahoo.com', 'tibi', 'alex', '$2a$10$W2isItDMcZqcNezYQC4jcuF4JwdSO94NPLMqmnr0aytelRDiJKIpG', 'ADMIN', 'Stefa');

INSERT INTO sport (id, name) values
    (17, 'Football');

INSERT INTO league (id, country, name, sport_id) values
    (18, 'Spania', 'La Liga', 1);
INSERT INTO league (id, country, name, sport_id) values
    (19, 'Romania', 'Liga I', 1);
INSERT INTO league (id, country, name, sport_id) values
    (20, 'Europa', 'Liga Campionilor', 1);

INSERT INTO team (id,country, name) values
    (4,'Romania', 'FCSB');
INSERT INTO team (id,country, name) values
    (5,'Romania', 'FC U Craiova');
INSERT INTO team (id,country, name) values
    (6,'Spnia', 'FC Barcelona');
INSERT INTO team (id,country, name) values
    (7,'Spania', 'Real Madrid');
INSERT INTO team (id,country, name) values
    (8,'Romania', 'FC Rapid');
INSERT INTO team (id,country, name) values
    (9,'Spania', 'Atletico Madrid');

INSERT INTO league_teams(league_id, teams_id) values
    (19, 4);
INSERT INTO league_teams(league_id, teams_id) values
    (19, 5);
INSERT INTO league_teams(league_id, teams_id) values
    (18, 6);
INSERT INTO league_teams(league_id, teams_id) values
    (18, 7);
INSERT INTO league_teams(league_id, teams_id) values
    (19, 8);
INSERT INTO league_teams(league_id, teams_id) values
    (18, 9);

INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id, league_id) values
    (10, 'super', 1, '???', LOCALTIMESTAMP(), 4, 5, 19);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id, league_id) values
    (11, 'super', 0, '1-1', DATE_SUB(LOCALTIMESTAMP(), INTERVAL 1 DAY), 7, 8, 20);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id, league_id) values
    (12, 'super', 1, '???', DATE_ADD(LOCALTIMESTAMP(), INTERVAL 1 DAY), 5, 7, 20);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id, league_id) values
    (13, 'super', 0, '2-3', DATE_SUB(DATE_SUB(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), 9, 4, 20);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id, league_id) values
    (14, 'super', 1, '???', DATE_ADD(DATE_ADD(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), 5, 6, 20);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id, league_id) values
    (15, 'super', 0, '0-0', DATE_SUB(DATE_SUB(DATE_SUB(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), INTERVAL 1 DAY), 6, 8, 20);
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id, league_id) values
    (16, 'super', 1, '???', DATE_ADD(DATE_ADD(DATE_ADD(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), INTERVAL 1 DAY), 6, 9, 18);