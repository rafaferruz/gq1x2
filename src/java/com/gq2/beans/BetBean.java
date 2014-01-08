package com.gq2.beans;

import com.gq2.domain.Bet;
import com.gq2.services.BetService;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

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
    private Integer numColumns;
    private Integer generationMode = 0;

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

    public Integer getNumColumns() {
	return numColumns;
    }

    public void setNumColumns(Integer numColumns) {
	this.numColumns = numColumns;
    }

    public Integer getGenerationMode() {
	return generationMode;
    }

    public void setGenerationMode(Integer generationMode) {
	this.generationMode = generationMode;
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
	this.setBetGroup1Errors1(betForEdition.getBetGroup1Errors1());
	this.setBetGroup1ErrorsX(betForEdition.getBetGroup1ErrorsX());
	this.setBetGroup1Errors2(betForEdition.getBetGroup1Errors2());
	this.setBetGroup2(betForEdition.getBetGroup2());
	this.setBetGroup2Values1(betForEdition.getBetGroup2Values1());
	this.setBetGroup2ValuesX(betForEdition.getBetGroup2ValuesX());
	this.setBetGroup2Values2(betForEdition.getBetGroup2Values2());
	this.setBetGroup2Errors1(betForEdition.getBetGroup2Errors1());
	this.setBetGroup2ErrorsX(betForEdition.getBetGroup2ErrorsX());
	this.setBetGroup2Errors2(betForEdition.getBetGroup2Errors2());
	this.setBetGroup3(betForEdition.getBetGroup3());
	this.setBetGroup3Values1(betForEdition.getBetGroup3Values1());
	this.setBetGroup3ValuesX(betForEdition.getBetGroup3ValuesX());
	this.setBetGroup3Values2(betForEdition.getBetGroup3Values2());
	this.setBetGroup3Errors1(betForEdition.getBetGroup3Errors1());
	this.setBetGroup3ErrorsX(betForEdition.getBetGroup3ErrorsX());
	this.setBetGroup3Errors2(betForEdition.getBetGroup3Errors2());
	this.setBetGroup4(betForEdition.getBetGroup4());
	this.setBetGroup4Values1(betForEdition.getBetGroup4Values1());
	this.setBetGroup4ValuesX(betForEdition.getBetGroup4ValuesX());
	this.setBetGroup4Values2(betForEdition.getBetGroup4Values2());
	this.setBetGroup4Errors1(betForEdition.getBetGroup4Errors1());
	this.setBetGroup4ErrorsX(betForEdition.getBetGroup4ErrorsX());
	this.setBetGroup4Errors2(betForEdition.getBetGroup4Errors2());
	this.setBetGroup5(betForEdition.getBetGroup5());
	this.setBetGroup5Values1(betForEdition.getBetGroup5Values1());
	this.setBetGroup5ValuesX(betForEdition.getBetGroup5ValuesX());
	this.setBetGroup5Values2(betForEdition.getBetGroup5Values2());
	this.setBetGroup5Errors1(betForEdition.getBetGroup5Errors1());
	this.setBetGroup5ErrorsX(betForEdition.getBetGroup5ErrorsX());
	this.setBetGroup5Errors2(betForEdition.getBetGroup5Errors2());
    }

    public void readBets(Integer season, Integer orderNumber, String description) {
	setBetList(betService.loadConditionalBetList(season, orderNumber, description));
    }

    public void showBets() {
	dataBetLines.clear();
	dataBetGroups.clear();
	readBets(betSeason, betOrderNumber, betDescription);
    }

    public void insertBet() {
	if (betSeason == null || betSeason == 0
		|| betOrderNumber == null || betOrderNumber == 0
		|| betDescription == null || betDescription.equals("")) {
	    // enviar mensaje
	    return;
	}
	Bet bet = betService.loadConditionalBet(getBetSeason(), getBetOrderNumber(), getBetDescription());
	if (bet == null) {
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
	bet.setBetGroup1Errors1(dataBetGroups.get(0).getBgrGroup1Errors());
	bet.setBetGroup1ErrorsX(dataBetGroups.get(1).getBgrGroup1Errors());
	bet.setBetGroup1Errors2(dataBetGroups.get(2).getBgrGroup1Errors());
	bet.setBetGroup2Values1(dataBetGroups.get(0).getBgrGroup2Values());
	bet.setBetGroup2ValuesX(dataBetGroups.get(1).getBgrGroup2Values());
	bet.setBetGroup2Values2(dataBetGroups.get(2).getBgrGroup2Values());
	bet.setBetGroup2Errors1(dataBetGroups.get(0).getBgrGroup2Errors());
	bet.setBetGroup2ErrorsX(dataBetGroups.get(1).getBgrGroup2Errors());
	bet.setBetGroup2Errors2(dataBetGroups.get(2).getBgrGroup2Errors());
	bet.setBetGroup3Values1(dataBetGroups.get(0).getBgrGroup3Values());
	bet.setBetGroup3ValuesX(dataBetGroups.get(1).getBgrGroup3Values());
	bet.setBetGroup3Values2(dataBetGroups.get(2).getBgrGroup3Values());
	bet.setBetGroup3Errors1(dataBetGroups.get(0).getBgrGroup3Errors());
	bet.setBetGroup3ErrorsX(dataBetGroups.get(1).getBgrGroup3Errors());
	bet.setBetGroup3Errors2(dataBetGroups.get(2).getBgrGroup3Errors());
	bet.setBetGroup4Values1(dataBetGroups.get(0).getBgrGroup4Values());
	bet.setBetGroup4ValuesX(dataBetGroups.get(1).getBgrGroup4Values());
	bet.setBetGroup4Values2(dataBetGroups.get(2).getBgrGroup4Values());
	bet.setBetGroup4Errors1(dataBetGroups.get(0).getBgrGroup4Errors());
	bet.setBetGroup4ErrorsX(dataBetGroups.get(1).getBgrGroup4Errors());
	bet.setBetGroup4Errors2(dataBetGroups.get(2).getBgrGroup4Errors());
	bet.setBetGroup5Values1(dataBetGroups.get(0).getBgrGroup5Values());
	bet.setBetGroup5ValuesX(dataBetGroups.get(1).getBgrGroup5Values());
	bet.setBetGroup5Values2(dataBetGroups.get(2).getBgrGroup5Values());
	bet.setBetGroup5Errors1(dataBetGroups.get(0).getBgrGroup5Errors());
	bet.setBetGroup5ErrorsX(dataBetGroups.get(1).getBgrGroup5Errors());
	bet.setBetGroup5Errors2(dataBetGroups.get(2).getBgrGroup5Errors());
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

	betService.completeBetWithBetLines(this);
	setSortby("rating");
	sortDataBetLines(getSortby());
	setNumColumns(null);
    }

    public void saveBet() {
	setSortby("order");
	sortDataBetLines(getSortby());
	fillBet(this);
	Integer idxOf = findIdInBetList(this.getBetId(), betList);
	if (idxOf == null) {
	    betService.save(this);
	    // No esta la lista de bets. Deberia guardarse como apuesta nueva
	    // TODO Enviar mensaje de que no existe y debe guardarse como nueva
	} else {
	    betService.update(this);
	    betList.set(idxOf, betService.loadConditionalBet(getBetSeason(), getBetOrderNumber(), getBetDescription()));
	}
	setNumColumns(null);
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
	    setBetOrderNumber(0);
	    setBetDescription("");
	    showBets();
	}
    }

    /**
     * Genera automaticamente la columna base de apuestas en base a la
     * diferencia de rating y segun los criterios definidos de forma standard a
     * partir del estudio estadistico de los resultados reales producidos
     */
    public void generateBets() {

	betService.generateBets(this);

	saveBet();
    }

    public void generateCols() {
	saveBet();
	List<String> colsBase = new ArrayList();
	/* getBetBase() proporciona la columna base de 14 apuestas. Cada apuesta 
	 * puede constar de 1 signo (sencilla) o multiple (2 o 3 signos)
	 *
	 */
	colsBase.add(getBetBase());
	List<String> colsBaseTemp = new ArrayList();
	/* getBet_grupo1() proporciona un string de 14 caracteres 0 o 1
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
	    if (sequenceMatchesInGroup.contains("1")) {
		/* Se ejecutaran trabajos combinatorios siguientes si existen partidos asignados
		 * al grupo en tratamiento o si el grupo es el numero 6 (global)
		 */

		/* El siguiente bucle 'for' extrae cada una de las posibles columnas base de apuestas.
		 * En nuestra actual versi�n de la aplicaci�n, solamente existe una columna base.
		 */
// Se hace desarrollo de combinaciones por cada columna base que se van formando
		for (String colBase : colsBase) {
		    // Ejemplo de colbase: "1,1,1X,1X2,X2,1,1,X2,X,1X,1X2,1X,1X,1"
		    List<String> betBaseList = Arrays.asList(colBase.split(","));
		    List<String> betGroupList = new ArrayList<>();

		    /* Se extraen de la columna base de apuestas aquellas que correspondan a los
		     * partidos que conforman al grupo que se este tratando y que lo indica el
		     * puntero del bucle del nivel anterior (variable g). Dichas apuestas se agregan
		     * a una lista de trabajo ( betGroupList ).
		     */
		    for (int i = 0; i < sequenceMatchesInGroup.length(); i++) {
			if (sequenceMatchesInGroup.substring(i, i + 1).equals("1")) {
			    /* betGroupList va llenandose con las apuestas de los partidos que conforman 
			     * un grupo
			     */
			    betGroupList.add(betBaseList.get(i));
			}
		    }
		    /* Si el grupo tiene apuestas se ejecuta el siguiente bloque if ...
		     * 
		     */
		    if (betGroupList.size() > 0) {
			/* El metodo getGroupBetsCombinationsSet devuelve un objeto LinkedHashSet
			 * con las posibles combinaciones diferentes de las apuestas de los
			 * partidos de un grupo de modo que la apuesta a un partido acabe
			 * intercambiandose con las del resto de partidos.
			 */
			Set<String> groupBetsCombinationsSet = new LinkedHashSet<>();
			/* Si el grupo es menor que 6 se procede a la combinacion total
			 * de apuestas en el grupo, pero si es el grupo 6 no se realiza
			 * ninguna combinacion de apuestas y se pasa directamente al desarrollo
			 * de las apuestas de algun partido que no haya intervenido en grupos. */
			if (groupNumber < 6) {
			    groupBetsCombinationsSet = getGroupBetsCombinationsSet(betGroupList);
			} else {
			    /* Se guarda en el LinkedHashSet groupBetsCombinationsSet la nueva combinacion */
			    String firstBet = "";
			    for (String str : betGroupList) {
				firstBet = firstBet.concat(str).concat(",");
			    }
			    if (firstBet.endsWith(",")) {
				firstBet = firstBet.substring(0, firstBet.length() - 1);
			    }
			    groupBetsCombinationsSet.add(firstBet);
			}

			/* Bloque de desarrollo de todas las combinaciones del grupo 
			 * 
			 */
			Set<String> developedCombinationsGroup = new LinkedHashSet<>();
			for (String betGroupStr : groupBetsCombinationsSet) {
			    betGroupList = Arrays.asList(betGroupStr.split(","));

			    /* La siguiente sentencia llama al metodo appenMyCombinations para
			     * ir obteniendo todas las* combinaciones posibles de signos de
			     * un partido con todas las combinaciones posibles de cada uno
			     * del resto de partidos del grupo.
			     */
			    developedCombinationsGroup.addAll(appendMyCombinations(betGroupList));

			    /**
			     * FASE DE COMPROBACION DE NUMERO DE SIGNOS 1, X y 2
			     * PERMITIDOS PARA EL GRUPO
			     */
			    if (groupNumber < 6) {
				/* Cuando es la combinaci�n final, no comprueba limitaciones de signos
				 * 
				 */
				Set<String> combinationsTemp = new LinkedHashSet<>();
				for (String combination : developedCombinationsGroup) {
				    if (!"".equals(checkCond(groupNumber, combination))) {
					combinationsTemp.add(combination);
				    }
				}
				developedCombinationsGroup = combinationsTemp;
			    }

			}
			/* Se crean las nuevas columnas base sustituyendo las combinaciones resultantes
			 * v�lidas del grupo que se acaba de tratar
			 * */
			colsBaseTemp.addAll(replaceCombinationsGroup(developedCombinationsGroup, sequenceMatchesInGroup, betBaseList));
		    }
		} // Final de bucle for de colsBase

	    } else {
		/* No hay partidos definidos en el grupo */
		colsBaseTemp.addAll(colsBase);
	    }	    /* Final del if que indica que no hay partidos en el grupo y no se ejecuta ningun desarrollo
	     combinatorio.
	     */

	    colsBase.clear();
	    colsBase.addAll(colsBaseTemp);
	    colsBaseTemp.clear();
	} // Final del bucle for por numberGroup
	/* Se aplican filtros especiales de generacion */
	colsBase = filterOnGenerationMode(colsBase);
	/* Se obtiene el numero de columnas definitivo */
	setNumColumns(colsBase.size());
	/* Se guardan en fichero de texto plano todas las columnas resultantes */
	printFileCols(colsBase);
    }

    /**
     * Genera todas las combinaciones posibles de un grupo de partidos segun las
     * bases de apuesta de cada partido. El numero de combinaciones posibles
     * viene dado por la formula: Nº signos partido 1 * Nº signos partidos 2 *
     * ... * Nº signos partido N
     *
     * @param combinationsList	Lista de combinaciones obtenidas hasta un
     * determinado nº de partido.
     * @param betGroupList	Lista de apuestas de un grupo de partidos
     * @param numberBet	Nº de indice de lista del partido del grupo para añadir
     * sus apuestas a la lista de combinaciones.
     * @return	Lista actualizada de combinaciones que incluye las recibidas como
     * parametro combinadas con las apuestas del ultimo partido tratado.
     */
    private Set<String> appendMyCombinations(List<String> betGroupList) {
	Set<String> previousCombinations = new LinkedHashSet<>();
	previousCombinations.add(""); // Se inicializa con un string vacio para que pueda ejecutarse un bucle como minimo
	for (String bet : betGroupList) {
	    Set<String> newCombinations = new LinkedHashSet<>();
	    for (int idx = 0; idx < bet.length(); idx++) {
		for (String previous : previousCombinations) {
		    newCombinations.add(previous + bet.substring(idx, idx + 1));
		}
	    }
	    previousCombinations = newCombinations;
	}
	return previousCombinations;
    }

    /**
     * Obtiene una secuencia de 14 caracteres 0 y 1, indicando los caracteres 1
     * los partidos que pertenecen al grupo pasado como parametro. Ejemplo: la
     * secuencia '00010110010000' indica que los partidos 4, 6, 7 y 10 (orden en
     * la secuencia de partidos) pertenecen o conforman el grupo pasado como
     * parametro.
     *
     * @param groupNumber	Numero de grupo del que se quiere obtener la secuencia
     * de partidos
     * @return	Secuencia de partidos que conforman el grupo.
     */
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

    /**
     * Comprueba si una combinacion desarrollada simple (un solo signo) de los
     * partidos que conforman un grupo contiene un numero de signos 1, X y 2
     * permitido por las reglas de signos del grupo.
     *
     * @param groupNumber	Numero de grupo al que pertenece la combinacion a
     * comprobar
     * @param chain	Cadena de signos 1, X y 2 de la combinacion que se desea
     * comprobar
     * @return	Si la cadena cumple las reglas se devuelve la cadena recibida. Si
     * no cumple las normas, se devuelve una cadena vacia "".
     */
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

    /**
     * Comprueba si el numero de signos 1, X y 2 se encuentran entre los
     * permitidos para un grupo determinado.
     *
     * @param values1 String indicando los numeros de signos 1 que se permiten.
     * Ejemplo: "1,2,4" indicaria que se permiten combinaciones que contengan 1,
     * 2 o 4 signos 1, devolviendo 'false' para cualquier otro numero de signos
     * 1 que pudieran existir.
     * @param n1	Recibe el numero de signos 1 que se han encontrado en una
     * combinacion y que se desea chequear.
     * @param valuesX String indicando los numeros de signos X que se permiten.
     * Ejemplo: "0,1" indicaria que se permiten combinaciones que contengan
     * ningun o 1 signos X, devolviendo 'false' para cualquier otro numero de
     * signos X que pudieran existir.
     * @param nX	Recibe el numero de signos X que se han encontrado en una
     * combinacion y que se desea chequear.
     * @param values2 String indicando los numeros de signos 2 que se permiten.
     * Ejemplo: "0,2" indicaria que se permiten combinaciones que contengan
     * ningun o 2 signos 2, devolviendo 'false' para cualquier otro numero de
     * signos 2 que pudieran existir.
     * @param n2	Recibe el numero de signos 2 que se han encontrado en una
     * combinacion y que se desea chequear.
     * @return	'true' si n1 esta el strin values1 Y nX esta en el string valuesX
     * Y n2 esta en el string values2. En otro caso, devuelve 'false'
     */
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
	    fw = new PrintWriter(fc.getExternalContext().getRealPath("/WEB-INF/columns/" + getBetSeason() + "_"
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

    private Set<String> getGroupBetsCombinationsSet(List<String> betGroupList) {
	/* El uso de objetos LinkedHashSet es para evitar combinaciones repetidas */
	Set<String> groupBetsSet = new LinkedHashSet<>();
	/* Se guarda en un LinkedHashSet la nueva combinacion */
	String firstBet = "";
	for (String str : betGroupList) {
	    firstBet = firstBet.concat(str).concat(",");
	}
	if (firstBet.endsWith(",")) {
	    firstBet = firstBet.substring(0, firstBet.length() - 1);
	}
	groupBetsSet.add(firstBet);


	for (int i = 1; i < betGroupList.size(); i++) {
	    Set<String> casualSet = new LinkedHashSet<>();
	    for (String groupBet : groupBetsSet) {
		List<String> workList = Arrays.asList(groupBet.split(","));
		for (int j = workList.size() - 1; j > 0; j--) {
		    /* Intercambio de posiciones de las apuestas de dos partidos */
		    Collections.swap(workList, j, j - 1);
		    /* Se guarda en un LinkedHashSet la nueva combinacion */
		    String newBet = "";
		    for (String str : workList) {
			newBet = newBet.concat(str).concat(",");
		    }
		    newBet = newBet.substring(0, newBet.length() - 1);
		    casualSet.add(newBet);
		}
	    }
	    groupBetsSet.addAll(casualSet);
	}
	return groupBetsSet;
    }

// Se crean las nuevas columnas base sustituyendo las combinaciones resultantes 
// v�lidas del grupo que se acaba de tratar
    private List<String> replaceCombinationsGroup(Set<String> developedCombinationsGroup, String sequenceMatchesInGroup, List<String> betBaseList) {
	List<String> colsBaseLocal = new ArrayList<>();
	for (String combination : developedCombinationsGroup) {
	    if (combination.length() > 0) {
		String chain = "";
		Integer s = 0;
		for (int r = 0; r < sequenceMatchesInGroup.length(); r++) {
		    switch (sequenceMatchesInGroup.substring(r, r + 1)) {
			case "0":
			    chain = chain + betBaseList.get(r) + ",";
			    break;
			case "1":
			    chain = chain + combination.substring(s, ++s) + ",";
			    break;
		    }
		}
		if (chain.endsWith(",")) {
		    chain = chain.substring(0, chain.length() - 1);
		}
		colsBaseLocal.add(chain);
	    }
	}
	return colsBaseLocal;
    }

    private List<String> filterOnGenerationMode(List<String> colsBase) {
	List<String> colsBaseLocal = new ArrayList<>();
	switch (getGenerationMode()) {
	    case 0:
		/* generationModeBasic */
		colsBaseLocal = colsBase;
		break;
	    case 1:
		/* generationModeLimited_456_Sign1 */
		setBetDescription("Limited_456_signs_1");
		insertBet();
		for (String colBase : colsBase) {
		    Integer n1 = 0, nX = 0, n2 = 0;
		    for (int i = 0; i < colBase.length(); i++) {
			if (colBase.substring(i, i + 1).equals("1")) {
			    n1++;
			} else if (colBase.substring(i, i + 1).toUpperCase().equals("X")) {
			    nX++;
			} else if (colBase.substring(i, i + 1).equals("2")) {
			    n2++;
			}
		    }
		    if (verifyGroupValues("4,5,6", n1,
			    "", nX,
			    "", n2)) {
			colsBaseLocal.add(colBase);
		    }
		}
		break;
	    default:
		colsBaseLocal = colsBase;
		break;

	}
	return colsBaseLocal;
    }
}
