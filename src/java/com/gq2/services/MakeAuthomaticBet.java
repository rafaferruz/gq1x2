package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.BetBean;
import com.gq2.domain.Championship;
import com.gq2.tools.Const;
import java.util.Calendar;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class MakeAuthomaticBet {

    private int chaId;
    private int round;
    private Championship championship;
    private int season;

    public MakeAuthomaticBet() {
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

    public Championship getChampionship() {
	return championship;
    }

    public void setChampionship(Championship championship) {
	this.championship = championship;
    }

    public int getSeason() {
	return season;
    }

    public void setSeason(int season) {
	this.season = season;
    }

    public void processRound(int chaId, int round) {
	setChaId(chaId);
	setRound(round);
	processRound();
    }

    private void processRound() {
	initCollections(chaId, round);
	deleteAuthomaticBetsCurrentRound(season, round);
	/*
	 * Se crea la nueva apuesta
	 */
	BetBean authomaticBet = new BetBean();
	authomaticBet.setBetSeason(season);
	authomaticBet.setBetOrderNumber(round);
	authomaticBet.setBetDescription(Const.GENERATED_AUTHOMATICALLY_TEXT);
	/* Se completa la nueva apuesta con las lineas que le correspondan desde PrePool */
	authomaticBet.editBet(); // Desde editBet se llama al servicio para completar las lineas
	/* Se generan las apuestas automaticas y se persisten en la base de datos */
	authomaticBet.generateBets();

	System.out.println("MakeBet: Generaci�n Bets Automaticas finalizada. Ronda " + round);

    }

    private void initCollections(int chaId, int round) {
	setChampionship(new DAOFactory().getChampionshipDAO().load(chaId));
	Calendar cal = Calendar.getInstance();
	cal.setTime(getChampionship().getChaStartDate());
	setSeason(cal.get(Calendar.YEAR));
    }

    private void deleteAuthomaticBetsCurrentRound(int season, int round) {
// Se eliminan los registros que existan de objetos Bet de creacion automatica de la round actual
	new DAOFactory().getBetDAO().deleteAuthomaticRoundBet(season, round);
    }
}