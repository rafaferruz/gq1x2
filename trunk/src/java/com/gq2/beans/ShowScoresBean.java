package com.gq2.beans;

import com.gq2.domain.Score;
import com.gq2.services.ChampionshipService;
import com.gq2.services.MakeClassification;
import com.gq2.services.MakePrognostic;
import com.gq2.services.MakeSuperTable;
import com.gq2.services.ScoreService;
import com.gq2.services.TeamService;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "showScores")
@ViewScoped
public class ShowScoresBean extends ScoreBean implements Serializable {

    private List<SelectItem> championshipItemList = new ArrayList();
    private List<SelectItem> roundItemList = new ArrayList();
    private List<SelectItem> teamItemList = new ArrayList();
    private List<SelectItem> teamItemList_1 = new ArrayList();
    private List<SelectItem> teamItemList_2 = new ArrayList();
    private List<ScoreBean> scoreList = new ArrayList();
    private boolean disabledRounds = false;
    private String disabledCreateCommand = "true";
    private ChampionshipService championshipService = new ChampionshipService();
    private TeamService teamService = new TeamService();
    private ScoreService scoreService = new ScoreService();

    /**
     * Creates a new instance of ShowScoresBean
     */
    public ShowScoresBean() {
    }

    @Override
    public void setScoDate(Date date){
	super.setScoDate(date);
    }
    @Override
    public Date getScoDate(){
	return super.getScoDate();
    }
    
    public String getDisabledCreateCommand() {
	return disabledCreateCommand;
    }

    public void setDisabledCreateCommand(String disabledCreateCommand) {
	this.disabledCreateCommand = disabledCreateCommand;
    }

    public void setRoundItemList(List<SelectItem> roundItemList) {
	this.roundItemList = roundItemList;
    }

    public List<SelectItem> getRoundItemList() {
	return this.roundItemList;
    }

    public List<ScoreBean> getScoreList() {
	return scoreList;
    }

    public void setScoreList(List<ScoreBean> scoreList) {
	this.scoreList = scoreList;
    }

    public List<SelectItem> getChampionshipItemList() {
	if (championshipItemList.isEmpty()) {
	    setChampionshipItemList(championshipService.getChampionshipItemList());
	}
	return this.championshipItemList;
    }

    public void setChampionshipItemList(List<SelectItem> championshipItemList) {
	this.championshipItemList = championshipItemList;
    }

    public boolean getDisabledRounds() {
	return this.disabledRounds;
    }

    public void setDisabledRounds(boolean disabledRounds) {
	this.disabledRounds = disabledRounds;
    }

    public List<SelectItem> getTeamItemList() {
	return teamItemList;
    }

    public void setTeamItemList(List<SelectItem> teamItemList) {
	this.teamItemList = teamItemList;
    }

    public List<SelectItem> getTeamItemList_1() {
	return teamItemList_1;
    }

    public void setTeamItemList_1(List<SelectItem> teamItemList_1) {
	this.teamItemList_1 = teamItemList_1;
    }

    public List<SelectItem> getTeamItemList_2() {
	return teamItemList_2;
    }

    public void setTeamItemList_2(List<SelectItem> teamItemList_2) {
	this.teamItemList_2 = teamItemList_2;
    }

    private void getRoundsAndTeams(int chaId) {
	setRoundItemList(championshipService.getRoundItemList(chaId));
	setTeamItemList(teamService.getTeamItemListInChampionship(chaId));
	reloadTeamItemLists();
    }

    private void getRoundScores() {
	if (getScoChaId() <= 0 || getScoRound() <= 0) {
	    scoreList.clear();
	    setScoDate(null);
	    return;
	}
	setScoreList(scoreService.getChampionshipRoundScores(getScoChaId(), getScoRound()));
	if (scoreList.size() > 0) {
	    setScoDate(scoreList.get(0).getScoDate());
	}
    }

    public boolean updateScoresDate() {
	return scoreService.updateScoresDate(this);
    }

    public void updateScores() {
	scoreService.updateScoreList(scoreList);
    }

