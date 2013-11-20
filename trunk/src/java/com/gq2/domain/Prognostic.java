package com.gq2.domain;

import java.util.Date;

/**
 * Define un objeto Prognostic
 *
 * @author RAFAEL FERRUZ
 */
public class Prognostic {

    int pro_id;
    int pro_cha_id;
    int pro_round;
    Date pro_date;
    int pro_sco_id;
    int pro_sco_team1_id;
    int pro_sco_team2_id;
    int pro_sco_score1;
    int pro_sco_score2;
    int pro_cla_previous1_id;
    int pro_cla_previous1_total_played_games;
    int pro_cla_previous1_total_won_games;
    int pro_cla_previous1_total_drawn_games;
    int pro_cla_previous1_total_lost_games;
    int pro_cla_previous1_total_own_goals;
    int pro_cla_previous1_total_against_goals;
    int pro_cla_previous1_home_played_games;
    int pro_cla_previous1_home_won_games;
    int pro_cla_previous1_home_drawn_games;
    int pro_cla_previous1_home_lost_games;
    int pro_cla_previous1_home_own_goals;
    int pro_cla_previous1_home_against_goals;
    int pro_cla_previous2_id;
    int pro_cla_previous2_total_played_games;
    int pro_cla_previous2_total_won_games;
    int pro_cla_previous2_total_drawn_games;
    int pro_cla_previous2_total_lost_games;
    int pro_cla_previous2_total_own_goals;
    int pro_cla_previous2_total_against_goals;
    int pro_cla_previous2_out_played_games;
    int pro_cla_previous2_out_won_games;
    int pro_cla_previous2_out_drawn_games;
    int pro_cla_previous2_out_lost_games;
    int pro_cla_previous2_out_own_goals;
    int pro_cla_previous2_out_against_goals;
    int pro_wdl_previous1_id;
    int pro_wdl_previous1_PSG;
    int pro_wdl_previous1_PSE;
    int pro_wdl_previous1_PSP;
    int pro_wdl_previous1_PSNG;
    int pro_wdl_previous1_PSNE;
    int pro_wdl_previous1_PSNP;
    int pro_wdl_previous2_id;
    int pro_wdl_previous2_PSG;
    int pro_wdl_previous2_PSE;
    int pro_wdl_previous2_PSP;
    int pro_wdl_previous2_PSNG;
    int pro_wdl_previous2_PSNE;
    int pro_wdl_previous2_PSNP;
    int pro_rat4_id;
    int pro_rat4_team1_id;
    int pro_rat4_team1_previous;
    int pro_rat4_team2_id;
    int pro_rat4_team2_previous;
    int pro_rat4_previous_diference;
    int pro_rat4_probability_win;
    int pro_rat4_probability_draw;
    int pro_rat4_probability_lose;
    String pro_rat4_score_sign;
    int pro_rat4_team1_post;
    int pro_rat4_team2_post;

    public Prognostic() {
    }

    public int getPro_id() {
	return pro_id;
    }

    public void setPro_id(int pro_id) {
	this.pro_id = pro_id;
    }

    public int getPro_cha_id() {
	return pro_cha_id;
    }

    public void setPro_cha_id(int pro_cha_id) {
	this.pro_cha_id = pro_cha_id;
    }

    public int getPro_round() {
	return pro_round;
    }

    public void setPro_round(int pro_round) {
	this.pro_round = pro_round;
    }

    public Date getPro_date() {
	return pro_date;
    }

    public void setPro_date(Date pro_date) {
	this.pro_date = pro_date;
    }

    public int getPro_sco_id() {
	return pro_sco_id;
    }

    public void setPro_sco_id(int pro_sco_id) {
	this.pro_sco_id = pro_sco_id;
    }

    public int getPro_sco_team1_id() {
	return pro_sco_team1_id;
    }

    public void setPro_sco_team1_id(int pro_sco_team1_id) {
	this.pro_sco_team1_id = pro_sco_team1_id;
    }

