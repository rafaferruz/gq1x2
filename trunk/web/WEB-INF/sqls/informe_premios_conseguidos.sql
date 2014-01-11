select hit_bet_id, het_bet_season, hit_bet_order_number, from hits LEFT JOIN awards 
on hit_bet_season = awa_season and hit_bet_order_number = awa_order_number
WHERE hit_bet_season = 2006 and hit_bet_description LIKE 'For%'
