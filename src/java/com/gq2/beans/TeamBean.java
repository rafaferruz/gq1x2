package com.gq2.beans;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Team;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public TeamBean() {
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
	List<Team> teamList = new ArrayList<>();
	teamList = new DAOFactory().getTeamDAO().loadAllTeams();
	rowCount = teamList.size();
	return teamList;
    }

    public String edit() {

	try {
	    load();
	    return "edit";
	} catch (Exception ex) {
	    Logger.getLogger(TeamBean.class.getName()).log(Level.SEVERE, null, ex);
	}
	return "";
    }

    public int save() throws SQLException {

	return new DAOFactory().getTeamDAO().save(this);

    }

    public void load() {

	Team team = new DAOFactory().getTeamDAO().load(teaId);
	if (team != null) {
	    setPropertiesFromTeamObject(team);
	}
    }

    public void retrieveLastContenido() throws SQLException,
	    javax.naming.NamingException {
	/*	Connection connection = null;

	 String selectCustomerStr =
	 "SELECT * "
	 + " FROM EQUIPOS "
	 + "ORDER BY EQU_ID DESC LIMIT 1";

	 PreparedStatement selectStatement = null;

	 try {
	 connection = jdbcHelper.getConnection();

	 // Now verify if the customer is registered or not.
	 selectStatement = connection.prepareStatement(selectCustomerStr);
	 //            selectStatement.setInt(1, teaId);

	 ResultSet rs = selectStatement.executeQuery();

	 if (rs.next()) {
	 setPropiedades(rs);

	 // The customer was registered - we can go straight to the
	 // response page
	 registered = true;
	 } else {
	 registered = false;
	 }
	 rs.close();
	 } finally {
	 jdbcHelper.cleanup(connection, selectStatement, null);
	 }
	 */    }

    public boolean delete() {

	return new DAOFactory().getTeamDAO().delete(this);
    }

    public void retrievePreviousContenido() throws SQLException,
	    javax.naming.NamingException {
	/*	Connection connection = null;

	 String selectCustomerStr =
	 "SELECT * "
	 + " FROM EQUIPOS "
	 + "WHERE EQU_ID < ? ORDER BY EQU_ID DESC LIMIT 1";

	 PreparedStatement selectStatement = null;

	 try {
	 connection = jdbcHelper.getConnection();

	 // Now verify if the customer is registered or not.
	 selectStatement = connection.prepareStatement(selectCustomerStr);
	 selectStatement.setInt(1, teaId);

	 ResultSet rs = selectStatement.executeQuery();

	 if (rs.next()) {
	 setPropiedades(rs);

	 // The customer was registered - we can go straight to the
	 // response page
	 registered = true;
	 } else {
	 registered = false;
	 }
	 rs.close();
	 } finally {
	 jdbcHelper.cleanup(connection, selectStatement, null);
	 }
	 */    }

    public void retrieveNextContenido() throws SQLException,
	    javax.naming.NamingException {
	/*	Connection connection = null;

	 String selectCustomerStr =
	 "SELECT * "
	 + " FROM EQUIPOS "
	 + "WHERE EQU_ID > ? ORDER BY EQU_ID LIMIT 1";

	 PreparedStatement selectStatement = null;

	 try {
	 connection = jdbcHelper.getConnection();

	 // Now verify if the customer is registered or not.
	 selectStatement = connection.prepareStatement(selectCustomerStr);
	 selectStatement.setInt(1, teaId);

	 ResultSet rs = selectStatement.executeQuery();

	 if (rs.next()) {
	 setPropiedades(rs);

	 // The customer was registered - we can go straight to the
	 // response page
	 registered = true;
	 } else {
	 registered = false;
	 }
	 rs.close();
	 } finally {
	 jdbcHelper.cleanup(connection, selectStatement, null);
	 }
	 */    }

    public boolean update() {

	return new DAOFactory().getTeamDAO().update(this);

    }

    public void setPropertiesFromTeamObject(Team team) {
	setTeaId(team.getTeaId());
	setTeaStatus(team.getTeaStatus());
	setTeaCode(team.getTeaCode());
	setTeaName(team.getTeaName());
	setTeaRating(team.getTeaRating());
    }

    public String cancelAction() {
	return "cancelTeam";
    }

    public String newTeam() {
	return "newTeam";
    }

    public String saveNewTeam() throws SQLException {
	save();
	return "newTeam";
    }

    public String deleteTeam() {
	delete();
	return "deleteTeam";
    }

    public String searchTeam() {
	// Implement method
	return "searchTeam";
    }
}
