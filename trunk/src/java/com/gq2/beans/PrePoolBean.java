package com.gq2.beans;

import com.gq2.domain.PrePool;
import com.gq2.domain.Prognostic;
import com.gq2.domain.Team;
import com.gq2.services.ChampionshipService;
import com.gq2.services.PrePoolService;
import com.gq2.services.PrognosticService;
import com.gq2.services.TeamService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author ESTHER
 */
@ManagedBean(name = "prePool")
@ViewScoped
public class PrePoolBean {

    static final int FIRST_SEASON = 2000;
    static final int LAST_SEASON = 2013;
    private List<PrePool> prePoolList = new ArrayList();
    private List<SelectItem> championshipItemList = new ArrayList();
    private List<SelectItem> roundItemList = new ArrayList();
    private List<SelectItem> teamItemList = new ArrayList();
    private List<PrePool> dataMatches = new ArrayList<>();
    private PrePool dataPrePoolMatch;
    private PrePool dataMatch;
    private int mode = 1;
    private int chaId;
    private int round = 0;
    private int season;
    private int orderNumber;
    private int team1;
    private int team2;
    private int matches;
    private int insertedmatches;
    private String sortby = "rating";
    private Boolean alternative;
    private Boolean disabledRounds;
    private int signs1 = 0;
    private int signsX = 0;
    private int signs2 = 0;
    private ChampionshipService championshipService = new ChampionshipService();
    private TeamService teamService = new TeamService();
    private PrognosticService prognosticService = new PrognosticService();
    private PrePoolService prePoolService = new PrePoolService();
    private Map<Integer, Team> teamMap = new HashMap<>();

    public PrePoolBean() {
    }

    public List<PrePool> getPrePoolList() {
	return prePoolList;
    }

    public void setPrePoolList(List<PrePool> prePoolList) {
	this.prePoolList = prePoolList;
    }

    public List<SelectItem> getChampionshipItemList() {
	if (this.championshipItemList.isEmpty()) {
	    setChampionshipItemList(championshipService.getChampionshipItemList());
	}
	return this.championshipItemList;
    }

    public void setChampionshipItemList(List<SelectItem> championshipItemList) {
	this.championshipItemList = championshipItemList;
    }

    public void setRoundItemList(List<SelectItem> roundItemList) {
	this.roundItemList = roundItemList;
    }

    public List<SelectItem> getRoundItemList() {
	return this.roundItemList;
    }

    public List<SelectItem> getTeamItemList() {
	return teamItemList;
    }

    public void setTeamItemList(List<SelectItem> teams) {
	this.teamItemList = teams;
    }

    public List<PrePool> getDataMatches() {
	if (season != 0 && orderNumber != 0) {
	    showMatches();
	    readTeamItemList();
	} else {
	    dataMatches.clear();
	}
	return dataMatches;
    }

    public void setDataMatches(List<PrePool> dataMatches) {
	this.dataMatches = dataMatches;
    }

    public PrePool getDataPrePoolMatch() {
	return dataPrePoolMatch;
    }

    public void setDataPrePoolMatch(PrePool datapreqMatch) {
	this.dataPrePoolMatch = datapreqMatch;
    }

    public PrePool getDataMatch() {
	return dataMatch;
    }

    public void setDataMatch(PrePool dataMatch) {
	this.dataMatch = dataMatch;
    }

    public int getMode() {
	return mode;
    }

    public void setMode(int mode) {
	this.mode = mode;
    }

    public void setChaId(int chaId) {
	this.chaId = chaId;
    }

    public int getChaId() {
	return chaId;
    }

    public void setRound(int round) {
	this.round = round;
    }

    public int getRound() {
	return round;
    }

    public int getSeason() {
	return season;
    }

    public void setSeason(int season) {
	this.season = season;
    }

    public int getOrderNumber() {
	return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
	this.orderNumber = orderNumber;
    }

    public Integer getTeam1() {
	return team1;
    }

    public void setTeam1(Integer team1) {
	this.team1 = team1;
    }

    public Integer getTeam2() {
	return team2;
    }

    public void setTeam2(Integer team2) {
	this.team2 = team2;
    }

    public Integer getMatches() {
	return matches;
    }

    public void setMatches(Integer matches) {
	this.matches = matches;
    }

    public int getInsertedmatches() {
	return insertedmatches;
    }

    public void setInsertedmatches(int insertedmatches) {
	this.insertedmatches = insertedmatches;
    }

    public String getSortby() {
	return sortby;
    }

    public void setSortby(String sortby) {
	this.sortby = sortby;
    }

    public Boolean getAlternative() {
	return alternative;
    }

    public void setAlternative(Boolean alternative) {
	this.alternative = alternative;
    }

    public Boolean getDisabledRounds() {
	return this.disabledRounds;
    }

    public void setDisabledRounds(Boolean disabledRounds) {
	this.disabledRounds = disabledRounds;
    }

    public Integer getSigns1() {
	return signs1;
    }

