/* Se extraen los partidos que forman la apuesta en una un coeficiente basado en puntos conseguidos casa frente a puntos
   conseguidos fuera. Se asignan 2 puntos por partido ganado y 1 punto partido empatado */
SELECT RAT, RES, count(RES)
FROM
(select 100*round(((PRO_CLAPREV1_PGC*2+PRO_CLAPREV1_PEC-PRO_CLAPREV2_PGF*2
    -PRO_CLAPREV2_PEF)/PRO_CLAPREV1_PJT),1) AS RAT,PRO_RAT4_RESUL AS RES
from PRONOSTICOS
WHERE PRO_RONDA > 4) AS T1
group by RAT, RES
ORDER BY RAT, RES
