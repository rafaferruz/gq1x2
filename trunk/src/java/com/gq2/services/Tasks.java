/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gq2.services;

import com.gq2.DAO.JDBCHelper;
import com.gq2.domain.Bet;
import com.gq2.beans.BetGroupBean;
import com.gq2.beans.BetLineBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author ESTHER
 */
public class Tasks {

    private static final String DATA_SOURCE_NAME = "GRANQ";
    private static JDBCHelper jdbcHelper;

    public static void Tasks() {
    }

    /**
     * Devuelve una colecci�n (List) con los partidos que forman la quiniela de
     * una determinada temporada y jornada
     *
     * @param season Temporada de quinielas
     * @param round Jornada
     * @return Lista de objetos BetLineBean con cada uno de los partidos que
     * componen la jornada
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */

    /**
     *
     * @param season Temporada de quinielas
     * @param round Jornada de quiniela
     * @param description Descripci�n o referencia de una apuesta
     * @param dataBet Registro de Apuesta activo
     * @param dataBetLines Lista de Lineas de Apuesta (en la actualidad 14)
     * @param dataBetGroups Lista con la definic�on de signos por grupo de
     * restricciones
     * @return Un objeto Bet con los datos actualizados si la actualizacion
     * se ha realizado correctamente o null en caso contrario.
     */
    public static Bet updateBet(int season, int round, String description, Bet dataBet, List dataBetLines, List<BetGroupBean> dataBetGroups) {

	BetLineBean dataBetLine;
	if (dataBet.getBet_id() != null && dataBet.getBet_id() > 0) {

	    if (season != 0 && round != 0
		    && (description != null && !description.equals(""))) {
		dataBet.setBet_season(season);
		dataBet.setBet_round(round);
		dataBet.setBet_description(description);

		dataBet.setBet_base("");
		dataBet.setBet_grupo1("");
		dataBet.setBet_grupo2("");
		dataBet.setBet_grupo3("");
		dataBet.setBet_grupo4("");
		dataBet.setBet_grupo5("");
		Iterator itBetLines = dataBetLines.listIterator();
		while (itBetLines.hasNext()) {
		    dataBetLine = (BetLineBean) itBetLines.next();

		    dataBet.setBet_base(dataBet.getBet_base() + dataBetLine.getBli_colbase() + (itBetLines.hasNext() ? "," : ""));
		    dataBet.setBet_grupo1(dataBet.getBet_grupo1() + (dataBetLine.getBli_colg1() ? "1" : "0"));
		    dataBet.setBet_grupo2(dataBet.getBet_grupo2() + (dataBetLine.getBli_colg2() ? "1" : "0"));
		    dataBet.setBet_grupo3(dataBet.getBet_grupo3() + (dataBetLine.getBli_colg3() ? "1" : "0"));
		    dataBet.setBet_grupo4(dataBet.getBet_grupo4() + (dataBetLine.getBli_colg4() ? "1" : "0"));
		    dataBet.setBet_grupo5(dataBet.getBet_grupo5() + (dataBetLine.getBli_colg5() ? "1" : "0"));
		}

		dataBet.setBet_g1valores1(dataBetGroups.get(0).getBgr_g1values());
		dataBet.setBet_g1valoresX(dataBetGroups.get(1).getBgr_g1values());
		dataBet.setBet_g1valores2(dataBetGroups.get(2).getBgr_g1values());
		dataBet.setBet_g2valores1(dataBetGroups.get(0).getBgr_g2values());
		dataBet.setBet_g2valoresX(dataBetGroups.get(1).getBgr_g2values());
		dataBet.setBet_g2valores2(dataBetGroups.get(2).getBgr_g2values());
		dataBet.setBet_g3valores1(dataBetGroups.get(0).getBgr_g3values());
		dataBet.setBet_g3valoresX(dataBetGroups.get(1).getBgr_g3values());
		dataBet.setBet_g3valores2(dataBetGroups.get(2).getBgr_g3values());
		dataBet.setBet_g4valores1(dataBetGroups.get(0).getBgr_g4values());
		dataBet.setBet_g4valoresX(dataBetGroups.get(1).getBgr_g4values());
		dataBet.setBet_g4valores2(dataBetGroups.get(2).getBgr_g4values());
		dataBet.setBet_g5valores1(dataBetGroups.get(0).getBgr_g5values());
		dataBet.setBet_g5valoresX(dataBetGroups.get(1).getBgr_g5values());
		dataBet.setBet_g5valores2(dataBetGroups.get(2).getBgr_g5values());
		try {
		    dataBet.updateRecord();
		    return dataBet;
		} catch (SQLException ex) {
		} catch (NamingException ex) {
		}

	    }
	}
	return null;
    }

    public static Collection sortDataBetLines(Collection dataBetLines, String sortby) {

	if (sortby.equals("order")) {
	    Comparator order = new dataBetLinesByOrder();
	    Collections.sort((List<BetLineBean>) dataBetLines, order);
	    return dataBetLines;
	} else if (sortby.equals("rating")) {
	    Comparator order = new dataBetLinesByRating();
	    Collections.sort((List<BetLineBean>) dataBetLines, order);
	    return dataBetLines;
	}
	return null;
    }

    static class dataBetLinesByOrder implements Comparator {

	public int compare(Object arg0, Object arg1) {
	    BetLineBean a0 = (BetLineBean) arg0;
	    BetLineBean a1 = (BetLineBean) arg1;
	    if (a0.getBli_preq_id() == a1.getBli_preq_id()) {
		return 0;
	    }
	    if (a0.getBli_preq_id() > a1.getBli_preq_id()) {
		return 1;
	    }
	    if (a0.getBli_preq_id() < a1.getBli_preq_id()) {
		return -1;
	    }
	    return 0;
	}
    }

    static class dataBetLinesByRating implements Comparator {

	/**
	 * Se realiza una ordenaci�n descendente
	 */
	public int compare(Object arg0, Object arg1) {
	    BetLineBean a0 = (BetLineBean) arg0;
	    BetLineBean a1 = (BetLineBean) arg1;
	    if (a0.getBli_rat4_dif_prev() == a1.getBli_rat4_dif_prev()) {
		return 0;
	    }
	    if (a0.getBli_rat4_dif_prev() > a1.getBli_rat4_dif_prev()) {
		return -1;
	    }
	    if (a0.getBli_rat4_dif_prev() < a1.getBli_rat4_dif_prev()) {
		return 1;
	    }
	    return 0;
	}
    }
}
