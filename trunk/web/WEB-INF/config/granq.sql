CREATE TABLE IF NOT EXISTS setup (
    stp_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stp_section VARCHAR(128) DEFAULT "",
    stp_parameter VARCHAR(128) DEFAULT "",
    stp_value VARCHAR(128) DEFAULT ""

);
ALTER TABLE setup DROP INDEX stp_parameter;
ALTER TABLE setup ADD UNIQUE INDEX stp_parameter (stp_section, stp_parameter);

CREATE TABLE IF NOT EXISTS championships (
    cha_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cha_code VARCHAR(12) DEFAULT "",
    cha_status INTEGER DEFAULT 0,
    cha_description VARCHAR(60) DEFAULT "",
    cha_season VARCHAR(12) DEFAULT "",
    cha_start_date DATE,
    cha_points_win INTEGER DEFAULT 0,
    cha_points_draw INTEGER DEFAULT 0,
    cha_points_lose INTEGER DEFAULT 0,
    cha_max_teams INTEGER DEFAULT 0

);
ALTER TABLE championships DROP INDEX cha_start_date;
ALTER TABLE championships ADD INDEX (cha_start_date);
ALTER TABLE championships DROP INDEX cha_description;
ALTER TABLE championships ADD UNIQUE INDEX (cha_description);

CREATE TABLE IF NOT EXISTS players (
    pla_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pla_code VARCHAR(12) DEFAULT "",
    pla_status INTEGER DEFAULT 0,
    pla_name VARCHAR(60) DEFAULT "",
    pla_rating INTEGER DEFAULT 0

);
ALTER TABLE players DROP INDEX pla_name;
ALTER TABLE players ADD INDEX (pla_name);
ALTER TABLE players DROP INDEX pla_code;
ALTER TABLE players ADD UNIQUE INDEX (pla_code);

CREATE TABLE IF NOT EXISTS teams (
    tea_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tea_code VARCHAR(24) DEFAULT "",
    tea_status INTEGER DEFAULT 0,
    tea_name VARCHAR(60) DEFAULT "",
    tea_rating INTEGER DEFAULT 0

);
ALTER TABLE teams DROP INDEX tea_name;
ALTER TABLE teams ADD UNIQUE INDEX (tea_name);
ALTER TABLE teams DROP INDEX tea_code;
ALTER TABLE teams ADD UNIQUE INDEX (tea_code);

CREATE TABLE IF NOT EXISTS enrolledTeams (
    ent_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ent_cha_id INTEGER DEFAULT 0,
    ent_tea_id INTEGER DEFAULT 0,
    ent_number INTEGER DEFAULT 0

);
ALTER TABLE enrolledTeams DROP FOREIGN KEY FK_ent_cha_id;
ALTER TABLE enrolledTeams DROP FOREIGN KEY FK_ent_tea_id;
ALTER TABLE enrolledTeams DROP INDEX ent_tea;
ALTER TABLE enrolledTeams ADD UNIQUE INDEX ent_tea (ent_cha_id, ent_tea_id);
ALTER TABLE enrolledTeams ADD CONSTRAINT FK_ent_cha_id FOREIGN KEY (ent_cha_id) 
    REFERENCES championships (cha_id);
ALTER TABLE enrolledTeams ADD CONSTRAINT FK_ent_tea_id FOREIGN KEY (ent_tea_id) 
    REFERENCES teams (tea_id);

CREATE TABLE IF NOT EXISTS enrolledPlayers (
    enp_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    enp_cha_id INTEGER DEFAULT 0,
    enp_pla_id INTEGER DEFAULT 0,
    enp_tea_id INTEGER DEFAULT 0,
    enp_number INTEGER DEFAULT 0

);
ALTER TABLE enrolledPlayers DROP FOREIGN KEY FK_enp_cha_id;
ALTER TABLE enrolledPlayers DROP FOREIGN KEY FK_enp_pla_id;
ALTER TABLE enrolledPlayers DROP INDEX enp_pla;
ALTER TABLE enrolledPlayers ADD UNIQUE INDEX enp_pla (enp_cha_id, enp_pla_id);
ALTER TABLE enrolledPlayers ADD CONSTRAINT FK_enp_cha_id FOREIGN KEY (enp_cha_id) 
    REFERENCES championships (cha_id);
