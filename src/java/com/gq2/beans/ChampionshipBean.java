package com.gq2.beans;

import com.gq2.domain.Championship;
import com.gq2.services.ChampionshipService;
import java.util.List;
import javax.faces.bean.*;
import javax.faces.model.SelectItem;

/**
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "championship")
@RequestScoped
public class ChampionshipBean extends Championship {

    private String runAction;
    private String keyId;
    private Integer rowCount;
    private Integer rowStart;
    private Integer rowChunk;
    private boolean registered;
    private ChampionshipService championshipService;

    public ChampionshipBean() {
	championshipService = new ChampionshipService();
    }

    public void setRunAction(String value) {
	this.runAction = value;
    }

    public String getRunAction() {
	return runAction;
    }

    public String getKeyId() {
	return keyId;
    }

    public void setKeyId(String keyId) {
	this.keyId = keyId;
    }

    public boolean isRegistered() {
	return registered;
    }

    public Integer getRowChunk() {
	return rowChunk;
    }

    public void setRowChunk(Integer rowChunk) {
	this.rowChunk = rowChunk;
    }

    public Integer getRowCount() {
	return rowCount;
    }

    public void setRowCount(Integer rowCount) {
	this.rowCount = rowCount;
    }

    public Integer getRowStart() {
	return rowStart;
    }

    public void setRowStart(Integer rowStart) {
	this.rowStart = rowStart;
    }

    public List<Championship> getChampionshipList() {
	List<Championship> championshipList = championshipService.getChampionshipList();
	setRowCount(championshipList.size());
	return championshipList;
    }

    public List<SelectItem> getChampionshipItemList() {
	return championshipService.getChampionshipItemList();
    }

    public void setPropertiesFromChampionshipObject(Championship championship) {
	setChaId(championship.getChaId());
	setChaStartDate(championship.getChaStartDate());
	setChaStatus(championship.getChaStatus());
	setChaCode(championship.getChaCode());
	setChaDescription(championship.getChaDescription());
	setChaSeason(championship.getChaSeason());
	setChaPointsWin(championship.getChaPointsWin());
	setChaPointsDraw(championship.getChaPointsDraw());
	setChaPointsLose(championship.getChaPointsLose());
	setChaMaxTeams(championship.getChaMaxTeams());

    }

    public String cancel() {
	return "cancelChampionship";
    }

    public String create() {
	return "newChampionship";
    }

    public String save() {
	if (this.getChaId() > 0) {
	    championshipService.update(this);
	    return "listChampionship";
	} else {
	    championshipService.save(this);
	    return "newChampionship";
	}
    }

    public String edit(Championship championship) {
	if (championship != null) {
	    setPropertiesFromChampionshipObject(championship);
	}
	return "editChampionship";
    }

    public String delete() {
	championshipService.delete(this);
	return "deleteChampionship";
    }

    public String search() {
	// TODO Implement method
	return "searchChampionship";
    }
}
