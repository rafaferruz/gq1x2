package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Team;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;

/**
 * @author RAFAEL FERRUZ
 */
public class TeamService {

    public TeamService() {
    }

    public int save(Team team) throws SQLException {

	return new DAOFactory().getTeamDAO().save(team);
    }

    public Team load(int chaId) {

	return new DAOFactory().getTeamDAO().load(chaId);
    }

    public boolean delete(Team team) {
	return new DAOFactory().getTeamDAO().delete(team);
    }

    public boolean update(Team team) {
	return new DAOFactory().getTeamDAO().update(team);
    }

    public List<Team> getTeamListInChampionship(int chaId) {
	return new DAOFactory().getTeamDAO().loadTeamsInChampionship(chaId);
    }

    public List<SelectItem> getTeamItemListInChampionship(int chaId) {
	List<SelectItem> teamItemList = new ArrayList<>();
	for (Team tea : getTeamListInChampionship(chaId)) {
	    teamItemList.add(new SelectItem(tea.getTeaId(), tea.getTeaName()));
	}
	return teamItemList;
    }

    public Map<Integer,Team> getAllTeamsMap() {
	Map<Integer,Team> teamMap=new HashMap<>();
	for (Team team:new DAOFactory().getTeamDAO().loadAllTeams()){
	    teamMap.put(team.getTeaId(), team);
	}
	return teamMap;
    }
}
