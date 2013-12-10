package com.gq2.beans;

import com.gq2.DAO.DAOFactory;
import com.gq2.print.Zprint;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.Serializable;
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

/**
 *
 * @author ESTHER
 */
@ManagedBean(name = "printBets")
@ViewScoped
public class PrintBetsBean implements Serializable {

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
    private FacesContext fc = null;
    private Zprint zPrint;
    private PrinterJob printJob;

    /**
     * Creates a new instance of fgenerarSuperTablaBean
     */
    public PrintBetsBean() {
	zPrint = new Zprint();
	getListPrinters();
	fc = FacesContext.getCurrentInstance();
	dataCols = (List<String>) fc.getExternalContext().getSessionMap().get("coldataCols");
	topage = (dataCols.size() + 7) / 8;

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
	printers.clear();
	for (String printer : (List<String>) zPrint.getPrinters("2D")) {
	    printers.add(new SelectItem(printer, printer));
	}
    }

    public void printCols() throws PrinterException {

	fc = FacesContext.getCurrentInstance();
	dataCols = (List) fc.getExternalContext().getSessionMap().get("coldataCols");
	topage = (dataCols.size() + 7) / 8;

	if (!selPrinter.equals("")) {
	    printJob = PrinterJob.getPrinterJob();
	    printJob.setPrintable(zPrint);

	    zPrint.setPrintJob(printJob);

	    zPrint.initPrinterJob(selPrinter);

	    zPrint.setData(dataCols);
	    zPrint.setMarginleft(marginleft);
	    zPrint.setMargintop(margintop);
	    zPrint.setWidthcolumn(widthcolumn);
	    zPrint.setWidthcell(widthcell);
	    zPrint.setHighrow(highrow);
	    zPrint.setFrompage(frompage);
	    zPrint.setTopage(topage);
	    if (zPrint.getPrintJob().printDialog()) {
		zPrint.startPrinting();
	    }
	}
    }
    

    public void defaultValues() {

	Zprint zPrint = new Zprint();
	setMarginleft(zPrint.getMarginleft());
	setMargintop(zPrint.getMargintop());
	setWidthcolumn(zPrint.getWidthcolumn());
	setWidthcell(zPrint.getWidthcell());
	setHighrow(zPrint.getHighrow());
	setHighcell(zPrint.getHighcell());
    }

    public void selectPrinter(ValueChangeEvent ev) {
	if (ev.getNewValue() != null) {
	    newPrinter = "printer-" + (String) ev.getNewValue();
	    changedPrinter = true;
	}
    }

    private void getPrinterParams() {
	Map<String, String> printerParamsMap = new DAOFactory().getSetupDAO().loadPrinterParams(newPrinter);

	for (String paramName : printerParamsMap.keySet()) {
	    switch (paramName) {
		case "marginleft":
		    marginleft = Double.parseDouble(printerParamsMap.get(paramName));
		    break;
		case "margintop":
		    margintop = Double.parseDouble(printerParamsMap.get(paramName));
		    break;
		case "widthcolumn":
		    widthcolumn = Double.parseDouble(printerParamsMap.get(paramName));
		    break;
		case "widthcell":
		    widthcell = Double.parseDouble(printerParamsMap.get(paramName));
		    break;
		case "highrow":
		    highrow = Double.parseDouble(printerParamsMap.get(paramName));
		    break;
		case "highcell":
		    highcell = Double.parseDouble(printerParamsMap.get(paramName));
		    break;
	    }
	}
    }

    public void savePrinter() {

	Map<String, String> valores = new HashMap<>();
	Set<String> keys = new HashSet();
	valores.put("marginleft", marginleft.toString());
	valores.put("margintop", margintop.toString());
	valores.put("widthcolumn", widthcolumn.toString());
	valores.put("widthcell", widthcell.toString());
	valores.put("highrow", highrow.toString());
	valores.put("highcell", highcell.toString());

	new DAOFactory().getSetupDAO().saveOrUpdatePrinterParams("printer-" + selPrinter, valores);
    }
}
