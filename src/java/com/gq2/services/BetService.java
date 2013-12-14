package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.BetLineBean;
import com.gq2.domain.Bet;
import com.gq2.domain.PrePool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author RAFAEL FERRUZ
 */
public class BetService {

    private PrePoolService prePoolService = new PrePoolService();

    public BetService() {
    }

    public int save(Bet bet) {

	return new DAOFactory().getBetDAO().save(bet);
    }

    public Bet load(int betId) {

	return new DAOFactory().getBetDAO().load(betId);
    }

    public boolean delete(Bet bet) {
	return new DAOFactory().getBetDAO().delete(bet);
    }

    public boolean update(Bet bet) {
	return new DAOFactory().getBetDAO().update(bet);
    }

    public List<Bet> loadConditionalBetList(int season, int orderNumber, String description) {
	return new DAOFactory().getBetDAO().loadConditionalBetList(season, orderNumber, description);
    }

    public Bet loadConditionalBet(int season, int orderNumber, String description) {
	return new DAOFactory().getBetDAO().loadConditionalBet(season, orderNumber, description);
    }

    public List<BetLineBean> generateBetLinesFromPrePoolLines(int season, int orderNumber) {
	List<BetLineBean> betLines = new ArrayList();
	if ((season != 0) && (orderNumber != 0)) {
	    List<PrePool> prePoolList = prePoolService.loadPrePoolById(season, orderNumber);
	    for (PrePool prePool : prePoolList) {
		BetLineBean betLine = new BetLineBean();
		betLine.setBliPrePoolId(prePool.getPreId());
		betLine.setBliTeamName1(prePool.getPreTeaName1());
		betLine.setBliTeamName2(prePool.getPreTeaName2());
		betLine.setBliRating4PreviousDiference(prePool.getPreRat4PreviousDiference());
		betLine.setBliPercentWin(prePool.getPrePercentWin());
		betLine.setBliPercentDraw(prePool.getPrePercentDraw());
		betLine.setBliPercentLose(prePool.getPrePercentLose());
		int result = (prePool.getPreScoScore1() - prePool.getPreScoScore2());
		if (result > 0) {
		    betLine.setBliSign("1");
		} else if (result == 0) {
		    betLine.setBliSign("X");
		} else if (result < 0) {
		    betLine.setBliSign("2");
		}
		betLines.add(betLine);
	    }
	}
	return betLines;
    }

    public static List<BetLineBean> sortDataBetLines(List<BetLineBean> dataBetLines, String sortby) {
	switch (sortby) {
	    case "order":
		Comparator byOrder = new dataBetLinesByOrder();
		Collections.sort((List<BetLineBean>) dataBetLines, byOrder);
		break;
	    case "rating":
		Comparator byRating = new dataBetLinesByRating();
		Collections.sort((List<BetLineBean>) dataBetLines, byRating);
		break;
	}
	 		return dataBetLines;
    }

    private static class dataBetLinesByOrder implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
	    BetLineBean a0 = (BetLineBean) arg0;
	    BetLineBean a1 = (BetLineBean) arg1;
	    if (a0.getBliPrePoolId() == a1.getBliPrePoolId()) {
		return 0;
	    }
	    if (a0.getBliPrePoolId() > a1.getBliPrePoolId()) {
		return 1;
	    }
	    if (a0.getBliPrePoolId() < a1.getBliPrePoolId()) {
		return -1;
	    }
	    return 0;
	}
    }

    private static class dataBetLinesByRating implements Comparator {

	/**
	 * Se realiza una ordenaci�n descendente
	 */
	@Override
	public int compare(Object arg0, Object arg1) {
	    BetLineBean a0 = (BetLineBean) arg0;
	    BetLineBean a1 = (BetLineBean) arg1;
	    if (a0.getBliRating4PreviousDiference() == a1.getBliRating4PreviousDiference()) {
		return 0;
	    }
	    if (a0.getBliRating4PreviousDiference() > a1.getBliRating4PreviousDiference()) {
		return -1;
	    }
	    if (a0.getBliRating4PreviousDiference() < a1.getBliRating4PreviousDiference()) {
		return 1;
	    }
	    return 0;
	}
    }
}
