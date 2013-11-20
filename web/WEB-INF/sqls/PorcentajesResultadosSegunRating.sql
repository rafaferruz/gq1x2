select pro_cha_id,pro_round,
if(pro_rat4_previous_diference<-60,"a < -60",
if(pro_rat4_previous_diference>=-60 and pro_rat4_previous_diference<=60,"b -60 < 60","c > 60")) as dif,
sum(if(pro_rat4_score_sign='W',1,0)) as W, 
sum(if(pro_rat4_score_sign='D',1,0)) as D, 
sum(if(pro_rat4_score_sign='L',1,0)) as L
INTO OUTFILE '/tmp/prognostics01.txt'
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
from prognostics
where pro_cha_id >= 61 and pro_round >5 and pro_round <=38
group by pro_cha_id,dif, pro_round 
order by pro_cha_id,dif, pro_round

