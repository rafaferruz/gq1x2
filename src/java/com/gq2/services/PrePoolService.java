package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.PrePool;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RAFAEL FERRUZ
 */
public class PrePoolService {

    public PrePoolService() {
    }

    public int save(PrePool prePool) {

	return new DAOFactory().getPrePoolDAO().save(prePool);
    }

    public PrePool load(int chaId) {

	return new DAOFactory().getPrePoolDAO().load(chaId);
    }

    public int delete(PrePool prePool) {
	return new DAOFactory().getPrePoolDAO().delete(prePool);
    }

    public boolean update(PrePool prePool) {
	return new DAOFactory().getPrePoolDAO().update(prePool);
    }

    public List<PrePool> loadPrePool(int season, int order_number) {
	return new DAOFactory().getPrePoolDAO().loadSeasonPrePoolListOrderByRating(season, order_number);
    }

    public List<PrePool> loadPrePoolById(int season, int order_number) {
	return new DAOFactory().getPrePoolDAO().loadSeasonPrePoolListOrderById(season, order_number);
    }

    public int deleteMatch(PrePool prePool) {
	return new DAOFactory().getPrePoolDAO().deleteMatch(prePool);
    }

    public List<PrePool> loadRoundPrePoolList(int season, int order_number) {
	return new DAOFactory().getPrePoolDAO().loadRoundPrePoolList(season, order_number);
    }

    public int deleteSeasons(int first_season, int last_season) {
	return new DAOFactory().getPrePoolDAO().deleteSeasons(first_season, last_season);
    }

    public List<Integer> loadPrePoolOrderNumberList(Integer season, Integer firstRound, Integer lastRound) {
	List<Integer> orderNumberList = new DAOFactory().getPrePoolDAO().loadPrePoolOrderNumberList(season);
	List<Integer> resultList = new ArrayList<>();
	for (Integer i : orderNumberList) {
	    if (i >= firstRound && i <= lastRound) {
		resultList.add(i);
	    }
	}
	return resultList;
    }

    public List<PrePool> loadNlastPreTool(Integer season, Integer orderNumber, Integer orderNumber4) {
	return new DAOFactory().getPrePoolDAO().loadNlastPreTool(season, orderNumber, orderNumber4);
    }
}
