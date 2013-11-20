package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Championship;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 * @author RAFAEL FERRUZ
 */
public class ChampionshipService {

    public ChampionshipService() {
    }

    public int save(Championship championship) throws SQLException {

	return new DAOFactory().getChampionshipDAO().save(championship);
    }

    public Championship load(int chaId) {

	return new DAOFactory().getChampionshipDAO().load(chaId);
    }

    public boolean delete(Championship championship) {
	return new DAOFactory().getChampionshipDAO().delete(championship);
    }

    public boolean update(Championship championship) {
	return new DAOFactory().getChampionshipDAO().update(championship);
    }

    public List<Championship> getChampionshipList() {
	return new DAOFactory().getChampionshipDAO().loadAllChampionships();
    }

    public List<SelectItem> getChampionshipItemList() {
	List<SelectItem> championshipItemList = new ArrayList<>();
	championshipItemList.add(new SelectItem(0, "Seleccione Campeonato"));
	for (Championship c : getChampionshipList()) {
	    championshipItemList.add(new SelectItem(c.getChaId(), c.getChaCode()));
	}
	return championshipItemList;
    }

    public List<Integer> getRoundList(Integer chaId) {
	return new DAOFactory().getScoreDAO().loadChampionshipRounds(chaId);
    }

    public List<SelectItem> getRoundItemList(int chaId) {
	List<SelectItem> roundItemList = new ArrayList<>();
	roundItemList.add(new SelectItem(0, "Seleccione Ronda"));
	for (Integer round : getRoundList(chaId)) {
	    roundItemList.add(new SelectItem(round, round.toString()));
	}
	return roundItemList;
    }

    public int getChaIdOfSeason(int season, String division) {
	return new DAOFactory().getChampionshipDAO().getChaIdOfSeason(season, division);
    }
}
