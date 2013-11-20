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

