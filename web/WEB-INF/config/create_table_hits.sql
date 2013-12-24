CREATE TABLE hits (
    hit_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    hit_bet_id INTEGER DEFAULT 0,
    hit_bet_season INTEGER DEFAULT 0,
    hit_bet_order_number INTEGER DEFAULT 0,
    hit_bet_description VARCHAR(40) DEFAULT "",
    hit_reduction_name VARCHAR(60) DEFAULT "",
    hit_total_columns INTEGER DEFAULT 0,
    hit_hits_number INTEGER DEFAULT 0,
    hit_columns_number INTEGER DEFAULT 0
);

ALTER TABLE hits DROP INDEX hit;
ALTER TABLE hits ADD INDEX hit (hit_bet_season, hit_bet_order_number, hit_bet_description,
hit_reduction_name);
ALTER TABLE hits ADD FOREIGN KEY (hit_bet_id) 
    REFERENCES bets (bet_id);