ALTER TABLE enrolledPlayers ADD CONSTRAINT FK_enp_pla_id FOREIGN KEY (enp_pla_id) 
    REFERENCES players (pla_id);

CREATE TABLE IF NOT EXISTS scores (
    sco_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sco_cha_id INTEGER DEFAULT 0,
    sco_round INTEGER DEFAULT 0,
    sco_date DATE,
    sco_team1_id INTEGER DEFAULT 0,
    sco_team2_id INTEGER DEFAULT 0,
    sco_score1 INTEGER DEFAULT 0,
    sco_score2 INTEGER DEFAULT 0

);
ALTER TABLE scores DROP FOREIGN KEY FK_sco_cha_id;
ALTER TABLE scores DROP FOREIGN KEY FK_sco_team1_id;
ALTER TABLE scores DROP FOREIGN KEY FK_sco_team2_id;
ALTER TABLE scores DROP INDEX sco;
ALTER TABLE scores ADD UNIQUE INDEX sco (sco_cha_id, sco_round, sco_id);
ALTER TABLE scores ADD UNIQUE INDEX sco_teams (sco_cha_id, sco_team1_id, sco_team2_id);
ALTER TABLE scores ADD CONSTRAINT FK_sco_cha_id FOREIGN KEY (sco_cha_id) 
    REFERENCES championships (cha_id);
ALTER TABLE scores ADD CONSTRAINT FK_sco_team1_id FOREIGN KEY (sco_team1_id) 
    REFERENCES teams (tea_id);
ALTER TABLE scores ADD CONSTRAINT FK_sco_team2_id FOREIGN KEY (sco_team2_id) 
    REFERENCES teams (tea_id);


CREATE TABLE IF NOT EXISTS classifications (
    cla_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cla_cha_id INTEGER DEFAULT 0,
    cla_round INTEGER DEFAULT 0,
    cla_tea_id INTEGER DEFAULT 0,
    cla_date DATE,
    cla_position INTEGER DEFAULT 0,
    cla_points INTEGER DEFAULT 0,
    cla_total_played_games INTEGER DEFAULT 0,      --partidos totales jugados
    cla_total_won_games INTEGER DEFAULT 0,      --partidos totales ganados
    cla_total_drawn_games INTEGER DEFAULT 0,      --partidos totales empatados
    cla_total_lost_games INTEGER DEFAULT 0,      --partidos totales perdidos
    cla_total_own_goals INTEGER DEFAULT 0,      --goles favor totales
    cla_total_against_goals INTEGER DEFAULT 0,      --goles contra totales
    cla_home_played_games INTEGER DEFAULT 0,      --partidos jugados casa
    cla_home_won_games INTEGER DEFAULT 0,      --partidos ganados casa
    cla_home_drawn_games INTEGER DEFAULT 0,      --partidos empatados casa
    cla_home_lost_games INTEGER DEFAULT 0,      --partidos perdidos casa
    cla_home_own_goals INTEGER DEFAULT 0,      --goles favor casa
    cla_home_against_goals INTEGER DEFAULT 0,      --goles contra casa
    cla_out_played_games INTEGER DEFAULT 0,      --partidos jugados fuera
    cla_out_won_games INTEGER DEFAULT 0,      --partidos ganados fuera
    cla_out_drawn_games INTEGER DEFAULT 0,      --partidos empatados fuera
    cla_out_lost_games INTEGER DEFAULT 0,      --partidos perdidos fuera
    cla_out_own_goals INTEGER DEFAULT 0,      --goles favor fuera
    cla_out_against_goals INTEGER DEFAULT 0,      --goles contra fuera
    cla_rating INTEGER DEFAULT 0
);
ALTER TABLE classifications DROP FOREIGN KEY FK_cla_cha_id;
ALTER TABLE classifications DROP FOREIGN KEY FK_cla_tea_id;
ALTER TABLE classifications DROP INDEX cla;
ALTER TABLE classifications ADD UNIQUE INDEX cla (cla_cha_id, cla_round, cla_tea_id);
ALTER TABLE classifications ADD CONSTRAINT FK_cla_cha_id FOREIGN KEY (cla_cha_id) 
    REFERENCES championships (cha_id);
