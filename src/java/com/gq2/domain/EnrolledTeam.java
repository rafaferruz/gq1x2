package com.gq2.domain;

import java.io.Serializable;

/**
 * Define un objeto EnrolledTeam (Inscripcion Equipo)
 *
 * @author RAFAEL FERRUZ
 */
public class EnrolledTeam implements Serializable {

    protected int entId;
    protected int entChaId;
    protected int entTeaId;
    protected int entNumber;

    public EnrolledTeam() {
    }

    public EnrolledTeam(Integer chaId, Integer teaId) {
	entChaId=chaId;
	entTeaId=teaId;
    }

    public int getEntId() {
	return entId;
    }

    public void setEntId(int entId) {
	this.entId = entId;
    }

    public int getEntChaId() {
	return entChaId;
    }

    public void setEntChaId(int entChaId) {
	this.entChaId = entChaId;
    }

    public int getEntTeaId() {
	return entTeaId;
    }

    public void setEntTeaId(int entTeaId) {
	this.entTeaId = entTeaId;
    }

    public int getEntNumber() {
	return entNumber;
    }

    public void setEntNumber(int entNumber) {
	this.entNumber = entNumber;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 29 * hash + this.entChaId;
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final EnrolledTeam other = (EnrolledTeam) obj;
	if (this.entChaId != other.entChaId) {
	    return false;
	}
	if (this.entTeaId != other.entTeaId) {
	    return false;
	}
	return true;
    }

}
