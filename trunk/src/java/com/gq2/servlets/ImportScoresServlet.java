/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gq2.servlets;

import com.gq2.services.ImportScoresAncientProcess;
import com.gq2.services.ImportScoresGranqProcess;
import com.gq2.services.ImportScoresProcess;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Lanza el proceso de integracion de campeonatos, equipos y resultados en la BD
 * a partir de un fichero de texto proporcionado por un tercero.
 *
 */
@WebServlet("/ImportScores")
public class ImportScoresServlet extends HttpServlet {

    public void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws IOException, Exception {

	String fileScores;
	ImportScoresProcess isp;

	switch (request.getParameter("source")) {
	    case "ancient":
		fileScores = getServletContext().getRealPath("/WEB-INF/ficheroResultados");
		isp = new ImportScoresAncientProcess();
		isp.doImport(fileScores);
		break;
	    case "granq":
		fileScores = getServletContext().getRealPath("/WEB-INF/resultados_futbol_granq.txt");
		isp = new ImportScoresGranqProcess();
		isp.doImport(fileScores);
		break;
	    default:
	}

    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
	try {
	    processRequest(request, response);
	} catch (Exception ex) {
	    Logger.getLogger(ImportScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
	try {
	    processRequest(request, response);
	} catch (Exception ex) {
	    Logger.getLogger(ImportScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}
