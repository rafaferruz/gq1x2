package com.gq2.domain;

/**
 *
 * @author Rafael Ferruz
 */
public class Hit {
    private int hitId;
    private int hitBetId;
    private int hitBetSeason;
    private int hitBetOrderNumber;
    private String hitBetDescription;
    private String hitReductionName;
    private int hitTotalColumns;
    private int hitHitsNumber;
    private int hitColumnsNumber;
    
    public Hit(){
	
    }

    public int getHitId() {
	return hitId;
    }

    public void setHitId(int hitId) {
	this.hitId = hitId;
    }

    public int getHitBetId() {
	return hitBetId;
    }

    public void setHitBetId(int hitBetId) {
	this.hitBetId = hitBetId;
    }

    public int getHitBetSeason() {
	return hitBetSeason;
    }

    public void setHitBetSeason(int hitBetSeason) {
	this.hitBetSeason = hitBetSeason;
    }

    public int getHitBetOrderNumber() {
	return hitBetOrderNumber;
    }

    public void setHitBetOrderNumber(int hitBetOrderNumber) {
	this.hitBetOrderNumber = hitBetOrderNumber;
    }

    public String getHitBetDescription() {
	return hitBetDescription;
    }

    public void setHitBetDescription(String hitBetDescription) {
	this.hitBetDescription = hitBetDescription;
    }

    public String getHitReductionName() {
	return hitReductionName;
    }

    public void setHitReductionName(String hitReductionName) {
	this.hitReductionName = hitReductionName;
    }

    public int getHitTotalColumns() {
	return hitTotalColumns;
    }

    public void setHitTotalColumns(int hitTotalColumns) {
	this.hitTotalColumns = hitTotalColumns;
    }

    public int getHitHitsNumber() {
	return hitHitsNumber;
    }

    public void setHitHitsNumber(int hitHitsNumber) {
	this.hitHitsNumber = hitHitsNumber;
    }

    public int getHitColumnsNumber() {
	return hitColumnsNumber;
    }

    public void setHitColumnsNumber(int hitColumnsNumber) {
	this.hitColumnsNumber = hitColumnsNumber;
    }
    
}
