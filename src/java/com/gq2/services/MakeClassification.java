package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Rating4;
import com.gq2.beans.ScoreBean;
import com.gq2.domain.Championship;
import com.gq2.domain.Classification;
import com.gq2.domain.Score;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class MakeClassification {

    private int chaId;
    private int generateMode;
    private int round;
    private int lastRound;
    private List<ScoreBean> scoreList= new ArrayList();
    private List<Classification> classificationList = new ArrayList();
    private Championship championship;

    public MakeClassification() {
    }

    public MakeClassification(int chaId, int generateMode, int round, int lastRound) {
	this.scoreList = new ArrayList();
	this.chaId = chaId;
	this.generateMode = generateMode;
	this.round = round;
	this.lastRound = lastRound;
    }

    public int getChaId() {
	return chaId;
    }

    public void setChaId(int chaId) {
	this.chaId = chaId;
    }

    public int getGenerateMode() {
	return generateMode;
    }

    public void setGenerateMode(int generateMode) {
	this.generateMode = generateMode;
    }

    public int getRound() {
	return round;
    }

    public void setRound(int round) {
	this.round = round;
    }

    public int getLastRound() {
	return lastRound;
    }

    public void setLastRound(int lastRound) {
	this.lastRound = lastRound;
    }

    private void setClassificationList(List<Classification> classificationList) {
	this.classificationList = classificationList;
    }

    public List<Classification> getClassificationList() {
	return classificationList;
    }

    public Championship getChampionship() {
	return championship;
    }

    public void setChampionship(Championship championship) {
	this.championship = championship;
    }

    public void processRound(int chaId, int round)  {
	setChaId(chaId);
	setRound(round);
	processRound();
    }

    private void processRound()  {

// Se borran los registros de la classification por Rating de la round actual
	deleteRowsRating4CurrentRound(getChaId(), getRound());
// Se cargan las variables de colecciones con los datos de Resultados y Clasificaciones            
	initCollections();

	if (scoreList.size() > 0) {
// Se crea la colecci�n de la Clasificaci�n de la round actual a partir de los datos de la round anterior
	    generateClassificationCurrentRound(scoreList);
	    generateClassificationWDLCurrentRound(scoreList);
	    generateRat4CurrentRound(scoreList);
	    // Se ejecuta la persistencia de los objetos 'Classification
	    insertRoundClassificationData(scoreList.get(0).getScoChaId(), scoreList.get(0).getScoRound());
	} else {
	    // Final por no existir resultados
	}
	System.out.println("Generaci�n Clasificaci�n finalizada. Campeonato " + chaId + "   Round " + round);

    }

    /**
     * Se eliminan los registros que existan de rating4 de la round actual
     *
     * @throws SQLException
     * @throws NamingException
     * @throws Exception
     */
    private void deleteRowsRating4CurrentRound(int chaId, int round) {
	new DAOFactory().getRating4DAO().deleteRating4Round(chaId, round);
    }

    private void initCollections() {
// Leer resultados round actual        
	scoreList = new DAOFactory().getScoreDAO().loadChampionshipRoundScores(chaId, round);
	championship=new DAOFactory().getChampionshipDAO().load(chaId);
    }

    private void generateClassificationCurrentRound(List<ScoreBean> scoreList) {

	if (!scoreList.isEmpty()) {
	    generateClassification(scoreList);
	    deleteRoundClassificationData(scoreList.get(0).getScoChaId(), scoreList.get(0).getScoRound());
	    /* La persistencia de los objetos 'Classification' de la ronda actual
	    se realiza despues del calculo del nuevo rating.*/
	    // insertRoundClassificationData(scoreList.get(0).getScoChaId(), scoreList.get(0).getScoRound());
	}
    }

    private void generateClassificationWDLCurrentRound(List<ScoreBean> scoreList) {

	ClassificationWDLService classificationWDL = new ClassificationWDLService(chaId, round);
	classificationWDL.generateClassificationWDL(scoreList);
	classificationWDL.deleteRoundClassificationWDLData(scoreList.get(0).getScoChaId(), scoreList.get(0).getScoRound());
	classificationWDL.insertRoundClassificatioWDLData();
// Final de actualizacion de classificationWDL
    }

    private void generateRat4CurrentRound(List<ScoreBean> scoreList) {
	Rating4Service rat4Service = new Rating4Service(chaId, round);
	/* Se generan y se persisten los nuevos Rating4 de la ronda actual.
	 * El nuevo rating de los equipos no se ha calculado todavia.*/
	rat4Service.getRating4PreviousRoundList(scoreList);
	// Calcula los nuevos rating4
	rat4Service.generateRating4CurrentRound(scoreList);
	// Llena el campo 'rating' en cada uno de los objetos classification
	for (Classification classification: classificationList){
	    for (Rating4 rating4: rat4Service.getRating4CurrentRoundList()){
		if (rating4.getRat4Team1Id()== classification.getClaTeaId()){
		    classification.setClaRating(rating4.getRat4Team1Post());
		}
	    }
	}
	// Persiste los nuevos Rating4
	rat4Service.deleteDataRating4Round(chaId, round);
	rat4Service.insertRating4RoundData(chaId, round);

// Final de actualizacion de classification
    }

    private List<Classification> getClassificationRound(int chaId, int round) {
	return new DAOFactory().getClassificationDAO().loadRoundClassificationList(chaId, round);
    }

    private void generateClassification(List<ScoreBean> scoreList) {
	if (!scoreList.isEmpty()) {
	    // Obtenemos un List con la clasificacion de la ronda anterior
	    setClassificationList(getClassificationRound(scoreList.get(0).getScoChaId(), scoreList.get(0).getScoRound() - 1));
	    for (Score score : scoreList) {
// Fase de actualizaci�n del Equipo1
		updateTeamClassification(getClassificationList(), score, score.getScoTeam1Id());
// Fase de actualizaci�n del Equipo2
		updateTeamClassification(getClassificationList(), score, score.getScoTeam2Id());
	    }
	    sortClassification();
	}
    }

    private void updateTeamClassification(List<Classification> classificationList, Score score, int teamId) {
	boolean notFoundTeam = true;
	for (Classification classification : classificationList) {
	    if (teamId == classification.getClaTeaId()) {
		calculateTeamClassification(classification, score);
		notFoundTeam = false;
		break;
	    }
	}
	if (notFoundTeam) {
	    Classification classification = new Classification();
	    classification.setClaTeaId(teamId);
	    calculateTeamClassification(classification, score);
	    classificationList.add(classification);
	}
    }

    /**
     * Actualiza un objeto Classification pasado por referencia con los datos de
     * un objeto Score
     *
     * @param classification	Objeto Classification que se desea actualizar
     * @param score	Objeto Score que tiene el resultado de un partido para
     * actualizar la clasificacion
     */
    private void calculateTeamClassification(Classification classification, Score score) {

	classification.setClaChaId(score.getScoChaId());
	classification.setClaRound(score.getScoRound());
	classification.setClaDate(score.getScoDate());
	classification.setClaPosition(0);
	if (classification.getClaTeaId() == score.getScoTeam1Id()) {
	    // Se incrementan Partidos ganados totales y Partidos ganados en casa
	    classification.setClaTotalPlayedGames(classification.getClaTotalPlayedGames() + 1);
	    classification.setClaHomePlayedGames(classification.getClaHomePlayedGames() + 1);
	    if (score.getScoScore1() > score.getScoScore2()) {
		// En caso de victoria en casa ...
		classification.setClaPoints(classification.getClaPoints() + championship.getChaPointsWin());
		classification.setClaTotalWonGames(classification.getClaTotalWonGames() + 1);
		classification.setClaHomeWonGames(classification.getClaHomeWonGames() + 1);
	    } else if (score.getScoScore1() == score.getScoScore2()) {
		// En caso de empate en casa ...
		classification.setClaPoints(classification.getClaPoints() + championship.getChaPointsDraw());
		classification.setClaTotalDrawnGames(classification.getClaTotalDrawnGames() + 1);
		classification.setClaHomeDrawnGames(classification.getClaHomeDrawnGames() + 1);
	    } else if (score.getScoScore1() < score.getScoScore2()) {
		// En caso de derrota en casa ...
		classification.setClaPoints(classification.getClaPoints() + championship.getChaPointsLose());
		classification.setClaTotalLostGames(classification.getClaTotalLostGames() + 1);
		classification.setClaHomeLostGames(classification.getClaHomeLostGames() + 1);
	    }
// Se suman goles a favor y en contra
	    classification.setClaTotalOwnGoals(classification.getClaTotalOwnGoals() + score.getScoScore1());
	    classification.setClaHomeOwnGoals(classification.getClaHomeOwnGoals() + score.getScoScore1());
	    classification.setClaTotalAgainstGoals(classification.getClaTotalAgainstGoals() + score.getScoScore2());
	    classification.setClaHomeAgainstGoals(classification.getClaHomeAgainstGoals() + score.getScoScore2());
	}

	if (classification.getClaTeaId() == score.getScoTeam2Id()) {
	    // Se incrementan Partidos ganados totales y Partidos ganados en casa
	    classification.setClaTotalPlayedGames(classification.getClaTotalPlayedGames() + 1);
	    classification.setClaOutPlayedGames(classification.getClaOutPlayedGames() + 1);
	    if (score.getScoScore2() > score.getScoScore1()) {
		// En caso de victoria fuera ...
		classification.setClaPoints(classification.getClaPoints() + championship.getChaPointsWin());
		classification.setClaTotalWonGames(classification.getClaTotalWonGames() + 1);
		classification.setClaOutWonGames(classification.getClaOutWonGames() + 1);
	    } else if (score.getScoScore2() == score.getScoScore1()) {
		// En caso de empate fuera ...
		classification.setClaPoints(classification.getClaPoints() + championship.getChaPointsDraw());
		classification.setClaTotalDrawnGames(classification.getClaTotalDrawnGames() + 1);
		classification.setClaOutDrawnGames(classification.getClaOutDrawnGames() + 1);
	    } else if (score.getScoScore2() < score.getScoScore1()) {
		// En caso de derrota fuera ...
		classification.setClaPoints(classification.getClaPoints() + championship.getChaPointsLose());
		classification.setClaTotalLostGames(classification.getClaTotalLostGames() + 1);
		classification.setClaOutLostGames(classification.getClaOutLostGames() + 1);
	    }
// Se suman goles a favor y en contra
	    classification.setClaTotalOwnGoals(classification.getClaTotalOwnGoals() + score.getScoScore2());
	    classification.setClaOutOwnGoals(classification.getClaOutOwnGoals() + score.getScoScore2());
	    classification.setClaTotalAgainstGoals(classification.getClaTotalAgainstGoals() + score.getScoScore1());
	    classification.setClaOutAgainstGoals(classification.getClaOutAgainstGoals() + score.getScoScore1());
	}

    }

    private void deleteRoundClassificationData(int chaId, int round) {
// Se eliminan los registros que existan de la classificationicaci�n de la round actual
	new DAOFactory().getClassificationDAO().deleteRoundClassification(chaId, round);
    }

    private void insertRoundClassificationData(int chaId, int round) {
// Se insertan los nuevos registros de classificationicaci�n a partir de la la nueva lista
	int insertedRows = 0;
	if (chaId == classificationList.get(0).getClaChaId()
		&& round == classificationList.get(0).getClaRound()) {
	    insertedRows = new DAOFactory().getClassificationDAO().insertClassificationList(classificationList);
	}

    }

    private void sortClassification() {
//	Comparator comp = new Classification();
	Collections.sort(classificationList);
// Se renumera el campo CLA_POSICION seg�n la nueva ordenaci�n

	for (int i = 0; i < classificationList.size(); i++) {
	    classificationList.get(i).setClaPosition(i + 1);
	}
    }
}