    public int getPro_sco_team2_id() {
	return pro_sco_team2_id;
    }

    public void setPro_sco_team2_id(int pro_sco_team2_id) {
	this.pro_sco_team2_id = pro_sco_team2_id;
    }

    public int getPro_sco_score1() {
	return pro_sco_score1;
    }

    public void setPro_sco_score1(int pro_sco_score1) {
	this.pro_sco_score1 = pro_sco_score1;
    }

    public int getPro_sco_score2() {
	return pro_sco_score2;
    }

    public void setPro_sco_score2(int pro_sco_score2) {
	this.pro_sco_score2 = pro_sco_score2;
    }

    public int getPro_cla_previous1_id() {
	return pro_cla_previous1_id;
    }

    public void setPro_cla_previous1_id(int pro_cla_previous1_id) {
	this.pro_cla_previous1_id = pro_cla_previous1_id;
    }

    public int getPro_cla_previous1_total_played_games() {
	return pro_cla_previous1_total_played_games;
    }

    public void setPro_cla_previous1_total_played_games(int pro_cla_previous1_total_played_games) {
	this.pro_cla_previous1_total_played_games = pro_cla_previous1_total_played_games;
    }

    public int getPro_cla_previous1_total_won_games() {
	return pro_cla_previous1_total_won_games;
    }

    public void setPro_cla_previous1_total_won_games(int pro_cla_previous1_total_won_games) {
	this.pro_cla_previous1_total_won_games = pro_cla_previous1_total_won_games;
    }

    public int getPro_cla_previous1_total_drawn_games() {
	return pro_cla_previous1_total_drawn_games;
    }

    public void setPro_cla_previous1_total_drawn_games(int pro_cla_previous1_total_drawn_games) {
	this.pro_cla_previous1_total_drawn_games = pro_cla_previous1_total_drawn_games;
    }

    public int getPro_cla_previous1_total_lost_games() {
	return pro_cla_previous1_total_lost_games;
    }

    public void setPro_cla_previous1_total_lost_games(int pro_cla_previous1_total_lost_games) {
	this.pro_cla_previous1_total_lost_games = pro_cla_previous1_total_lost_games;
    }

    public int getPro_cla_previous1_total_own_goals() {
	return pro_cla_previous1_total_own_goals;
    }

    public void setPro_cla_previous1_total_own_goals(int pro_cla_previous1_total_own_goals) {
	this.pro_cla_previous1_total_own_goals = pro_cla_previous1_total_own_goals;
    }

    public int getPro_cla_previous1_total_against_goals() {
	return pro_cla_previous1_total_against_goals;
    }

    public void setPro_cla_previous1_total_against_goals(int pro_cla_previous1_total_against_goals) {
	this.pro_cla_previous1_total_against_goals = pro_cla_previous1_total_against_goals;
    }

    public int getPro_cla_previous1_home_played_games() {
	return pro_cla_previous1_home_played_games;
    }

    public void setPro_cla_previous1_home_played_games(int pro_cla_previous1_home_played_games) {
	this.pro_cla_previous1_home_played_games = pro_cla_previous1_home_played_games;
    }

    public int getPro_cla_previous1_home_won_games() {
	return pro_cla_previous1_home_won_games;
    }

    public void setPro_cla_previous1_home_won_games(int pro_cla_previous1_home_won_games) {
	this.pro_cla_previous1_home_won_games = pro_cla_previous1_home_won_games;
    }

    public int getPro_cla_previous1_home_drawn_games() {
	return pro_cla_previous1_home_drawn_games;
    }

    public void setPro_cla_previous1_home_drawn_games(int pro_cla_previous1_home_drawn_games) {
	this.pro_cla_previous1_home_drawn_games = pro_cla_previous1_home_drawn_games;
    }

    public int getPro_cla_previous1_home_lost_games() {
	return pro_cla_previous1_home_lost_games;
    }

    public void setPro_cla_previous1_home_lost_games(int pro_cla_previous1_home_lost_games) {
	this.pro_cla_previous1_home_lost_games = pro_cla_previous1_home_lost_games;
    }

