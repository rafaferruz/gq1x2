select convert((year(cha_start_date)-1976)/10,SIGNED),  
if(rat4_previous_diference<-60,"< -60",
if(rat4_previous_diference>=-60 and rat4_previous_diference<=60,"-60 < 60","> 60")) as dif,
sum(if(rat4_score_sign='W',1,0)) as W, 
sum(if(rat4_score_sign='D',1,0)) as D, 
sum(if(rat4_score_sign='L',1,0)) as L
INTO OUTFILE '/tmp/result03.txt'
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
from scores, ratings4, championships
where rat4_cha_id=sco_cha_id and rat4_round=sco_round and rat4_team1_id=sco_team1_id and cha_id = rat4_cha_id 
group by  convert((year(cha_start_date)-1976)/10,SIGNED),dif
order by  convert((year(cha_start_date)-1976)/10,SIGNED),dif