ALTER TABLE classifications ADD CONSTRAINT FK_cla_tea_id FOREIGN KEY (cla_tea_id) 
    REFERENCES teams (tea_id);



CREATE TABLE IF NOT EXISTS classificationsWonDrawnLost (
    wdl_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    wdl_cha_id INTEGER DEFAULT 0,
    wdl_round INTEGER DEFAULT 0,
    wdl_date DATE,
    wdl_position INTEGER DEFAULT 0,
    wdl_tea_id INTEGER DEFAULT 0,
    wdl_PSG INTEGER DEFAULT 0,
    wdl_PSE INTEGER DEFAULT 0,
    wdl_PSP INTEGER DEFAULT 0,
    wdl_PSNG INTEGER DEFAULT 0,
    wdl_PSNE INTEGER DEFAULT 0,
    wdl_PSNP INTEGER DEFAULT 0
);
ALTER TABLE classificationsWonDrawnLost DROP INDEX wdl;
ALTER TABLE classificationsWonDrawnLost ADD UNIQUE INDEX wdl (wdl_cha_id, wdl_round, wdl_tea_id);
ALTER TABLE classificationsWonDrawnLost ADD FOREIGN KEY (wdl_cha_id) 
    REFERENCES championships (cha_id);
ALTER TABLE classificationsWonDrawnLost ADD FOREIGN KEY (wdl_tea_id) 
    REFERENCES teams (tea_id);


CREATE TABLE IF NOT EXISTS ratings4 (
    rat4_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    rat4_cha_id INTEGER DEFAULT 0,
    rat4_round INTEGER DEFAULT 0,
    rat4_date DATE,
    rat4_team1_id INTEGER DEFAULT 0,
    rat4_team1_previous INTEGER DEFAULT 0,
    rat4_team2_id INTEGER DEFAULT 0,
    rat4_team2_previous INTEGER DEFAULT 0,
    rat4_previous_diference INTEGER DEFAULT 0,
    rat4_probability_win INTEGER DEFAULT 0,
    rat4_probability_draw INTEGER DEFAULT 0,
    rat4_probability_lose INTEGER DEFAULT 0,
    rat4_score_sign VARCHAR(1) DEFAULT "",
    rat4_team1_post INTEGER DEFAULT 0,
    rat4_team2_post INTEGER DEFAULT 0
);
ALTER TABLE ratings4 DROP INDEX rat4;
ALTER TABLE ratings4 ADD UNIQUE INDEX rat4 (rat4_cha_id, rat4_round, rat4_team1_id);
ALTER TABLE ratings4 ADD UNIQUE INDEX rat4_team1_date (rat4_team1_id, rat4_date DESC);
ALTER TABLE ratings4 ADD FOREIGN KEY (rat4_cha_id) 
    REFERENCES championships (cha_id);
ALTER TABLE ratings4 ADD FOREIGN KEY (rat4_team1_id) 
    REFERENCES teams (tea_id);
ALTER TABLE ratings4 ADD FOREIGN KEY (rat4_team2_id) 
    REFERENCES teams (tea_id);

