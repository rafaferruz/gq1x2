package com.gq2.beans;

import com.gq2.domain.Bet;
import com.gq2.DAO.JDBCHelper;
import com.gq2.services.BetService;
import com.gq2.services.Tasks;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

/**
 *
 * @author ESTHER
 */
@ManagedBean(name = "columnsOLD")
@SessionScoped
public class columnsBean implements Serializable {

    private List<Bet> betList = new ArrayList();
    private Bet dataBet = new Bet();
    private Integer season;
    private Integer orderNumber;
    private String description = "";
    private BetLineBean dataBetLine = new BetLineBean();
    private List<BetLineBean> dataBetLines = new ArrayList();
    private BetGroupBean dataBetGroup = new BetGroupBean();
    private List<BetGroupBean> dataBetGroups = new ArrayList();
    private List<String> dataCols = new ArrayList();
    private List<Map<String, String>> dataShowCols = new ArrayList<Map<String, String>>();
    private Integer firstCol = 1;
    private Integer lastCol = 8;
    private Integer numcol1 = 1;
    private Integer numcol2 = 2;
    private Integer numcol3 = 3;
    private Integer numcol4 = 4;
    private Integer numcol5 = 5;
    private Integer numcol6 = 6;
    private Integer numcol7 = 7;
    private Integer numcol8 = 8;
    private Integer numCols = 0;
    private Integer colgoto = 1;
    private Integer selReduction = 0;
    private Integer reduceFromCol = 1;
    private Boolean reduceRandom = false;
    private String saveReduction = "";
    private List<SelectItem> columnItemList = new ArrayList();
    private String siCol = "";
    private FacesContext fc = null;
        private BetService betService = new BetService();


    /**
     * Creates a new instance of fgenerarSuperTablaBean
     */
    public columnsBean(){
    }

    public List<String> getDataCols() {
	return dataCols;
    }

    public void setDataCols(List<String> dataCols) {
	this.dataCols = dataCols;
    }

    public List<Map<String, String>> getDataShowCols() {
	return dataShowCols;
    }

    public void setDataShowCols(List<Map<String, String>> dataShowCols) {
	this.dataShowCols = dataShowCols;
    }

    public Integer getColgoto() {
	return colgoto;
    }

    public void setColgoto(Integer colgoto) {
	this.colgoto = colgoto;
    }

    public Integer getFirstCol() {
	return firstCol;
    }

    public void setFirstCol(Integer firstCol) {
	this.firstCol = firstCol;
    }

    public Integer getLastCol() {
	return lastCol;
    }

    public void setLastCol(Integer lastCol) {
	this.lastCol = lastCol;
    }

    public Integer getNumcol1() {
	return numcol1;
    }

    public void setNumcol1(Integer numcol1) {
	this.numcol1 = numcol1;
    }

    public Integer getNumcol2() {
	return numcol2;
    }

    public void setNumcol2(Integer numcol2) {
	this.numcol2 = numcol2;
    }

    public Integer getNumcol3() {
	return numcol3;
    }

    public void setNumcol3(Integer numcol3) {
	this.numcol3 = numcol3;
    }

    public Integer getNumcol6() {
	return numcol6;
    }

    public void setNumcol6(Integer numcol6) {
	this.numcol6 = numcol6;
    }

    public Integer getNumcol8() {
	return numcol8;
    }

    public void setNumcol8(Integer numcol8) {
	this.numcol8 = numcol8;
    }

    public Integer getNumcol4() {
	return numcol4;
    }

    public void setNumcol4(Integer numcol4) {
	this.numcol4 = numcol4;
    }

    public Integer getNumcol5() {
	return numcol5;
    }

    public void setNumcol5(Integer numcol5) {
	this.numcol5 = numcol5;
    }

    public Integer getNumcol7() {
	return numcol7;
    }

    public void setNumcol7(Integer numcol7) {
	this.numcol7 = numcol7;
    }

