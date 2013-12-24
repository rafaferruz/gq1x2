package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.ColumnsBean;
import com.gq2.domain.Hit;
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
	switch (columnsBean.getSelReduction()) {
	    case Const.MAXIMUN_LINES_BY_FORM:
		dataColsWork = columnsBean.getDataCols();
		break;
	    case 0:
		dataColsWork = columnsBean.getDataCols();
		break;
	    default:
		if (columnsBean.getReduceFromCol() > 1) {
		    dataColsWork.addAll(columnsBean.getDataCols().subList(columnsBean.getReduceFromCol() - 1, columnsBean.getNumCols()));
		    dataColsWork.addAll(columnsBean.getDataCols().subList(0, columnsBean.getReduceFromCol() - 1));
		} else {
		    dataColsWork.addAll(columnsBean.getDataCols());
		}
		int i = 0;
		int k;
		int diferentBets;
		int minimumDiferences = 1 + Const.MAXIMUN_LINES_BY_FORM - columnsBean.getSelReduction();
		while (i < dataColsWork.size()) {
		    List<String> dataColsToRemove = new ArrayList();

		    for (int j = 1; j < dataColsWork.size(); j++) {
			char[] s1 = dataColsWork.get(j).toCharArray();
			char[] s2 = dataColsWork.get(i).toCharArray();
			diferentBets = 0;
			for (k = 0; k < Const.MAXIMUN_LINES_BY_FORM; k++) {
			    if (s1[k] != s2[k]) {
				diferentBets++;
			    }
			}
			if (diferentBets < minimumDiferences) {
			    dataColsToRemove.add(dataColsWork.get(j));
			}
		    }
		    dataColsWork.removeAll(dataColsToRemove);
		    i++;
		}
		/* Reduciendo columnas hasta un maximo de columnas solicitado */
		if (columnsBean.getMaximumColumnsNumber() != null
			&& columnsBean.getMaximumColumnsNumber() > 0) {
		    while (dataColsWork.size() > columnsBean.getMaximumColumnsNumber()) {
			dataColsWork.remove(new Double(dataColsWork.size() * Math.random()).intValue());
		    }
		}
		break;
	}
	return dataColsWork;
    }

    public void saveReduction(ColumnsBean columns) throws IOException {
	String pathNameFileReduction = columns.getFc().getExternalContext().getRealPath("/WEB-INF/columns/"
		+ columns.getBetSeason() + "_"
		+ columns.getBetOrderNumber() + "_" + columns.getBetId() + columns.getSaveReduction() + ".col");
	PrintWriter fw = null;

	try {
	    fw = new PrintWriter(new FileWriter(pathNameFileReduction));

	    fw.println("//selReduction:" + columns.getSelReduction().toString());
	    fw.println("//reduceFromCol:" + columns.getReduceFromCol().toString());
	    for (String record : columns.getDataCols()) {
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

    public void saveHits(ColumnsBean columns) {
	DAOFactory df = new DAOFactory();
	/* Inicio de la transaccion */
//	df.startTransaction();
	/* Eliminacion de datos de la reduccion de la tabla hits */
	df.getHitDAO().deleteReductionHits(columns.getBetSeason(), columns.getBetOrderNumber(), columns.getBetDescription(), columns.getSaveReduction());
	/* Grabacion de nuevos datos de la reduccion en la tabla hits */
	for (Integer hitsKey : columns.getDataShowHits().keySet()) {
	    Hit hit = new Hit();
	    hit.setHitBetId(columns.getBetId());
	    hit.setHitBetSeason(columns.getBetSeason());
	    hit.setHitBetOrderNumber(columns.getBetOrderNumber());
	    hit.setHitBetDescription(columns.getBetDescription());
	    hit.setHitReductionName(columns.getSaveReduction());
	    hit.setHitTotalColumns(columns.getDataCols().size());
	    hit.setHitHitsNumber(hitsKey);
	    hit.setHitColumnsNumber(columns.getDataShowHits().get(hitsKey).getHitItemList().size());
	    df.getHitDAO().save(hit);
	}
	/* Fin de la transaccion */
//	df.commit();

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
	    fr = new BufferedReader(new FileReader(columnsFileName));
	    String record;
	    while ((record = fr.readLine()) != null) {
		dataCols.add(record);
	    }
	} catch (FileNotFoundException e) {
	    fc.getExternalContext().log("URL mal formada");


	} catch (IOException ex) {
	    Logger.getLogger(ColumnsBean.class
		    .getName()).log(Level.SEVERE, null, ex);
	} finally {
	    if (fr != null) {
		try {
		    fr.close();


		} catch (IOException ex) {
		    Logger.getLogger(ColumnsBean.class
			    .getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
	return dataCols;
    }
}
