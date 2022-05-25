INSERT INTO user (id, wallet, confirmed, email, first_name, last_name, password, role, username) values
(1,1000,1,'tibi.alex@yahoo.com', 'tibi', 'alex', '$2a$10$W2isItDMcZqcNezYQC4jcuF4JwdSO94NPLMqmnr0aytelRDiJKIpG', 'ADMIN', 'TibiAlex');
INSERT INTO user (id, wallet, confirmed, email, first_name, last_name, password, role, username) values
    (2,1000,1,'tibi.alex@yahoo.com', 'tibi', 'alex', '$2a$10$W2isItDMcZqcNezYQC4jcuF4JwdSO94NPLMqmnr0aytelRDiJKIpG', 'ADMIN', 'Martin');
INSERT INTO user (id, wallet, confirmed, email, first_name, last_name, password, role, username) values
    (3,200,1,'tibi.alex@yahoo.com', 'tibi', 'alex', '$2a$10$W2isItDMcZqcNezYQC4jcuF4JwdSO94NPLMqmnr0aytelRDiJKIpG', 'ADMIN', 'Stefa');

INSERT INTO sport (id, name) values
    (17, 'Football');

INSERT INTO league (id, country, name, sport_id) values
    (18, 'Spania', 'La Liga', 17);
INSERT INTO league (id, country, name, sport_id) values
    (19, 'Romania', 'Liga I', 17);
INSERT INTO league (id, country, name, sport_id) values
    (20, 'Europa', 'Liga Campionilor', 17);

INSERT INTO team (id,country, name, home_league_id) values
    (4,'Romania', 'FCSB', 19);
INSERT INTO team (id,country, name, home_league_id) values
    (5,'Romania', 'FC U Craiova', 19);
INSERT INTO team (id,country, name, home_league_id) values
    (6,'Spnia', 'FC Barcelona', 18);
INSERT INTO team (id,country, name, home_league_id) values
    (7,'Spania', 'Real Madrid', 18);
INSERT INTO team (id,country, name, home_league_id) values
    (8,'Romania', 'FC Rapid', 19);
INSERT INTO team (id,country, name, home_league_id) values
    (9,'Spania', 'Atletico Madrid', 18);

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

INSERT INTO league_matches(league_id, matches_id) values
    (19, 10);
INSERT INTO league_matches(league_id, matches_id) values
    (20, 11);
INSERT INTO league_matches(league_id, matches_id) values
    (20, 12);
INSERT INTO league_matches(league_id, matches_id) values
    (20, 13);
INSERT INTO league_matches(league_id, matches_id) values
    (20, 14);
INSERT INTO league_matches(league_id, matches_id) values
    (20, 15);
INSERT INTO league_matches(league_id, matches_id) values
    (18, 16);

INSERT INTO match_entity (id, data, status, result, start_time, team1_id, team2_id, league_id, duration) values
    (10, 'super', 'going', '0-0', LOCALTIMESTAMP(), 4, 5, 19, 90);
INSERT INTO match_entity (id, data, status, result, start_time, team1_id, team2_id, league_id, duration) values
    (11, 'super', 'finished', '1-1', DATE_SUB(LOCALTIMESTAMP(), INTERVAL 1 DAY), 7, 8, 20, 90);
INSERT INTO match_entity (id, data, status, result, start_time, team1_id, team2_id, league_id, duration) values
    (12, 'super', 'upcoming', '-', DATE_ADD(LOCALTIMESTAMP(), INTERVAL 1 DAY), 5, 7, 20, 90);
INSERT INTO match_entity (id, data, status, result, start_time, team1_id, team2_id, league_id, duration) values
    (13, 'super', 'finished', '2-3', DATE_SUB(DATE_SUB(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), 9, 4, 20, 90);
INSERT INTO match_entity (id, data, status, result, start_time, team1_id, team2_id, league_id, duration) values
    (14, 'super', 'upcoming', '-', DATE_ADD(DATE_ADD(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), 5, 6, 20, 90);
INSERT INTO match_entity (id, data, status, result, start_time, team1_id, team2_id, league_id, duration) values
    (15, 'super', 'finished', '0-0', DATE_SUB(DATE_SUB(DATE_SUB(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), INTERVAL 1 DAY), 6, 8, 20, 90);
INSERT INTO match_entity (id, data, status, result, start_time, team1_id, team2_id, league_id, duration) values
    (16, 'super', 'upcoming', '-', DATE_ADD(DATE_ADD(DATE_ADD(LOCALTIMESTAMP(), INTERVAL 1 DAY), INTERVAL 1 DAY), INTERVAL 1 DAY), 6, 9, 18, 90);

INSERT INTO bet (id, bet_choice_user1, bet_choice_user2, match_id, user1_id, user2_id, amount, status, result) values
    (21, 0, 2, 10, 1, 2, 100, 'history', 1);

INSERT INTO user_bets(user_id, bets_id) values
    (1, 21);
INSERT INTO user_bets(user_id, bets_id) values
    (2, 21);