    public void generateClassifications() {

	updateScores();
	FacesContext fcClasifications = FacesContext.getCurrentInstance();
	HttpServletRequest request = (HttpServletRequest) fcClasifications.getExternalContext().getRequest();
	HttpServletResponse response = (HttpServletResponse) fcClasifications.getExternalContext().getResponse();
	request.setAttribute("campeonatos", getScoChaId());
	request.setAttribute("generartodos", null);
	request.setAttribute("generardesde", null);
	request.setAttribute("getScoRound()", getScoRound());
	request.setAttribute("roundfinal", getScoRound());
	MakeClassification clasif = new MakeClassification();
	clasif.processRound(getScoChaId(), getScoRound());
	MakeSuperTable supertable = new MakeSuperTable();
	supertable.processRound(getScoChaId(), getScoRound());
	MakePrognostic prognostic = new MakePrognostic();
	prognostic.processRound(getScoChaId(), getScoRound());
    }

    private void reloadTeamItemLists() {
	teamItemList_1.clear();
	for (SelectItem item : teamItemList) {
	    teamItemList_1.add(item);
	}
	teamItemList_2.clear();
	for (SelectItem item : teamItemList) {
	    teamItemList_2.add(item);
	}
    }

    public void createMatch() {
	setScoScore1(0);
	setScoScore2(0);
	scoreService.save((Score) this);
	getRoundScores();
	checkTeamItemListSizes();
    }

    public void deleteMatches() {
	for (ScoreBean scoreBean : scoreList) {
	    if (scoreBean.isScoMarkDelete()) {
		scoreService.delete(scoreBean);
	    }
	}
	getRoundScores();
	reloadTeamItemLists();
	checkTeamItemListSizes();
    }

    public void changeRoundDate() {
	updateScoresDate();
	getRoundScores();
    }

    public void roundDateValidator(FacesContext context, UIComponent component,
	    Object value) {
	String message;
	DateFormat df = new SimpleDateFormat();
	try {
	    setScoDate((Date) value);
	} catch (Exception e) {
	    message = "Fecha erronea.";
	    context.addMessage(component.getClientId(context),
		    new FacesMessage(message));
	}
    }

    public void championshipChangeEvent(ValueChangeEvent ev) {
	if (ev.getNewValue() != 0) {
	    if (championshipItemList.isEmpty()) {
		setChampionshipItemList(championshipService.getChampionshipItemList());
	    }
	    setScoChaId((Integer) ev.getNewValue());
	    setScoRound(0);
	    scoreList.clear();
	    getRoundsAndTeams(getScoChaId());
	    setScoDate(null);
	} else {
	    clearLists();
	}
    }

    public void roundChangeEvent(ValueChangeEvent ev) {
	scoreList.clear();
	if (ev.getNewValue() != null) {
	    setScoRound((Integer) ev.getNewValue());
/*	Las siguientes líneas deberán ser restauradas si se modifica el mecanismo de mostrar los resultados
	    getRoundScores();
	    reloadTeamItemLists();
	    checkTeamItemListSizes();
*/
	}
    }

    public void showResultsEvent(ActionEvent ev) {
	scoreList.clear();
	if (ev != null) {
	    getRoundScores();
	    reloadTeamItemLists();
	    checkTeamItemListSizes();
	}
    }

    public void dateChangeEvent(ValueChangeEvent ev) {
	if (ev.getNewValue() == null) {
	    disabledCreateCommand = "true";
	}
    }

    private void clearLists() {
	championshipItemList.clear();
	roundItemList.clear();
	teamItemList.clear();
	scoreList.clear();
	setScoChaId(0);
	setScoDate(null);
    }

    private void checkTeamItemListSizes() {
	if (scoreList.isEmpty()) {
	    setScoDate(null);
	    disabledCreateCommand = "true";
	}
	removeTeamsFromLists();
	if (teamItemList_1.isEmpty() && teamItemList_2.isEmpty()) {
	    disabledCreateCommand = "true";
	} else {
	    disabledCreateCommand = "false";
	}

    }

    private void removeTeamsFromLists() {
	for (ScoreBean score : scoreList) {
	    for (SelectItem item : teamItemList_1) {
		if (item.getValue() == score.getScoTeam1Id()) {
		    teamItemList_1.remove(item);
		    break;
		}
	    }
	    for (SelectItem item : teamItemList_1) {
		if (item.getValue() == score.getScoTeam2Id()) {
		    teamItemList_1.remove(item);
		    break;
		}
	    }
	    for (SelectItem item : teamItemList_2) {
		if (item.getValue() == score.getScoTeam1Id()) {
		    teamItemList_2.remove(item);
		    break;
		}
	    }
	    for (SelectItem item : teamItemList_2) {
		if (item.getValue() == score.getScoTeam2Id()) {
		    teamItemList_2.remove(item);
		    break;
		}
	    }
	}
    }
}
