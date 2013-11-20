package com.gq2.domain;

/**
 * @author RAFAEL FERRUZ
 */
public class Bet {

    public int betId;
    public Integer betSeason;
    public Integer betOrderNumber;
    public String betDescription = "";
    public String betBase = "";    /* Contiene las apuestas base de cada partido separadas por el caracter ','
     * La apuesta base de cada partido puede estar vacia o contener los caracteres '1' y/o 'X' y/o '2'.
     */
    public String betGroup1 = "";  /* Contiene una secuencia de caracteres 0 y 1 con un numero de caracteres total
     * igual al numero de partidos que conforman la apuesta. Cada caracter referencia por su numero de orden en
     * la secuencia el numero de orden de un partido en la relacion de partidos. Si el valor del caracter es '1 signifioca
     * que dicho partido se ha asignado al grupo. */
    public String betGroup1Values1 = "";
    public String betGroup1ValuesX = "";
    public String betGroup1Values2 = "";
    public String betGroup2 = "";
    public String betGroup2Values1 = "";
    public String betGroup2ValuesX = "";
    public String betGroup2Values2 = "";
    public String betGroup3 = "";
    public String betGroup3Values1 = "";
    public String betGroup3ValuesX = "";
    public String betGroup3Values2 = "";
    public String betGroup4 = "";
    public String betGroup4Values1 = "";
    public String betGroup4ValuesX = "";
    public String betGroup4Values2 = "";
    public String betGroup5 = "";
    public String betGroup5Values1 = "";
    public String betGroup5ValuesX = "";
    public String betGroup5Values2 = "";

    public Bet() {
    }

    public Integer getBetId() {
	return betId;
    }

    public void setBetId(Integer betId) {
	this.betId = betId;
    }

    public Integer getBetSeason() {
	return betSeason;
    }

    public void setBetSeason(Integer betSeason) {
	this.betSeason = betSeason;
    }

    public Integer getBetOrderNumber() {
	return betOrderNumber;
    }

    public void setBetOrderNumber(Integer betOrderNumber) {
	this.betOrderNumber = betOrderNumber;
    }

    public String getBetDescription() {
	return betDescription;
    }

    public void setBetDescription(String betDescription) {
	this.betDescription = betDescription;
    }

    public String getBetBase() {
	return betBase;
    }

    public void setBetBase(String betBase) {
	this.betBase = betBase;
    }

    public String getBetGroup1() {
	return betGroup1;
    }

    public void setBetGroup1(String betGroup1) {
	this.betGroup1 = betGroup1;
    }

    public String getBetGroup1Values1() {
	return betGroup1Values1;
    }

    public void setBetGroup1Values1(String betGroup1Values1) {
	this.betGroup1Values1 = betGroup1Values1;
    }

    public String getBetGroup1ValuesX() {
	return betGroup1ValuesX;
    }

    public void setBetGroup1ValuesX(String betGroup1ValuesX) {
	this.betGroup1ValuesX = betGroup1ValuesX;
    }

    public String getBetGroup1Values2() {
	return betGroup1Values2;
    }

    public void setBetGroup1Values2(String betGroup1Values2) {
	this.betGroup1Values2 = betGroup1Values2;
    }

    public String getBetGroup2() {
	return betGroup2;
    }

    public void setBetGroup2(String betGroup2) {
	this.betGroup2 = betGroup2;
    }

    public String getBetGroup2Values1() {
	return betGroup2Values1;
    }

    public void setBetGroup2Values1(String betGroup2Values1) {
	this.betGroup2Values1 = betGroup2Values1;
    }

    public String getBetGroup2ValuesX() {
	return betGroup2ValuesX;
    }

    public void setBetGroup2ValuesX(String betGroup2ValuesX) {
	this.betGroup2ValuesX = betGroup2ValuesX;
    }

    public String getBetGroup2Values2() {
	return betGroup2Values2;
    }

    public void setBetGroup2Values2(String betGroup2Values2) {
	this.betGroup2Values2 = betGroup2Values2;
    }

    public String getBetGroup3() {
	return betGroup3;
    }

    public void setBetGroup3(String betGroup3) {
	this.betGroup3 = betGroup3;
    }

    public String getBetGroup3Values1() {
	return betGroup3Values1;
    }

    public void setBetGroup3Values1(String betGroup3Values1) {
	this.betGroup3Values1 = betGroup3Values1;
    }

    public String getBetGroup3ValuesX() {
	return betGroup3ValuesX;
    }

    public void setBetGroup3ValuesX(String betGroup3ValuesX) {
	this.betGroup3ValuesX = betGroup3ValuesX;
    }

    public String getBetGroup3Values2() {
	return betGroup3Values2;
    }

    public void setBetGroup3Values2(String betGroup3Values2) {
	this.betGroup3Values2 = betGroup3Values2;
    }

    public String getBetGroup4() {
	return betGroup4;
    }

    public void setBetGroup4(String betGroup4) {
	this.betGroup4 = betGroup4;
    }

    public String getBetGroup4Values1() {
	return betGroup4Values1;
    }

    public void setBetGroup4Values1(String betGroup4Values1) {
	this.betGroup4Values1 = betGroup4Values1;
    }

    public String getBetGroup4ValuesX() {
	return betGroup4ValuesX;
    }

    public void setBetGroup4ValuesX(String betGroup4ValuesX) {
	this.betGroup4ValuesX = betGroup4ValuesX;
    }

    public String getBetGroup4Values2() {
	return betGroup4Values2;
    }

    public void setBetGroup4Values2(String betGroup4Values2) {
	this.betGroup4Values2 = betGroup4Values2;
    }

    public String getBetGroup5() {
	return betGroup5;
    }

    public void setBetGroup5(String betGroup5) {
	this.betGroup5 = betGroup5;
    }

    public String getBetGroup5Values1() {
	return betGroup5Values1;
    }

    public void setBetGroup5Values1(String betGroup5Values1) {
	this.betGroup5Values1 = betGroup5Values1;
    }

    public String getBetGroup5ValuesX() {
	return betGroup5ValuesX;
    }

    public void setBetGroup5ValuesX(String betGroup5ValuesX) {
	this.betGroup5ValuesX = betGroup5ValuesX;
    }

    public String getBetGroup5Values2() {
	return betGroup5Values2;
    }

    public void setBetGroup5Values2(String betGroup5Values2) {
	this.betGroup5Values2 = betGroup5Values2;
    }
}
