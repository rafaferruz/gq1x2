mysql> select cto.cto_codigo, res.res_ronda, equ1.equ_nombre, equ2.equ_nombre, r
es.res_resul1, res.res_resul2 from resultados as res, campeonatos as cto, equipo
s as equ1, equipos as equ2 where cto.cto_id = res.res_cto_id and equ1.equ_id = r
es.res_equ1_id  and equ2.equ_id = res.res_equ2_id limit 3 INTO OUTFILE 'resultad
os_futbol.txt';