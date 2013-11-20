select rat4_cto_id, convert(rat4_dif_prev/10,SIGNED) as dif, 
sum(if(rat4_resul='G',1,0)) as G, 
sum(if(rat4_resul='E',1,0)) as E, 
sum(if(rat4_resul='P',1,0)) as P,
 rat4_fecha
from rating4 as r
group by  dif
order by  dif, rat4_fecha