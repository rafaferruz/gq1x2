package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.ScoreBean;
import com.gq2.domain.Classification;
import com.gq2.domain.ClassificationWonDrawnLost;
import com.gq2.domain.Prognostic;
import com.gq2.domain.Rating4;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class MakePrognostic {

    // JNDI name of the data source this class requires
    private List<ScoreBean> scoreList = new ArrayList();
    private List<Classification> previousRoundClassificationList = new ArrayList<>();
    private List<ClassificationWonDrawnLost> previousRoundClassificationWDLList = new ArrayList<>();
    private List<Rating4> rating4List = new ArrayList<>();
    private int chaId;
    private int round;

    public MakePrognostic() {
    }

    public int getChaId() {
	return chaId;
    }

    public void setChaId(int chaId) {
	this.chaId = chaId;
    }

    public int getRound() {
	return round;
    }

    public void setRound(int round) {
	this.round = round;
    }

    public List<ScoreBean> getScoreList() {
	return scoreList;
    }

    public void setScoreList(List<ScoreBean> scoreList) {
	this.scoreList = scoreList;
    }

    public List<Classification> getPreviousRoundClassificationList() {
	return previousRoundClassificationList;
    }

    public void setPreviousRoundClassificationList(List<Classification> previousRoundClassificationList) {
	this.previousRoundClassificationList = previousRoundClassificationList;
    }

    public List<ClassificationWonDrawnLost> getPreviousRoundClassificationWDLList() {
	return previousRoundClassificationWDLList;
    }

    public void setPreviousRoundClassificationWDLList(List<ClassificationWonDrawnLost> previousRoundClassificationWDLList) {
	this.previousRoundClassificationWDLList = previousRoundClassificationWDLList;
    }

    public List<Rating4> getRating4List() {
	return rating4List;
    }

    public void setRating4List(List<Rating4> rating4List) {
	this.rating4List = rating4List;
    }

    public void processRound(int chaId, int round) {
	setChaId(chaId);
	setRound(round);
	processRound();
    }

    private void processRound() {
	deletePrognosticsCurrentRound(chaId, round);
// Se cargan las variables de colecciones con los datos de Resultados y Clasificaciones            
	initCollections(chaId, round);

	if (scoreList.size() > 0) {
	    generatePrognosticsCurrentRound();
	    // Final por no existir resultados
	}
	System.out.println("MakePrognostic: Generaci�n Prognostics finalizada. Ronda " + round);

    }

    private void initCollections(int chaId, int round) {
	// Leer resultados round actual        
	setScoreList(loadRoundScores(chaId, round));
	// Leer clasificaciones de la ronda anterior
	setPreviousRoundClassificationList(loadRoundClassification(chaId, round - 1));
	// Leer clasificaciones WDL de la ronda
	setPreviousRoundClassificationWDLList(loadRoundClassificationWDL(chaId, round - 1));
	// Leer Ratings4 de la ronda
	setRating4List(loadRoundRating4(chaId, round));
    }

    private void deletePrognosticsCurrentRound(int chaId, int round) {
// Se eliminan los registros que existan de la clasificaci�n de la round actual
	new DAOFactory().getPrognosticDAO().deleteRoundPrognostic(chaId, round);
    }

    private void generatePrognosticsCurrentRound() {
	for (ScoreBean score : scoreList) {
	    Prognostic prognostic = new Prognostic();
	    fillPrognosticScore(prognostic, score);
	    fillPrognosticData(prognostic, score.getScoTeam1Id(), score.getScoTeam2Id());
	    insertPrognosticCurrentRound(prognostic);
	}
    }

    private Prognostic fillPrognosticData(Prognostic prognostic, int team1Id, int team2Id) {

	fillPrognosticTeam1PreviousRoundClassification(prognostic, team1Id);
	fillPrognosticTeam2PreviousRoundClassification(prognostic, team2Id);
	fillPrognosticTeam1PreviousRoundClassificationWDL(prognostic, team1Id);
	fillPrognosticTeam2PreviousRoundClassificationWDL(prognostic, team2Id);
	fillPrognosticRating4Round(prognostic, team1Id);
	return prognostic;
    }

    private void fillPrognosticScore(Prognostic prognostic, ScoreBean score) {
	prognostic.setPro_id(0);
	prognostic.setPro_cha_id(chaId);
	prognostic.setPro_round(round);
	prognostic.setPro_date(score.getScoDate());
	prognostic.setPro_sco_id(score.getScoId());
	prognostic.setPro_sco_team1_id(score.getScoTeam1Id());
	prognostic.setPro_sco_team2_id(score.getScoTeam2Id());
	prognostic.setPro_sco_score1(score.getScoScore1());
	prognostic.setPro_sco_score2(score.getScoScore2());
    }

    private void insertPrognosticCurrentRound(Prognostic prognostic) {
// Se insertan los nuevos registros de superTable a partir de la la nueva lista
	new DAOFactory().getPrognosticDAO().save(prognostic);
    }

    private List<ScoreBean> loadRoundScores(int chaId, int round) {
	return new DAOFactory().getScoreDAO().loadChampionshipRoundScores(chaId, round);
    }

    private List<Classification> loadRoundClassification(int chaId, int round) {
	return new DAOFactory().getClassificationDAO().loadRoundClassificationList(chaId, round);
    }

    private List<ClassificationWonDrawnLost> loadRoundClassificationWDL(int chaId, int round) {
	return new DAOFactory().getClassificationWonDrawnLostDAO().loadRoundClassificationWDLList(chaId, round);
    }

    private List<Rating4> loadRoundRating4(int chaId, int round) {
	return new DAOFactory().getRating4DAO().loadAllRating4Round(chaId, round);
    }

    private void fillPrognosticTeam1PreviousRoundClassification(Prognostic prognostic, int teamId) {
	Classification classification = getTeamPreviousRoundClassificationFromList(teamId);
	if (classification != null) {
	    prognostic.setPro_cla_previous1_id(classification.getClaId());
	    prognostic.setPro_cla_previous1_total_played_games(classification.getClaTotalPlayedGames());
	    prognostic.setPro_cla_previous1_total_won_games(classification.getClaTotalWonGames());
	    prognostic.setPro_cla_previous1_total_drawn_games(classification.getClaTotalDrawnGames());
	    prognostic.setPro_cla_previous1_total_lost_games(classification.getClaTotalLostGames());
	    prognostic.setPro_cla_previous1_total_own_goals(classification.getClaTotalOwnGoals());
	    prognostic.setPro_cla_previous1_total_against_goals(classification.getClaTotalAgainstGoals());
	    prognostic.setPro_cla_previous1_home_played_games(classification.getClaHomePlayedGames());
	    prognostic.setPro_cla_previous1_home_won_games(classification.getClaHomeWonGames());
	    prognostic.setPro_cla_previous1_home_drawn_games(classification.getClaHomeDrawnGames());
	    prognostic.setPro_cla_previous1_home_lost_games(classification.getClaHomeLostGames());
	    prognostic.setPro_cla_previous1_home_own_goals(classification.getClaHomeOwnGoals());
	    prognostic.setPro_cla_previous1_home_against_goals(classification.getClaHomeAgainstGoals());
	}
    }

    private void fillPrognosticTeam2PreviousRoundClassification(Prognostic prognostic, int teamId) {
	Classification classification = getTeamPreviousRoundClassificationFromList(teamId);
	if (classification != null) {
	    prognostic.setPro_cla_previous2_id(classification.getClaId());
	    prognostic.setPro_cla_previous2_total_played_games(classification.getClaTotalPlayedGames());
	    prognostic.setPro_cla_previous2_total_won_games(classification.getClaTotalWonGames());
	    prognostic.setPro_cla_previous2_total_drawn_games(classification.getClaTotalDrawnGames());
	    prognostic.setPro_cla_previous2_total_lost_games(classification.getClaTotalLostGames());
	    prognostic.setPro_cla_previous2_total_own_goals(classification.getClaTotalOwnGoals());
	    prognostic.setPro_cla_previous2_total_against_goals(classification.getClaTotalAgainstGoals());
	    prognostic.setPro_cla_previous2_out_played_games(classification.getClaOutPlayedGames());
	    prognostic.setPro_cla_previous2_out_won_games(classification.getClaOutWonGames());
	    prognostic.setPro_cla_previous2_out_drawn_games(classification.getClaOutDrawnGames());
	    prognostic.setPro_cla_previous2_out_lost_games(classification.getClaOutLostGames());
	    prognostic.setPro_cla_previous2_out_own_goals(classification.getClaOutOwnGoals());
	    prognostic.setPro_cla_previous2_out_against_goals(classification.getClaOutAgainstGoals());
	}
    }

    private Classification getTeamPreviousRoundClassificationFromList(int teamId) {
	for (Classification classification : previousRoundClassificationList) {
	    if (classification.getClaTeaId() == teamId) {
		return classification;
	    }
	}
	return null;
    }

    private void fillPrognosticTeam1PreviousRoundClassificationWDL(Prognostic prognostic, int teamId) {
	ClassificationWonDrawnLost classificationWDL = getTeamPreviousRoundClassificationWDLFromList(teamId);
	if (classificationWDL != null) {
	    prognostic.setPro_wdl_previous1_id(classificationWDL.getWdlId());
	    prognostic.setPro_wdl_previous1_PSG(classificationWDL.getWdlPSG());
	    prognostic.setPro_wdl_previous1_PSE(classificationWDL.getWdlPSE());
	    prognostic.setPro_wdl_previous1_PSP(classificationWDL.getWdlPSP());
	    prognostic.setPro_wdl_previous1_PSNG(classificationWDL.getWdlPSNG());
	    prognostic.setPro_wdl_previous1_PSNE(classificationWDL.getWdlPSNE());
	    prognostic.setPro_wdl_previous1_PSNP(classificationWDL.getWdlPSNP());
	}
    }

    private void fillPrognosticTeam2PreviousRoundClassificationWDL(Prognostic prognostic, int teamId) {
	ClassificationWonDrawnLost classificationWDL = getTeamPreviousRoundClassificationWDLFromList(teamId);
	if (classificationWDL != null) {
	    prognostic.setPro_wdl_previous2_id(classificationWDL.getWdlId());
	    prognostic.setPro_wdl_previous2_PSG(classificationWDL.getWdlPSG());
	    prognostic.setPro_wdl_previous2_PSE(classificationWDL.getWdlPSE());
	    prognostic.setPro_wdl_previous2_PSP(classificationWDL.getWdlPSP());
	    prognostic.setPro_wdl_previous2_PSNG(classificationWDL.getWdlPSNG());
	    prognostic.setPro_wdl_previous2_PSNE(classificationWDL.getWdlPSNE());
	    prognostic.setPro_wdl_previous2_PSNP(classificationWDL.getWdlPSNP());
	}
    }

    private ClassificationWonDrawnLost getTeamPreviousRoundClassificationWDLFromList(int teamId) {
	for (ClassificationWonDrawnLost classificationWDL : previousRoundClassificationWDLList) {
	    if (classificationWDL.getWdlTeaId() == teamId) {
		return classificationWDL;
	    }
	}
	return null;
    }

    private void fillPrognosticRating4Round(Prognostic prognostic, int teamId) {
	Rating4 rating4 = getTeamRoundRating4FromList(teamId);
	if (rating4 != null) {
	    prognostic.setPro_rat4_id(rating4.getRat4Id());
	    prognostic.setPro_rat4_team1_id(rating4.getRat4Team1Id());
	    prognostic.setPro_rat4_team1_previous(rating4.getRat4Team1Previous());
	    prognostic.setPro_rat4_team2_id(rating4.getRat4Team2Id());
	    prognostic.setPro_rat4_team2_previous(rating4.getRat4Team2Previous());
	    prognostic.setPro_rat4_previous_diference(rating4.getRat4PreviousDiference());
	    prognostic.setPro_rat4_probability_win(rating4.getRat4ProbabilityWin());
	    prognostic.setPro_rat4_probability_draw(rating4.getRat4ProbabilityDraw());
	    prognostic.setPro_rat4_probability_lose(rating4.getRat4ProbabilityLose());
	    prognostic.setPro_rat4_score_sign(rating4.getRat4ScoreSign());
	    prognostic.setPro_rat4_team1_post(rating4.getRat4Team1Post());
	    prognostic.setPro_rat4_team2_post(rating4.getRat4Team2Post());
	}
    }

    private Rating4 getTeamRoundRating4FromList(int teamId) {
	for (Rating4 rating4 : rating4List) {
	    if (rating4.getRat4Team1Id() == teamId) {
		return rating4;
	    }
	}
	return null;
    }
}