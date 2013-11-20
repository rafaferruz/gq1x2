/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gq2.beans;

import com.gq2.DAO.JDBCHelper;
import com.gq2.print.Zprint;
import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

/**
 *
 * @author ESTHER
 */
@ManagedBean(name = "printBets")
@ViewScoped
public class printBetsBean implements Serializable {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "GRANQ";
    private List<String> dataCols = new ArrayList();
    private Double margintop = 0.0;
    private Double marginleft = 0.0;
    private Double widthcolumn = 0.0;
    private Double widthcell = 0.0;
    private Double highrow = 0.0;
    private Double highcell = 0.0;
    private Integer frompage = 1;
    private Integer topage = 1;
    private Boolean defaultPrinter = false;
    private Boolean changedPrinter = false;
    private String newPrinter = "";
    private String selPrinter = "";
    private List<SelectItem> printers = new ArrayList();
    private JDBCHelper jdbcHelper;
    private FacesContext fc = null;

    /**
     * Creates a new instance of fgenerarSuperTablaBean
     */
    public printBetsBean() throws NamingException, SQLException, FileNotFoundException, IOException {
	log("constructed");
	jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
	getListPrinters();
	fc = FacesContext.getCurrentInstance();
	dataCols = (List<String>) fc.getExternalContext().getSessionMap().get("coldataCols");
	topage = (dataCols.size() + 7) / 8;

    }

    // Helpers ------------------------------------------------------------------------------------
    private void log(Object object) {
	String methodName = new Exception().getStackTrace()[1].getMethodName();
	System.out.println("MyBean " + methodName + ": " + object);
    }

    public Integer getFrompage() {
	return frompage;
    }

    public void setFrompage(Integer frompage) {
	this.frompage = frompage;
    }

    public Integer getTopage() {
	return topage;
    }

    public void setTopage(Integer topage) {
	this.topage = topage;
    }

    public Double getHighcell() {
	if (changedPrinter) {
	    getPrinterParams();
	    changedPrinter = false;
	}
	return highcell;
    }

    public void setHighcell(Double highcell) {
	this.highcell = highcell;
    }

    public Double getHighrow() {
	if (changedPrinter) {
	    getPrinterParams();
	    changedPrinter = false;
	}
	return highrow;
    }

    public void setHighrow(Double highrow) {
	this.highrow = highrow;
    }

    public Double getMarginleft() {
	if (changedPrinter) {
	    getPrinterParams();
	    changedPrinter = false;
	}
	return marginleft;
    }

    public void setMarginleft(Double marginleft) {
	this.marginleft = marginleft;
    }

    public Double getMargintop() {
	if (changedPrinter) {
	    getPrinterParams();
	    changedPrinter = false;
	}
	return margintop;
    }

    public void setMargintop(Double margintop) {
	this.margintop = margintop;
    }

    public List<SelectItem> getPrinters() {
	return printers;
    }

    public void setPrinters(List<SelectItem> printers) {
	this.printers = printers;
    }

    public String getSelPrinter() {
	return selPrinter;
    }

    public void setSelPrinter(String selPrinter) {
	this.selPrinter = selPrinter;
    }

    public Double getWidthcell() {
	if (changedPrinter) {
	    getPrinterParams();
	    changedPrinter = false;
	}
	return widthcell;
    }

    public void setWidthcell(Double widthcell) {
	this.widthcell = widthcell;
    }

    public Double getWidthcolumn() {
	if (changedPrinter) {
	    getPrinterParams();
	    changedPrinter = false;
	}
	return widthcolumn;
    }

    public void setWidthcolumn(Double widthcolumn) {
	this.widthcolumn = widthcolumn;
    }

    public Boolean getDefaultPrinter() {
	return defaultPrinter;
    }

    public void setDefaultPrinter(Boolean defaultPrinter) {
	this.defaultPrinter = defaultPrinter;
    }

    public List<String> getDataCols() {
	return dataCols;
    }

    public void setDataCols(List<String> dataCols) {
	this.dataCols = dataCols;
    }

    private void getListPrinters() {
	log("ListPrinters");
	Zprint pcols = new Zprint();
	printers.clear();
	List<String> listPrinters = (List<String>) pcols.getPrinters("2D");
	for (int i = 0; i < listPrinters.size(); i++) {
	    printers.add(new SelectItem(listPrinters.get(i), listPrinters.get(i)));
	}
	pcols = null;
    }

    public void printCols() throws PrinterException {

	fc = FacesContext.getCurrentInstance();
	dataCols = (List) fc.getExternalContext().getSessionMap().get("coldataCols");
	topage = (dataCols.size() + 7) / 8;

	log("printCols");
	Zprint pcols = new Zprint();
	List<String> listPrinters = (List<String>) pcols.getPrinters("2D");
	for (int i = 0; i < listPrinters.size(); i++) {
	    if (selPrinter.equals(listPrinters.get(i))) {
		pcols.initPrinterJob(i);
		break;
	    }
	}
	pcols.setData(dataCols);
	pcols.setMarginleft(marginleft);
	pcols.setMargintop(margintop);
	pcols.setWidthcolumn(widthcolumn);
	pcols.setWidthcell(widthcell);
	pcols.setHighrow(highrow);
	pcols.setFrompage(frompage);
	pcols.setTopage(topage);

	pcols.startPrinting();
    }

