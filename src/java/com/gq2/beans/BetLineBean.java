package com.gq2.beans;


/**
 * @author RAFAEL FERRUZ
 */
public class BetLineBean{

    private String bliTeamName1;
    private String bliTeamName2;
    private String bliColumnBase;
    private String bliSign;
    private boolean bliColumnGroup1;	// 'true' si esta linea de apuesta se ha asignado al grupo 1
    private boolean bliColumnGroup2;
    private boolean bliColumnGroup3;
    private boolean bliColumnGroup4;
    private boolean bliColumnGroup5;
    private int bliRating4PreviousDiference;
    private int bliPercentWin;
    private int bliPercentDraw;
    private int bliPercentLose;
    private int bliPrePoolId;

    public BetLineBean() {
    }

    public String getBliColumnBase() {
	return bliColumnBase;
    }

    public void setBliColumnBase(String bliColumnBase) {
	this.bliColumnBase = bliColumnBase;
    }

    public boolean getBliColumnGroup1() {
	return bliColumnGroup1;
    }

    public void setBliColumnGroup1(boolean bliColumnGroup1) {
	this.bliColumnGroup1 = bliColumnGroup1;
    }

    public boolean getBliColumnGroup2() {
	return bliColumnGroup2;
    }

    public void setBliColumnGroup2(boolean bliColumnGroup2) {
	this.bliColumnGroup2 = bliColumnGroup2;
    }

    public boolean getBliColumnGroup3() {
	return bliColumnGroup3;
    }

    public void setBliColumnGroup3(boolean bliColumnGroup3) {
	this.bliColumnGroup3 = bliColumnGroup3;
    }

    public boolean getBliColumnGroup4() {
	return bliColumnGroup4;
    }

    public void setBliColumnGroup4(boolean bliColumnGroup4) {
	this.bliColumnGroup4 = bliColumnGroup4;
    }

    public boolean getBliColumnGroup5() {
	return bliColumnGroup5;
    }

    public void setBliColumnGroup5(boolean bliColumnGroup5) {
	this.bliColumnGroup5 = bliColumnGroup5;
    }

    public String getBliTeamName1() {
	return bliTeamName1;
    }

    public void setBliTeamName1(String bliTeamName1) {
	this.bliTeamName1 = bliTeamName1;
    }

    public String getBliTeamName2() {
	return bliTeamName2;
    }

    public void setBliTeamName2(String bliTeamName2) {
	this.bliTeamName2 = bliTeamName2;
    }

    public int getBliPercentDraw() {
	return bliPercentDraw;
    }

    public void setBliPercentDraw(int bliPercentDraw) {
	this.bliPercentDraw = bliPercentDraw;
    }

    public int getBliPercentWin() {
	return bliPercentWin;
    }

    public void setBliPercentWin(int bliPercentWin) {
	this.bliPercentWin = bliPercentWin;
    }

    public int getBliPercentLose() {
	return bliPercentLose;
    }

    public void setBliPercentLose(int bliPercentLose) {
	this.bliPercentLose = bliPercentLose;
    }

    public int getBliRating4PreviousDiference() {
	return bliRating4PreviousDiference;
    }

    public void setBliRating4PreviousDiference(int bliRating4PreviousDiference) {
	this.bliRating4PreviousDiference = bliRating4PreviousDiference;
    }

    public int getBliPrePoolId() {
	return bliPrePoolId;
    }

    public void setBliPrePoolId(int bliPrePoolId) {
	this.bliPrePoolId = bliPrePoolId;
    }

    public String getBliSign() {
	return bliSign;
    }

    public void setBliSign(String bliSign) {
	this.bliSign = bliSign;
    }
}
