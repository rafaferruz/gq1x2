package com.gq2.beans;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Classification;
import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "classification")
@RequestScoped
public class ClassificationBean extends Classification {

    private String teamName;
    private String runCommand;
    private boolean registered;

    public ClassificationBean() {
    }

    public String getTeamName() {
	return teamName;
    }

    public void setTeamName(String teamName) {
	this.teamName = teamName;
    }

    public String getRunCommand() {
	return runCommand;
    }

    public void setRunCommand(String runCommand) {
	this.runCommand = runCommand;
    }

    public boolean isRegistered() {
	return registered;
    }

    public void setRegistered(boolean registered) {
	this.registered = registered;
    }

    public Classification getClassification(int id) {
	return new DAOFactory().getClassificationDAO().load(id);
    }
}