    public Integer getNumCols() {
	return numCols;
    }

    public void setNumCols(Integer numCols) {
	this.numCols = numCols;
    }

    public Integer getReduceFromCol() {
	return reduceFromCol;
    }

    public void setReduceFromCol(Integer reduceFromCol) {
	this.reduceFromCol = reduceFromCol;
    }

    public Boolean getReduceRandom() {
	return reduceRandom;
    }

    public void setReduceRandom(Boolean reduceRandom) {
	this.reduceRandom = reduceRandom;
    }

    public String getSaveReduction() {
	return saveReduction;
    }

    public void setSaveReduction(String saveReduction) {
	this.saveReduction = saveReduction;
    }

    public Integer getSelReduction() {
	return selReduction;
    }

    public void setSelReduction(Integer selReduction) {
	this.selReduction = selReduction;
    }

    public String getSiCol() {
	return siCol;
    }

    public void setSiCol(String siCol) {
	this.siCol = siCol;
    }

    public List<SelectItem> getColumnItemList() {
	return columnItemList;
    }

    public void setColumnItemList(List<SelectItem> columnItemList) {
	this.columnItemList = columnItemList;
    }

    public List<Bet> getBetList() {
	return betList;
    }

    public void setBetList(List<Bet> dataBets) {
	this.betList = dataBets;
    }

    public Bet getDataBet() {
	return dataBet;
    }

