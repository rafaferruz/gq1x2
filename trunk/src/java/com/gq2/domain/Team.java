package com.gq2.domain;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Define un objeto Team (Equipo)
 *
 * @author RAFAEL FERRUZ
 */
public class Team implements Serializable {

    protected int teaId;
    protected String teaCode;
    protected int teaStatus;
    protected String teaName;
    protected int teaRating;
    protected String teaEquivalentNames="";
    

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

    public String getTeaEquivalentNames() {
	return teaEquivalentNames;
    }

    public void setTeaEquivalentNames(String teaEquivalentNames) {
	this.teaEquivalentNames = teaEquivalentNames;
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
    
        /**
     * Comparador que ordena por la propiedad teaName de cada Team.
     */
    public static Comparator<Team> getNameComparator() {
	return new Comparator<Team>() {
	    @Override
	    public int compare(Team c1, Team c2) {
		return c1.getTeaName().compareTo(c2.getTeaName());
	    }
	};
    }

}
