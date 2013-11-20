package com.gq2.beans;

import com.gq2.domain.Championship;
import com.gq2.services.ChampionshipService;
import java.sql.*;
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

    public String edit() {
	load();
	return "edit";
    }

    public int save() throws SQLException {

	return championshipService.save(this);

    }

    public void load() {

	Championship championship = championshipService.load(chaId);
	if (championship != null) {
	    setPropertiesFromChampionshipObject(championship);
	}
    }

    public boolean delete() {
	return championshipService.delete(this);
    }

    public boolean update() {
	return championshipService.update(this);
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

    public String cancelAction() {
	return "cancelChampionship";
    }

    public String newChampionship() {
	return "newChampionship";
    }

    public String saveNewChampionship() throws SQLException {
	save();
	return "newChampionship";
    }

    public String deleteChampionship() {
	delete();
	return "deleteChampionship";
    }

    public String searchChampionship() {
	// TODO Implement method
	return "searchChampionship";
    }

    public List<Championship> getChampionshipList() {
	List<Championship> championshipList = championshipService.getChampionshipList();
	setRowCount(championshipList.size());
	return championshipList;
    }

    public List<SelectItem> getChampionshipItemList() {
	return championshipService.getChampionshipItemList();
    }
}
