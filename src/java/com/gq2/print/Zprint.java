package com.gq2.print;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class Zprint implements Printable {

    // Set the document type
    private DocFlavor myFormat;
    // Create a Doc
    private Doc myDoc;    // Build a set of attributes
    private PrintRequestAttributeSet attset = new HashPrintRequestAttributeSet();    // discover the printers that can print the format according to the
    // instructions in the attribute set
    private PrintService[] services;
// Create a print job from one of the print services (after services are discovered)
    private DocPrintJob docJob;
//  Obtain a print job.
    private PrinterJob printJob;
    private String modeLookup = "";
    private List<String> dataPrint;
    private Double marginleft = 61.0;
    private Double margintop = 13.0;
    private Double widthcolumn = 31.85;
    private Double widthcell = 10.36;
    private Double highrow = 14.2;
    private Double highcell = 14.2;
    private Integer frompage = 1;
    private Integer topage = 1;

    public Zprint() {
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {

	if (pageIndex > (topage - frompage)) {
	    return NO_SUCH_PAGE;
	}
	Boolean lastPage = (pageIndex == (topage - frompage) ? true : false);

	Graphics2D g2d = (Graphics2D) g;
	String font = Font.SANS_SERIF;
	g2d.translate(pf.getImageableX(), pf.getImageableY());
	g2d.setColor(Color.black);
	g2d.setFont(Font.getFont(font));

	for (Integer i = 0; i < 8; i++) {
	    if (lastPage && (topage * 8 >= dataPrint.size()) && (i > (dataPrint.size() - 1) % 8)) {
// Reached end of report
		return PAGE_EXISTS;
	    }
	    if (((pageIndex + frompage - 1) * 8 + i) == 23) {
		boolean nada = true;
	    }
	    String s = dataPrint.get((pageIndex + frompage - 1) * 8 + i);
	    for (Integer j = 0; j < 14; j++) {
		Double d1 = marginleft + ((i % 8) * widthcolumn);
		Double d2 = margintop + (j * highrow);
		switch (s.substring(j, j + 1).toUpperCase()) {
		    case "1":
			break;
		    case "X":
			d1 = d1 + widthcell;
			break;
		    case "2":
			d1 = d1 + (widthcell * 2);
			break;
		}
		g2d.drawString("x", Float.parseFloat(d1.toString()), Float.parseFloat(d2.toString()));
	    }
	}
	return PAGE_EXISTS;
    }

    public void setDocFlavor() {
	this.myFormat = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
    }

    public void setDoc(Object docStream) {
	this.myDoc = new SimpleDoc(docStream, this.myFormat, null);
    }

    public List<String> getPrinters(String modeLookup) {
	this.modeLookup = modeLookup;
	List<String> printers = new ArrayList<>();
	printers.clear();
	switch (modeLookup) {
	    case "doc":
		this.services = PrintServiceLookup.lookupPrintServices(myFormat, attset);
		break;
	    case "2D":
		this.services = PrinterJob.lookupPrintServices();
		break;
	    default:
		services = null;
		break;
	}
	for (PrintService service : Arrays.asList(services)) {
	    printers.add(service.getName());
	}
	return printers;
    }

    public void initPrinterJob(String printerName) throws PrinterException {
	for (PrintService service : Arrays.asList(services)) {
	    if (service.getName().equals(printerName)) {
		// Create a print job from one of the print services
		switch (modeLookup) {
		    case "doc":
			this.docJob = service.createPrintJob();
			break;
		    case "2D":
			this.printJob.setPrintService(service);
			break;
		}
		break;
	    }
	}
    }

    public void startPrinting() throws PrinterException {
	switch (modeLookup) {
	    case "doc":
		break;
	    case "2D":
		this.printJob.print();
		break;
	}

    }

    public void setData(Collection data) {
	this.dataPrint = (List) data;
    }

    public void setHighcell(Double highcell) {
	this.highcell = highcell;
    }

    public void setHighrow(Double highrow) {
	this.highrow = highrow;
    }

    public void setMarginleft(Double marginleft) {
	this.marginleft = marginleft;
    }

    public void setMargintop(Double margintop) {
	this.margintop = margintop;
    }

    public void setWidthcell(Double widthcell) {
	this.widthcell = widthcell;
    }

    public void setWidthcolumn(Double widthcolumn) {
	this.widthcolumn = widthcolumn;
    }

    public Double getHighcell() {
	return highcell;
    }

    public Double getMarginleft() {
	return marginleft;
    }

    public Double getMargintop() {
	return margintop;
    }

    public Double getWidthcell() {
	return widthcell;
    }

    public Double getWidthcolumn() {
	return widthcolumn;
    }

    public Double getHighrow() {
	return highrow;
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

    public PrinterJob getPrintJob() {
	return printJob;
    }

    public void setPrintJob(PrinterJob printJob) {
	this.printJob = printJob;
    }
    
}
