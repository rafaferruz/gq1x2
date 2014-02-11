package com.gq2.reports;

import com.gq2.beans.ScoreBean;
import com.gq2.domain.Company;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChampionshipScoresReportGenerator {

    Company company = new Company();
    Integer year;
    Collection collectionData;
    Locale locale = new Locale("es", "es");
    List<ScoreBean> championshipScoreList;
    FacesContext fc;
    ServletContext context;
    private Logger log = LogManager.getLogger(this.getClass());

    public ChampionshipScoresReportGenerator() {
    }

    private Map<String, Object> getParameters() {
	Map<String, Object> parameters = new HashMap<>();
	/*
	 * Añadir los parametros que se desean pasar al report
	 */
	return parameters;
    }

    public void writePDFReport(FacesContext fc, List<ScoreBean> championshipScoreList, String championshipDescription, String reportName) {
	this.championshipScoreList = championshipScoreList;
	this.fc = fc;
	this.context = (ServletContext) fc.getExternalContext().getContext();

	HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	response.setContentType("application/pdf");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + "\"");

	ReportBuilder rb = new ReportBuilder();
	Map<String, Object> parameters = getParameters();
	parameters.put("championshipDescription", championshipDescription);
	try {
	    rb.writePDFReport(context, locale, company, year, "championshipScores", parameters, championshipScoreList, response.getOutputStream());
	} catch (IOException ex) {
	    log.error(ex);
	}
    }

    /**
     * Acta con resultados
     */
    public void writeFileReport(Locale locale, HttpServletResponse response, FileFormat fileFormat) throws IOException {
	response.setContentType("application/pdf");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + "championshipScoresReport.pdf" + "\"");

	ReportBuilder rb = new ReportBuilder();
	Map<String, Object> parameters = getParameters();
	response.setContentType(fileFormat.getContentType());
	switch (fileFormat) {
	    case PDF:
		response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
		rb.writePDFReport(context, locale, company, year, "championshipScoresReport", parameters, this.championshipScoreList, response.getOutputStream());
		break;
	    case EXCEL:
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		rb.writeExcelReport(context, locale, company, year, "championshipScoresReport", parameters, this.championshipScoreList, response.getOutputStream());
		break;
	}
    }
}