    public void setDataBet(Bet dataBet) {
	this.dataBet = dataBet;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Integer getOrderNumber() {
	return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
	this.orderNumber = orderNumber;
    }

    public Integer getSeason() {
	return season;
    }

    public void setSeason(Integer season) {
	this.season = season;
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

    private void readBets(Integer season, Integer orderNumber, String description) {
	setBetList(betService.loadConditionalBetList(season, orderNumber, description));
    }

    public void showBets() {
	readBets(season, orderNumber, description);
    }

/*    public void readBets(Integer season, Integer orderNumber, String description) throws NamingException, SQLException {

	Connection connection = null;
	this.betList.clear();

	String selectCustomerStr =
		"SELECT * "
		+ " FROM BETS ";
	String conditions = "";
	if ((season != null && season != 0) || (orderNumber != null && orderNumber != 0)
		|| (description != null && !description.equals(""))) {
	    conditions = conditions + " WHERE ";
	}
	if (season != null && season != 0) {
	    conditions = conditions + " BET_SEASON = " + season.toString();
	    if ((orderNumber != null && orderNumber != 0) || (description != null && !description.equals(""))) {
		conditions = conditions + " AND ";
	    }
	}
	if (orderNumber != null && orderNumber != 0) {
	    conditions = conditions + " BET_ROUND = " + orderNumber.toString();
	    if ((description != null && !description.equals(""))) {
		conditions = conditions + " AND ";
	    }
	}
	if (description != null && !description.equals("")) {
	    conditions = conditions + " BET_DESCRIPTION LIKE '%" + description + "%' ";
	}
	if (conditions.length() > 0) {
	    selectCustomerStr = selectCustomerStr + conditions;
	}
	selectCustomerStr = selectCustomerStr + " ORDER BY BET_SEASON DESC, BET_ROUND DESC";


	PreparedStatement selectStatement = null;
	try {
	    connection = jdbcHelper.getConnection();
	    // Now verify if the customer is registered or not.
	    selectStatement =
		    connection.prepareStatement(selectCustomerStr);
	    ResultSet rs = selectStatement.executeQuery();
	    betList.clear();
	    while (rs.next()) {
		betList.add(new Bet(rs.getInt("BET_ID")));
	    }
	    rs.close();
	} finally {
	    jdbcHelper.cleanup(connection, selectStatement, null);
	}

    }

    public void showBets() throws NamingException, SQLException {
	readBets(season, orderNumber, description);
    }
*/
    public void insertBet() throws NamingException, SQLException {

	if (!(season != null && season != 0) && (orderNumber != null && orderNumber != 0)
		&& (description != null && !description.equals(""))) {
	    // enviar mensaje
	    return;
	}

	Bet bet = new Bet();
	bet.setBet_season(season);
	bet.setBet_orderNumber(orderNumber);
	bet.setBet_description(description);
	bet.searchRecord(season, orderNumber, description);
	if (!bet.isRegistered()) {
	    bet.insertRecord();
	    if (!bet.isRegistered()) {
		// fallo al insertar el registro
	    } else {
		bet.searchRecord(season, orderNumber, description);
		betList.add(bet);
		dataBetLines.clear();
		dataBetGroups.clear();
	    }
	} else {
	    // ya existe el registro
	}
    }

    public void editBet() {
	setSeason(dataBet.getBetSeason());
	setOrderNumber(dataBet.getBetOrderNumber());
	setDescription(dataBet.getBetDescription());

	// Obtiene las lineas de la apuesta desde la tabla de PrePools 
	generateBetLinesFromPrePoolLines();
//	String[] arrayBase = dataBet.getBetBase().split(",");
	List<String> betBaseList = (dataBet.getBetBase().equals("")
		? new ArrayList<String>()
		: Arrays.asList(dataBet.getBetBase().split(",")));
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

	    if (dataBet.getBetGroup1() != null && dataBet.getBetGroup1().length() > idx) {
		betLine.setBliColumnGroup1((dataBet.getBetGroup1().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup1(false);
	    }
	    if (dataBet.getBetGroup2() != null && dataBet.getBetGroup2().length() > idx) {
		betLine.setBliColumnGroup2((dataBet.getBetGroup2().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup2(false);
	    }
	    if (dataBet.getBetGroup3() != null && dataBet.getBetGroup3().length() > idx) {
		betLine.setBliColumnGroup3((dataBet.getBetGroup3().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup3(false);
	    }
	    if (dataBet.getBetGroup4() != null && dataBet.getBetGroup4().length() > idx) {
		betLine.setBliColumnGroup4((dataBet.getBetGroup4().substring(idx, idx + 1).equals("1") ? true : false));
	    } else {
		betLine.setBliColumnGroup4(false);
	    }
	    if (dataBet.getBetGroup5() != null && dataBet.getBetGroup5().length() > idx) {
		betLine.setBliColumnGroup5((dataBet.getBetGroup5().substring(idx, idx + 1).equals("1") ? true : false));
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
		    dataBetGroup.setBgrGroup1Values(dataBet.getBetGroup1Values1());
		    dataBetGroup.setBgrGroup2Values(dataBet.getBetGroup2Values1());
		    dataBetGroup.setBgrGroup3Values(dataBet.getBetGroup3Values1());
		    dataBetGroup.setBgrGroup4Values(dataBet.getBetGroup4Values1());
		    dataBetGroup.setBgrGroup5Values(dataBet.getBetGroup5Values1());
		    dataBetGroups.add(dataBetGroup);
		    break;
		case 1:
		    dataBetGroup.setBgrSign("X");
		    dataBetGroup.setBgrGroup1Values(dataBet.getBetGroup1ValuesX());
		    dataBetGroup.setBgrGroup2Values(dataBet.getBetGroup2ValuesX());
		    dataBetGroup.setBgrGroup3Values(dataBet.getBetGroup3ValuesX());
		    dataBetGroup.setBgrGroup4Values(dataBet.getBetGroup4ValuesX());
		    dataBetGroup.setBgrGroup5Values(dataBet.getBetGroup5ValuesX());
		    dataBetGroups.add(dataBetGroup);
		    break;
		case 2:
		    dataBetGroup.setBgrSign("2");
		    dataBetGroup.setBgrGroup1Values(dataBet.getBetGroup1Values2());
		    dataBetGroup.setBgrGroup2Values(dataBet.getBetGroup2Values2());
		    dataBetGroup.setBgrGroup3Values(dataBet.getBetGroup3Values2());
		    dataBetGroup.setBgrGroup4Values(dataBet.getBetGroup4Values2());
		    dataBetGroup.setBgrGroup5Values(dataBet.getBetGroup5Values2());
		    dataBetGroups.add(dataBetGroup);
		    break;
	    }
	}

    }

    public void saveBet() {
	sortDataBetLines("order");
	int id = dataBet.getBetId();
	dataBet = new Bet();
	dataBet.setBetId(id);
	fillBet(dataBet);
	betService.update(dataBet);
    }

    private void fillBet(Bet bet) {
	bet.setBetId(dataBet.getBetId());
	bet.setBetSeason(season);
	bet.setBetOrderNumber(orderNumber);
	bet.setBetDescription(description);
	bet.setBetBase("");
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
    public void readPreQ() throws NamingException, SQLException {

	dataBetLines = Tasks.generateBetLinesFromPrePoolLines(season.intValue(), orderNumber.intValue());
    }

    public void generateCols() throws SQLException, NamingException, FileNotFoundException {
	saveBet();
	List<String> colsBase = new ArrayList();
	colsBase.add(dataBet.getBet_base());
	List<String> colsBaseTemp = new ArrayList();
// Por cada grupo de partidos condicionados + un grupo para la combinaci�n general final
	for (int g = 1; g <= 6; g++) {
	    String seq_group = "";
	    switch (g) {
		case 1:
		    seq_group = dataBet.getBet_grupo1();
		    break;
		case 2:
		    seq_group = dataBet.getBet_grupo2();
		    break;
		case 3:
		    seq_group = dataBet.getBet_grupo3();
		    break;
		case 4:
		    seq_group = dataBet.getBet_grupo4();
		    break;
		case 5:
		    seq_group = dataBet.getBet_grupo5();
		    break;
		case 6:
		    seq_group = "11111111111111";
		    break;
	    }
// Se hace desarrollo de combinaciones por cada columna base que se van formando
	    for (String colBase : colsBase) {
		String[] arrayBase = colBase.split(",");
		List<String> bets_group = new ArrayList<String>();
		bets_group.clear();
// Se extraen en una lista de trabajo los partidos que forman el grupo condicionado
		for (int i = 0; i < seq_group.length(); i++) {
		    if (seq_group.substring(i, i + 1).equals("1")) {
			bets_group.add(arrayBase[i]);
		    }
		}
		if (bets_group.size() > 0) {

//                for (int j = 0; j < bets_group.size(); j++) {
		    Iterator bg = bets_group.listIterator();
// Se calcula el n�mero de combinaciones posibles del grupo                
		    Integer numCombi = 1;
		    while (bg.hasNext()) {
			numCombi = numCombi * ((String) bg.next()).length();
		    }
// Se asignan divisores utlizados para calcular los cocientes y restos de cada columna
		    Integer[] dividers = new Integer[bets_group.size()];
		    for (int k = (bets_group.size() - 1); k >= 0; k--) {
			if (k == (bets_group.size() - 1)) {
			    dividers[k] = 1;
			} else {
			    dividers[k] = dividers[k + 1] * bets_group.get(k + 1).length();
			}
			if (k == 0) {
			    break;
			}
		    }
// Se generan las posibles combinaciones del grupo
		    String[] combi_group = new String[numCombi];
		    for (int m = 1; m <= numCombi; m++) {
			Integer F = m - 1;
			combi_group[F] = "";
			for (int n = 0; n < bets_group.size(); n++) {
			    if (n < (bets_group.size() - 1)) {
				combi_group[m - 1] = combi_group[m - 1] + bets_group.get(n).substring((F / dividers[n]), ((F / dividers[n]) + 1));
				F = F % dividers[n];
			    } else {
				combi_group[m - 1] = combi_group[m - 1] + bets_group.get(n).substring((F % bets_group.get(n).length()), ((F % bets_group.get(n).length()) + 1));
			    }
			}
//                    combi_group[m - 1] = checkCond(g, combi_group[m - 1], j);
			if (g < 6) {
// Cuando es la combinaci�n final, no comprueba limitaciones de signos                            
			    combi_group[m - 1] = checkCond(g, combi_group[m - 1]);
			}
		    }
// Se crean las nuevas columnas base sustituyendo las combinaciones resultantes 
// v�lidas del grupo que se acaba de tratar
		    Boolean isCombi = false;
		    for (int p = 0; p < combi_group.length; p++) {
			if (combi_group[p].length() > 0) {
			    isCombi = true;
			    String chain = "";
			    Integer s = 0;
			    for (int r = 0; r < seq_group.length(); r++) {
				if (seq_group.substring(r, r + 1).equals("0")) {
				    chain = chain + arrayBase[r] + ",";
				} else if (seq_group.substring(r, r + 1).equals("1")) {
				    chain = chain + combi_group[p].substring(s, ++s) + ",";
				}
			    }
			    if (chain.endsWith(",")) {
				chain = chain.substring(0, chain.length() - 1);
			    }
			    colsBaseTemp.add(chain);
			}
		    }
		    if (!isCombi) {
			colsBaseTemp.add(colBase);//                    }
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

//    private String checkCond(Integer g, String chain, Integer j) {
    private String checkCond(Integer g, String chain) {

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
	switch (g) {
	    case 1:
		if (("," + dataBet.getBet_g1valores1() + ",").contains("," + n1.toString() + ",")
			&& ("," + dataBet.getBet_g1valoresX() + ",").contains("," + nX.toString() + ",")
			&& ("," + dataBet.getBet_g1valores2() + ",").contains("," + n2.toString() + ",")) {
		    return chain;
		} else {
		    return "";
		}
	    case 2:
		if (("," + dataBet.getBet_g2valores1() + ",").contains("," + n1.toString() + ",")
			&& ("," + dataBet.getBet_g2valoresX() + ",").contains("," + nX.toString() + ",")
			&& ("," + dataBet.getBet_g2valores2() + ",").contains("," + n2.toString() + ",")) {
		    return chain;
		} else {
		    return "";
		}
	    case 3:
		if (("," + dataBet.getBet_g3valores1() + ",").contains("," + n1.toString() + ",")
			&& ("," + dataBet.getBet_g3valoresX() + ",").contains("," + nX.toString() + ",")
			&& ("," + dataBet.getBet_g3valores2() + ",").contains("," + n2.toString() + ",")) {
		    return chain;
		} else {
		    return "";
		}
	    case 4:
		if (("," + dataBet.getBet_g4valores1() + ",").contains("," + n1.toString() + ",")
			&& ("," + dataBet.getBet_g4valoresX() + ",").contains("," + nX.toString() + ",")
			&& ("," + dataBet.getBet_g4valores2() + ",").contains("," + n2.toString() + ",")) {
		    return chain;
		} else {
		    return "";
		}
	    case 5:
		if (("," + dataBet.getBet_g5valores1() + ",").contains("," + n1.toString() + ",")
			&& ("," + dataBet.getBet_g5valoresX() + ",").contains("," + nX.toString() + ",")
			&& ("," + dataBet.getBet_g5valores2() + ",").contains("," + n2.toString() + ",")) {
		    return chain;
		} else {
		    return "";
		}
	}
	return "";
    }

    private void printFileCols(List<String> colsBase) throws FileNotFoundException {

	// Abre el fichero externo para escribir las columnas
//            FileInputStream file = null;
	PrintWriter fw = null;
	fc = FacesContext.getCurrentInstance();

	try {
//                file = new FileInputStream(getServletContext().getRealPath("/WEB-INF/ficheroResultados"));
	    fw = new PrintWriter(fc.getExternalContext().getRealPath("/WEB-INF/" + season.toString() + "_"
		    + orderNumber.toString() + "_" + dataBet.getBet_id().toString() + ".col"));

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

    private void readFileCols(String nameCombi) throws FileNotFoundException, IOException {

	// Abre el fichero externo para escribir las columnas
//            FileInputStream file = null;
	BufferedReader fr = null;
	dataCols.clear();
	fc = FacesContext.getCurrentInstance();
	fc.getExternalContext().getSessionMap().put("coldataCols", dataCols);

	try {
//                file = new FileInputStream(getServletContext().getRealPath("/WEB-INF/ficheroResultados"));
	    fr = new BufferedReader(new FileReader(fc.getExternalContext().getRealPath("/WEB-INF/" + season.toString() + "_"
		    + orderNumber.toString() + "_" + dataBet.getBet_id().toString() + nameCombi + ".col")));
	    String record = "";
	    dataCols.clear();
	    while ((record = fr.readLine()) != null) {
		if (record.startsWith("//selReduction:")) {
		    selReduction = Integer.parseInt(record.substring(record.indexOf(":") + 1));
		} else if (record.startsWith("//reduceFromCol:")) {
		    reduceFromCol = Integer.parseInt(record.substring(record.indexOf(":") + 1));
		} else {
		    dataCols.add(record);
		}
	    }
	    fr.close();
	    fc = FacesContext.getCurrentInstance();
	    fc.getExternalContext().getSessionMap().put("coldataCols", dataCols);

	} catch (FileNotFoundException e) {
	    fc.getExternalContext().log("URL mal formada");
	} finally {
	    if (fr != null) {
		fr.close();
	    }
	}
    }

    public void showCols() throws FileNotFoundException, IOException {
	this.firstCol = 1;
	this.lastCol = 8;
	readFileCols("");
	loadReductions();
	setShowCols(this.firstCol, this.lastCol);
    }

    private void setShowCols(Integer firstCol, Integer lastCol) {
	dataShowCols.clear();
	this.numCols = dataCols.size();
	if (this.numCols > 0) {
	    int maxCols = Math.min(8, this.numCols - firstCol + 1);
	    int[] targets = new int[maxCols];
	    for (int c = 0; c < 14; c++) {
		Map<String, String> show = new HashMap();
		for (int i = 1; i <= maxCols; i++) {
		    show.put("col" + i, dataCols.get(firstCol - 2 + i).substring(c, c + 1));
		    if (dataCols.get(firstCol - 2 + i).substring(c, c + 1).toUpperCase().equals(
			    dataBetLines.get(c).getBli_sign().toUpperCase())) {
			targets[i - 1]++;
		    }
		}
		dataShowCols.add(show);
	    }
	    Map<String, String> show = new HashMap();
	    for (int i = 1; i <= maxCols; i++) {
		show.put("col" + i, String.valueOf(targets[i - 1]));
	    }
	    dataShowCols.add(show);

	} else {
	    firstCol = 1;
	    lastCol = 8;
	}
	numcol1 = firstCol;
	numcol2 = firstCol + 1;
	numcol3 = firstCol + 2;
	numcol4 = firstCol + 3;
	numcol5 = firstCol + 4;
	numcol6 = firstCol + 5;
	numcol7 = firstCol + 6;
	numcol8 = firstCol + 7;
    }

    public void movenext() {
	firstCol = Math.min(firstCol + 8, (((numCols - 1) / 8) * 8) + 1);
	lastCol = firstCol + 7;
	setShowCols(firstCol, lastCol);
    }

    public void moveprev() {
	firstCol = Math.max(firstCol - 8, 1);
	lastCol = firstCol + 7;
	setShowCols(firstCol, lastCol);
    }

    public void movefirst() {
	firstCol = 1;
	lastCol = 8;
	setShowCols(firstCol, lastCol);
    }

    public void movelast() {
	firstCol = (((numCols - 1) / 8) * 8) + 1;
	lastCol = firstCol + 7;
	setShowCols(firstCol, lastCol);
    }

    public void movegoto() {
	firstCol = Math.min((((colgoto - 1) / 8) * 8) + 1, (((numCols - 1) / 8) * 8) + 1);
	lastCol = firstCol + 7;
	setShowCols(firstCol, lastCol);
    }

    public void generateReduction() {
	List<String> dataColsWork = new ArrayList();
	if (selReduction != 0) {
	    if (reduceFromCol > 1) {
		dataColsWork.addAll(dataCols.subList(reduceFromCol - 1, numCols));
		dataColsWork.size();
		dataColsWork.addAll(dataCols.subList(0, reduceFromCol - 1));
	    } else {
		dataColsWork.addAll(dataCols);
	    }
	    int i = 1;
	    int j = 0;
	    int k = 0;
	    int m = 0;
	    while (i < dataColsWork.size()) {
		for (j = 0; j < i; j++) {
		    char[] s1 = dataColsWork.get(j).toCharArray();
		    char[] s2 = dataColsWork.get(i).toCharArray();
		    m = 0;
		    for (k = 0; k < 14; k++) {
			if (s1[k] != s2[k]) {
			    m++;
			}
		    }
		    if (m <= selReduction) {
			dataColsWork.remove(i);
			i--;
			break;
		    }

		}
		i++;
		if (i >= dataColsWork.size()) {
		    break;
		}
	    }
	    dataCols.clear();
	    dataCols.addAll(dataColsWork);
	    fc = FacesContext.getCurrentInstance();
	    fc.getExternalContext().getSessionMap().put("coldataCols", dataCols);
	    numCols = dataCols.size();
	    firstCol = 1;
	    lastCol = 8;
	}
	setShowCols(firstCol, lastCol);
    }

    public void saveReduction() throws IOException {
	PrintWriter fw = null;
	fc = FacesContext.getCurrentInstance();

	try {
	    fw = new PrintWriter(new FileWriter(fc.getExternalContext().getRealPath("/WEB-INF/" + season.toString() + "_"
		    + orderNumber.toString() + "_" + dataBet.getBet_id().toString() + saveReduction + ".col")));

	    fw.println("//selReduction:" + selReduction.toString());
	    fw.println("//reduceFromCol:" + reduceFromCol.toString());
	    for (String record : dataCols) {
		fw.println(record);
	    }
	} catch (FileNotFoundException e) {
	    fc.getExternalContext().log("URL mal formada");
	} finally {
	    if (fw != null) {
		fw.close();
	    }
	}
    }

    private void loadReductions() {
	fc = FacesContext.getCurrentInstance();
	columnItemList.clear();
//        File fr = new File(sc.getRealPath("/WEB-INF/" + season.toString() + "_" +
//                orderNumber.toString() + "_" + dataBet.getBet_id().toString() + ".col"));
	File fr = new File(fc.getExternalContext().getRealPath("/WEB-INF/"));
	if (fr.exists()) {
	    File[] fileNames = fr.listFiles();
	    for (File nameFile : fileNames) {
		if (nameFile.getName().startsWith(season.toString() + "_"
			+ orderNumber.toString() + "_" + dataBet.getBet_id().toString())
			&& nameFile.getName().endsWith(".col")) {
		    String nameCombi = nameFile.getName().substring(
			    (season.toString() + "_" + orderNumber.toString() + "_" + dataBet.getBet_id()).length(),
			    nameFile.getName().indexOf(".col"));
		    if (nameCombi.equals("")) {
			nameCombi = "Seleccionar reducci�n";
		    }
		    SelectItem siCombi = new SelectItem(nameCombi, nameCombi);
		    columnItemList.add(siCombi);

		}
	    }
	}
    }

    public void restoreReduction() throws FileNotFoundException, IOException {
	if (!siCol.equals("Seleccionar reducci�n")) {
	    this.firstCol = 1;
	    this.lastCol = 8;
	    readFileCols(siCol);
	    setShowCols(this.firstCol, this.lastCol);
	    saveReduction = siCol;

	}
    }
}
