package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.ScoreBean;
import com.gq2.domain.Championship;
import com.gq2.domain.PrePool;
import com.gq2.domain.Prognostic;
import com.gq2.tools.Const;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class MakePrePool {

    private List<ScoreBean> scoreOneList = new ArrayList();
    private List<ScoreBean> scoreTwoList = new ArrayList();
    private List<PrePool> prePoolList = new ArrayList<>();
    private List<Prognostic> prognosticOneList = new ArrayList<>();
    private List<Prognostic> prognosticTwoList = new ArrayList<>();
    private int chaId;
    private int round;
    private Championship championship;
    private int season;
    private int orderNumber;

    public MakePrePool() {
    }

    public int getChaId() {
	return chaId;
    }

    public void setChaId(int chaId) {
	this.chaId = chaId;
    }

    public Championship getChampionship() {
	return championship;
    }

    public void setChampionship(Championship championship) {
	this.championship = championship;
    }

    public int getRound() {
	return round;
    }

    public void setRound(int round) {
	this.round = round;
    }

    public int getSeason() {
	return season;
    }

    public void setSeason(int season) {
	this.season = season;
    }

    public int getOrderNumber() {
	return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
	this.orderNumber = orderNumber;
    }

    public List<ScoreBean> getScoreOneList() {
	return scoreOneList;
    }

    public void setScoreOneList(List<ScoreBean> scoreOneList) {
	this.scoreOneList = scoreOneList;
    }

    public List<ScoreBean> getScoreTwoList() {
	return scoreTwoList;
    }

    public void setScoreTwoList(List<ScoreBean> scoreTwoList) {
	this.scoreTwoList = scoreTwoList;
    }

    public List<PrePool> getPrePoolList() {
	return prePoolList;
    }

    public void setPrePoolList(List<PrePool> prePoolList) {
	this.prePoolList = prePoolList;
    }

    public List<Prognostic> getPrognosticOneList() {
	return prognosticOneList;
    }

    public void setPrognosticOneList(List<Prognostic> prognosticOneList) {
	this.prognosticOneList = prognosticOneList;
    }

    public List<Prognostic> getPrognosticTwoList() {
	return prognosticTwoList;
    }

    public void setPrognosticTwoList(List<Prognostic> prognosticTwoList) {
	this.prognosticTwoList = prognosticTwoList;
    }

    public void processRound(int chaId, int round) {
	setChaId(chaId);
	setRound(round);
	processRound();
    }

    private void processRound() {
	initSeasonAndOrderNumber(chaId, round);
	deletePrePoolsCurrentRound(season, orderNumber);
// Se cargan las variables de colecciones con los datos de Prognostics del campeonato en generacion y el siguiente            
	initCollections(chaId, round);
	generatePrePoolsCurrentRound();
	/* Se persiste la lista de objetos PrePool de la jornada */
	insertCurrentRoundPrePoolList(prePoolList);

	System.out.println("MakePrePool: Generaci�n PrePools finalizada. Ronda " + round);

    }

    private void initSeasonAndOrderNumber(int chaId, int round) {
	setChampionship(new DAOFactory().getChampionshipDAO().load(chaId));
	Calendar cal = Calendar.getInstance();
	cal.setTime(getChampionship().getChaStartDate());
	setSeason(cal.get(Calendar.YEAR));

	setOrderNumber(round);
    }

    private void initCollections(int chaId, int round) {
	// Leer resultados del campeonato de 1ª divison round actual        
	setScoreOneList(loadRoundScores(chaId, round));
	// Leer resultados del campeonato de 2ª divison round actual        
	setScoreTwoList(loadRoundScores(chaId + 1, round));
	// Leer pronosticos del campeonato de 1ª divison round actual        
	setPrognosticOneList(loadRoundPrognostic(chaId, round));
	// Leer pronosticos del campeonato de 2ª divison round actual        
	setPrognosticTwoList(loadRoundPrognostic(chaId + 1, round));
    }

    private void deletePrePoolsCurrentRound(int season, int orderNumber) {
// Se eliminan los registros que existan de objetos PrePool de la round actual
	new DAOFactory().getPrePoolDAO().deleteRoundPrePool(season, orderNumber);
    }

    private void generatePrePoolsCurrentRound() {
	/* Se generan todos los partidos de 1ª division y cuatro de 2ª division */
	/* Se generan todos los partidos de 1ª division */
	prePoolList.clear();
	for (ScoreBean score : scoreOneList) {
	    for (Prognostic prognostic : prognosticOneList) {
		if (prognostic.getPro_sco_team1_id() == score.getScoTeam1Id()) {
		    PrePool prePool = new PrePool();
		    fillPrePoolFromPrognostic(prePool, prognostic, score);
		    prePoolList.add(prePool);
		}
	    }
	}
	/* Se generan cuatro de 2ª division */
	for (ScoreBean score : scoreTwoList) {
	    if (scoreTwoList.indexOf(score) == 2
		    || scoreTwoList.indexOf(score) == 4
		    || scoreTwoList.indexOf(score) == 6
		    || scoreTwoList.indexOf(score) == 8) {
		for (Prognostic prognostic : prognosticTwoList) {
		    if (prognostic.getPro_sco_team1_id() == score.getScoTeam1Id()) {
			PrePool prePool = new PrePool();
			fillPrePoolFromPrognostic(prePool, prognostic, score);
			prePoolList.add(prePool);
		    }
		}
	    }
	}
    }

    private void insertCurrentRoundPrePoolList(List<PrePool> prePoolList) {
// Se insertan los nuevos registros de superTable a partir de la la nueva lista
	DAOFactory df = new DAOFactory();
	df.startTransaction();
	for (PrePool prePool : prePoolList) {
	    df.getPrePoolDAO().save(prePool);
	}
	df.commit();
    }

    private List<ScoreBean> loadRoundScores(int chaId, int round) {
	return new DAOFactory().getScoreDAO().loadChampionshipRoundScores(chaId, round);
    }

    private List<Prognostic> loadRoundPrognostic(int chaId, int round) {
	return new DAOFactory().getPrognosticDAO().loadPrognosticRoundList(chaId, round);
    }

    private void fillPrePoolFromPrognostic(PrePool prePool, Prognostic prognostic, ScoreBean score) {
	prePool.setPreSeason(getSeason());
	prePool.setPreOrderNumber(getOrderNumber());
	prePool.setPreDate(prognostic.getPro_date());
	prePool.setPreChaId(prognostic.getPro_cha_id());
	prePool.setPreRound(prognostic.getPro_round());
	prePool.setPreScoId(prognostic.getPro_sco_id());
	prePool.setPreScoTeam1Id(prognostic.getPro_sco_team1_id());
	prePool.setPreScoTeam2Id(prognostic.getPro_sco_team2_id());
	prePool.setPreTeaName1(score.getScoTeamName1());
	prePool.setPreTeaName2(score.getScoTeamName2());
	prePool.setPreScoScore1(prognostic.getPro_sco_score1());
	prePool.setPreScoScore2(prognostic.getPro_sco_score2());

	/* Calcula el Rating segun Puntos en un metodo estatico de PrePoolService */
	prePool.setPreRatPoints(PrePoolService.getPrePoolRatPointsFromPrognostic(prognostic).intValue());
	prePool.setPreRat4PreviousDiference(prognostic.getPro_rat4_previous_diference());

	/* Calcula los porcentajes de probabilidades de ganar, empatar, perder en un metodo estatico de PrePoolService */
	PrePoolService.setPrePoolPercentsFromPrognosticPreviousDiference(prognostic, prePool);

	/* Calcula el Prognostic en un metodo estatico de PrePoolService */
	prePool.setPrePrognostic(PrePoolService.getPrePoolPrognosticFromPercents(prePool));
	prePool.setPreRat4ScoreSign(prognostic.getPro_rat4_score_sign());
	prePool.setPreFailedPrognostic(prePool.getPrePrognostic().contains(prePool.getPreRat4ScoreSign()) ? Const.HITTED_PROGNOSTIC : Const.FAILED_PROGNOSTIC);
    }
}