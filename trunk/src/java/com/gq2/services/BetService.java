package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.BetBean;
import com.gq2.beans.BetGroupBean;
import com.gq2.beans.BetLineBean;
import com.gq2.domain.Bet;
import com.gq2.domain.PrePool;
import com.gq2.tools.Const;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RAFAEL FERRUZ
 */
public class BetService {

    /* La constante CORRECTION_FACTOR_CALCULATION_RANGE_GROUP se ha utilizado para realizar
     * pruebas de ajuste en el rango minimo-maximo de signos 1X2 en cada grupo de partidos
     * en funcion de las probabilidades de 1, X y 2 que tenga el grupo.
     * Los resultados obtenidos haciendo la constante igual a 110 (se tomo este valor para
     * intentar corregir el numero de errores que se obtenian) fueron similares al valor de
     * 100 con el que se deja.
     * En las formulas usadas se puede ver que en el denominador se multiplica dos veces 
     * por 100, una de ellas corresponde al porcentaje y la otra al efecto de la constante
     * CORRECTION_FACTOR_CALCULATION_RANGE_GROUP
     */
    private PrePoolService prePoolService = new PrePoolService();

    public BetService() {
    }

    public int save(Bet bet) {

	return new DAOFactory().getBetDAO().save(bet);
    }

    public Bet load(int betId) {

	return new DAOFactory().getBetDAO().load(betId);
    }

    public boolean delete(Bet bet) {
	return new DAOFactory().getBetDAO().delete(bet);
    }

    public boolean update(Bet bet) {
	return new DAOFactory().getBetDAO().update(bet);
    }

    public List<Bet> loadConditionalBetList(int season, int orderNumber, String description) {
	return new DAOFactory().getBetDAO().loadConditionalBetList(season, orderNumber, description);
    }

    public Bet loadConditionalBet(int season, int orderNumber, String description) {
	return new DAOFactory().getBetDAO().loadConditionalBet(season, orderNumber, description);
    }

    public List<BetLineBean> generateBetLinesFromPrePoolLines(int season, int orderNumber) {
	List<BetLineBean> betLines = new ArrayList();
	if ((season != 0) && (orderNumber != 0)) {
	    List<PrePool> prePoolList = prePoolService.loadPrePoolById(season, orderNumber);
	    for (PrePool prePool : prePoolList) {
		BetLineBean betLine = new BetLineBean();
		betLine.setBliPrePoolId(prePool.getPreId());
		betLine.setBliTeamName1(prePool.getPreTeaName1());
		betLine.setBliTeamName2(prePool.getPreTeaName2());
		betLine.setBliRating4PreviousDiference(prePool.getPreRat4PreviousDiference());
		betLine.setBliPercentWin(prePool.getPrePercentWin());
		betLine.setBliPercentDraw(prePool.getPrePercentDraw());
		betLine.setBliPercentLose(prePool.getPrePercentLose());
		int result = (prePool.getPreScoScore1() - prePool.getPreScoScore2());
		if (result > 0) {
		    betLine.setBliSign("1");
		} else if (result == 0) {
		    betLine.setBliSign("X");
		} else if (result < 0) {
		    betLine.setBliSign("2");
		}
		betLines.add(betLine);
	    }
	}
	return betLines;
    }

    public static List<BetLineBean> sortDataBetLines(List<BetLineBean> dataBetLines, String sortby) {
	switch (sortby) {
	    case "order":
		Comparator byOrder = new dataBetLinesByOrder();
		Collections.sort((List<BetLineBean>) dataBetLines, byOrder);
		break;
	    case "rating":
		Comparator byRating = new dataBetLinesByRating();
		Collections.sort((List<BetLineBean>) dataBetLines, byRating);
		break;
	}
	return dataBetLines;
    }

