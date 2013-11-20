package com.gq2.domain;

import java.io.Serializable;

/**
 * Define un objeto Team (Equipo)
 *
 * @author RAFAEL FERRUZ
 */
public class Team implements Serializable {

    protected int teaId;
    protected String teaCode;
    protected int teaStatus = 0;
    protected String teaName;
    protected int teaRating;

    public Team() {
    }

    public Team(int teaId) {
	this.teaId = teaId;
    }

    public String getTeaCode() {
	return teaCode;
    }

    public void setTeaCode(String teaCode) {
	this.teaCode = teaCode;
    }

    public int getTeaStatus() {
	return teaStatus;
    }

    public void setTeaStatus(int teaStatus) {
	this.teaStatus = teaStatus;
    }

    public int getTeaId() {
	return teaId;
    }

    public void setTeaId(int teaId) {
	this.teaId = teaId;
    }

    public String getTeaName() {
	return teaName;
    }

    public void setTeaName(String teaName) {
	this.teaName = teaName;
    }

    public int getTeaRating() {
	return teaRating;
    }

    public void setTeaRating(int teaRating) {
	this.teaRating = teaRating;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Team other = (Team) obj;
	if (this.teaId != other.teaId && (this.teaId == 0 || this.teaId != other.teaId)) {
	    return false;
	}
	if (!this.teaCode.equals(other.teaCode) && (this.teaCode == null || !this.teaCode.equals(other.teaCode))) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 83 * hash + (this.teaCode != null ? this.teaCode.hashCode() : 0);
	return hash;
    }
}