CREATE TABLE IF NOT EXISTS superTable (
    stab_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stab_cha_id INTEGER DEFAULT 0,
    stab_round INTEGER DEFAULT 0,
    stab_date DATE,
    stab_sco_id INTEGER DEFAULT 0,
    stab_sco_team1_id INTEGER DEFAULT 0,
    stab_sco_team2_id INTEGER DEFAULT 0,
    stab_sco_score1 INTEGER DEFAULT 0,
    stab_sco_score2 INTEGER DEFAULT 0,
    stab_cla_id INTEGER DEFAULT 0,
    stab_cla_tea_id INTEGER DEFAULT 0,
    stab_cla_position INTEGER DEFAULT 0,
    stab_cla_points INTEGER DEFAULT 0,
    stab_cla_total_played_games INTEGER DEFAULT 0,
    stab_cla_total_won_games INTEGER DEFAULT 0,
    stab_cla_total_drawn_games INTEGER DEFAULT 0,
    stab_cla_total_lost_games INTEGER DEFAULT 0,
    stab_cla_total_own_goals INTEGER DEFAULT 0,
    stab_cla_total_against_goals INTEGER DEFAULT 0,
    stab_cla_home_played_games INTEGER DEFAULT 0,
    stab_cla_home_won_games INTEGER DEFAULT 0,
    stab_cla_home_drawn_games INTEGER DEFAULT 0,
    stab_cla_home_lost_games INTEGER DEFAULT 0,
    stab_cla_home_own_goals INTEGER DEFAULT 0,
    stab_cla_home_against_goals INTEGER DEFAULT 0,
    stab_cla_out_played_games INTEGER DEFAULT 0,
    stab_cla_out_won_games INTEGER DEFAULT 0,
    stab_cla_out_drawn_games INTEGER DEFAULT 0,
    stab_cla_out_lost_games INTEGER DEFAULT 0,
    stab_cla_out_own_goals INTEGER DEFAULT 0,
    stab_cla_out_against_goals INTEGER DEFAULT 0,
    stab_cla_rating INTEGER DEFAULT 0,
    stab_cla_previous_id INTEGER DEFAULT 0,
    stab_cla_previous_tea_id INTEGER DEFAULT 0,
    stab_cla_previous_position INTEGER DEFAULT 0,
    stab_cla_previous_points INTEGER DEFAULT 0,
    stab_cla_previous_total_played_games INTEGER DEFAULT 0,
    stab_cla_previous_total_won_games INTEGER DEFAULT 0,
    stab_cla_previous_total_drawn_games INTEGER DEFAULT 0,
    stab_cla_previous_total_lost_games INTEGER DEFAULT 0,
    stab_cla_previous_total_own_goals INTEGER DEFAULT 0,
    stab_cla_previous_total_against_goals INTEGER DEFAULT 0,
    stab_cla_previous_home_played_games INTEGER DEFAULT 0,
    stab_cla_previous_home_won_games INTEGER DEFAULT 0,
    stab_cla_previous_home_drawn_games INTEGER DEFAULT 0,
    stab_cla_previous_home_lost_games INTEGER DEFAULT 0,
    stab_cla_previous_home_own_goals INTEGER DEFAULT 0,
    stab_cla_previous_home_against_goals INTEGER DEFAULT 0,
    stab_cla_previous_out_played_games INTEGER DEFAULT 0,
    stab_cla_previous_out_won_games INTEGER DEFAULT 0,
    stab_cla_previous_out_drawn_games INTEGER DEFAULT 0,
    stab_cla_previous_out_lost_games INTEGER DEFAULT 0,
    stab_cla_previous_out_own_goals INTEGER DEFAULT 0,
    stab_cla_previous_out_against_goals INTEGER DEFAULT 0,
    stab_cla_previous_rating INTEGER DEFAULT 0,
    stab_wdl_id INTEGER DEFAULT 0,
    stab_wdl_position INTEGER DEFAULT 0,
    stab_wdl_tea_id INTEGER DEFAULT 0,
    stab_wdl_PSG INTEGER DEFAULT 0,
    stab_wdl_PSE INTEGER DEFAULT 0,
    stab_wdl_PSP INTEGER DEFAULT 0,
    stab_wdl_PSNG INTEGER DEFAULT 0,
    stab_wdl_PSNE INTEGER DEFAULT 0,
    stab_wdl_PSNP INTEGER DEFAULT 0,
    stab_wdl_previous_id INTEGER DEFAULT 0,
    stab_wdl_previous_position INTEGER DEFAULT 0,
    stab_wdl_previous_tea_id INTEGER DEFAULT 0,
    stab_wdl_previous_PSG INTEGER DEFAULT 0,
    stab_wdl_previous_PSE INTEGER DEFAULT 0,
    stab_wdl_previous_PSP INTEGER DEFAULT 0,
    stab_wdl_previous_PSNG INTEGER DEFAULT 0,
    stab_wdl_previous_PSNE INTEGER DEFAULT 0,
    stab_wdl_previous_PSNP INTEGER DEFAULT 0,
    stab_rat4_id INTEGER DEFAULT 0,
    stab_rat4_team1_id INTEGER DEFAULT 0,
    stab_rat4_team1_previous INTEGER DEFAULT 0,
    stab_rat4_team2_id INTEGER DEFAULT 0,
    stab_rat4_team2_previous INTEGER DEFAULT 0,
    stab_rat4_previous_diference INTEGER DEFAULT 0,
    stab_rat4_probability_win INTEGER DEFAULT 0,
    stab_rat4_probability_draw INTEGER DEFAULT 0,
    stab_rat4_probability_lose INTEGER DEFAULT 0,
    stab_rat4_score_sign VARCHAR(1) DEFAULT "",
    stab_rat4_team1_post INTEGER DEFAULT 0,
    stab_rat4_team2_post INTEGER DEFAULT 0
);

