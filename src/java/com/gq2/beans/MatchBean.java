package com.gq2.beans;

/**
 * @author RAFAEL FERRUZ
 */
public class MatchBean  {

    private String team1;
    private String team2;

    public MatchBean() {
    }

    public MatchBean(String team1, String team2) {
	this.team1 = team1;
	this.team2 = team2;
    }

    public String getTeam1() {
	return team1;
    }

    public void setTeam1(String team1) {
	this.team1 = team1;
    }

    public String getTeam2() {
	return team2;
    }

    public void setTeam2(String team2) {
	this.team2 = team2;
    }
}