    public int getPro_cla_previous1_home_own_goals() {
	return pro_cla_previous1_home_own_goals;
    }

    public void setPro_cla_previous1_home_own_goals(int pro_cla_previous1_home_own_goals) {
	this.pro_cla_previous1_home_own_goals = pro_cla_previous1_home_own_goals;
    }

    public int getPro_cla_previous1_home_against_goals() {
	return pro_cla_previous1_home_against_goals;
    }

    public void setPro_cla_previous1_home_against_goals(int pro_cla_previous1_home_against_goals) {
	this.pro_cla_previous1_home_against_goals = pro_cla_previous1_home_against_goals;
    }

    public int getPro_cla_previous2_id() {
	return pro_cla_previous2_id;
    }

    public void setPro_cla_previous2_id(int pro_cla_previous2_id) {
	this.pro_cla_previous2_id = pro_cla_previous2_id;
    }

    public int getPro_cla_previous2_total_played_games() {
	return pro_cla_previous2_total_played_games;
    }

    public void setPro_cla_previous2_total_played_games(int pro_cla_previous2_total_played_games) {
	this.pro_cla_previous2_total_played_games = pro_cla_previous2_total_played_games;
    }

    public int getPro_cla_previous2_total_won_games() {
	return pro_cla_previous2_total_won_games;
    }

    public void setPro_cla_previous2_total_won_games(int pro_cla_previous2_total_won_games) {
	this.pro_cla_previous2_total_won_games = pro_cla_previous2_total_won_games;
    }

    public int getPro_cla_previous2_total_drawn_games() {
	return pro_cla_previous2_total_drawn_games;
    }

    public void setPro_cla_previous2_total_drawn_games(int pro_cla_previous2_total_drawn_games) {
	this.pro_cla_previous2_total_drawn_games = pro_cla_previous2_total_drawn_games;
    }

    public int getPro_cla_previous2_total_lost_games() {
	return pro_cla_previous2_total_lost_games;
    }

    public void setPro_cla_previous2_total_lost_games(int pro_cla_previous2_total_lost_games) {
	this.pro_cla_previous2_total_lost_games = pro_cla_previous2_total_lost_games;
    }

    public int getPro_cla_previous2_total_own_goals() {
	return pro_cla_previous2_total_own_goals;
    }

    public void setPro_cla_previous2_total_own_goals(int pro_cla_previous2_total_own_goals) {
	this.pro_cla_previous2_total_own_goals = pro_cla_previous2_total_own_goals;
    }

    public int getPro_cla_previous2_total_against_goals() {
	return pro_cla_previous2_total_against_goals;
    }

    public void setPro_cla_previous2_total_against_goals(int pro_cla_previous2_total_against_goals) {
	this.pro_cla_previous2_total_against_goals = pro_cla_previous2_total_against_goals;
    }

    public int getPro_cla_previous2_out_played_games() {
	return pro_cla_previous2_out_played_games;
    }

    public void setPro_cla_previous2_out_played_games(int pro_cla_previous2_out_played_games) {
	this.pro_cla_previous2_out_played_games = pro_cla_previous2_out_played_games;
    }

    public int getPro_cla_previous2_out_won_games() {
	return pro_cla_previous2_out_won_games;
    }

    public void setPro_cla_previous2_out_won_games(int pro_cla_previous2_out_won_games) {
	this.pro_cla_previous2_out_won_games = pro_cla_previous2_out_won_games;
    }

    public int getPro_cla_previous2_out_drawn_games() {
	return pro_cla_previous2_out_drawn_games;
    }

    public void setPro_cla_previous2_out_drawn_games(int pro_cla_previous2_out_drawn_games) {
	this.pro_cla_previous2_out_drawn_games = pro_cla_previous2_out_drawn_games;
    }

    public int getPro_cla_previous2_out_lost_games() {
	return pro_cla_previous2_out_lost_games;
    }

