package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.ScoreBean;
import com.gq2.domain.Classification;
import com.gq2.domain.ClassificationWonDrawnLost;
import com.gq2.domain.Rating4;
import com.gq2.domain.SuperTable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ESTHER
 */
public class MakeSuperTable {
    // JNDI name of the data source this class requires

    private java.sql.Date fechaSQL = new java.sql.Date(0);
    private List<ScoreBean> scoreList = new ArrayList<>();
    private List<Classification> roundClassificationList = new ArrayList<>();
    private List<Classification> previousRoundClassificationList = new ArrayList<>();
    private List<ClassificationWonDrawnLost> roundClassificationWDLList = new ArrayList<>();
    private List<ClassificationWonDrawnLost> previousRoundClassificationWDLList = new ArrayList<>();
    private List<Rating4> rating4List = new ArrayList<>();
    private int chaId;
    private int round;
    private int roundfinal;
    private int processingMode;

    public MakeSuperTable() {
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

    public int getRoundfinal() {
	return roundfinal;
    }

    public void setRoundfinal(int roundfinal) {
	this.roundfinal = roundfinal;
    }

    public int getProcessingMode() {
	return processingMode;
    }

    public void setProcessingMode(int processingMode) {
	this.processingMode = processingMode;
    }

    public List<ScoreBean> getScoreList() {
	return scoreList;
    }

    public void setScoreList(List<ScoreBean> scoreList) {
	this.scoreList = scoreList;
    }

    public List<Classification> getRoundClassificationList() {
	return roundClassificationList;
    }

    public void setRoundClassificationList(List<Classification> roundClassificationList) {
	this.roundClassificationList = roundClassificationList;
    }

    public List<Classification> getPreviousRoundClassificationList() {
	return previousRoundClassificationList;
    }

    public void setPreviousRoundClassificationList(List<Classification> previousRoundClassificationList) {
	this.previousRoundClassificationList = previousRoundClassificationList;
    }

    public List<ClassificationWonDrawnLost> getRoundClassificationWDLList() {
	return roundClassificationWDLList;
    }

    public void setRoundClassificationWDLList(List<ClassificationWonDrawnLost> roundClassificationWDLList) {
	this.roundClassificationWDLList = roundClassificationWDLList;
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
	deleteSuperTableCurrentRound(chaId, round);
// Se cargan las variables de colecciones con los datos de Resultados y Clasificaciones            
	initCollections(chaId, round);

	if (scoreList.size() > 0) {
	    generateSuperTableCurrentRound();
	    // Final por no existir resultados
	}
	System.out.println("Generaci�n SuperTable finalizada. Ronda " + round);

    }

    private void initCollections(int chaId, int round) {
	// Leer resultados round actual        
	setScoreList(loadRoundScores(chaId, round));
	// Leer clasificaciones de la ronda
	setRoundClassificationList(loadRoundClassification(chaId, round));
	// Leer clasificaciones de la ronda anterior
	setPreviousRoundClassificationList(loadRoundClassification(chaId, round - 1));
	// Leer clasificaciones WDL de la ronda
	setRoundClassificationWDLList(loadRoundClassificationWDL(chaId, round));
	// Leer clasificaciones WDL de la ronda anterior
	setPreviousRoundClassificationWDLList(loadRoundClassificationWDL(chaId, round - 1));
	// Leer Ratings4 de la ronda
	setRating4List(loadRoundRating4(chaId, round));
    }

    private void deleteSuperTableCurrentRound(int chaId, int round) {
// Se eliminan los registros que existan de la clasificaci�n de la round actual
	new DAOFactory().getSuperTableDAO().deleteRoundSuperTable(chaId, round);
    }

    private void generateSuperTableCurrentRound() {
	for (ScoreBean score : scoreList) {
	    SuperTable supertable;
	    supertable = fillSuperTableData(score, score.getScoTeam1Id());
	    insertSuperTableCurrentRound(supertable);
	    supertable = fillSuperTableData(score, score.getScoTeam2Id());
	    insertSuperTableCurrentRound(supertable);
	}
    }

    private SuperTable fillSuperTableData(ScoreBean score, int teamId) {
	SuperTable superTable = new SuperTable();

	fillSuperTableScore(superTable, score, teamId);
	fillSuperTableTeamRoundClassification(superTable, teamId);
	fillSuperTableTeamPreviousRoundClassification(superTable, teamId);
	fillSuperTableTeamRoundClassificationWDL(superTable, teamId);
	fillSuperTableTeamPreviousRoundClassificationWDL(superTable, teamId);
	fillSuperTableRating4Round(superTable, teamId);
	return superTable;
    }

    private void fillSuperTableScore(SuperTable superTable, ScoreBean score, int teamId) {
	superTable.setStab_id(0);
	superTable.setStab_cha_id(chaId);
	superTable.setStab_round(round);
	superTable.setStab_date(score.getScoDate());
	superTable.setStab_sco_id(score.getScoId());
	superTable.setStab_sco_team1_id(score.getScoTeam1Id());
	superTable.setStab_sco_team2_id(score.getScoTeam2Id());
	superTable.setStab_sco_score1(score.getScoScore1());
	superTable.setStab_sco_score2(score.getScoScore2());
    }

    private void fillSuperTableTeamRoundClassification(SuperTable superTable, int teamId) {
	Classification classification = getTeamRoundClassificationFromList(teamId);
	if (classification != null) {
	    superTable.setStab_cla_id(classification.getClaId());
	    superTable.setStab_cla_position(classification.getClaPosition());
	    superTable.setStab_cla_tea_id(classification.getClaTeaId());
	    superTable.setStab_cla_points(classification.getClaPoints());
	    superTable.setStab_cla_total_played_games(classification.getClaTotalPlayedGames());
	    superTable.setStab_cla_total_won_games(classification.getClaTotalWonGames());
	    superTable.setStab_cla_total_drawn_games(classification.getClaTotalDrawnGames());
	    superTable.setStab_cla_total_lost_games(classification.getClaTotalLostGames());
	    superTable.setStab_cla_total_own_goals(classification.getClaTotalOwnGoals());
	    superTable.setStab_cla_total_against_goals(classification.getClaTotalAgainstGoals());
	    superTable.setStab_cla_home_played_games(classification.getClaHomePlayedGames());
	    superTable.setStab_cla_home_won_games(classification.getClaHomeWonGames());
	    superTable.setStab_cla_home_drawn_games(classification.getClaHomeDrawnGames());
	    superTable.setStab_cla_home_lost_games(classification.getClaHomeLostGames());
	    superTable.setStab_cla_home_own_goals(classification.getClaHomeOwnGoals());
	    superTable.setStab_cla_home_against_goals(classification.getClaHomeAgainstGoals());
	    superTable.setStab_cla_out_played_games(classification.getClaOutPlayedGames());
	    superTable.setStab_cla_out_won_games(classification.getClaOutWonGames());
	    superTable.setStab_cla_out_drawn_games(classification.getClaOutDrawnGames());
	    superTable.setStab_cla_out_lost_games(classification.getClaOutLostGames());
	    superTable.setStab_cla_out_own_goals(classification.getClaOutOwnGoals());
	    superTable.setStab_cla_out_against_goals(classification.getClaOutAgainstGoals());
	    superTable.setStab_cla_rating(classification.getClaRating());
	}
    }

    private void fillSuperTableTeamPreviousRoundClassification(SuperTable superTable, int teamId) {
	Classification classification = getTeamRoundClassificationFromList(teamId);
	if (classification != null) {
	    superTable.setStab_cla_previous_id(classification.getClaId());
	    superTable.setStab_cla_previous_position(classification.getClaPosition());
	    superTable.setStab_cla_previous_tea_id(classification.getClaTeaId());
	    superTable.setStab_cla_previous_points(classification.getClaPoints());
	    superTable.setStab_cla_previous_total_played_games(classification.getClaTotalPlayedGames());
	    superTable.setStab_cla_previous_total_won_games(classification.getClaTotalWonGames());
	    superTable.setStab_cla_previous_total_drawn_games(classification.getClaTotalDrawnGames());
	    superTable.setStab_cla_previous_total_lost_games(classification.getClaTotalLostGames());
	    superTable.setStab_cla_previous_total_own_goals(classification.getClaTotalOwnGoals());
	    superTable.setStab_cla_previous_total_against_goals(classification.getClaTotalAgainstGoals());
	    superTable.setStab_cla_previous_home_played_games(classification.getClaHomePlayedGames());
	    superTable.setStab_cla_previous_home_won_games(classification.getClaHomeWonGames());
	    superTable.setStab_cla_previous_home_drawn_games(classification.getClaHomeDrawnGames());
	    superTable.setStab_cla_previous_home_lost_games(classification.getClaHomeLostGames());
	    superTable.setStab_cla_previous_home_own_goals(classification.getClaHomeOwnGoals());
	    superTable.setStab_cla_previous_home_against_goals(classification.getClaHomeAgainstGoals());
	    superTable.setStab_cla_previous_out_played_games(classification.getClaOutPlayedGames());
	    superTable.setStab_cla_previous_out_won_games(classification.getClaOutWonGames());
	    superTable.setStab_cla_previous_out_drawn_games(classification.getClaOutDrawnGames());
	    superTable.setStab_cla_previous_out_lost_games(classification.getClaOutLostGames());
	    superTable.setStab_cla_previous_out_own_goals(classification.getClaOutOwnGoals());
	    superTable.setStab_cla_previous_out_against_goals(classification.getClaOutAgainstGoals());
	    superTable.setStab_cla_previous_rating(classification.getClaRating());
	}
    }

    private void fillSuperTableTeamRoundClassificationWDL(SuperTable superTable, int teamId) {
	ClassificationWonDrawnLost classificationWDL = getTeamRoundClassificationWDLFromList(teamId);
	if (classificationWDL != null) {
	    superTable.setStab_wdl_id(classificationWDL.getWdlId());
	    superTable.setStab_wdl_position(classificationWDL.getWdlPosition());
	    superTable.setStab_wdl_tea_id(classificationWDL.getWdlTeaId());
	    superTable.setStab_wdl_PSG(classificationWDL.getWdlPSG());
	    superTable.setStab_wdl_PSE(classificationWDL.getWdlPSE());
	    superTable.setStab_wdl_PSP(classificationWDL.getWdlPSP());
	    superTable.setStab_wdl_PSNG(classificationWDL.getWdlPSNG());
	    superTable.setStab_wdl_PSNE(classificationWDL.getWdlPSNE());
	    superTable.setStab_wdl_PSNP(classificationWDL.getWdlPSNP());
	}
    }

    private void fillSuperTableTeamPreviousRoundClassificationWDL(SuperTable superTable, int teamId) {
	ClassificationWonDrawnLost classificationWDL = getTeamRoundClassificationWDLFromList(teamId);
	if (classificationWDL != null) {
	    superTable.setStab_wdl_previous_id(classificationWDL.getWdlId());
	    superTable.setStab_wdl_previous_position(classificationWDL.getWdlPosition());
	    superTable.setStab_wdl_previous_tea_id(classificationWDL.getWdlTeaId());
	    superTable.setStab_wdl_previous_PSG(classificationWDL.getWdlPSG());
	    superTable.setStab_wdl_previous_PSE(classificationWDL.getWdlPSE());
	    superTable.setStab_wdl_previous_PSP(classificationWDL.getWdlPSP());
	    superTable.setStab_wdl_previous_PSNG(classificationWDL.getWdlPSNG());
	    superTable.setStab_wdl_previous_PSNE(classificationWDL.getWdlPSNE());
	    superTable.setStab_wdl_previous_PSNP(classificationWDL.getWdlPSNP());
	}
    }

    private void fillSuperTableRating4Round(SuperTable superTable, int teamId) {
	Rating4 rating4 = getTeamRoundRating4FromList(teamId);
	if (rating4 != null) {
	    superTable.setStab_rat4_id(rating4.getRat4Id());
	    superTable.setStab_rat4_team1_id(rating4.getRat4Team1Id());
	    superTable.setStab_rat4_team1_previous(rating4.getRat4Team1Previous());
	    superTable.setStab_rat4_team2_id(rating4.getRat4Team2Id());
	    superTable.setStab_rat4_team2_previous(rating4.getRat4Team2Previous());
	    superTable.setStab_rat4_previous_diference(rating4.getRat4PreviousDiference());
	    superTable.setStab_rat4_probability_win(rating4.getRat4ProbabilityWin());
	    superTable.setStab_rat4_probability_draw(rating4.getRat4ProbabilityDraw());
	    superTable.setStab_rat4_probability_lose(rating4.getRat4ProbabilityLose());
	    superTable.setStab_rat4_score_sign(rating4.getRat4ScoreSign());
	    superTable.setStab_rat4_team1_post(rating4.getRat4Team1Post());
	    superTable.setStab_rat4_team2_post(rating4.getRat4Team2Post());
	}
    }

    private Classification getTeamRoundClassificationFromList(int teamId) {
	for (Classification classification : roundClassificationList) {
	    if (classification.getClaTeaId() == teamId) {
		return classification;
	    }
	}
	return null;
    }

    private ClassificationWonDrawnLost getTeamRoundClassificationWDLFromList(int teamId) {
	for (ClassificationWonDrawnLost classificationWDL : roundClassificationWDLList) {
	    if (classificationWDL.getWdlTeaId() == teamId) {
		return classificationWDL;
	    }
	}
	return null;
    }

    private Rating4 getTeamRoundRating4FromList(int teamId) {
	for (Rating4 rating4 : rating4List) {
	    if (rating4.getRat4Team1Id() == teamId) {
		return rating4;
	    }
	}
	return null;
    }

    private void insertSuperTableCurrentRound(SuperTable supertable) {
// Se insertan los nuevos registros de superTable a partir de la la nueva lista
	new DAOFactory().getSuperTableDAO().save(supertable);
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
}
