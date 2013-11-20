package com.gq2.beans;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.EnrolledTeam;
import com.gq2.domain.Team;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "enrolledTeam")
@ViewScoped
public class EnrolledTeamBean extends EnrolledTeam {

    private String entTeamName;
    private ChampionshipBean championshipBean;
    private List<SelectItem> championshipItemList = new ArrayList();
    private List<SelectItem> enrolledTeamItemList = new ArrayList();
    private List<SelectItem> disposableTeamItemList = new ArrayList();
    private Integer selectedDisposableTeam;

    public EnrolledTeamBean() {
    }

    public String getEntTeamName() {
	return entTeamName;
    }

    public void setEntTeamName(String entTeamName) {
	this.entTeamName = entTeamName;
    }

    public List<SelectItem> getChampionshipItemList() {
	if (championshipItemList.isEmpty()) {
	    loadAllChampionshipItemList();
	}
	return championshipItemList;
    }

    public void setChampionshipItemList(List<SelectItem> championshipItemList) {
	this.championshipItemList = championshipItemList;
    }

    public List<SelectItem> getEnrolledTeamItemList() {
	return enrolledTeamItemList;
    }

    public void setEnrolledTeamItemList(List<SelectItem> enrolledTeamItemList) {
	this.enrolledTeamItemList = enrolledTeamItemList;
    }

    public List<SelectItem> getDisposableTeamItemList() {
	if (disposableTeamItemList.isEmpty()) {
	    disposableTeamItemList = loadDisposableTeamItemList();
	}
	removeFromDisposableTeamItemList(enrolledTeamItemList);
	return disposableTeamItemList;
    }

    public void setDisposableTeamItemList(List<SelectItem> disposableTeamItemList) {
	this.disposableTeamItemList = disposableTeamItemList;
    }

    public Integer getSelectedDisposableTeam() {
	return selectedDisposableTeam;
    }

    public void setSelectedDisposableTeam(Integer selectedDisposableTeam) {
	this.selectedDisposableTeam = selectedDisposableTeam;
    }

    public EnrolledTeam loadEnrolledTeam() {

	EnrolledTeam enrolledTeam = new DAOFactory().getEnrolledTeamDAO().load(entId);
	if (enrolledTeam != null) {
	    setEntId(enrolledTeam.getEntId());
	    setEntChaId(enrolledTeam.getEntChaId());
	    setEntTeaId(enrolledTeam.getEntChaId());
	}
	return enrolledTeam;
    }

    private List<EnrolledTeamBean> loadEnrolledTeamBeanList(Integer entChaId) {
	return new DAOFactory().getEnrolledTeamDAO().loadChampionshipEnrolledTeams(entChaId);
    }

    private List<SelectItem> loadEnrolledTeamItemList(Integer entChaId) {
	List<SelectItem> selectItemList = new ArrayList<>();
	for (EnrolledTeamBean enrolledTeam : loadEnrolledTeamBeanList(entChaId)) {
	    selectItemList.add(new SelectItem(enrolledTeam.getEntTeaId(), enrolledTeam.getEntTeamName()));
	}
	return selectItemList;
    }

    public List<Team> loadAllTeamsList() {
	return new DAOFactory().getTeamDAO().loadAllTeams();
    }

    private List<SelectItem> loadDisposableTeamItemList() {
	List<SelectItem> selectItemList = new ArrayList<>();
	for (Team team : loadAllTeamsList()) {
	    selectItemList.add(new SelectItem(team.getTeaId(), team.getTeaName()));
	}
	return selectItemList;
    }

    private void loadAllChampionshipItemList() {
	championshipItemList = new DAOFactory().getChampionshipDAO().loadAllChampionshipsItemList();
    }

    public void enrolling() {
	// Cada disposableTeam ....
	for (SelectItem disposableItem : disposableTeamItemList) {
	    // ...se compara con el equipo buscado y cuando se encuentra...
		if (selectedDisposableTeam == (Integer) disposableItem.getValue()) {
		    // se persiste en la BD
		    new DAOFactory().getEnrolledTeamDAO().save(new EnrolledTeam(entChaId, (Integer)disposableItem.getValue()));
		    // ... se añade a la lista de inscritos (enrolled)...
		    enrolledTeamItemList.add(disposableItem);
		    // ...y se elimina de equipos disponibles
		    disposableTeamItemList.remove(disposableItem);
		    break;
		}
	}
    }

    public void backenrolling() {
	// Cada enrolledTeam ....
	for (SelectItem enrolledItem : enrolledTeamItemList) {
	    // ...se compara con el equipo buscado y cuando se encuentra...
		if (entTeaId == (Integer) enrolledItem.getValue()) {
		    // ...se elimina de la BD...
		    new DAOFactory().getEnrolledTeamDAO().delete(entChaId, (Integer)enrolledItem.getValue());
		    // ... se añade a la lista de disponibles...
		    disposableTeamItemList.add(enrolledItem);
		    // ...y se elimina de equipos inscritos
		    enrolledTeamItemList.remove(enrolledItem);
		    break;
		}
	}

    }

    public void championshipChangeEvent(ValueChangeEvent ev) {
	if (ev.getNewValue() != null) {
	    if (championshipItemList.isEmpty()) {
		loadAllChampionshipItemList();
	    }
	    entChaId = (Integer) ev.getNewValue();
	    enrolledTeamItemList.clear();
	    enrolledTeamItemList = loadEnrolledTeamItemList(entChaId);
	} else {
	    clearItemLists();
	    entChaId = 0;
	}
    }

    private void clearItemLists() {
	championshipItemList.clear();
	enrolledTeamItemList.clear();
	entChaId = 0;
    }

    private void removeFromDisposableTeamItemList(List<SelectItem> enrolledTeamItemList) {
	// Cada enrolledTeam ....
	for (SelectItem enrolledItem : enrolledTeamItemList) {
	    // ...se busca en la lista de equipos disponibles...
	    for (SelectItem disposableItem : disposableTeamItemList) {
		// ...y si se encuentra en dicha lista se saca de ella
		if ((Integer) disposableItem.getValue() == (Integer) enrolledItem.getValue()) {
		    disposableTeamItemList.remove(disposableItem);
		    break;
		}
	    }
	}
    }
}
