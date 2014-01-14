package com.gq2.reports;

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

public class AwardedHitsReportGenerator {

    Company company = new Company();
    Integer year = 2006;
    Collection collectionData;
    Locale locale = new Locale("es", "es");
    List<AwardedHit> awardedHitList;
    FacesContext fc;
    ServletContext context;
    private Logger log = LogManager.getLogger(this.getClass());

    public AwardedHitsReportGenerator() {
    }

    private Map<String, Object> getParameters() {
	Map<String, Object> parameters = new HashMap<>();
	/*
	 * Añadir los parametros que se desean pasar al report
	 */
	return parameters;
    }

    public void writePDFReport(FacesContext fc, List<AwardedHit> awardedHitList,String reportName) {
	this.awardedHitList = awardedHitList;
	this.fc = fc;
	this.context = (ServletContext) fc.getExternalContext().getContext();
 
	HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	response.setContentType("application/pdf");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + "\"");

	ReportBuilder rb = new ReportBuilder();
	Map<String, Object> parameters = getParameters();
	try {
	    rb.writePDFReport(context, locale, company, year, "awardedHits", parameters, awardedHitList, response.getOutputStream());
	} catch (IOException ex) {
	    java.util.logging.Logger.getLogger(AwardedHitsReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    /**
     * Acta con resultados
     */
    public void writeFileReport(Locale locale, HttpServletResponse response, FileFormat fileFormat) throws IOException {
	response.setContentType("application/pdf");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + "awardedHitsReport.pdf" + "\"");

	ReportBuilder rb = new ReportBuilder();
	Map<String, Object> parameters = getParameters();
	response.setContentType(fileFormat.getContentType());
	switch (fileFormat) {
	    case PDF:
		response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
		rb.writePDFReport(context, locale, company, year, "reportName", parameters, this.awardedHitList, response.getOutputStream());
		break;
	    case EXCEL:
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		rb.writeExcelReport(context, locale, company, year, "reportName", parameters, this.awardedHitList, response.getOutputStream());
		break;
	}
    }
}
