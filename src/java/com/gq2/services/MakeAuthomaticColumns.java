package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.ColumnsBean;
import com.gq2.domain.Championship;
import com.gq2.tools.Const;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class MakeAuthomaticColumns {

    private int chaId;
    private int round;
    private Championship championship;
    private int season;

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
	System.out.println("Inicio    " + new Date());
	/* Borrado de Apuestas generadas automaticamente */
	authomaticBet.readBets(season, round, Const.GENERATED_AUTHOMATICALLY_TEXT);
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
	    /* Se inicializan los parametros de reduccion */
	    /* Se ejecuta un bucle for para generar todas las reducciones desde MAXIMUN_LINES_BY_FORM
	     * hasta AUTHOMATIC_REDUCTION_TO
	     */
	    for (int factor_reduction = Const.MAXIMUN_LINES_BY_FORM;
		    factor_reduction >= Const.AUTHOMATIC_REDUCTION_TO;
		    factor_reduction--) {
		/* Se leen las columnas desde el fichero de texto generado (todas las columnas iniciales posibles) */
//		if (factor_reduction == Const.MAXIMUN_LINES_BY_FORM) {
		authomaticBet.getDataColumns("");
//		} else {
//		    authomaticBet.getDataColumns(Const.AUTHOMATIC_REDUCTION_SUFFIX_TEXT + (factor_reduction + 1));
//		}
		System.out.println("Leidas columnas    " + new Date());
		authomaticBet.setSelReduction(factor_reduction);
//		authomaticBet.setReduceFromCol(Const.AUTHOMATIC_REDUCE_FROM_COLUMN);
		authomaticBet.setReduceFromCol(authomaticBet.getDataCols().size() / 2); // Desde la columna central de la lista de columnas
		authomaticBet.setMaximumColumnsNumber(0);
		/* Se ejecuta el proceso de reduccion de columnas */
		authomaticBet.setDataCols(authomaticBet.getColumnService().generateReduction(authomaticBet));
		System.out.println("Fin de generacion    " + new Date());
		authomaticBet.setSaveReduction(Const.AUTHOMATIC_REDUCTION_SUFFIX_TEXT + factor_reduction);
		authomaticBet.saveReduction();
		System.out.println("Fin de guardado     " + new Date());
	    }
	    /* Reduccion especial para un maximo de columnas */
	    authomaticBet.setMaximumColumnsNumber(Const.AUTHOMATIC_MAXIMUN_COLUMNS_IN_REDUCTION);
	    /* Se ejecuta el proceso de reduccion de columnas */
	    authomaticBet.setReduceFromCol(Math.min(Const.AUTHOMATIC_REDUCE_FROM_COLUMN, 
		    Const.AUTHOMATIC_MAXIMUN_COLUMNS_IN_REDUCTION));
	    authomaticBet.setDataCols(authomaticBet.getColumnService().generateReduction(authomaticBet));
	    authomaticBet.setSaveReduction(Const.MAXIMUN_COLUMNS_SUFFIX_TEXT
		    + Const.AUTHOMATIC_MAXIMUN_COLUMNS_IN_REDUCTION);
	    authomaticBet.saveReduction();
	    System.out.println("Final total    " + new Date());

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