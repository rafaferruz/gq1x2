select convert((year(cha_start_date)-1976)/10,SIGNED),  convert(rat4_previous_diference/30,SIGNED) as dif, 
sum(if(rat4_score_sign='W',1,0)) as W, 
sum(if(rat4_score_sign='D',1,0)) as D, 
sum(if(rat4_score_sign='L',1,0)) as L
INTO OUTFILE '/tmp/result01.txt'
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
from scores, ratings4, championships
where rat4_cha_id=sco_cha_id and rat4_round=sco_round and rat4_team1_id=sco_team1_id and cha_id = rat4_cha_id 
group by  convert((year(cha_start_date)-1976)/10,SIGNED),dif
order by  convert((year(cha_start_date)-1976)/10,SIGNED),dif