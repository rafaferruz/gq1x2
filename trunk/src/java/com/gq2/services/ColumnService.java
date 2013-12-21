package com.gq2.services;

import com.gq2.beans.ColumnsBean;
import com.gq2.tools.Const;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 * @author RAFAEL FERRUZ
 */
public class ColumnService {

    private FacesContext fc;

    public ColumnService() {
	fc = FacesContext.getCurrentInstance();
    }

    public List<String> generateReduction(ColumnsBean columnsBean) {
	List<String> dataColsWork = new ArrayList();
	if (columnsBean.getSelReduction() != 0) {
	    if (columnsBean.getReduceFromCol() > 1) {
		dataColsWork.addAll(columnsBean.getDataCols().subList(columnsBean.getReduceFromCol() - 1, columnsBean.getNumCols()));
		dataColsWork.addAll(columnsBean.getDataCols().subList(0, columnsBean.getReduceFromCol() - 1));
	    } else {
		dataColsWork.addAll(columnsBean.getDataCols());
	    }
	    int i = 1;
	    int k;
	    int m;
	    while (i < dataColsWork.size()) {
		for (int j = 0; j < i; j++) {
		    char[] s1 = dataColsWork.get(j).toCharArray();
		    char[] s2 = dataColsWork.get(i).toCharArray();
		    m = 0;
		    for (k = 0; k < Const.MAXIMUN_LINES_BY_FORM; k++) {
			if (s1[k] != s2[k]) {
			    m++;
			}
		    }
		    if (m <= columnsBean.getSelReduction()) {
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
	}
	return dataColsWork;

    }

    public void saveReduction(String pathNameFileReduction, Integer selReduction, Integer reduceFromCol, List<String> dataCols) throws IOException {
	PrintWriter fw = null;

	try {
	    fw = new PrintWriter(new FileWriter(pathNameFileReduction));

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

    public List<SelectItem> loadReductionFileNames(String dirPathName, String suffixFileName) {
	List<SelectItem> columnItemList = new ArrayList<>();
	File fr = new File(dirPathName);
	if (fr.exists()) {
	    String combinatedName;
	    File[] fileNames = fr.listFiles();
	    columnItemList.add(new SelectItem("Seleccionar reducci�n", "Seleccionar reducci�n"));
	    for (File nameFile : fileNames) {
		if (nameFile.getName().startsWith(suffixFileName)
			&& nameFile.getName().endsWith(".col")) {
		    combinatedName = nameFile.getName().substring(
			    suffixFileName.length(),
			    nameFile.getName().indexOf(".col"));
		    columnItemList.add(new SelectItem(combinatedName, combinatedName));
		}
	    }
	}
	return columnItemList;
    }

    public List<String> readFileColumns(String columnsFileName) {

	// Abre el fichero externo para leer las columnas
	BufferedReader fr = null;
	List<String> dataCols = new ArrayList();
	try {
	    fr = new BufferedReader(new FileReader(columnsFileName ));
	    String record;
	    while ((record = fr.readLine()) != null) {
		dataCols.add(record);
	    }
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
	return dataCols;
    }
}
