package com.gq2.beans;

import com.gq2.domain.Bet;
import com.gq2.services.BetService;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "bets")
@ViewScoped
public class BetBean extends Bet {

    private List<Bet> betList = new ArrayList();
    private BetLineBean dataBetLine = new BetLineBean();
    private List<BetLineBean> dataBetLines = new ArrayList();
    private BetGroupBean dataBetGroup = new BetGroupBean();
    private List<BetGroupBean> dataBetGroups = new ArrayList();
    private String sortby = "order";
    private Integer checkcol = 0;
    private FacesContext fc = null;
    private BetService betService = new BetService();
    private Bet betToDelete;
    private Bet betForEdition;

    public BetBean() {
    }

    public List<Bet> getBetList() {
	return betList;
    }

    public void setBetList(List<Bet> betList) {
	this.betList = betList;
    }

    public String getSortby() {
	return sortby;
    }

    public void setSortby(String sortby) {
	sortDataBetLines(sortby);
	this.sortby = sortby;
    }

    public Integer getCheckcol() {
	return checkcol;
    }

    public void setCheckcol(Integer checkcol) {
	this.checkcol = checkcol;
    }

    public BetLineBean getDataBetLine() {
	return dataBetLine;
    }

    public void setDataBetLine(BetLineBean dataBetLine) {
	this.dataBetLine = dataBetLine;
    }

    public List<BetLineBean> getDataBetLines() {
	return dataBetLines;
    }

    public void setDataBetLines(List<BetLineBean> dataBetLines) {
	this.dataBetLines = dataBetLines;
    }

    public BetGroupBean getDataBetGroup() {
	return dataBetGroup;
    }

    public void setDataBetGroup(BetGroupBean dataBetGroup) {
	this.dataBetGroup = dataBetGroup;
    }

    public List<BetGroupBean> getDataBetGroups() {
	return dataBetGroups;
    }

    public void setDataBetGroups(List<BetGroupBean> dataBetGroups) {
	this.dataBetGroups = dataBetGroups;
    }

    public Bet getBetToDelete() {
	return betToDelete;
    }

    public void setBetToDelete(Bet betToDelete) {
	this.betToDelete = betToDelete;
    }

    public Bet getBetForEdition() {
	return betForEdition;
    }

    public void setBetForEdition(Bet betForEdition) {
	this.setBetId(betForEdition.getBetId());
	this.setBetSeason(betForEdition.getBetSeason());
	this.setBetOrderNumber(betForEdition.getBetOrderNumber());
	this.setBetDescription(betForEdition.getBetDescription());
	this.setBetBase(betForEdition.getBetBase());
	this.setBetGroup1(betForEdition.getBetGroup1());
	this.setBetGroup1Values1(betForEdition.getBetGroup1Values1());
	this.setBetGroup1ValuesX(betForEdition.getBetGroup1ValuesX());
	this.setBetGroup1Values2(betForEdition.getBetGroup1Values2());
	this.setBetGroup2(betForEdition.getBetGroup2());
	this.setBetGroup2Values1(betForEdition.getBetGroup2Values1());
	this.setBetGroup2ValuesX(betForEdition.getBetGroup2ValuesX());
	this.setBetGroup2Values2(betForEdition.getBetGroup2Values2());
	this.setBetGroup3(betForEdition.getBetGroup3());
	this.setBetGroup3Values1(betForEdition.getBetGroup3Values1());
	this.setBetGroup3ValuesX(betForEdition.getBetGroup3ValuesX());
	this.setBetGroup3Values2(betForEdition.getBetGroup3Values2());
	this.setBetGroup4(betForEdition.getBetGroup4());
	this.setBetGroup4Values1(betForEdition.getBetGroup4Values1());
	this.setBetGroup4ValuesX(betForEdition.getBetGroup4ValuesX());
	this.setBetGroup4Values2(betForEdition.getBetGroup4Values2());
	this.setBetGroup5(betForEdition.getBetGroup5());
	this.setBetGroup5Values1(betForEdition.getBetGroup5Values1());
	this.setBetGroup5ValuesX(betForEdition.getBetGroup5ValuesX());
	this.setBetGroup5Values2(betForEdition.getBetGroup5Values2());
    }

    public void readBets(Integer season, Integer orderNumber, String description) {
	setBetList(betService.loadConditionalBetList(season, orderNumber, description));
    }

    public void showBets() {
	dataBetLines.clear();
	dataBetGroups.clear();
	readBets(betSeason, betOrderNumber, betDescription);
    }

