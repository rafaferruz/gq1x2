package com.gq2.domain;

import java.util.Date;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class Classification implements Comparable {

    private int claId ;
    private int claChaId ;
    private int claRound;
    private Date claDate;
    private int claTeaId;
    private int claPosition;
    private int claPoints;
    private int claTotalPlayedGames;
    private int claTotalWonGames;
    private int claTotalDrawnGames;
    private int claTotalLostGames;
    private int claTotalOwnGoals;
    private int claTotalAgainstGoals;
    private int claHomePlayedGames;
    private int claHomeWonGames;
    private int claHomeDrawnGames;
    private int claHomeLostGames;
    private int claHomeOwnGoals;
    private int claHomeAgainstGoals;
    private int claOutPlayedGames;
    private int claOutWonGames;
    private int claOutDrawnGames;
    private int claOutLostGames;
    private int claOutOwnGoals;
    private int claOutAgainstGoals;
    private int claRating;

    public Classification(){
    }

    public int getClaId() {
	return claId;
    }

    public void setClaId(int claId) {
	this.claId = claId;
    }

    public int getClaChaId() {
	return claChaId;
    }

    public void setClaChaId(int claChaId) {
	this.claChaId = claChaId;
    }

    public int getClaRound() {
	return claRound;
    }

    public void setClaRound(int claRound) {
	this.claRound = claRound;
    }

    public Date getClaDate() {
	return claDate;
    }

    public void setClaDate(Date claDate) {
	this.claDate = claDate;
    }

    public int getClaTeaId() {
	return claTeaId;
    }

    public void setClaTeaId(int claTeaId) {
	this.claTeaId = claTeaId;
    }

    public int getClaPosition() {
	return claPosition;
    }

    public void setClaPosition(int claPosition) {
	this.claPosition = claPosition;
    }

    public int getClaPoints() {
	return claPoints;
    }

    public void setClaPoints(int claPoints) {
	this.claPoints = claPoints;
    }

    public int getClaTotalPlayedGames() {
	return claTotalPlayedGames;
    }

    public void setClaTotalPlayedGames(int claTotalPlayedGames) {
	this.claTotalPlayedGames = claTotalPlayedGames;
    }

    public int getClaTotalWonGames() {
	return claTotalWonGames;
    }

    public void setClaTotalWonGames(int claTotalWonGames) {
	this.claTotalWonGames = claTotalWonGames;
    }

    public int getClaTotalDrawnGames() {
	return claTotalDrawnGames;
    }

    public void setClaTotalDrawnGames(int claTotalDrawnGames) {
	this.claTotalDrawnGames = claTotalDrawnGames;
    }

    public int getClaTotalLostGames() {
	return claTotalLostGames;
    }

    public void setClaTotalLostGames(int claTotalLostGames) {
	this.claTotalLostGames = claTotalLostGames;
    }

    public int getClaTotalOwnGoals() {
	return claTotalOwnGoals;
    }

    public void setClaTotalOwnGoals(int claTotalOwnGoals) {
	this.claTotalOwnGoals = claTotalOwnGoals;
    }

    public int getClaTotalAgainstGoals() {
	return claTotalAgainstGoals;
    }

    public void setClaTotalAgainstGoals(int claTotalAgainstGoals) {
	this.claTotalAgainstGoals = claTotalAgainstGoals;
    }

    public int getClaHomePlayedGames() {
	return claHomePlayedGames;
    }

    public void setClaHomePlayedGames(int claHomePlayedGames) {
	this.claHomePlayedGames = claHomePlayedGames;
    }

    public int getClaHomeWonGames() {
	return claHomeWonGames;
    }

    public void setClaHomeWonGames(int claHomeWonGames) {
	this.claHomeWonGames = claHomeWonGames;
    }

    public int getClaHomeDrawnGames() {
	return claHomeDrawnGames;
    }

    public void setClaHomeDrawnGames(int claHomeDrawnGames) {
	this.claHomeDrawnGames = claHomeDrawnGames;
    }

    public int getClaHomeLostGames() {
	return claHomeLostGames;
    }

    public void setClaHomeLostGames(int claHomeLostGames) {
	this.claHomeLostGames = claHomeLostGames;
    }

    public int getClaHomeOwnGoals() {
	return claHomeOwnGoals;
    }

    public void setClaHomeOwnGoals(int claHomeOwnGoals) {
	this.claHomeOwnGoals = claHomeOwnGoals;
    }

    public int getClaHomeAgainstGoals() {
	return claHomeAgainstGoals;
    }

    public void setClaHomeAgainstGoals(int claHomeAgainstGoals) {
	this.claHomeAgainstGoals = claHomeAgainstGoals;
    }

    public int getClaOutPlayedGames() {
	return claOutPlayedGames;
    }

    public void setClaOutPlayedGames(int claOutPlayedGames) {
	this.claOutPlayedGames = claOutPlayedGames;
    }

    public int getClaOutWonGames() {
	return claOutWonGames;
    }

    public void setClaOutWonGames(int claOutWonGames) {
	this.claOutWonGames = claOutWonGames;
    }

    public int getClaOutDrawnGames() {
	return claOutDrawnGames;
    }

    public void setClaOutDrawnGames(int claOutDrawnGames) {
	this.claOutDrawnGames = claOutDrawnGames;
    }

    public int getClaOutLostGames() {
	return claOutLostGames;
    }

    public void setClaOutLostGames(int claOutLostGames) {
	this.claOutLostGames = claOutLostGames;
    }

    public int getClaOutOwnGoals() {
	return claOutOwnGoals;
    }

    public void setClaOutOwnGoals(int claOutOwnGoals) {
	this.claOutOwnGoals = claOutOwnGoals;
    }

    public int getClaOutAgainstGoals() {
	return claOutAgainstGoals;
    }

    public void setClaOutAgainstGoals(int claOutAgainstGoals) {
	this.claOutAgainstGoals = claOutAgainstGoals;
    }

    public int getClaRating() {
	return claRating;
    }

    public void setClaRating(int claRating) {
	this.claRating = claRating;
    }

    @Override
    public int compareTo(Object o) {
	Classification cla2 = (Classification) o;
	int goalsDiference = (this.getClaTotalOwnGoals() - this.getClaTotalAgainstGoals())
		- (cla2.getClaTotalOwnGoals() - cla2.getClaTotalAgainstGoals());
	if (this.getClaPoints() > cla2.getClaPoints()) {
	    return -1;
	} else if (this.getClaPoints() == cla2.getClaPoints()) {
	    if (goalsDiference > 0) {
		return -1;
	    } else if (goalsDiference == 0) {
		if (this.getClaTotalOwnGoals() > cla2.getClaTotalOwnGoals()) {
		    return -1;
		} else if (this.getClaTotalOwnGoals() == cla2.getClaTotalOwnGoals()) {
		    return 0;
		} else {
		    return 1;
		}

	    } else {
		return 1;
	    }
	} else {
	    return 1;
	}
    }

}
