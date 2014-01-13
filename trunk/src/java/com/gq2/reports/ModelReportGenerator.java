package com.gq2.reports;

import com.gq2.domain.Company;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




public class ModelReportGenerator {

    Company company= new Company();
    Integer year=2006;
    Collection collectionData;
    Locale locale=new Locale("es","es");
    List<AwardedHit>awardedHitList;
    
	private Logger log = LogManager.getLogger(this.getClass());

	public ModelReportGenerator(List<AwardedHit>awardedHitList) {
	    this.awardedHitList=awardedHitList;
	}


	private Map<String, Object> getParameters() {
		Map<String, Object> parameters = new HashMap<>();
		/*
		 * Añadir los parametros que se desean pasar al report
		 */
		return parameters;
	}


	public void writePDFReport(ServletContext context,  OutputStream outputStream) {
		ReportBuilder rb = new ReportBuilder();
		Map<String, Object> parameters = getParameters();
		rb.writePDFReport(context, locale, company, year, "awardedHits", parameters, awardedHitList, outputStream);
	}

	/**
	 * Acta con resultados
	 */
	public void writeFileReport(ServletContext context, Locale locale, HttpServletResponse response, FileFormat fileFormat) throws IOException {
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