    public void setPro_cla_previous2_out_lost_games(int pro_cla_previous2_out_lost_games) {
	this.pro_cla_previous2_out_lost_games = pro_cla_previous2_out_lost_games;
    }

    public int getPro_cla_previous2_out_own_goals() {
	return pro_cla_previous2_out_own_goals;
    }

    public void setPro_cla_previous2_out_own_goals(int pro_cla_previous2_out_own_goals) {
	this.pro_cla_previous2_out_own_goals = pro_cla_previous2_out_own_goals;
    }

    public int getPro_cla_previous2_out_against_goals() {
	return pro_cla_previous2_out_against_goals;
    }

    public void setPro_cla_previous2_out_against_goals(int pro_cla_previous2_out_against_goals) {
	this.pro_cla_previous2_out_against_goals = pro_cla_previous2_out_against_goals;
    }

    public int getPro_wdl_previous1_id() {
	return pro_wdl_previous1_id;
    }

    public void setPro_wdl_previous1_id(int pro_wdl_previous1_id) {
	this.pro_wdl_previous1_id = pro_wdl_previous1_id;
    }

    public int getPro_wdl_previous1_PSG() {
	return pro_wdl_previous1_PSG;
    }

    public void setPro_wdl_previous1_PSG(int pro_wdl_previous1_PSG) {
	this.pro_wdl_previous1_PSG = pro_wdl_previous1_PSG;
    }

    public int getPro_wdl_previous1_PSE() {
	return pro_wdl_previous1_PSE;
    }

    public void setPro_wdl_previous1_PSE(int pro_wdl_previous1_PSE) {
	this.pro_wdl_previous1_PSE = pro_wdl_previous1_PSE;
    }

    public int getPro_wdl_previous1_PSP() {
	return pro_wdl_previous1_PSP;
    }

    public void setPro_wdl_previous1_PSP(int pro_wdl_previous1_PSP) {
	this.pro_wdl_previous1_PSP = pro_wdl_previous1_PSP;
    }

    public int getPro_wdl_previous1_PSNG() {
	return pro_wdl_previous1_PSNG;
    }

    public void setPro_wdl_previous1_PSNG(int pro_wdl_previous1_PSNG) {
	this.pro_wdl_previous1_PSNG = pro_wdl_previous1_PSNG;
    }

    public int getPro_wdl_previous1_PSNE() {
	return pro_wdl_previous1_PSNE;
    }

    public void setPro_wdl_previous1_PSNE(int pro_wdl_previous1_PSNE) {
	this.pro_wdl_previous1_PSNE = pro_wdl_previous1_PSNE;
    }

    public int getPro_wdl_previous1_PSNP() {
	return pro_wdl_previous1_PSNP;
    }

    public void setPro_wdl_previous1_PSNP(int pro_wdl_previous1_PSNP) {
	this.pro_wdl_previous1_PSNP = pro_wdl_previous1_PSNP;
    }

    public int getPro_wdl_previous2_id() {
	return pro_wdl_previous2_id;
    }

    public void setPro_wdl_previous2_id(int pro_wdl_previous2_id) {
	this.pro_wdl_previous2_id = pro_wdl_previous2_id;
    }

    public int getPro_wdl_previous2_PSG() {
	return pro_wdl_previous2_PSG;
    }

    public void setPro_wdl_previous2_PSG(int pro_wdl_previous2_PSG) {
	this.pro_wdl_previous2_PSG = pro_wdl_previous2_PSG;
    }

    public int getPro_wdl_previous2_PSE() {
	return pro_wdl_previous2_PSE;
    }

    public void setPro_wdl_previous2_PSE(int pro_wdl_previous2_PSE) {
	this.pro_wdl_previous2_PSE = pro_wdl_previous2_PSE;
    }

    public int getPro_wdl_previous2_PSP() {
	return pro_wdl_previous2_PSP;
    }

    public void setPro_wdl_previous2_PSP(int pro_wdl_previous2_PSP) {
	this.pro_wdl_previous2_PSP = pro_wdl_previous2_PSP;
    }