    public void setSigns1(Integer signs1) {
	this.signs1 = signs1;
    }

    public Integer getSigns2() {
	return signs2;
    }

    public void setSigns2(Integer signs2) {
	this.signs2 = signs2;
    }

    public Integer getSignsX() {
	return signsX;
    }

    public void setSignsX(Integer signsX) {
	this.signsX = signsX;
    }

    public void loadRounds(ValueChangeEvent ev) {
	chaId = (Integer) ev.getNewValue();
	if (chaId != 0) {
	    leerRounds(chaId);
	}
    }

    private void leerRounds(int chaId) {
	setRoundItemList(championshipService.getRoundItemList(chaId));
    }

    public void loadPrePoolList(int season, int orderNumber) {
	setPrePoolList(prePoolService.loadPrePool(season, orderNumber));
	sortPrePoolList();
	obtainSigns();
    }

    private void sortPrePoolList() {
	switch (sortby) {
	    case "rating":
		Collections.sort(prePoolList, getRatingComparator());
		break;
	    case "order":
		Collections.sort(prePoolList, getOrderComparator());
		break;
	}
    }

    private void obtainSigns() {
	signs1 = 0;
	signsX = 0;
	signs2 = 0;
	for (PrePool prepool : getPrePoolList()) {
	    if (prepool.getPreRat4PreviousDiference() > 0) {
		signs1++;
	    } else if (prepool.getPreRat4PreviousDiference() <= 0 && prepool.getPreRat4PreviousDiference() >= -40) {
		signsX++;
	    } else if (prepool.getPreRat4PreviousDiference() < -40) {
		signs2++;
	    }
	}
    }

    public void deleteMatch() {
	if (dataPrePoolMatch != null) {
	    prePoolService.deleteMatch(dataPrePoolMatch);
	    loadPrePoolList(season, orderNumber);
	}
    }

    public void changeValueChampionship(ValueChangeEvent ev) {
	if (ev.getNewValue() != null) {
	    leerRounds((Integer) ev.getNewValue());
	}
    }

    public void cambiaValorMode(ValueChangeEvent ev) {
	if (ev.getNewValue() != null) {
	    if ((Integer) ev.getNewValue() == 1) {
		disabledRounds = false;
	    } else {
		disabledRounds = true;
	    }

	}
    }

    public void changeValueSeason(ValueChangeEvent ev) {
	if (ev.getNewValue() != ev.getOldValue()) {
	    setSeason((Integer) ev.getNewValue());
	    setOrderNumber(0);
	}
    }

    public void changeValueOrderNumber(ValueChangeEvent ev) {
	if (ev.getNewValue() != ev.getOldValue()) {
	    setOrderNumber((Integer) ev.getNewValue());
	    loadPrePoolList(season, orderNumber);
	}
    }

    public void changeValueSortby(ValueChangeEvent ev) {
	if (ev.getNewValue() != ev.getOldValue()) {
	    sortby = (String) ev.getNewValue();
	    sortPrePoolList();
	}
    }

    public void showMatches() {
	dataMatches.clear();
	List<Prognostic> prognosticList = prognosticService.loadPrognosticRoundList(chaId, round);
	for (Prognostic prognostic : prognosticList) {
	    PrePool prePool = populatePrePoolFromPrognostic(prognostic, season, orderNumber);
	    dataMatches.add(prePool);
	}
    }

    public void inputMatch() {
	dataMatches.clear();
	List<Prognostic> prognosticList = prognosticService.loadPrognosticRoundList(chaId, round);
	for (Prognostic prognostic : prognosticList) {
	    if (dataMatch.getPreScoTeam1Id() == prognostic.getPro_sco_team1_id()
		    & dataMatch.getPreScoTeam2Id() == prognostic.getPro_sco_team2_id()) {
		PrePool prePool = populatePrePoolFromPrognostic(prognostic, season, orderNumber);
		prePoolService.save(prePool);
		break;
	    }
	}
	loadPrePoolList(season, orderNumber);
    }

    public void insertMatches() {
	int m = 0;
	dataMatches.clear();
	List<Prognostic> prognosticList = prognosticService.loadPrognosticRoundList(chaId, round);
	for (Prognostic prognostic : prognosticList) {
	    if (getInsertedmatches() != 0) {
		if (getAlternative() != null && getAlternative() == true) {
		    ++m;
		    if (++m > (2 * getInsertedmatches())) {
			break;
		    }

		} else if (++m > getInsertedmatches()) {
		    break;
		}

	    }
	    PrePool prePool = populatePrePoolFromPrognostic(prognostic, season, orderNumber);
	    prePoolService.save(prePool);
	}

	loadPrePoolList(season, orderNumber);
    }

