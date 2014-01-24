package com.gq2.beans;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Team;
import com.gq2.services.TeamService;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author ESTHER
 */
@ManagedBean(name = "team")
@RequestScoped
public class TeamBean extends Team {

    private String runAction;
    private String keyId;
    private Integer rowCount;
    private Integer rowStart;
    private Integer rowChunk;
    private boolean registered;
    private TeamService teamService;

    public TeamBean() {
	teamService = new TeamService();
    }

    public void setRunAction(String value) {
	this.runAction = value;
    }

    public String getRunAction() {
	return runAction;
    }

    public String getKeyId() {
	return keyId;
    }

    public void setKeyId(String keyId) {
	this.keyId = keyId;
    }

    public Integer getRowChunk() {
	return rowChunk;
    }

    public void setRowChunk(Integer rowChunk) {
	this.rowChunk = rowChunk;
    }

    public Integer getRowCount() {
	return rowCount;
    }

    public void setRowCount(Integer rowCount) {
	this.rowCount = rowCount;
    }

    public Integer getRowStart() {
	return rowStart;
    }

    public void setRowStart(Integer rowStart) {
	this.rowStart = rowStart;
    }

    public boolean isRegistered() {
	return registered;
    }

    public List<Team> getTeamList() {
	List<Team> teamList;
	teamList = new DAOFactory().getTeamDAO().loadAllTeams();
	rowCount = teamList.size();
	return teamList;
    }

    public void setPropertiesFromTeamObject(Team team) {
	setTeaId(team.getTeaId());
	setTeaStatus(team.getTeaStatus());
	setTeaCode(team.getTeaCode());
	setTeaName(team.getTeaName());
	setTeaRating(team.getTeaRating());
	setTeaEquivalentNames(team.getTeaEquivalentNames());
    }

    public String cancel() {
	return "cancelTeam";
    }

    public String create() {
	return "newTeam";
    }

    public String save() {
	if (this.getTeaId() > 0) {
	    teamService.update(this);
	    return "listTeam";
	} else {
	    teamService.save(this);
	    return "newTeam";
	}
    }

    public String edit(Team team) {
	if (team != null) {
	    setPropertiesFromTeamObject(team);
	}
	return "editTeam";
    }

    public String delete(Team tea) {
	new DAOFactory().getTeamDAO().delete(tea);
	return "deleteTeam";
    }

    public String search() {
	// Implement method
	return "searchTeam";
    }
}
