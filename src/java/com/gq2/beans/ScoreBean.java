package com.gq2.beans;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Score;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "score")
@ViewScoped

public class ScoreBean extends Score  implements Serializable{

    private String scoTeamName1;
    private String scoTeamName2;
    private String command;
    private boolean registered;
    private boolean scoMarkDelete;

    public ScoreBean() {
    }

    public String getScoTeamName1() {
	return scoTeamName1;
    }

    public void setScoTeamName1(String scoTeamName1) {
	this.scoTeamName1 = scoTeamName1;
    }

    public String getScoTeamName2() {
	return scoTeamName2;
    }

    public void setScoTeamName2(String scoTeamName2) {
	this.scoTeamName2 = scoTeamName2;
    }

    public void setCommand(String value) {
	this.command = value;
    }

    public String getCommand() {
	return command;
    }

    public boolean isRegistered() {
	return registered;
    }

    public boolean isScoMarkDelete() {
	return scoMarkDelete;
    }

    public void setScoMarkDelete(boolean scoMarkDelete) {
	this.scoMarkDelete = scoMarkDelete;
    }

    public int saveScore() {

	return new DAOFactory().getScoreDAO().save((Score) this);

    }

    public Score loadScore() {

	Score score = new DAOFactory().getScoreDAO().load(getScoId());
	if (score != null) {
	    setScoId(score.getScoId());
	    setScoDate(score.getScoDate());
	    setScoChaId(score.getScoChaId());
	    setScoRound(score.getScoRound());
	    setScoTeam1Id(score.getScoTeam1Id());
	    setScoTeam2Id(score.getScoTeam2Id());
	    setScoScore1(score.getScoScore1());
	    setScoScore2(score.getScoScore2());
	}
	return score;
    }

    public boolean deleteScore() {
	if (getScoId() != 0) {
	    return new DAOFactory().getScoreDAO().delete((Score) this);
	}
	return false;
    }

    public boolean updateScore() {

	if (getScoId() != 0) {
	    return new DAOFactory().getScoreDAO().update((Score) this);
	}
	return false;

    }

}
