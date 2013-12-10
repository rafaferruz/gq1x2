package com.gq2.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "columns")
@ViewScoped
public class ColumnsBean extends BetBean {

    static final int MAXIMUN_COLUMNS_BY_FORM = 8;
    static final int MAXIMUN_LINES_BY_FORM = 14;
    private List<String> dataCols = new ArrayList();
    private List<Map<String, String>> dataShowCols = new ArrayList<>();
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

    public ColumnsBean() {
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

    private void readFileColumns(String combinatedName) {

	// Abre el fichero externo para leer las columnas
	BufferedReader fr = null;
	dataCols.clear();
	fc = FacesContext.getCurrentInstance();
	fc.getExternalContext().getSessionMap().put("coldataCols", dataCols);

	try {
	    fr = new BufferedReader(new FileReader(fc.getExternalContext().getRealPath("/WEB-INF/" + getBetSeason() + "_"
		    + getBetOrderNumber() + "_" + getBetId() + combinatedName + ".col")));
	    String record;
	    while ((record = fr.readLine()) != null) {
		if (record.startsWith("//selReduction:")) {
		    selReduction = Integer.parseInt(record.substring(record.indexOf(":") + 1));
		} else if (record.startsWith("//reduceFromCol:")) {
		    reduceFromCol = Integer.parseInt(record.substring(record.indexOf(":") + 1));
		} else {
		    dataCols.add(record);
		}
	    }
	    fc = FacesContext.getCurrentInstance();
	    fc.getExternalContext().getSessionMap().put("coldataCols", dataCols);

	} catch (FileNotFoundException e) {
	    fc.getExternalContext().log("URL mal formada");
	} catch (IOException ex) {
	    Logger.getLogger(ColumnsBean.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    if (fr != null) {
		try {
		    fr.close();
		} catch (IOException ex) {
		    Logger.getLogger(ColumnsBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
	setNumCols(dataCols.size());
    }

    public void showCols() {
	this.firstCol = 1;
	this.lastCol = 8;
	readFileColumns("");
	loadReductions();
	setShowColumns(this.firstCol, this.lastCol);
    }

    private void setShowColumns(Integer firstCol, Integer lastCol) {
	dataShowCols.clear();
	if (dataCols.size() > 0) {
	    int maxCols = Math.min(MAXIMUN_COLUMNS_BY_FORM, dataCols.size() - firstCol + 1);
	    int[] targets = new int[maxCols];
	    for (int c = 0; c < MAXIMUN_LINES_BY_FORM; c++) {
		Map<String, String> show = new HashMap();
		for (int i = 1; i <= maxCols; i++) {
		    show.put("col" + i, dataCols.get(firstCol - 2 + i).substring(c, c + 1));
		    if (dataCols.get(firstCol - 2 + i).substring(c, c + 1).toUpperCase().equals(
			    getDataBetLines().get(c).getBliSign().toUpperCase())) {
			// Se incrementa el numero de aciertos en la columna
			targets[i - 1]++;
		    }
		}
		dataShowCols.add(show);
	    }
	    // Se añade los totales de aciertos en cada columna
	    Map<String, String> show = new HashMap();
	    for (int i = 1; i <= maxCols; i++) {
		show.put("col" + i, String.valueOf(targets[i - 1]));
	    }
	    dataShowCols.add(show);

	} else {
	    firstCol = 1;
	    lastCol = firstCol + (MAXIMUN_COLUMNS_BY_FORM - 1);
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
	firstCol = Math.min(firstCol + MAXIMUN_COLUMNS_BY_FORM, (((numCols - 1) / MAXIMUN_COLUMNS_BY_FORM) * MAXIMUN_COLUMNS_BY_FORM) + 1);
	lastCol = firstCol + (MAXIMUN_COLUMNS_BY_FORM - 1);
	setShowColumns(firstCol, lastCol);
    }

    public void moveprev() {
	firstCol = Math.max(firstCol - MAXIMUN_COLUMNS_BY_FORM, 1);
	lastCol = firstCol + (MAXIMUN_COLUMNS_BY_FORM - 1);
	setShowColumns(firstCol, lastCol);
    }

    public void movefirst() {
	firstCol = 1;
	lastCol = firstCol + (MAXIMUN_COLUMNS_BY_FORM - 1);
	setShowColumns(firstCol, lastCol);
    }

    public void movelast() {
	firstCol = (((numCols - 1) / MAXIMUN_COLUMNS_BY_FORM) * MAXIMUN_COLUMNS_BY_FORM) + 1;
	lastCol = firstCol + (MAXIMUN_COLUMNS_BY_FORM - 1);
	setShowColumns(firstCol, lastCol);
    }

    public void movegoto() {
	firstCol = Math.min((((colgoto - 1) / MAXIMUN_COLUMNS_BY_FORM) * MAXIMUN_COLUMNS_BY_FORM) + 1, (((numCols - 1) / MAXIMUN_COLUMNS_BY_FORM) * MAXIMUN_COLUMNS_BY_FORM) + 1);
	lastCol = firstCol + (MAXIMUN_COLUMNS_BY_FORM - 1);
	setShowColumns(firstCol, lastCol);
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
	    int k;
	    int m;
	    while (i < dataColsWork.size()) {
		for (int j = 0; j < i; j++) {
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
	    lastCol = firstCol + (MAXIMUN_COLUMNS_BY_FORM - 1);
	}
	setShowColumns(firstCol, lastCol);
	setNumCols(dataCols.size());

    }

    public void saveReduction() throws IOException {
	PrintWriter fw = null;
	fc = FacesContext.getCurrentInstance();

	try {
	    fw = new PrintWriter(new FileWriter(fc.getExternalContext().getRealPath("/WEB-INF/" + getBetSeason() + "_"
		    + getBetOrderNumber() + "_" + getBetId() + saveReduction + ".col")));

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
//                getBetOrderNumber().toString() + "_" + dataBet.getBet_id().toString() + ".col"));
	File fr = new File(fc.getExternalContext().getRealPath("/WEB-INF/"));
	if (fr.exists()) {
	    File[] fileNames = fr.listFiles();
	    for (File nameFile : fileNames) {
		if (nameFile.getName().startsWith(getBetSeason() + "_"
			+ getBetOrderNumber() + "_" + getBetId().toString())
			&& nameFile.getName().endsWith(".col")) {
		    String combinatedName = nameFile.getName().substring(
			    (getBetSeason() + "_" + getBetOrderNumber() + "_" + getBetId()).length(),
			    nameFile.getName().indexOf(".col"));
		    if (combinatedName.equals("")) {
			combinatedName = "Seleccionar reducci�n";
		    }
		    SelectItem siCombi = new SelectItem(combinatedName, combinatedName);
		    columnItemList.add(siCombi);

		}
	    }
	}
    }

    public void restoreReduction() {
	if (!siCol.equals("Seleccionar reducci�n")) {
	    this.firstCol = 1;
	    this.lastCol = firstCol + (MAXIMUN_COLUMNS_BY_FORM - 1);
	    readFileColumns(siCol);
	    setShowColumns(this.firstCol, this.lastCol);
	    saveReduction = siCol;

	}
    }
}
