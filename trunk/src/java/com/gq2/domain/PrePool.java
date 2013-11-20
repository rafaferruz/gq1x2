package com.gq2.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Define un objeto PrePool (preQuiniela)
 *
 * @author RAFAEL FERRUZ
 */
public class PrePool {

    private int preId;
    private int preSeason;
    private int preOrderNumber;
    private int preChaId;
    private int preRound;
    private Date preDate;
    private int preScoId;
    private int preScoTeam1Id;
    private String preTeaName1;
    private int preScoTeam2Id;
    private String preTeaName2;
    private int preScoScore1;
    private int preScoScore2;
    private int preRatPoints;
    private int preRat4PreviousDiference;
    private int prePercentWin;
    private int prePercentDraw;
    private int prePercentLose;
    private String prePrognostic;
    private String preRat4ScoreSign;
    private int preFailedPrognostic;

    public PrePool() {
    }

    public int getPreId() {
	return preId;
    }

    public void setPreId(int preId) {
	this.preId = preId;
    }

    public int getPreSeason() {
	return preSeason;
    }

    public void setPreSeason(int preSeason) {
	this.preSeason = preSeason;
    }

    public int getPreOrderNumber() {
	return preOrderNumber;
    }

    public void setPreOrderNumber(int preOrderNumber) {
	this.preOrderNumber = preOrderNumber;
    }

    public int getPreChaId() {
	return preChaId;
    }

    public void setPreChaId(int preChaId) {
	this.preChaId = preChaId;
    }

    public int getPreRound() {
	return preRound;
    }

    public void setPreRound(int preRound) {
	this.preRound = preRound;
    }

    public Date getPreDate() {
	return preDate;
    }

    public void setPreDate(Date preDate) {
	this.preDate = preDate;
    }

    public int getPreScoId() {
	return preScoId;
    }

    public void setPreScoId(int preScoId) {
	this.preScoId = preScoId;
    }

    public int getPreScoTeam1Id() {
	return preScoTeam1Id;
    }

    public void setPreScoTeam1Id(int preScoTeam1Id) {
	this.preScoTeam1Id = preScoTeam1Id;
    }

    public String getPreTeaName1() {
	return preTeaName1;
    }

    public void setPreTeaName1(String preTeaName1) {
	this.preTeaName1 = preTeaName1;
    }

    public int getPreScoTeam2Id() {
	return preScoTeam2Id;
    }

    public void setPreScoTeam2Id(int preScoTeam2Id) {
	this.preScoTeam2Id = preScoTeam2Id;
    }

    public String getPreTeaName2() {
	return preTeaName2;
    }

    public void setPreTeaName2(String preTeaName2) {
	this.preTeaName2 = preTeaName2;
    }

    public int getPreScoScore1() {
	return preScoScore1;
    }

    public void setPreScoScore1(int preScoScore1) {
	this.preScoScore1 = preScoScore1;
    }

    public int getPreScoScore2() {
	return preScoScore2;
    }

    public void setPreScoScore2(int preScoScore2) {
	this.preScoScore2 = preScoScore2;
    }

    public int getPreRatPoints() {
	return preRatPoints;
    }

    public void setPreRatPoints(int preRatPoints) {
	this.preRatPoints = preRatPoints;
    }

    public int getPreRat4PreviousDiference() {
	return preRat4PreviousDiference;
    }

    public void setPreRat4PreviousDiference(int preRat4PreviousDiference) {
	this.preRat4PreviousDiference = preRat4PreviousDiference;
    }

    public int getPrePercentWin() {
	return prePercentWin;
    }

    public void setPrePercentWin(int prePercentWin) {
	this.prePercentWin = prePercentWin;
    }

    public int getPrePercentDraw() {
	return prePercentDraw;
    }

    public void setPrePercentDraw(int prePercentDraw) {
	this.prePercentDraw = prePercentDraw;
    }

    public int getPrePercentLose() {
	return prePercentLose;
    }

    public void setPrePercentLose(int prePercentLose) {
	this.prePercentLose = prePercentLose;
    }

    public String getPrePrognostic() {
	return prePrognostic;
    }

    public void setPrePrognostic(String prePrognostic) {
	this.prePrognostic = prePrognostic;
    }

    public String getPreRat4ScoreSign() {
	return preRat4ScoreSign;
    }

    public void setPreRat4ScoreSign(String preRat4ScoreSign) {
	this.preRat4ScoreSign = preRat4ScoreSign;
    }

    public int getPreFailedPrognostic() {
	return preFailedPrognostic;
    }

    public void setPreFailedPrognostic(int preFailedPrognostic) {
	this.preFailedPrognostic = preFailedPrognostic;
    }
    
}