    /**
     * Genera automaticamente la columna base de apuestas en base a la
     * diferencia de rating y segun los criterios definidos de forma standard a
     * partir del estudio estadistico de los resultados reales producidos
     */
    public void generateBets(BetBean aThis) {

	/*
	 * Limpieza de datos de apuestas y grupos de partidos a rellenar
	 */
	aThis.setBetDescription("Generated Authomatically");
	for (BetLineBean betLine : aThis.getDataBetLines()) {
	    betLine.setBliColumnGroup1(false);
	    betLine.setBliColumnGroup2(false);
	    betLine.setBliColumnGroup3(false);
	    betLine.setBliColumnGroup4(false);
	    betLine.setBliColumnGroup5(false);
	    betLine.setBliColumnBase("");
	}
	aThis.getDataBetGroups().get(0).setBgrGroup1Values("");
	aThis.getDataBetGroups().get(1).setBgrGroup1Values("");
	aThis.getDataBetGroups().get(2).setBgrGroup1Values("");
	aThis.getDataBetGroups().get(0).setBgrGroup2Values("");
	aThis.getDataBetGroups().get(1).setBgrGroup2Values("");
	aThis.getDataBetGroups().get(2).setBgrGroup2Values("");
	aThis.getDataBetGroups().get(0).setBgrGroup3Values("");
	aThis.getDataBetGroups().get(1).setBgrGroup3Values("");
	aThis.getDataBetGroups().get(2).setBgrGroup3Values("");
	aThis.getDataBetGroups().get(0).setBgrGroup4Values("");
	aThis.getDataBetGroups().get(1).setBgrGroup4Values("");
	aThis.getDataBetGroups().get(2).setBgrGroup4Values("");
	aThis.getDataBetGroups().get(0).setBgrGroup5Values("");
	aThis.getDataBetGroups().get(1).setBgrGroup5Values("");
	aThis.getDataBetGroups().get(2).setBgrGroup5Values("");
	/*
	 * Calculo del grupo al que asignar el partido
	 */
	Map<Integer, CalculationBetData> ratingDiferenceGroups = new HashMap<>();
	for (BetLineBean betLine : aThis.getDataBetLines()) {
	    if (betLine.getBliRating4PreviousDiference() / 10 > 30) {
		betLine.setBliColumnGroup1(true);
		/* Se fuerza al grupo 1 a jugar el signo 1 exclusivamente en generacion automatica */
		betLine.setBliColumnBase("1");
		ratingDiferenceGroups.put(1, (ratingDiferenceGroups.get(1) == null
			? new CalculationBetData(betLine)
			: ratingDiferenceGroups.get(1).calculateSums(betLine)));
	    } else if (betLine.getBliRating4PreviousDiference() / 10 > 5 && betLine.getBliRating4PreviousDiference() / 10 <= 30) {
		betLine.setBliColumnGroup2(true);
		betLine.setBliColumnBase("1X2");
		ratingDiferenceGroups.put(2, (ratingDiferenceGroups.get(2) == null
			? new CalculationBetData(betLine)
			: ratingDiferenceGroups.get(2).calculateSums(betLine)));

	    } else if (betLine.getBliRating4PreviousDiference() / 10 > -12 && betLine.getBliRating4PreviousDiference() / 10 <= 5) {
		betLine.setBliColumnGroup3(true);
		betLine.setBliColumnBase("1X2");
		ratingDiferenceGroups.put(3, (ratingDiferenceGroups.get(3) == null
			? new CalculationBetData(betLine)
			: ratingDiferenceGroups.get(3).calculateSums(betLine)));

	    } else if (betLine.getBliRating4PreviousDiference() / 10 <= -12) {
		betLine.setBliColumnGroup4(true);
		betLine.setBliColumnBase("1X2");
		ratingDiferenceGroups.put(4, (ratingDiferenceGroups.get(4) == null
			? new CalculationBetData(betLine)
			: ratingDiferenceGroups.get(4).calculateSums(betLine)));

	    }
	}

	/*
	 * Calculo del rango de signos que deben aparecer en cada grupo
	 */
	int errors1;
	int errorsX;
	int errors2;

	for (Integer i : ratingDiferenceGroups.keySet()) {
	    CalculationBetData calculation = ratingDiferenceGroups.get(i);
	    Integer min1 = (calculation.getSumMatches() * calculation.getSumPercents1() * Const.CORRECTION_FACTOR_CALCULATION_RANGE_GROUP)
		    / (calculation.getSumMatches() * 100 * 100);
	    String range1 = min1 + "," + (min1 + 1);
	    /* Las siguientes lineas se ponen para forzar al grupo 1 a jugar todos los signos como 1 */
	    if (i == 1) {
		min1 = calculation.getSumMatches();
		range1 = min1 + "," + min1;
	    }
	    /* fin de opcion */

	    if (calculation.getSumScore1() < min1) {
		errors1 = calculation.getSumScore1() - min1;
	    } else if (calculation.getSumScore1() > (min1 + 1)) {
		errors1 = calculation.getSumScore1() - (min1 + 1);
	    } else {
		errors1 = 0;
	    }
	    Integer minX = (calculation.getSumMatches() * calculation.getSumPercentsX() * Const.CORRECTION_FACTOR_CALCULATION_RANGE_GROUP)
		    / (calculation.getSumMatches() * 100 * 100);
	    String rangeX = minX + "," + (minX + 1);
	    /* Las siguientes lineas se ponen para forzar al grupo 1 a jugar todos los signos como 1 */
	    if (i == 1) {
		minX = 0;
		rangeX = minX + "," + minX;
	    }
	    /* fin de opcion */

	    if (calculation.getSumScoreX() < minX) {
		errorsX = calculation.getSumScoreX() - minX;
	    } else if (calculation.getSumScoreX() > (minX + 1)) {
		errorsX = calculation.getSumScoreX() - (minX + 1);
	    } else {
		errorsX = 0;
	    }
	    Integer min2 = (calculation.getSumMatches() * calculation.getSumPercents2() * Const.CORRECTION_FACTOR_CALCULATION_RANGE_GROUP)
		    / (calculation.getSumMatches() * 100 * 100);
	    String range2 = min2 + "," + (min2 + 1);
	    /* Las siguientes lineas se ponen para forzar al grupo 1 a jugar todos los signos como 1 */
	    if (i == 1) {
		min2 = 0;
		range2 = min2 + "," + min2;
	    }
	    /* fin de opcion */

	    if (calculation.getSumScore2() < min2) {
		errors2 = calculation.getSumScore2() - min2;
	    } else if (calculation.getSumScore2() > (min2 + 1)) {
		errors2 = calculation.getSumScore2() - (min2 + 1);
	    } else {
		errors2 = 0;
	    }

	    switch (i) {
		case 1:
		    aThis.getDataBetGroups().get(0).setBgrGroup1Values(range1);
		    aThis.getDataBetGroups().get(1).setBgrGroup1Values(rangeX);
		    aThis.getDataBetGroups().get(2).setBgrGroup1Values(range2);
		    aThis.getDataBetGroups().get(0).setBgrGroup1Errors(errors1);
		    aThis.getDataBetGroups().get(1).setBgrGroup1Errors(errorsX);
		    aThis.getDataBetGroups().get(2).setBgrGroup1Errors(errors2);
		    break;
		case 2:
		    aThis.getDataBetGroups().get(0).setBgrGroup2Values(range1);
		    aThis.getDataBetGroups().get(1).setBgrGroup2Values(rangeX);
		    aThis.getDataBetGroups().get(2).setBgrGroup2Values(range2);
		    aThis.getDataBetGroups().get(0).setBgrGroup2Errors(errors1);
		    aThis.getDataBetGroups().get(1).setBgrGroup2Errors(errorsX);
		    aThis.getDataBetGroups().get(2).setBgrGroup2Errors(errors2);
		    break;
		case 3:
		    aThis.getDataBetGroups().get(0).setBgrGroup3Values(range1);
		    aThis.getDataBetGroups().get(1).setBgrGroup3Values(rangeX);
		    aThis.getDataBetGroups().get(2).setBgrGroup3Values(range2);
		    aThis.getDataBetGroups().get(0).setBgrGroup3Errors(errors1);
		    aThis.getDataBetGroups().get(1).setBgrGroup3Errors(errorsX);
		    aThis.getDataBetGroups().get(2).setBgrGroup3Errors(errors2);
		    break;
		case 4:
		    aThis.getDataBetGroups().get(0).setBgrGroup4Values(range1);
		    aThis.getDataBetGroups().get(1).setBgrGroup4Values(rangeX);
		    aThis.getDataBetGroups().get(2).setBgrGroup4Values(range2);
		    aThis.getDataBetGroups().get(0).setBgrGroup4Errors(errors1);
		    aThis.getDataBetGroups().get(1).setBgrGroup4Errors(errorsX);
		    aThis.getDataBetGroups().get(2).setBgrGroup4Errors(errors2);
		    break;
	    }
	}
    }

