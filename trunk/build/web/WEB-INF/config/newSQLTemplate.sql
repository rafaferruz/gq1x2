--select * from classifications where cla_cha_id = 67 and cla_round = 10
--select * from classificationsWonDrawnLost where wdl_cha_id = 70
--select * from superTable where stab_cha_id = 3
--select * from prognostics where pro_cha_id = 1 order by pro_cha_id, pro_round
--select * from ratings4 where rat4_cha_id = 67


--elect * from prePools 
--SELECT pro.*, tea1.tea_name AS tea_name1, tea2.tea_name AS tea_name2 FROM prognostics AS pro, teams AS tea1, teams AS tea2  WHERE pro_cha_id = 50  AND pro_round = 2  AND tea1.tea_id = pro_sco_team1_id  AND tea2.tea_id = pro_sco_team2_id  ORDER BY pro_round
--select distinct pro_cha_id,pro_round from prognostics 


--select * from bets
--select * from scores 
--ALTER TABLE scores ADD UNIQUE INDEX sco_teams (sco_cha_id, sco_team1_id, sco_team2_id);
--delete from scores where sco_cha_id=22 and sco_round>=35
select * from championships
--select * from teams
--select * from scores where sco_cha_id >=65
--select count(*) from scores = 25340
--select count(*) from scores where sco_cha_id = 69

