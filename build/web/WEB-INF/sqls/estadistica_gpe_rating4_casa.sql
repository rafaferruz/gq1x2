select clasifgpe.*, rat4_dif_prev from clasifgpe, resultados, rating4
where gpe_fecha>"2001-08-01" and 
(gpe_cto_id=res_cto_id and gpe_ronda=res_ronda and gpe_equ_id=res_equ1_id) and
(gpe_cto_id=rat4_cto_id and gpe_ronda=rat4_ronda and gpe_equ_id=rat4_equ1_id) 
order by gpe_equ_id, rat4_dif_prev, gpe_fecha