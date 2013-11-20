package com.gq2.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Define un objeto Campeonato (Championship)
 *
 * @author RAFAEL FERRUZ
 */
public class Championship implements Serializable {

    protected int chaId;
    protected String chaDescription;
    protected String chaCode;
    protected int chaStatus = 0;
    protected String chaSeason;
    protected Date chaStartDate;
    protected int chaPointsWin;
    protected int chaPointsDraw;
    protected int chaPointsLose;
    protected int chaMaxTeams;

    public Championship() {
    }

    public String getChaCode() {
	return chaCode;
    }

    public void setChaCode(String chaCode) {
	this.chaCode = chaCode;
    }

    public String getChaDescription() {
	return chaDescription;
    }

    public void setChaDescription(String chaDescription) {
	this.chaDescription = chaDescription;
    }

    public int getChaStatus() {
	return chaStatus;
    }

    public void setChaStatus(int chaStatus) {
	this.chaStatus = chaStatus;
    }

    public String getChaSeason() {
	return chaSeason;
    }

    public void setChaSeason(String chaSeason) {
	this.chaSeason = chaSeason;
    }

    public Date getChaStartDate() {
	return chaStartDate;
    }

    public void setChaStartDate(Date chaStartDate) {
	this.chaStartDate = chaStartDate;
    }

    public int getChaId() {
	return chaId;
    }

    public void setChaId(int chaId) {
	this.chaId = chaId;
    }

    public int getChaMaxTeams() {
	return chaMaxTeams;
    }

    public void setChaMaxTeams(int chaMaxTeams) {
	this.chaMaxTeams = chaMaxTeams;
    }

    public int getChaPointsDraw() {
	return chaPointsDraw;
    }

    public void setChaPointsDraw(int chaPointsDraw) {
	this.chaPointsDraw = chaPointsDraw;
    }

    public int getChaPointsWin() {
	return chaPointsWin;
    }

    public void setChaPointsWin(int chaPointsWin) {
	this.chaPointsWin = chaPointsWin;
    }

    public int getChaPointsLose() {
	return chaPointsLose;
    }

    public void setChaPointsLose(int chaPointsLose) {
	this.chaPointsLose = chaPointsLose;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Championship other = (Championship) obj;
	if (this.chaId != other.chaId && (this.chaId == 0 || this.chaId != other.chaId)) {
	    return false;
	}
	if (!this.chaCode.equals(other.chaCode) && (this.chaCode == null || !this.chaCode.equals(other.chaCode))) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 83 * hash + (this.chaCode != null ? this.chaCode.hashCode() : 0);
	return hash;
    }
}
