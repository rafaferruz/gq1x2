select bet_group1_errors_1, bet_group1_errors_X, bet_group1_errors_2,
 bet_group2_errors_1, bet_group2_errors_X, bet_group2_errors_2,
 bet_group3_errors_1, bet_group3_errors_X, bet_group3_errors_2,
 bet_group4_errors_1, bet_group4_errors_X, bet_group4_errors_2,
 bet_group5_errors_1, bet_group5_errors_X, bet_group5_errors_2
INTO OUTFILE '/tmp/result08.txt'
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
from bets
where bet_season >= 2006 and bet_description="Generated Authomatically"
order by bet_season, bet_order_number