ALTER TABLE superTable DROP INDEX STAB;
ALTER TABLE superTable ADD INDEX STAB (stab_cha_id, stab_round, stab_sco_team1_id, stab_sco_team2_id);
ALTER TABLE superTable ADD FOREIGN KEY (stab_cha_id) 
    REFERENCES championships (cha_id);
ALTER TABLE superTable ADD FOREIGN KEY (stab_sco_team1_id) 
    REFERENCES teams (tea_id);
ALTER TABLE superTable ADD FOREIGN KEY (stab_sco_team2_id) 
    REFERENCES teams (tea_id);
ALTER TABLE superTable ADD FOREIGN KEY (stab_cla_tea_id) 
    REFERENCES teams (tea_id);
ALTER TABLE superTable ADD FOREIGN KEY (stab_rat4_team1_id) 
    REFERENCES teams (tea_id);
ALTER TABLE superTable ADD FOREIGN KEY (stab_rat4_team2_id) 
    REFERENCES teams (tea_id);

CREATE TABLE IF NOT EXISTS prognostics (
    pro_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pro_cha_id INTEGER DEFAULT 0,
    pro_round INTEGER DEFAULT 0,
    pro_date DATE,
    pro_sco_id INTEGER DEFAULT 0,
    pro_sco_team1_id INTEGER DEFAULT 0,
    pro_sco_team2_id INTEGER DEFAULT 0,
    pro_sco_score1 INTEGER DEFAULT 0,
    pro_sco_score2 INTEGER DEFAULT 0,
    pro_cla_previous1_id INTEGER DEFAULT 0,
    pro_cla_previous1_total_played_games INTEGER DEFAULT 0,
    pro_cla_previous1_total_won_games INTEGER DEFAULT 0,
    pro_cla_previous1_total_drawn_games INTEGER DEFAULT 0,
    pro_cla_previous1_total_lost_games INTEGER DEFAULT 0,
    pro_cla_previous1_total_own_goals INTEGER DEFAULT 0,
    pro_cla_previous1_total_against_goals INTEGER DEFAULT 0,
    pro_cla_previous1_home_played_games INTEGER DEFAULT 0,
    pro_cla_previous1_home_won_games INTEGER DEFAULT 0,
    pro_cla_previous1_home_drawn_games INTEGER DEFAULT 0,
    pro_cla_previous1_home_lost_games INTEGER DEFAULT 0,
    pro_cla_previous1_home_own_goals INTEGER DEFAULT 0,
    pro_cla_previous1_home_against_goals INTEGER DEFAULT 0,
    pro_cla_previous2_id INTEGER DEFAULT 0,
    pro_cla_previous2_total_played_games INTEGER DEFAULT 0,
    pro_cla_previous2_total_won_games INTEGER DEFAULT 0,
    pro_cla_previous2_total_drawn_games INTEGER DEFAULT 0,
    pro_cla_previous2_total_lost_games INTEGER DEFAULT 0,
    pro_cla_previous2_total_own_goals INTEGER DEFAULT 0,
    pro_cla_previous2_total_against_goals INTEGER DEFAULT 0,
    pro_cla_previous2_out_played_games INTEGER DEFAULT 0,
    pro_cla_previous2_out_won_games INTEGER DEFAULT 0,
    pro_cla_previous2_out_drawn_games INTEGER DEFAULT 0,
    pro_cla_previous2_out_lost_games INTEGER DEFAULT 0,
    pro_cla_previous2_out_own_goals INTEGER DEFAULT 0,
    pro_cla_previous2_out_against_goals INTEGER DEFAULT 0,
    pro_wdl_previous1_id INTEGER DEFAULT 0,
    pro_wdl_previous1_PSG INTEGER DEFAULT 0,
    pro_wdl_previous1_PSE INTEGER DEFAULT 0,
    pro_wdl_previous1_PSP INTEGER DEFAULT 0,
    pro_wdl_previous1_PSNG INTEGER DEFAULT 0,
    pro_wdl_previous1_PSNE INTEGER DEFAULT 0,
    pro_wdl_previous1_PSNP INTEGER DEFAULT 0,
    pro_wdl_previous2_id INTEGER DEFAULT 0,
    pro_wdl_previous2_PSG INTEGER DEFAULT 0,
    pro_wdl_previous2_PSE INTEGER DEFAULT 0,
    pro_wdl_previous2_PSP INTEGER DEFAULT 0,
    pro_wdl_previous2_PSNG INTEGER DEFAULT 0,
    pro_wdl_previous2_PSNE INTEGER DEFAULT 0,
    pro_wdl_previous2_PSNP INTEGER DEFAULT 0,
    pro_rat4_id INTEGER DEFAULT 0,
    pro_rat4_team1_id INTEGER DEFAULT 0,
    pro_rat4_team1_previous INTEGER DEFAULT 0,
    pro_rat4_team2_id INTEGER DEFAULT 0,
    pro_rat4_team2_previous INTEGER DEFAULT 0,
    pro_rat4_previous_diference INTEGER DEFAULT 0,
    pro_rat4_probability_win INTEGER DEFAULT 0,
    pro_rat4_probability_draw INTEGER DEFAULT 0,
    pro_rat4_probability_lose INTEGER DEFAULT 0,
    pro_rat4_score_sign VARCHAR(1) DEFAULT "",
    pro_rat4_team1_post INTEGER DEFAULT 0,
    pro_rat4_team2_post INTEGER DEFAULT 0
);