    public void completeBetWithBetLines(BetBean aThis) {

	// Obtiene las lineas de la apuesta desde la tabla de PrePools 
	generateBetLinesFromPrePoolLines(aThis);

	List<String> betBaseList = (aThis.getBetBase().equals("")
		? new ArrayList<String>()
		: Arrays.asList(aThis.getBetBase().split(",")));
	int idx;
	for (BetLineBean betLine : aThis.getDataBetLines()) {
	    idx = aThis.getDataBetLines().indexOf(betLine);
	    /* Si el numero de apuestas base de lineas es menor al numero de lineas
	     que forman la apuesta total, se asigna el signo "1" para las siguientes apuestas */
	    if (idx > betBaseList.size() - 1) {
		betLine.setBliColumnBase("1");
	    } else {
		betLine.setBliColumnBase(betBaseList.get(idx));
	    }
	    /* betGroup1 deberia ser un string de 14 acaracteres 0 y 1 para indicar que partidos 
	     * estan asignados a este grupo.
	     * 
	     * bliColumnGroup1 = true indicara si la linea esta incluida en el grupo 1.
	     */
	    if (aThis.getBetGroup1() != null && aThis.getBetGroup1().length() > idx) {
		betLine.setBliColumnGroup1((aThis.getBetGroup1().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup1(false);
	    }
	    if (aThis.getBetGroup2() != null && aThis.getBetGroup2().length() > idx) {
		betLine.setBliColumnGroup2((aThis.getBetGroup2().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup2(false);
	    }
	    if (aThis.getBetGroup3() != null && aThis.getBetGroup3().length() > idx) {
		betLine.setBliColumnGroup3((aThis.getBetGroup3().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup3(false);
	    }
	    if (aThis.getBetGroup4() != null && aThis.getBetGroup4().length() > idx) {
		betLine.setBliColumnGroup4((aThis.getBetGroup4().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup4(false);
	    }
	    if (aThis.getBetGroup5() != null && aThis.getBetGroup5().length() > idx) {
		betLine.setBliColumnGroup5((aThis.getBetGroup5().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup5(false);
	    }
	}

	aThis.getDataBetGroups().clear();
	for (int i = 0; i < 3; i++) {
	    aThis.setDataBetGroup(new BetGroupBean());
	    switch (i) {
		case 0:
		    aThis.getDataBetGroup().setBgrSign("1");
		    aThis.getDataBetGroup().setBgrGroup1Values(aThis.getBetGroup1Values1());
		    aThis.getDataBetGroup().setBgrGroup2Values(aThis.getBetGroup2Values1());
		    aThis.getDataBetGroup().setBgrGroup3Values(aThis.getBetGroup3Values1());
		    aThis.getDataBetGroup().setBgrGroup4Values(aThis.getBetGroup4Values1());
		    aThis.getDataBetGroup().setBgrGroup5Values(aThis.getBetGroup5Values1());
		    aThis.getDataBetGroup().setBgrGroup1Errors(aThis.getBetGroup1Errors1());
		    aThis.getDataBetGroup().setBgrGroup2Errors(aThis.getBetGroup2Errors1());
		    aThis.getDataBetGroup().setBgrGroup3Errors(aThis.getBetGroup3Errors1());
		    aThis.getDataBetGroup().setBgrGroup4Errors(aThis.getBetGroup4Errors1());
		    aThis.getDataBetGroup().setBgrGroup5Errors(aThis.getBetGroup5Errors1());
		    aThis.getDataBetGroups().add(aThis.getDataBetGroup());
		    break;
		case 1:
		    aThis.getDataBetGroup().setBgrSign("X");
		    aThis.getDataBetGroup().setBgrGroup1Values(aThis.getBetGroup1ValuesX());
		    aThis.getDataBetGroup().setBgrGroup2Values(aThis.getBetGroup2ValuesX());
		    aThis.getDataBetGroup().setBgrGroup3Values(aThis.getBetGroup3ValuesX());
		    aThis.getDataBetGroup().setBgrGroup4Values(aThis.getBetGroup4ValuesX());
		    aThis.getDataBetGroup().setBgrGroup5Values(aThis.getBetGroup5ValuesX());
		    aThis.getDataBetGroup().setBgrGroup1Errors(aThis.getBetGroup1ErrorsX());
		    aThis.getDataBetGroup().setBgrGroup2Errors(aThis.getBetGroup2ErrorsX());
		    aThis.getDataBetGroup().setBgrGroup3Errors(aThis.getBetGroup3ErrorsX());
		    aThis.getDataBetGroup().setBgrGroup4Errors(aThis.getBetGroup4ErrorsX());
		    aThis.getDataBetGroup().setBgrGroup5Errors(aThis.getBetGroup5ErrorsX());
		    aThis.getDataBetGroups().add(aThis.getDataBetGroup());
		    break;
		case 2:
		    aThis.getDataBetGroup().setBgrSign("2");
		    aThis.getDataBetGroup().setBgrGroup1Values(aThis.getBetGroup1Values2());
		    aThis.getDataBetGroup().setBgrGroup2Values(aThis.getBetGroup2Values2());
		    aThis.getDataBetGroup().setBgrGroup3Values(aThis.getBetGroup3Values2());
		    aThis.getDataBetGroup().setBgrGroup4Values(aThis.getBetGroup4Values2());
		    aThis.getDataBetGroup().setBgrGroup5Values(aThis.getBetGroup5Values2());
		    aThis.getDataBetGroup().setBgrGroup1Errors(aThis.getBetGroup1Errors2());
		    aThis.getDataBetGroup().setBgrGroup2Errors(aThis.getBetGroup2Errors2());
		    aThis.getDataBetGroup().setBgrGroup3Errors(aThis.getBetGroup3Errors2());
		    aThis.getDataBetGroup().setBgrGroup4Errors(aThis.getBetGroup4Errors2());
		    aThis.getDataBetGroup().setBgrGroup5Errors(aThis.getBetGroup5Errors2());
		    aThis.getDataBetGroups().add(aThis.getDataBetGroup());
		    break;
	    }
	}

    }

    private void generateBetLinesFromPrePoolLines(BetBean aThis) {
	aThis.setDataBetLines(generateBetLinesFromPrePoolLines(aThis.getBetSeason(), aThis.getBetOrderNumber()));
    }

    /**
     * calculationBetData es una clase privada para ayuda en los calculos de
     * generacion de apuestas de forma automatica. contiene las sumas de los
     * partidos de un determinado grupo y las sumas de los porcentajes de signos
     * de los partidos que forman cada grupo
     */
    private class CalculationBetData {

	private int sumMatches;
	private int sumPercents1;
	private int sumPercentsX;
	private int sumPercents2;
	private int sumScore1;
	private int sumScoreX;
	private int sumScore2;

	private CalculationBetData(BetLineBean betLine) {
	    calculateSums(betLine);
	}

	public int getSumMatches() {
	    return sumMatches;
	}

	public void setSumMatches(int sumMatches) {
	    this.sumMatches = sumMatches;
	}

	public int getSumPercents1() {
	    return sumPercents1;
	}

	public void setSumPercents1(int sumPercents1) {
	    this.sumPercents1 = sumPercents1;
	}

	public int getSumPercentsX() {
	    return sumPercentsX;
	}

	public void setSumPercentsX(int sumPercentsX) {
	    this.sumPercentsX = sumPercentsX;
	}

	public int getSumPercents2() {
	    return sumPercents2;
	}

	public void setSumPercents2(int sumPercents2) {
	    this.sumPercents2 = sumPercents2;
	}

	public int getSumScore1() {
	    return sumScore1;
	}

	public void setSumScore1(int sumScore1) {
	    this.sumScore1 = sumScore1;
	}

	public int getSumScoreX() {
	    return sumScoreX;
	}

	public void setSumScoreX(int sumScoreX) {
	    this.sumScoreX = sumScoreX;
	}

	public int getSumScore2() {
	    return sumScore2;
	}

	public void setSumScore2(int sumScore2) {
	    this.sumScore2 = sumScore2;
	}

	private CalculationBetData calculateSums(BetLineBean betLine) {
	    sumMatches++;
	    sumPercents1 += betLine.getBliPercentWin();
	    sumPercentsX += betLine.getBliPercentDraw();
	    sumPercents2 += betLine.getBliPercentLose();
	    switch (betLine.getBliSign()) {
		case "1":
		    sumScore1++;
		    break;
		case "X":
		    sumScoreX++;
		    break;
		case "2":
		    sumScore2++;
		    break;
	    }

	    return this;
	}
    }

    private static class dataBetLinesByOrder implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
	    BetLineBean a0 = (BetLineBean) arg0;
	    BetLineBean a1 = (BetLineBean) arg1;
	    if (a0.getBliPrePoolId() == a1.getBliPrePoolId()) {
		return 0;
	    }
	    if (a0.getBliPrePoolId() > a1.getBliPrePoolId()) {
		return 1;
	    }
	    if (a0.getBliPrePoolId() < a1.getBliPrePoolId()) {
		return -1;
	    }
	    return 0;
	}
    }

    private static class dataBetLinesByRating implements Comparator {

	/**
	 * Se realiza una ordenaci�n descendente
	 */
	@Override
	public int compare(Object arg0, Object arg1) {
	    BetLineBean a0 = (BetLineBean) arg0;
	    BetLineBean a1 = (BetLineBean) arg1;
	    if (a0.getBliRating4PreviousDiference() == a1.getBliRating4PreviousDiference()) {
		return 0;
	    }
	    if (a0.getBliRating4PreviousDiference() > a1.getBliRating4PreviousDiference()) {
		return -1;
	    }
	    if (a0.getBliRating4PreviousDiference() < a1.getBliRating4PreviousDiference()) {
		return 1;
	    }
	    return 0;
	}
    }
}
