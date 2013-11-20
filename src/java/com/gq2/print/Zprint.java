/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author ESTHER
 */
public class Zprint implements Printable {

    // Set the document type
    private DocFlavor myFormat = null;
    // Create a Doc
    private Doc myDoc = null;    // Build a set of attributes
    private PrintRequestAttributeSet attset = new HashPrintRequestAttributeSet();    // discover the printers that can print the format according to the
    // instructions in the attribute set
    private PrintService[] services = null;
    private Integer numPrinter = null;
// Create a print job from one of the print services (after services are discovered)
    private DocPrintJob docJob = null;
//  Obtain a print job.
    private PrinterJob printJob = PrinterJob.getPrinterJob();
    private String modeLookup = "";
    private List<String> dataPrint = null;
    private Double marginleft = 61.0;
    private Double margintop = 13.0;
    private Double widthcolumn = 31.85;
    private Double widthcell = 10.36;
    private Double highrow = 14.2;
    private Double highcell = 14.2;
    private Integer frompage = 1;
    private Integer topage = 1;

    public Zprint() {
	printJob.setPrintable(this);
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {

	if (pageIndex > (topage - frompage)) {
	    return Printable.NO_SUCH_PAGE;
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
		return Printable.PAGE_EXISTS;
	    }
	    if (((pageIndex + frompage - 1) * 8 + i) == 23) {
		boolean nada = true;
	    }
	    String s = dataPrint.get((pageIndex + frompage - 1) * 8 + i);
	    for (Integer j = 0; j < 14; j++) {
		Double d1 = marginleft + ((i % 8) * widthcolumn);
		Double d2 = margintop + (j * highrow);
		if (s.substring(j, j + 1).toUpperCase().equals("1")) {
		} else if (s.substring(j, j + 1).toUpperCase().equals("X")) {
		    d1 = d1 + widthcell;
		} else if (s.substring(j, j + 1).toUpperCase().equals("2")) {
		    d1 = d1 + (widthcell * 2);
		}
		g2d.drawString("x", Float.parseFloat(d1.toString()), Float.parseFloat(d2.toString()));
	    }
	}
	return Printable.PAGE_EXISTS;
    }

    public void setDocFlavor() {
	this.myFormat = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
    }

    public void setDoc(Object docStream) {
	this.myDoc = new SimpleDoc(docStream, this.myFormat, null);
    }

    public Collection getPrinters(String modeLookup) {
	this.modeLookup = modeLookup;
	List<String> printers = new ArrayList();
	printers.clear();
	if (modeLookup.equals("doc")) {
	    this.services = PrintServiceLookup.lookupPrintServices(myFormat, attset);
	} else if (modeLookup.equals("2D")) {
	    this.services = PrinterJob.lookupPrintServices();
	} else {
	    services = null;
	}
	if (this.services != null && this.services.length > 0) {
	    for (int i = 0; i < this.services.length; i++) {
		printers.add(this.services[i].getName());
	    }
	}
	return printers;
    }

    public void initPrinterJob(Integer numPrinter) throws PrinterException {
	this.numPrinter = numPrinter;
// Create a print job from one of the print services
	if (this.services.length > 0) {
	    if (modeLookup.equals("doc")) {
		this.docJob = services[numPrinter].createPrintJob();
	    } else if (modeLookup.equals("2D")) {
		this.printJob.setPrintService(services[numPrinter]);
	    }
	}
    }

    public void startPrinting() throws PrinterException {
	if (modeLookup.equals("doc")) {
//                this.docJob = services[numPrinter].createPrintJob();
	} else if (modeLookup.equals("2D")) {
	    this.printJob.print();
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
}