ALTER TABLE prognostics DROP INDEX pro;
ALTER TABLE prognostics ADD INDEX pro (pro_cha_id, pro_round, pro_sco_team1_id, pro_sco_team2_id);
ALTER TABLE prognostics ADD FOREIGN KEY (pro_cha_id) 
    REFERENCES championships (cha_id);
ALTER TABLE prognostics ADD FOREIGN KEY (pro_sco_team1_id) 
    REFERENCES teams (tea_id);
ALTER TABLE prognostics ADD FOREIGN KEY (pro_sco_team2_id) 
    REFERENCES teams (tea_id);
ALTER TABLE prognostics ADD FOREIGN KEY (pro_rat4_team1_id) 
    REFERENCES teams (tea_id);
ALTER TABLE prognostics ADD FOREIGN KEY (pro_rat4_team2_id) 
    REFERENCES teams (tea_id);

CREATE TABLE IF NOT EXISTS prePools (
    pre_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pre_season INTEGER DEFAULT 0,
    pre_order_number INTEGER DEFAULT 0,
    pre_cha_id INTEGER DEFAULT 0,
    pre_round INTEGER DEFAULT 0,
    pre_date DATE,
    pre_sco_id INTEGER DEFAULT 0,
    pre_sco_team1_id INTEGER DEFAULT 0,
    pre_tea_name1 VARCHAR(60) DEFAULT "",
    pre_sco_team2_id INTEGER DEFAULT 0,
    pre_tea_name2 VARCHAR(60) DEFAULT "",
    pre_sco_score1 INTEGER DEFAULT 0,
    pre_sco_score2 INTEGER DEFAULT 0,
    pre_rat_points INTEGER DEFAULT 0,
    pre_rat4_previous_diference INTEGER DEFAULT 0,
    pre_percent_win INTEGER DEFAULT 0,
    pre_percent_draw INTEGER DEFAULT 0,
    pre_percent_lose INTEGER DEFAULT 0,
    pre_prognostic VARCHAR(3) DEFAULT "",
    pre_rat4_score_sign VARCHAR(1) DEFAULT "",
    pre_failed_prognostic INTEGER DEFAULT 0
);