    public void insertTotal() {
	deleteTotal();
	for (season = FIRST_SEASON; season
		<= LAST_SEASON; season++) {
	    // Toma el numero de rondas de 1ª division por ser menor que el de 2ª division
	    chaId = championshipService.getChaIdOfSeason(season, "1�");

	    for (Integer r : championshipService.getRoundList(chaId)) {
		setRound(r);
		setOrderNumber(r);
		// Incorpora equipos de 1ª division
		chaId = championshipService.getChaIdOfSeason(season, "1�");
		setInsertedmatches(0);
		setAlternative(false);
		insertMatches();
		System.out.println("Campeonato " + chaId + "  Round " + orderNumber);
		// Incorpora equipos de 2ª division
		chaId = championshipService.getChaIdOfSeason(season, "2�");
		setInsertedmatches(4);
		setAlternative(true);
		insertMatches();
		System.out.println("Campeonato " + chaId + "  Round " + orderNumber);
	    }
	}
    }

    private int deleteTotal() {
	return prePoolService.deleteSeasons(FIRST_SEASON, LAST_SEASON);
    }

    public void createMatch() {

	inputMatch();

    }

    private void readTeamItemList() {

	teamItemList = teamService.getTeamItemListInChampionship(chaId);

    }

    public void updateScores() {

	List<Prognostic> prognosticList = prognosticService.loadPrognosticRoundList(chaId, round);

	for (PrePool prePool : prePoolList) {
	    for (Prognostic prognostic : prognosticList) {
		if (prognostic.getPro_sco_team1_id() == prePool.getPreScoTeam1Id()
			& prognostic.getPro_sco_team2_id() == prePool.getPreScoTeam2Id()) {
		    prePool.setPreScoScore1(prognostic.getPro_sco_score1());
		    prePool.setPreScoScore2(prognostic.getPro_sco_score2());
		    prePool.setPreRat4ScoreSign(prognostic.getPro_rat4_score_sign());
		    prePool.setPreFailedPrognostic(!prePool.getPrePrognostic().contains(prePool.getPreRat4ScoreSign()) ? 1 : 0);
		    prePoolService.update(prePool);
		    break;
		}
	    }
	}
	loadPrePoolList(season, round);
    }

    private PrePool populatePrePoolFromPrognostic(Prognostic prognostic, int season, int orderNumber) {
	if (teamMap.isEmpty()) {
	    teamMap = teamService.getAllTeamsMap();
	}
	PrePool prePool = new PrePool();

	prePool.setPreSeason(season);
	prePool.setPreOrderNumber(orderNumber);
	prePool.setPreChaId(prognostic.getPro_cha_id());
	prePool.setPreRound(prognostic.getPro_round());
	prePool.setPreDate(prognostic.getPro_date());
	prePool.setPreScoId(prognostic.getPro_sco_id());
	prePool.setPreScoTeam1Id(prognostic.getPro_sco_team1_id());
	prePool.setPreTeaName1(teamMap.get(prognostic.getPro_sco_team1_id()).getTeaName());
	prePool.setPreScoTeam2Id(prognostic.getPro_sco_team2_id());
	prePool.setPreTeaName2(teamMap.get(prognostic.getPro_sco_team2_id()).getTeaName());
	prePool.setPreScoScore1(prognostic.getPro_sco_score1());
	prePool.setPreScoScore2(prognostic.getPro_sco_score2());

	/* Calcula el Rating segun Puntos en un metodo estatico de PrePoolService */
	prePool.setPreRatPoints(PrePoolService.getPrePoolRatPointsFromPrognostic(prognostic).intValue());
	prePool.setPreRat4PreviousDiference(prognostic.getPro_rat4_previous_diference());

	/* Calcula los porcentajes de probabilidades de ganar, empatar, perder en un metodo estatico de PrePoolService */
	PrePoolService.setPrePoolPercentsFromPrognosticPreviousDiference(prognostic, prePool);

	/* Calcula el Prognostic en un metodo estatico de PrePoolService */
	prePool.setPrePrognostic(PrePoolService.getPrePoolPrognosticFromPercents(prePool));
	prePool.setPreRat4ScoreSign(prognostic.getPro_rat4_score_sign());
	return prePool;
    }

    /**
     * Comparador que ordena por la propiedad rating de cada PrePool.
     */
    public static Comparator<PrePool> getRatingComparator() {
	return new Comparator<PrePool>() {
	    public int compare(PrePool c1, PrePool c2) {
		if (c1.getPreRat4PreviousDiference() > c2.getPreRat4PreviousDiference()) {
		    return 1;
		} else if (c1.getPreRat4PreviousDiference() == c2.getPreRat4PreviousDiference()) {
		    return 0;
		} else {
		    return -1;
		}
	    }
	};
    }

    /**
     * Comparador que ordena por la propiedad order de cada PrePool.
     */
    public static Comparator<PrePool> getOrderComparator() {
	return new Comparator<PrePool>() {
	    public int compare(PrePool c1, PrePool c2) {
		if (c1.getPreId() > c2.getPreId()) {
		    return 1;
		} else if (c1.getPreId() == c2.getPreId()) {
		    return 0;
		} else {
		    return -1;
		}
	    }
	};
    }
}
