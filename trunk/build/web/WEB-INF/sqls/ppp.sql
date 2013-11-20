select res_equ1_id, res_equ2_id,res_resul1,res_resul2,cla_equ_id,cla_gfc,cla_gcc,
cla_gff,cla_gcf
from resultados, campeonatos, clasificaciones
where res_fecha>="2001-08-01" and res_ronda>4
and (cto_id=res_cto_id and substr(cto_codigo,9,1)="1")
and (cla_cto_id=res_cto_id and cla_fecha=res_fecha and 
(cla_equ_id=res_equ1_id or cla_equ_id=res_equ2_id))
order by res_fecha, res_id