    public void insertBet() throws NamingException, SQLException {
	if (betSeason == null || betSeason == 0
		|| betOrderNumber == null || betOrderNumber == 0
		|| betDescription == null || betDescription.equals("")) {
	    // enviar mensaje
	    return;
	}
	Bet bet = betService.loadConditionalBet(getBetSeason(), getBetOrderNumber(), getBetDescription());
	if (betService.loadConditionalBet(getBetSeason(), getBetOrderNumber(), getBetDescription()) == null) {
//	if (bet == null) {
	    //bet = new Bet();
	    fillBet(this);   // LLena un objeto Bet ya existente con los datos entrados o modificados
	    // Persiste la apuesta
	    setBetId(betService.save(this));
	    if (this.getBetId() == 0) {
		// fallo al insertar el registro
		// TODO Enviar mensaje de fallo en la persistencia del objeto
	    } else {
		betList.add(bet);
		dataBetLines.clear();
		dataBetGroups.clear();
	    }
	} else {
	    // ya existe el registro
	    // TODO Enviar mensaje notificacion de que el objeto ya existe
	}
    }

    private void fillBet(Bet bet) {
	bet.setBetId(betId);
	bet.setBetSeason(betSeason);
	bet.setBetOrderNumber(betOrderNumber);
	bet.setBetDescription(betDescription);
	bet.setBetBase("");
	bet.setBetGroup1("");
	bet.setBetGroup2("");
	bet.setBetGroup3("");
	bet.setBetGroup4("");
	bet.setBetGroup5("");
	for (BetLineBean betLine : dataBetLines) {
	    bet.setBetBase(bet.getBetBase()
		    + betLine.getBliColumnBase()
		    + (dataBetLines.indexOf(betLine) < dataBetLines.size() - 1 ? "," : ""));
	    buildStringMatchesInGroups(bet, betLine); // Strings de 1 y 0 para cada partido en cada grupo
	}
	if (dataBetGroups.isEmpty()) {
	    dataBetGroups.add(new BetGroupBean());
	    dataBetGroups.add(new BetGroupBean());
	    dataBetGroups.add(new BetGroupBean());
	}
	bet.setBetGroup1Values1(dataBetGroups.get(0).getBgrGroup1Values());
	bet.setBetGroup1ValuesX(dataBetGroups.get(1).getBgrGroup1Values());
	bet.setBetGroup1Values2(dataBetGroups.get(2).getBgrGroup1Values());
	bet.setBetGroup2Values1(dataBetGroups.get(0).getBgrGroup2Values());
	bet.setBetGroup2ValuesX(dataBetGroups.get(1).getBgrGroup2Values());
	bet.setBetGroup2Values2(dataBetGroups.get(2).getBgrGroup2Values());
	bet.setBetGroup3Values1(dataBetGroups.get(0).getBgrGroup3Values());
	bet.setBetGroup3ValuesX(dataBetGroups.get(1).getBgrGroup3Values());
	bet.setBetGroup3Values2(dataBetGroups.get(2).getBgrGroup3Values());
	bet.setBetGroup4Values1(dataBetGroups.get(0).getBgrGroup4Values());
	bet.setBetGroup4ValuesX(dataBetGroups.get(1).getBgrGroup4Values());
	bet.setBetGroup4Values2(dataBetGroups.get(2).getBgrGroup4Values());
	bet.setBetGroup5Values1(dataBetGroups.get(0).getBgrGroup5Values());
	bet.setBetGroup5ValuesX(dataBetGroups.get(1).getBgrGroup5Values());
	bet.setBetGroup5Values2(dataBetGroups.get(2).getBgrGroup5Values());
    }
    /* Por cada linea de apuesta, se va contruyendo un string de signos '1' y '0' para cada uno de los 
     * cinco grupos en los que se agrupan los partidos en funcion de similares probabilidades de resultados.
     * Se añade un signo '1' al string del grupo al que pertenezca el partido de la linea de apuesta,
     * y un signo '0' a los grupos a los que no pertenezca. 
     * Ejemplo final del de un grupo: '00010100001100'. A este grupo se habran asignado los partidos
     * 4, 6, 11 y 12 de la lista de partidos que componen la apuesta.
     */

