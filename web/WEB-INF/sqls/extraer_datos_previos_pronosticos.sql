select PRO_GPEPREV2_PSNG,PRO_RAT4_RESUL,count(PRO_GPEPREV2_PSNG),count(PRO_RAT4_RESUL) from PRONOSTICOS
WHERE PRO_RONDA > 4
group by PRO_GPEPREV2_PSNG,PRO_RAT4_RESUL
order by PRO_GPEPREV2_PSNG,PRO_RAT4_RESUL
