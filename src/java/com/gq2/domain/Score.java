package com.gq2.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class Score implements Serializable{

    public int scoId;
    public int scoChaId;
    public int scoRound;
    public Date scoDate;
    public int scoTeam1Id;
    public int scoTeam2Id;
    public int scoScore1;
    public int scoScore2;

    public Score() {
    }

    public Score(int scoId) {
	this.scoId = scoId;
    }

    public int getScoChaId() {
	return scoChaId;
    }

    public void setScoChaId(int scoChaId) {
	this.scoChaId = scoChaId;
    }

    public int getScoTeam1Id() {
	return scoTeam1Id;
    }

    public void setScoTeam1Id(int scoTeam1Id) {
	this.scoTeam1Id = scoTeam1Id;
    }

    public int getScoTeam2Id() {
	return scoTeam2Id;
    }

    public void setScoTeam2Id(int scoTeam2Id) {
	this.scoTeam2Id = scoTeam2Id;
    }

    public Date getScoDate() {
	return scoDate;
    }

    public void setScoDate(Date scoDate) {
	this.scoDate = scoDate;
    }

    public int getScoId() {
	return scoId;
    }

    public void setScoId(int scoId) {
	this.scoId = scoId;
    }

    public int getScoScore1() {
	return scoScore1;
    }

    public void setScoScore1(int scoScore1) {
	this.scoScore1 = scoScore1;
    }

    public int getScoScore2() {
	return scoScore2;
    }

    public void setScoScore2(int scoScore2) {
	this.scoScore2 = scoScore2;
    }

    public int getScoRound() {
	return scoRound;
    }

    public void setScoRound(int scoRound) {
	this.scoRound = scoRound;
    }
}