    private void buildStringMatchesInGroups(Bet bet, BetLineBean betLine) {
	bet.setBetGroup1(bet.getBetGroup1() + (betLine.getBliColumnGroup1() ? "1" : "0"));
	bet.setBetGroup2(bet.getBetGroup2() + (betLine.getBliColumnGroup2() ? "1" : "0"));
	bet.setBetGroup3(bet.getBetGroup3() + (betLine.getBliColumnGroup3() ? "1" : "0"));
	bet.setBetGroup4(bet.getBetGroup4() + (betLine.getBliColumnGroup4() ? "1" : "0"));
	bet.setBetGroup5(bet.getBetGroup5() + (betLine.getBliColumnGroup5() ? "1" : "0"));
    }

    public void editBet() {

	// Obtiene las lineas de la apuesta desde la tabla de PrePools 
	generateBetLinesFromPrePoolLines();
//	String[] arrayBase = getBetBase().split(",");
	List<String> betBaseList = (getBetBase().equals("")
		? new ArrayList<String>()
		: Arrays.asList(getBetBase().split(",")));
	int idx;
	for (BetLineBean betLine : dataBetLines) {
	    idx = dataBetLines.indexOf(betLine);
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
	    if (getBetGroup1() != null && getBetGroup1().length() > idx) {
		betLine.setBliColumnGroup1((getBetGroup1().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup1(false);
	    }
	    if (getBetGroup2() != null && getBetGroup2().length() > idx) {
		betLine.setBliColumnGroup2((getBetGroup2().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup2(false);
	    }
	    if (getBetGroup3() != null && getBetGroup3().length() > idx) {
		betLine.setBliColumnGroup3((getBetGroup3().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup3(false);
	    }
	    if (getBetGroup4() != null && getBetGroup4().length() > idx) {
		betLine.setBliColumnGroup4((getBetGroup4().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup4(false);
	    }
	    if (getBetGroup5() != null && getBetGroup5().length() > idx) {
		betLine.setBliColumnGroup5((getBetGroup5().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup5(false);
	    }
	}

	dataBetGroups.clear();
	for (int i = 0; i < 3; i++) {
	    dataBetGroup = new BetGroupBean();
	    switch (i) {
		case 0:
		    dataBetGroup.setBgrSign("1");
		    dataBetGroup.setBgrGroup1Values(getBetGroup1Values1());
		    dataBetGroup.setBgrGroup2Values(getBetGroup2Values1());
		    dataBetGroup.setBgrGroup3Values(getBetGroup3Values1());
		    dataBetGroup.setBgrGroup4Values(getBetGroup4Values1());
		    dataBetGroup.setBgrGroup5Values(getBetGroup5Values1());
		    dataBetGroups.add(dataBetGroup);
		    break;
		case 1:
		    dataBetGroup.setBgrSign("X");
		    dataBetGroup.setBgrGroup1Values(getBetGroup1ValuesX());
		    dataBetGroup.setBgrGroup2Values(getBetGroup2ValuesX());
		    dataBetGroup.setBgrGroup3Values(getBetGroup3ValuesX());
		    dataBetGroup.setBgrGroup4Values(getBetGroup4ValuesX());
		    dataBetGroup.setBgrGroup5Values(getBetGroup5ValuesX());
		    dataBetGroups.add(dataBetGroup);
		    break;
		case 2:
		    dataBetGroup.setBgrSign("2");
		    dataBetGroup.setBgrGroup1Values(getBetGroup1Values2());
		    dataBetGroup.setBgrGroup2Values(getBetGroup2Values2());
		    dataBetGroup.setBgrGroup3Values(getBetGroup3Values2());
		    dataBetGroup.setBgrGroup4Values(getBetGroup4Values2());
		    dataBetGroup.setBgrGroup5Values(getBetGroup5Values2());
		    dataBetGroups.add(dataBetGroup);
		    break;
	    }
	}

    }

    public void saveBet() {
	sortDataBetLines("order");
	fillBet(this);
	Integer idxOf = findIdInBetList(this.getBetId(), betList);
	if (idxOf == null) {
	    // No esta la lista de bets. Deberia guardarse como apuesta nueva
	    // TODO Enviar mensaje de que no existe y debe guardarse como nueva
	} else {
	    betService.update(this);
	    betList.set(idxOf, betService.loadConditionalBet(getBetSeason(), getBetOrderNumber(), getBetDescription()));
	}
    }

    private Integer findIdInBetList(int id, List<Bet> bets) {
	int idxOf;
	for (Bet bet : bets) {
	    if (bet.getBetId() == id) {
		idxOf = bets.indexOf(bet);
		return idxOf;
	    }
	}
	return null;
    }

    public void deleteBet() {
	if (betToDelete != null) {
	    betService.delete(betToDelete);
	    showBets();
	}
    }

    private void generateBetLinesFromPrePoolLines() {
	setDataBetLines(betService.generateBetLinesFromPrePoolLines(getBetSeason(), getBetOrderNumber()));
    }

    public void generateCols() {
	saveBet();
	List<String> colsBase = new ArrayList();
	/* getBetBase() proporciona la columna base de 14 apuestas
	 *
	 */
	colsBase.add(getBetBase());
	List<String> colsBaseTemp = new ArrayList();
	/* getBet_grupo1() proporciona un string de 14 caracteres 0 � 1
	 * Otros 4 grupos, del 2 al 5, indican cuatro posibles grupos de partidos
	 * El caracter 1 indica que dicho partido pertenece a uno de los 5 posibles grupos
	 * El bucle que se inicia a continuaci�n obtiene estos strings de cada grupo
	 * para desarrollar las combinaciones de las apuestas que pertenezcan a cada grupo.
	 * Se a�ade un grupo final en el que intervienen todos los partidos por
	 * si hubiese alg�n partido que no se hubiera incluido en alguno de los
	 * cinco grupos; se tratar�a de un grupo general al que pertenecen los 14 partidos
	 * del boleto de apuestas.
	 */
	for (int groupNumber = 1; groupNumber <= 6; groupNumber++) {
	    /* Se obtiene la secuencia de partidos activos y no activos en cada grupo */
	    String sequenceMatchesInGroup = obtainSequenceMatchesInGroup(groupNumber);

	    /* El siguiente bucle for extrae cada una de las posibles columnas base de apuestas.
	     * En nuestra actual versi�n de la aplicaci�n, solamente existe una columna base.
	     */
// Se hace desarrollo de combinaciones por cada columna base que se van formando
	    for (String colBase : colsBase) {
		List<String> betBaseList = Arrays.asList(colBase.split(","));
		List<String> betsGroup = new ArrayList<>();

		/* Se extraen de la columna base de apuestas aquellas que correspondan a los
		 * partidos que conforman al grupo que se este tratando y que lo indica el
		 * puntero del bucle del nivel anterior (variable g). Dichas apuestas se agregan
		 * a una lista de trabajo ( betsGroup ).
		 */
		for (int i = 0; i < sequenceMatchesInGroup.length(); i++) {
		    if (sequenceMatchesInGroup.substring(i, i + 1).equals("1")) {
			betsGroup.add(betBaseList.get(i));
		    }
		}

		if (betsGroup.size() > 0) {
		    List<String> combinationsGroup = new ArrayList<>();
		    combinationsGroup.add("");
		    combinationsGroup = appendMyCombinations(combinationsGroup, betsGroup, 0);
//                    combiGroup[m - 1] = checkCond(g, combiGroup[m - 1], j);
		    if (groupNumber < 6) {
// Cuando es la combinaci�n final, no comprueba limitaciones de signos        
			List<String> combinationsTemp = new ArrayList<>();
			for (String combination : combinationsGroup) {
			    if (!"".equals(checkCond(groupNumber, combination))) {
				combinationsTemp.add(combination);
			    }
			}
			combinationsGroup = combinationsTemp;
		    }


		    /* Se calcula el n�mero de combinaciones posibles del grupo


		     int combinationsNumber = obtainCombinationsNumberGroup(betsGroup);
		     // Se obtienen los divisores utilizados para calcular los cocientes y restos de cada columna
		     List<Integer> dividers = obtainDividersGroup(combinationsNumber, betsGroup);

		     // Se generan las posibles combinaciones del grupo
		     String[] combiGroup = new String[combinationsNumber];
		     for (int m = 1; m <= combinationsNumber; m++) {
		     Integer F = m - 1;
		     combiGroup[F] = "";
		     for (int n = 0; n < betsGroup.size(); n++) {
		     if (n < (betsGroup.size() - 1)) {
		     combiGroup[m - 1] = combiGroup[m - 1] + betsGroup.get(n).substring((F / dividers.get(n)), ((F / dividers.get(n)) + 1));
		     F = F % dividers.get(n);
		     } else {
		     combiGroup[m - 1] = combiGroup[m - 1] + betsGroup.get(n).substring((F % betsGroup.get(n).length()), ((F % betsGroup.get(n).length()) + 1));
		     }
		     }
		     //                    combiGroup[m - 1] = checkCond(g, combiGroup[m - 1], j);
		     if (groupNumber < 6) {
		     // Cuando es la combinaci�n final, no comprueba limitaciones de signos                            
		     combiGroup[m - 1] = checkCond(g, combiGroup[m - 1]);
		     }
		     }
		     * 		     */
// Se crean las nuevas columnas base sustituyendo las combinaciones resultantes 
// v�lidas del grupo que se acaba de tratar
		    Boolean isCombi = false;
		    for (int p = 0; p < combinationsGroup.size(); p++) {
			if (combinationsGroup.get(p).length() > 0) {
			    isCombi = true;
			    String chain = "";
			    Integer s = 0;
			    for (int r = 0; r < sequenceMatchesInGroup.length(); r++) {
				switch (sequenceMatchesInGroup.substring(r, r + 1)) {
				    case "0":
					chain = chain + betBaseList.get(r) + ",";
					break;
				    case "1":
					chain = chain + combinationsGroup.get(p).substring(s, ++s) + ",";
					break;
				}
			    }
			    if (chain.endsWith(",")) {
				chain = chain.substring(0, chain.length() - 1);
			    }
			    colsBaseTemp.add(chain);
			}
		    }
		    if (!isCombi) {
//                        colsBaseTemp.add(colBase);//                    }
		    }
		} else {
// No hay partidos definidos en el grupo                    
		    colsBaseTemp.add(colBase);
		}
	    }
	    colsBase.clear();
	    colsBase.addAll(colsBaseTemp);
	    colsBaseTemp.clear();
	}
	printFileCols(colsBase);
    }

    /**
     * Genera todas las combinaciones posibles de un grupo de partidos segun las
     * bases de apuesta de cada partido
     *
     * @param combinationsList
     * @param betsGroup
     * @param numberGroup
     * @return
     */
    private List<String> appendMyCombinations(List<String> combinationsList, List<String> betsGroup, int numberBet) {
	List<String> newCombinations = new ArrayList<>();
	for (String combination : combinationsList) {
	    for (int idx = 0; idx < betsGroup.get(numberBet).length(); idx++) {
//		combination = combination + betsGroup.get(numberBet).substring(idx, idx + 1);
		newCombinations.add(combination + betsGroup.get(numberBet).substring(idx, idx + 1));
	    }
	}
	if (++numberBet < betsGroup.size()) {
	    return appendMyCombinations(newCombinations, betsGroup, numberBet);
	}
	return newCombinations;
    }

    private String obtainSequenceMatchesInGroup(int groupNumber) {
	String sequenceMatchesInGroup = "";
	switch (groupNumber) {
	    case 1:
		sequenceMatchesInGroup = getBetGroup1();
		break;
	    case 2:
		sequenceMatchesInGroup = getBetGroup2();
		break;
	    case 3:
		sequenceMatchesInGroup = getBetGroup3();
		break;
	    case 4:
		sequenceMatchesInGroup = getBetGroup4();
		break;
	    case 5:
		sequenceMatchesInGroup = getBetGroup5();
		break;
	    case 6:
		sequenceMatchesInGroup = "11111111111111";
		break;
	}
	return sequenceMatchesInGroup;
    }

    /* Se calcula el n�mero de combinaciones posibles del grupo
     */
    private int obtainCombinationsNumberGroup(List<String> betsGroup) {
	int combinationsNumber = 1;
	for (String betGroup : betsGroup) {
	    combinationsNumber = combinationsNumber * betGroup.length();
	}
	return combinationsNumber;
    }

    // Se obtienen los divisores utilizados para calcular los cocientes y restos de cada columna
    private List<Integer> obtainDividersGroup(int combinationsNumber, List<String> betsGroup) {
	List<Integer> dividers = new ArrayList<>();
	Integer divider = 0;
	for (String betGroup : betsGroup) {
	    if (dividers.isEmpty()) {
		divider = combinationsNumber / betGroup.length();
	    } else {
		divider = divider / betGroup.length();
	    }
	    dividers.add(divider);
	}
	return dividers;
    }

    private String checkCond(Integer groupNumber, String chain) {

	Integer n1 = 0, nX = 0, n2 = 0;
	for (int i = 0; i < chain.length(); i++) {
	    if (chain.substring(i, i + 1).equals("1")) {
		n1++;
	    } else if (chain.substring(i, i + 1).toUpperCase().equals("X")) {
		nX++;
	    } else if (chain.substring(i, i + 1).equals("2")) {
		n2++;
	    }
	}
	switch (groupNumber) {
	    case 1:
		if (verifyGroupValues(getBetGroup1Values1(), n1,
			getBetGroup1ValuesX(), nX,
			getBetGroup1Values2(), n2)) {
		    return chain;
		} else {
		    return "";
		}
	    case 2:
		if (verifyGroupValues(getBetGroup2Values1(), n1,
			getBetGroup2ValuesX(), nX,
			getBetGroup2Values2(), n2)) {
		    return chain;
		} else {
		    return "";
		}
	    case 3:
		if (verifyGroupValues(getBetGroup3Values1(), n1,
			getBetGroup3ValuesX(), nX,
			getBetGroup3Values2(), n2)) {
		    return chain;
		} else {
		    return "";
		}
	    case 4:
		if (verifyGroupValues(getBetGroup4Values1(), n1,
			getBetGroup4ValuesX(), nX,
			getBetGroup4Values2(), n2)) {
		    return chain;
		} else {
		    return "";
		}
	    case 5:
		if (verifyGroupValues(getBetGroup5Values1(), n1,
			getBetGroup5ValuesX(), nX,
			getBetGroup5Values2(), n2)) {
		    return chain;
		} else {
		    return "";
		}
	}
	return "";
    }

    private boolean verifyGroupValues(String values1, int n1, String valuesX, int nX, String values2, int n2) {
	if ((values1.equals("") || ("," + values1 + ",").contains("," + n1 + ","))
		&& (valuesX.equals("") || ("," + valuesX + ",").contains("," + nX + ","))
		&& (values2.equals("") || ("," + values2 + ",").contains("," + n2 + ","))) {
	    return true;
	}
	return false;
    }

    private void printFileCols(List<String> colsBase) {

	// Abre el fichero externo para escribir las columnas
//            FileInputStream file = null;
	PrintWriter fw;
	fc = FacesContext.getCurrentInstance();

	try {
//                file = new FileInputStream(getServletContext().getRealPath("/WEB-INF/ficheroResultados"));
	    fw = new PrintWriter(fc.getExternalContext().getRealPath("/WEB-INF/" + getBetSeason() + "_"
		    + getBetOrderNumber() + "_" + getBetId() + ".col"));

	    for (String colBase : colsBase) {
		String record = "";
		String[] arrayBase = colBase.split(",");
		for (int i = 0; i < arrayBase.length; i++) {
		    record = record + arrayBase[i];
		}
		fw.println(record);
	    }

	    fw.close();

	} catch (FileNotFoundException e) {
	    fc.getExternalContext().log("URL mal formada");
	}
    }

    private void sortDataBetLines(String sortby) {
	if (dataBetLines.size() > 0) {
	    dataBetLines = BetService.sortDataBetLines(dataBetLines, sortby);
	}
    }

    public void checkcolChange(ValueChangeEvent ev) {
	Integer numCol = (Integer) ev.getNewValue();
	boolean setStatus = true;
	int idxOf;
	for (BetLineBean betLine : dataBetLines) {
	    idxOf = dataBetLines.indexOf(betLine);
	    switch (numCol) {
		case 1:
		    if (idxOf == 0) {
			setStatus = (betLine.getBliColumnGroup1() == false ? true : !betLine.getBliColumnGroup1());
		    }
		    betLine.setBliColumnGroup1(setStatus);
		    break;
		case 2:
		    if (idxOf == 0) {
			setStatus = (betLine.getBliColumnGroup2() == false ? true : !betLine.getBliColumnGroup2());
		    }
		    betLine.setBliColumnGroup2(setStatus);
		    break;
		case 3:
		    if (idxOf == 0) {
			setStatus = (betLine.getBliColumnGroup3() == false ? true : !betLine.getBliColumnGroup3());
		    }
		    betLine.setBliColumnGroup3(setStatus);
		    break;
		case 4:
		    if (idxOf == 0) {
			setStatus = (betLine.getBliColumnGroup4() == false ? true : !betLine.getBliColumnGroup4());
		    }
		    betLine.setBliColumnGroup4(setStatus);
		    break;
		case 5:
		    if (idxOf == 0) {
			setStatus = (betLine.getBliColumnGroup5() == false ? true : !betLine.getBliColumnGroup5());
		    }
		    betLine.setBliColumnGroup5(setStatus);
		    break;
	    }

	}
    }
}
