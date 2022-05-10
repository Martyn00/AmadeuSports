create table if not exists bet (
                     id bigint not null auto_increment,
                     bet_choice_user1 integer,
                     bet_choice_user2 integer,
                     match_id bigint,
                     user1_id bigint,
                     user2_id bigint,
                     primary key (id)
) engine=MyISAM;

create table if not exists confirmation_token (
                                    id bigint not null auto_increment,
                                    confirmed_at datetime,
                                    created_at datetime not null,
                                    expires_at datetime not null,
                                    token varchar(255) not null,
                                    user_id bigint not null,
                                    primary key (id)
) engine=MyISAM;

create table if not exists league (
                        id bigint not null auto_increment,
                        country varchar(255),
                        name varchar(255),
                        sport_id bigint,
                        primary key (id)
) engine=MyISAM;

create table if not exists match_entity (
                              id bigint not null auto_increment,
                              data varchar(255),
                              is_upcoming bit not null,
                              result varchar(255),
                              start_time datetime,
                              team1_id bigint,
                              team2_id bigint,
                              team_id bigint,
                              league_id bigint,
                              primary key (id)
) engine=MyISAM;

create table  if not exists sport (
                       id bigint not null auto_increment,
                       name varchar(255),
                       primary key (id)
) engine=MyISAM;

create table if not exists team (
                      id bigint not null auto_increment,
                      country varchar(255),
                      name varchar(255),
                      league_id bigint,
                      primary key (id)
) engine=MyISAM;

create table if not exists user (
                      id bigint not null auto_increment,
                      wallet integer not null,
                      confirmed bit,
                      email varchar(255),
                      first_name varchar(255),
                      last_name varchar(255),
                      password varchar(255),
                      role varchar(255),
                      username varchar(255),
                      primary key (id)
) engine=MyISAM;

create table if not exists user_bet_history (
                                  user_id bigint not null,
                                  bet_history_id bigint not null,
                                  primary key (user_id, bet_history_id)
) engine=MyISAM;

create table if not exists user_favorite_leagues (
                                       user_id bigint not null,
                                       favorite_leagues_id bigint not null,
                                       primary key (user_id, favorite_leagues_id)
) engine=MyISAM;

create table if not exists user_favorite_matches (
                                       user_id bigint not null,
                                       favorite_matches_id bigint not null,
                                       primary key (user_id, favorite_matches_id)
) engine=MyISAM;

create table if not exists user_favorite_teams (
                                     user_id bigint not null,
                                     favorite_teams_id bigint not null,
                                     primary key (user_id, favorite_teams_id)
) engine=MyISAM;

alter table user_bet_history
    add constraint UK_2c6panntjkxv5w5ogsc4lxpa7 unique (bet_history_id);

alter table user_favorite_leagues
    add constraint UK_lhko7iqf4rt6q9b2sauq33cc1 unique (favorite_leagues_id);

alter table user_favorite_matches
    add constraint UK_am7rd7iu9t37a52dfw9wtc5e5 unique (favorite_matches_id);

alter table user_favorite_teams
    add constraint UK_elhl0b0fk583yu2sviv792ghr unique (favorite_teams_id);

alter table bet
    add constraint FK6okt30geuvye4va3y2dctwcs2
        foreign key (match_id)
            references match_entity (id);

alter table bet
    add constraint FK115n3uho26unht09309t5fw80
        foreign key (user1_id)
            references user (id);

alter table bet
    add constraint FKe7ucumi746eukm7rwg65qe3oc
        foreign key (user2_id)
            references user (id);

alter table confirmation_token
    add constraint FKhjrtky9wbd6lbk7mu9tuddqgn
        foreign key (user_id)
            references user (id);

alter table league
    add constraint FK11gkb0t51knp0pqxryy274iaw
        foreign key (sport_id)
            references sport (id);

alter table match_entity
    add constraint FKm9mttyiibvk7hd91y56iwt431
        foreign key (team1_id)
            references team (id);

alter table match_entity
    add constraint FK1hapjugyi8g527gjv5cs3qt8d
        foreign key (team2_id)
            references team (id);

alter table match_entity
    add constraint FKkelnpklvunnhjscnsany4aixx
        foreign key (team_id)
            references team (id);

alter table match_entity
    add constraint FKnu3auj5ptltmktbhb2n7ect24
        foreign key (league_id)
            references league (id);

alter table team
    add constraint FK9rk8716asfr76xkn99aa3uvp
        foreign key (league_id)
            references league (id);

alter table user_bet_history
    add constraint FKcqylffnm5brx1hjn48hoho6l7
        foreign key (bet_history_id)
            references bet (id);

alter table user_bet_history
    add constraint FK2xdy6lx9w9dua4c5awwbr3oa5
        foreign key (user_id)
            references user (id);

alter table user_favorite_leagues
    add constraint FK734rgdloln6ulu9xcsrsfhdvw
        foreign key (favorite_leagues_id)
            references league (id);

alter table user_favorite_leagues
    add constraint FKav9svv3lmud1dfv24j2e7w1rn
        foreign key (user_id)
            references user (id);

alter table user_favorite_matches
    add constraint FK3mbgffvf4vgnovkbtvn1837l1
        foreign key (favorite_matches_id)
            references match_entity (id);

alter table user_favorite_matches
    add constraint FKia91w4rx4vebwbtbh6pi9ax76
        foreign key (user_id)
            references user (id);

alter table user_favorite_teams
    add constraint FKetu7qq4cw9j210n9rqvsm20f5
        foreign key (favorite_teams_id)
            references team (id);

alter table user_favorite_teams
    add constraint FKs3tifbk98jtsf7nepn1eq6yg
        foreign key (user_id)
            references user (id);