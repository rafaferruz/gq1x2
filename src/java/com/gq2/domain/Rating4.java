package com.gq2.domain;

import java.util.Date;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class Rating4{

    private int rat4Id;
    private int rat4ChaId;
    private int rat4Round;
    private Date rat4Date;
    private int rat4Team1Id;
    private int rat4Team1Previous;
    private int rat4Team2Id;
    private int rat4Team2Previous;
    private int rat4PreviousDiference;
    private int rat4ProbabilityWin;
    private int rat4ProbabilityDraw;
    private int rat4ProbabilityLose;
    private String rat4ScoreSign="";
    private int rat4Team1Post;
    private int rat4Team2Post;

    public Rating4() {
    }

    public Rating4(int rat4Id) {
	this.rat4Id = rat4Id;
    }

    public int getRat4Id() {
	return rat4Id;
    }

    public void setRat4Id(int rat4Id) {
	this.rat4Id = rat4Id;
    }

    public int getRat4Round() {
	return rat4Round;
    }

    public void setRat4Round(int rat4Round) {
	this.rat4Round = rat4Round;
    }

    public Date getRat4Date() {
	return rat4Date;
    }

    public void setRat4Date(Date rat4Date) {
	this.rat4Date = rat4Date;
    }

    public int getRat4Team1Previous() {
	return rat4Team1Previous;
    }

    public void setRat4Team1Previous(int rat4Team1Previous) {
	this.rat4Team1Previous = rat4Team1Previous;
    }

    public int getRat4Team2Previous() {
	return rat4Team2Previous;
    }

    public void setRat4Team2Previous(int rat4Team2Previous) {
	this.rat4Team2Previous = rat4Team2Previous;
    }

    public int getRat4PreviousDiference() {
	return rat4PreviousDiference;
    }

    public void setRat4PreviousDiference(int rat4PreviousDiference) {
	this.rat4PreviousDiference = rat4PreviousDiference;
    }

    public int getRat4ProbabilityWin() {
	return rat4ProbabilityWin;
    }

    public void setRat4ProbabilityWin(int rat4ProbabilityWin) {
	this.rat4ProbabilityWin = rat4ProbabilityWin;
    }

    public int getRat4ProbabilityDraw() {
	return rat4ProbabilityDraw;
    }

    public void setRat4ProbabilityDraw(int rat4ProbabilityDraw) {
	this.rat4ProbabilityDraw = rat4ProbabilityDraw;
    }

    public int getRat4ProbabilityLose() {
	return rat4ProbabilityLose;
    }

    public void setRat4ProbabilityLose(int rat4ProbabilityLose) {
	this.rat4ProbabilityLose = rat4ProbabilityLose;
    }

    public String getRat4ScoreSign() {
	return rat4ScoreSign;
    }

    public void setRat4ScoreSign(String rat4ScoreSign) {
	this.rat4ScoreSign = rat4ScoreSign;
    }

    public int getRat4Team1Post() {
	return rat4Team1Post;
    }

    public void setRat4Team1Post(int rat4Team1Post) {
	this.rat4Team1Post = rat4Team1Post;
    }

    public int getRat4Team2Post() {
	return rat4Team2Post;
    }

    public void setRat4Team2Post(int rat4Team2Post) {
	this.rat4Team2Post = rat4Team2Post;
    }

    public int getRat4ChaId() {
	return rat4ChaId;
    }

    public void setRat4ChaId(int rat4ChaId) {
	this.rat4ChaId = rat4ChaId;
    }

    public int getRat4Team1Id() {
	return rat4Team1Id;
    }

    public void setRat4Team1Id(int rat4Team1Id) {
	this.rat4Team1Id = rat4Team1Id;
    }

    public int getRat4Team2Id() {
	return rat4Team2Id;
    }

    public void setRat4Team2Id(int rat4Team2Id) {
	this.rat4Team2Id = rat4Team2Id;
    }


    @Override
    public String toString() {
	return "com.gq2.domain.Rating4[rat4Id=" + rat4Id + "]";
    }
}
