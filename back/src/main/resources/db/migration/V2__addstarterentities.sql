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
INSERT INTO match_entity (id,data, is_upcoming, result, start_time, team1_id, team2_id ) values
    (6, 'super', 1, '0-3', '2022-05-11 21:00:00', 4, 5);