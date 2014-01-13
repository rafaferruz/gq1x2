package com.gq2.servlets;

import com.gq2.reports.AwardedHit;
import com.gq2.reports.ModelReportGenerator;
import com.gq2.reports.ReportService;
import com.gq2.services.ImportScoresAncientProcess;
import com.gq2.services.ImportScoresGranqProcess;
import com.gq2.services.ImportScoresProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RAFAEL FERRUZ
 */
@WebServlet("/AwardedHitsReport")

public class ModelReportServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	
	/*
	 * Se obtiene un objeto ReportService
	 */
	ReportService reportService =new ReportService();
	
	/*
	 * Se obtiene la lista de datos para enviar al report
	 */
	List<AwardedHit> awardedHitList= reportService.getAwardedHitList();

/*
 * Se lanza el generador del report	
 */
		ModelReportGenerator modelReportGenerator = new ModelReportGenerator(awardedHitList);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + "awardedHitsReport.pdf" + "\"");

		// Pinta el pdf a la salida
		modelReportGenerator.writePDFReport(this.getServletContext(), response.getOutputStream());
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>
}
