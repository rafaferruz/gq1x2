package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.ScoreBean;
import com.gq2.domain.ClassificationWonDrawnLost;
import com.gq2.domain.Score;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ClassificationWDLService {

    private int chaId;
    private int round;
    private List<ClassificationWonDrawnLost> classificationWDLList = new ArrayList<>();

    public ClassificationWDLService() {
    }

    public ClassificationWDLService(int chaId, int round) {
	this.chaId = chaId;
	this.round = round;
    }

    public int getChampionshipId() {
	return chaId;
    }

    public void setChampionshipId(int chaId) {
	this.chaId = chaId;
    }

    public List<ClassificationWonDrawnLost> getClassificationWDLList() {
	return classificationWDLList;
    }

    public void setClassificationWDLList(List<ClassificationWonDrawnLost> classificationWDLList) {
	this.classificationWDLList = classificationWDLList;
    }

    public int getRound() {
	return round;
    }

    public void setRound(int round) {
	this.round = round;
    }

    private List<ClassificationWonDrawnLost> getClassificationWDLRound(Integer chaId, Integer round) {
	return new DAOFactory().getClassificationWonDrawnLostDAO().loadRoundClassificationWDLList(chaId, round);
    }

    public void generateClassificationWDL(List<ScoreBean> scoreList) {
	if (!scoreList.isEmpty()) {
	    // Obtenemos un List con la clasificacion de la ronda anterior
	    setClassificationWDLList(getClassificationWDLRound(scoreList.get(0).getScoChaId(), scoreList.get(0).getScoRound() - 1));
	    for (Score score : scoreList) {
// Fase de actualizaci�n del Equipo1
		updateTeamClassificationWDL(getClassificationWDLList(), score, score.getScoTeam1Id());
// Fase de actualizaci�n del Equipo2
		updateTeamClassificationWDL(getClassificationWDLList(), score, score.getScoTeam2Id());
	    }
	   // sortClassificationWDL();
	}
    }

    private void updateTeamClassificationWDL(List<ClassificationWonDrawnLost> classificationWDLList, Score score, Integer teamId) {
	boolean notFoundTeam = true;
	for (ClassificationWonDrawnLost classificationWDL : classificationWDLList) {
	    if (teamId == classificationWDL.getWdlTeaId()) {
		calculateTeamClassificationWDL(classificationWDL, score);
		notFoundTeam = false;
		break;
	    }
	}
	if (notFoundTeam) {
	    ClassificationWonDrawnLost classificationWDL = new ClassificationWonDrawnLost();
	    classificationWDL.setWdlTeaId(teamId);
	    calculateTeamClassificationWDL(classificationWDL, score);
	    classificationWDLList.add(classificationWDL);
	}
    }

    /**
     * Actualiza un objeto Classification pasado por referencia con los datos de
     * un objeto Score
     *
     * @param classificationWDL	Objeto ClassificationWonDrawnLost que se desea
     * actualizar
     * @param score	Objeto Score que tiene el resultado de un partido para
     * actualizar la clasificacion
     */
    private void calculateTeamClassificationWDL(ClassificationWonDrawnLost classificationWDL, Score score) {

	classificationWDL.setWdlChaId(score.getScoChaId());
	classificationWDL.setWdlRound(score.getScoRound());
	classificationWDL.setWdlDate(score.getScoDate());
	classificationWDL.setWdlPosition(0);
	if (classificationWDL.getWdlTeaId() == score.getScoTeam1Id()) {
	    if (score.getScoScore1() > score.getScoScore2()) {
		// En caso de victoria en casa ...
		classificationWDL.setWdlPSG(classificationWDL.getWdlPSG() + 1);
		classificationWDL.setWdlPSE(0);
		classificationWDL.setWdlPSP(0);
		classificationWDL.setWdlPSNG(0);
		classificationWDL.setWdlPSNE(classificationWDL.getWdlPSNE() + 1);
		classificationWDL.setWdlPSNP(classificationWDL.getWdlPSNP() + 1);
	    } else if (score.getScoScore1() == score.getScoScore2()) {
		// En caso de empate en casa ...
		classificationWDL.setWdlPSG(0);
		classificationWDL.setWdlPSE(classificationWDL.getWdlPSE() + 1);
		classificationWDL.setWdlPSP(0);
		classificationWDL.setWdlPSNG(classificationWDL.getWdlPSNG() + 1);
		classificationWDL.setWdlPSNE(0);
		classificationWDL.setWdlPSNP(classificationWDL.getWdlPSNP() + 1);
	    } else if (score.getScoScore1() < score.getScoScore2()) {
		// En caso de derrota en casa ...
		classificationWDL.setWdlPSG(0);
		classificationWDL.setWdlPSE(0);
		classificationWDL.setWdlPSP(classificationWDL.getWdlPSP() + 1);
		classificationWDL.setWdlPSNG(classificationWDL.getWdlPSNG() + 1);
		classificationWDL.setWdlPSNE(classificationWDL.getWdlPSNE() + 1);
		classificationWDL.setWdlPSNP(0);
	    }
	}
	if (classificationWDL.getWdlTeaId() == score.getScoTeam2Id()) {
	    if (score.getScoScore1() < score.getScoScore2()) {
		// En caso de victoria fuera ...
		classificationWDL.setWdlPSG(classificationWDL.getWdlPSG() + 1);
		classificationWDL.setWdlPSE(0);
		classificationWDL.setWdlPSP(0);
		classificationWDL.setWdlPSNG(0);
		classificationWDL.setWdlPSNE(classificationWDL.getWdlPSNE() + 1);
		classificationWDL.setWdlPSNP(classificationWDL.getWdlPSNP() + 1);
	    } else if (score.getScoScore1() == score.getScoScore2()) {
		// En caso de empate fuera ...
		classificationWDL.setWdlPSG(0);
		classificationWDL.setWdlPSE(classificationWDL.getWdlPSE() + 1);
		classificationWDL.setWdlPSP(0);
		classificationWDL.setWdlPSNG(classificationWDL.getWdlPSNG() + 1);
		classificationWDL.setWdlPSNE(0);
		classificationWDL.setWdlPSNP(classificationWDL.getWdlPSNP() + 1);
	    } else if (score.getScoScore1() > score.getScoScore2()) {
		// En caso de derrota en casa ...
		classificationWDL.setWdlPSG(0);
		classificationWDL.setWdlPSE(0);
		classificationWDL.setWdlPSP(classificationWDL.getWdlPSP() + 1);
		classificationWDL.setWdlPSNG(classificationWDL.getWdlPSNG() + 1);
		classificationWDL.setWdlPSNE(classificationWDL.getWdlPSNE() + 1);
		classificationWDL.setWdlPSNP(0);
	    }
	}

    }

    public int insertRoundClassificatioWDLData() {
// Se insertan los nuevos registros de clasificaci�n a partir de la la nueva lista
	return new DAOFactory().getClassificationWonDrawnLostDAO().insertClassificationList(classificationWDLList);
    }

    public int deleteRoundClassificationWDLData(Integer chaId, Integer round) {
	return new DAOFactory().getClassificationWonDrawnLostDAO().deleteRoundClassificationWDL(chaId, round);
    }
}
