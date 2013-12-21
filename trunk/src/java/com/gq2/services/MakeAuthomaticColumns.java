package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.ColumnsBean;
import com.gq2.domain.Championship;
import com.gq2.tools.Const;
import java.util.Calendar;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class MakeAuthomaticColumns {

    private int chaId;
    private int round;
    private Championship championship;
    private int season;
    private BetService betService = new BetService();
    private Object columnService;

    public MakeAuthomaticColumns() {
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
	/*
	 * Se crea el objeto ColumnsBean para leer la apuesta y generar las columnas desarrolladas.
	 * ColumnsBean es un objeto extended de BetBean por lo que hereda sus variables y metodos publicos
	 */
	ColumnsBean authomaticBet = new ColumnsBean();
	/* Borrado de Apuestas generadas automaticamente */
	authomaticBet.readBets(season, round, "Generated Authomatically");
	if (authomaticBet.getBetList().size() > 0) {
	    authomaticBet.setBetId(authomaticBet.getBetList().get(0).getBetId());
	    authomaticBet.setBetSeason(authomaticBet.getBetList().get(0).getBetSeason());
	    authomaticBet.setBetOrderNumber(authomaticBet.getBetList().get(0).getBetOrderNumber());
	    authomaticBet.setBetDescription(authomaticBet.getBetList().get(0).getBetDescription());
	    /* Se completan datos del objeto Bet */
	    authomaticBet.setBetForEdition(authomaticBet.getBetList().get(0));
	    /* Se completa la nueva apuesta con las lineas que le correspondan desde PrePool */
	    authomaticBet.editBet(); // Desde editBet se llama al servicio para completar las lineas
	    /* Se generan las columnas que corresponden a la apuesta automatica y se guardan
	     en fichero de texto */
	    authomaticBet.generateCols();
	    /* Se leen las columnas desde el fichero de texto generado */
	    authomaticBet.getDataColumns("");
	    /* Se inicializan los parametros de reduccion */
	    authomaticBet.setSelReduction(Const.MAXIMUN_LINES_BY_FORM - Const.AUTHOMATIC_REDUCTION_TO);
	    authomaticBet.setReduceFromCol(Const.AUTHOMATIC_REDUCE_FROM_COLUMN);
	    /* Se ejecuta el proceso de reduccion de columnas */
	    authomaticBet.setDataCols(authomaticBet.getColumnService().generateReduction(authomaticBet));
	    authomaticBet.setSaveReduction("Authomatic_reduction_to " + Const.AUTHOMATIC_REDUCTION_TO);
	    authomaticBet.saveReduction();
	}

	System.out.println("MakeAuthomaticColumns: Generaci�n Columns Automaticas finalizada. Ronda " + round);

    }

    private void initCollections(int chaId, int round) {
	setChampionship(new DAOFactory().getChampionshipDAO().load(chaId));
	Calendar cal = Calendar.getInstance();
	cal.setTime(getChampionship().getChaStartDate());
	setSeason(cal.get(Calendar.YEAR));
    }
}