    public int getPro_wdl_previous2_PSNG() {
	return pro_wdl_previous2_PSNG;
    }

    public void setPro_wdl_previous2_PSNG(int pro_wdl_previous2_PSNG) {
	this.pro_wdl_previous2_PSNG = pro_wdl_previous2_PSNG;
    }

    public int getPro_wdl_previous2_PSNE() {
	return pro_wdl_previous2_PSNE;
    }

    public void setPro_wdl_previous2_PSNE(int pro_wdl_previous2_PSNE) {
	this.pro_wdl_previous2_PSNE = pro_wdl_previous2_PSNE;
    }

    public int getPro_wdl_previous2_PSNP() {
	return pro_wdl_previous2_PSNP;
    }

    public void setPro_wdl_previous2_PSNP(int pro_wdl_previous2_PSNP) {
	this.pro_wdl_previous2_PSNP = pro_wdl_previous2_PSNP;
    }

    public int getPro_rat4_id() {
	return pro_rat4_id;
    }

    public void setPro_rat4_id(int pro_rat4_id) {
	this.pro_rat4_id = pro_rat4_id;
    }

    public int getPro_rat4_team1_id() {
	return pro_rat4_team1_id;
    }

    public void setPro_rat4_team1_id(int pro_rat4_team1_id) {
	this.pro_rat4_team1_id = pro_rat4_team1_id;
    }

    public int getPro_rat4_team1_previous() {
	return pro_rat4_team1_previous;
    }

    public void setPro_rat4_team1_previous(int pro_rat4_team1_previous) {
	this.pro_rat4_team1_previous = pro_rat4_team1_previous;
    }

    public int getPro_rat4_team2_id() {
	return pro_rat4_team2_id;
    }

    public void setPro_rat4_team2_id(int pro_rat4_team2_id) {
	this.pro_rat4_team2_id = pro_rat4_team2_id;
    }

    public int getPro_rat4_team2_previous() {
	return pro_rat4_team2_previous;
    }

    public void setPro_rat4_team2_previous(int pro_rat4_team2_previous) {
	this.pro_rat4_team2_previous = pro_rat4_team2_previous;
    }

    public int getPro_rat4_previous_diference() {
	return pro_rat4_previous_diference;
    }

    public void setPro_rat4_previous_diference(int pro_rat4_previous_diference) {
	this.pro_rat4_previous_diference = pro_rat4_previous_diference;
    }

    public int getPro_rat4_probability_win() {
	return pro_rat4_probability_win;
    }

    public void setPro_rat4_probability_win(int pro_rat4_probability_win) {
	this.pro_rat4_probability_win = pro_rat4_probability_win;
    }

    public int getPro_rat4_probability_draw() {
	return pro_rat4_probability_draw;
    }

    public void setPro_rat4_probability_draw(int pro_rat4_probability_draw) {
	this.pro_rat4_probability_draw = pro_rat4_probability_draw;
    }

    public int getPro_rat4_probability_lose() {
	return pro_rat4_probability_lose;
    }

    public void setPro_rat4_probability_lose(int pro_rat4_probability_lose) {
	this.pro_rat4_probability_lose = pro_rat4_probability_lose;
    }

    public String getPro_rat4_score_sign() {
	return pro_rat4_score_sign;
    }

    public void setPro_rat4_score_sign(String pro_rat4_score_sign) {
	this.pro_rat4_score_sign = pro_rat4_score_sign;
    }

    public int getPro_rat4_team1_post() {
	return pro_rat4_team1_post;
    }

    public void setPro_rat4_team1_post(int pro_rat4_team1_post) {
	this.pro_rat4_team1_post = pro_rat4_team1_post;
    }

    public int getPro_rat4_team2_post() {
	return pro_rat4_team2_post;
    }

    public void setPro_rat4_team2_post(int pro_rat4_team2_post) {
	this.pro_rat4_team2_post = pro_rat4_team2_post;
    }

}