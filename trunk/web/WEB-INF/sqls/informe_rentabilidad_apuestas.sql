select awa_season, awa_order_number, hit_reduction_name, hit_hits_number,(hit_total_columns * awa_bet_price), 
IF (hit_hits_number = 14, hit_columns_number * awa_14_hits_amount, 0),
IF (hit_hits_number = 13, hit_columns_number * awa_13_hits_amount, 0),
IF (hit_hits_number = 12, hit_columns_number * awa_12_hits_amount, 0),
IF (hit_hits_number = 11, hit_columns_number * awa_11_hits_amount, 0),
IF (hit_hits_number = 10, hit_columns_number * awa_10_hits_amount, 0)
 from hits LEFT JOIN  awards ON (awa_season=hit_bet_season and awa_order_number=hit_bet_order_number)
where awa_season=hit_bet_season and awa_order_number=hit_bet_order_number and hit_reduction_name like '%11%'
ORDER BY awa_season, awa_order_number, hit_reduction_name DESC, hit_hits_number DESC
;