    public void defaultValues() {

	log("defaultValues");
	Zprint dv = new Zprint();
	this.marginleft = dv.getMarginleft();
	this.margintop = dv.getMargintop();
	this.widthcolumn = dv.getWidthcolumn();
	this.widthcell = dv.getWidthcell();
	this.highrow = dv.getHighrow();
	this.highcell = dv.getHighcell();
	/*        margintopComp.setValue(this.margintop);
	 marginleftComp.setValue(this.marginleft);
	 widthcolumnComp.setValue(this.widthcolumn);
	 widthcellComp.setValue(this.widthcell);
	 highrowComp.setValue(this.highrow);
	 highcellComp.setValue(this.highcell);
	 */
	dv = null;
    }

    public void selectPrinter(ValueChangeEvent ev) throws NamingException, SQLException {
	log(ev.getOldValue() + " to " + ev.getNewValue());
	if (ev.getNewValue() != null) {
	    newPrinter = "printer-" + (String) ev.getNewValue();
	    changedPrinter = true;
	    return;
	}
    }

    private void getPrinterParams() {
	Connection connection = null;

	String selectCustomerStr =
		"SELECT * "
		+ " FROM CONFIGURACION "
		+ "WHERE CFG_SECCION = ? ";

	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
	    selectStatement.setString(1, newPrinter);

	    ResultSet rs = selectStatement.executeQuery();

	    while (rs.next()) {
		if (rs.getString("CFG_PARAMETRO").equals("marginleft")) {
		    marginleft = Double.parseDouble(rs.getString("CFG_VALOR"));
		} else if (rs.getString("CFG_PARAMETRO").equals("margintop")) {
		    margintop = Double.parseDouble(rs.getString("CFG_VALOR"));
		} else if (rs.getString("CFG_PARAMETRO").equals("widthcolumn")) {
		    widthcolumn = Double.parseDouble(rs.getString("CFG_VALOR"));
		} else if (rs.getString("CFG_PARAMETRO").equals("widthcell")) {
		    widthcell = Double.parseDouble(rs.getString("CFG_VALOR"));
		} else if (rs.getString("CFG_PARAMETRO").equals("highrow")) {
		    highrow = Double.parseDouble(rs.getString("CFG_VALOR"));
		} else if (rs.getString("CFG_PARAMETRO").equals("highcell")) {
		    highcell = Double.parseDouble(rs.getString("CFG_VALOR"));
		}
	    }
	    rs.close();
	    jdbcHelper.cleanup(connection, selectStatement, null);
	} catch (Exception ex) {
	}

    }
// Skip validation of non-immediate components and invocation of the submit() method.
//        FacesContext.getCurrentInstance().renderResponse();

    public void savePrinter() throws NamingException, SQLException {

	log("savePrinter");

	Map<String, String> valores = new HashMap<String, String>();
	Set<String> keys = new HashSet();
	valores.put("marginleft", marginleft.toString());
	valores.put("margintop", margintop.toString());
	valores.put("widthcolumn", widthcolumn.toString());
	valores.put("widthcell", widthcell.toString());
	valores.put("highrow", highrow.toString());
	valores.put("highcell", highcell.toString());
	keys.addAll(valores.keySet());

	Connection connection = null;

	String insertStatementStr = "";
	PreparedStatement insertStatement = null;
	String selectStatementStr =
		"SELECT * FROM CONFIGURACION WHERE " + ""
		+ "CFG_SECCION = ? AND "
		+ "CFG_PARAMETRO = ? ";
	PreparedStatement selectStatement = null;



	try {
	    connection = jdbcHelper.getConnection();


//            insertStatement.setInt(1, cfg_id);


	    for (String k : keys) {
		selectStatement = connection.prepareStatement(selectStatementStr);
		selectStatement.setString(1, "printer-" + selPrinter);
		selectStatement.setString(2, k);
		ResultSet rs = selectStatement.executeQuery();


		if (rs.next()) {
		    insertStatementStr =
			    "UPDATE CONFIGURACION SET CFG_VALOR = ? WHERE "
			    + "CFG_SECCION = ? AND "
			    + "CFG_PARAMETRO = ? ";
		    insertStatement = connection.prepareStatement(insertStatementStr);
		    insertStatement.setString(1, valores.get(k));
		    insertStatement.setString(2, "printer-" + selPrinter);
		    insertStatement.setString(3, k);
		    insertStatement.executeUpdate();


		} else {
		    insertStatementStr =
			    "INSERT INTO CONFIGURACION VALUES( 0, ?, ?, ?)";
		    insertStatement = connection.prepareStatement(insertStatementStr);
		    insertStatement.setString(1, "printer-" + selPrinter);
		    insertStatement.setString(2, k);
		    insertStatement.setString(3, valores.get(k));
		    insertStatement.executeUpdate();


		}
		rs.close();


	    }
	} finally {
	    jdbcHelper.cleanup(connection, insertStatement, selectStatement);

	}
    }
}