ALTER TABLE prePools DROP INDEX pre;
ALTER TABLE prePools ADD INDEX pre (pre_season, pre_order_number);
ALTER TABLE prePools ADD FOREIGN KEY (pre_cha_id) 
    REFERENCES championships (cha_id);
ALTER TABLE prePools ADD FOREIGN KEY (pre_sco_team1_id) 
    REFERENCES teams (tea_id);
ALTER TABLE prePools ADD FOREIGN KEY (pre_sco_team2_id) 
    REFERENCES teams (tea_id);

CREATE TABLE bets (
    bet_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bet_season INTEGER DEFAULT 0,
    bet_order_number INTEGER DEFAULT 0,
    bet_description VARCHAR(40) DEFAULT "",
    bet_base VARCHAR(56) DEFAULT "",
    bet_group_1 VARCHAR(15) DEFAULT "",
    bet_group1_values_1 VARCHAR(35) DEFAULT "",
    bet_group1_values_X VARCHAR(35) DEFAULT "",
    bet_group1_values_2 VARCHAR(35) DEFAULT "",
    bet_group_2 VARCHAR(15) DEFAULT "",
    bet_group2_values_1 VARCHAR(35) DEFAULT "",
    bet_group2_values_X VARCHAR(35) DEFAULT "",
    bet_group2_values_2 VARCHAR(35) DEFAULT "",
    bet_group_3 VARCHAR(15) DEFAULT "",
    bet_group3_values_1 VARCHAR(35) DEFAULT "",
    bet_group3_values_X VARCHAR(35) DEFAULT "",
    bet_group3_values_2 VARCHAR(35) DEFAULT "",
    bet_group_4 VARCHAR(15) DEFAULT "",
    bet_group4_values_1 VARCHAR(35) DEFAULT "",
    bet_group4_values_X VARCHAR(35) DEFAULT "",
    bet_group4_values_2 VARCHAR(35) DEFAULT "",
    bet_group_5 VARCHAR(15) DEFAULT "",
    bet_group5_values_1 VARCHAR(35) DEFAULT "",
    bet_group5_values_X VARCHAR(35) DEFAULT "",
    bet_group5_values_2 VARCHAR(35) DEFAULT ""
);

ALTER TABLE bets DROP INDEX bet;
ALTER TABLE bets ADD INDEX bet (bet_season, bet_order_number);
ALTER TABLE bets DROP INDEX bet_description;
ALTER TABLE bets ADD INDEX bet_description (bet_description);


CREATE TABLE awards (
    awa_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    awa_season INTEGER DEFAULT 0,
    awa_order_number INTEGER DEFAULT 0,
    awa_description VARCHAR(40) DEFAULT "",
    awa_bet_price DECIMAL(12, 2) DEFAULT 0,
    awa_14_hits_amount INTEGER DEFAULT 0,
    awa_13_hits_amount INTEGER DEFAULT 0,
    awa_12_hits_amount INTEGER DEFAULT 0,
    awa_11_hits_amount INTEGER DEFAULT 0,
    awa_10_hits_amount INTEGER DEFAULT 0
);

ALTER TABLE awards DROP INDEX awa;
ALTER TABLE awards ADD INDEX awa (awa_season, awa_order_number);
