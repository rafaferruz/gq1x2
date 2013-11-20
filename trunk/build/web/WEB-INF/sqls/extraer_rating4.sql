select rat4_cha_id, convert(rat4_previous_diference/10,SIGNED), rat4_score_sign,
sum(if(rat4_score_sign='W',1,0)) as W, 
sum(if(rat4_score_sign='D',1,0)) as D, 
sum(if(rat4_score_sign='L',1,0)) as L,
 rat4_date
from ratings4 as r
group by rat4_cha_id
order by rat4_date, rat4_cha_id