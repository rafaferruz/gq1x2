package com.gq2.beans;

import com.gq2.DAO.DAOFactory;
import com.gq2.services.ChampionshipService;
import com.gq2.services.MakeSuperTable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "genSuperTable")
@ViewScoped
public class GenerateSuperTableBean implements Serializable {

    private List<SelectItem> championshipItemList = new ArrayList();
    private List<SelectItem> roundItemList = new ArrayList();
    private int generateMode = 1;
    private int chaId;
    private int round;
    private Boolean disabledRounds = false;
    private ChampionshipService championshipService = new ChampionshipService();

    /**
     * Creates a new instance of fgenerarSuperTableBean
     */
    public GenerateSuperTableBean() {
    }

    public GenerateSuperTableBean(int chaId, int round) {
	this.chaId = chaId;
	this.round = round;
    }

    public Boolean getDisabledRondas() {
	return disabledRounds;
    }

    public void setDisabledRondas(Boolean disabledRounds) {
	this.disabledRounds = disabledRounds;
    }

    public int getGenerateMode() {
	return generateMode;
    }

    public void setGenerateMode(int generateMode) {
	this.generateMode = generateMode;
    }

    public int getChaId() {
	return chaId;
    }

    public void setChaId(int chaId) {
	this.chaId = chaId;
    }

    public Integer getRound() {
	return round;
    }

    public void setRound(Integer round) {
	this.round = round;
    }

    public void setRoundItemList(List<SelectItem> roundItemList) {
	this.roundItemList = roundItemList;
    }

    public List<SelectItem> getRoundItemList() {
	return this.roundItemList;
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

    public void championshipChangeEvent(ValueChangeEvent ev) {
	if (ev.getNewValue() != 0) {
	    if (championshipItemList.isEmpty()) {
		setChampionshipItemList(championshipService.getChampionshipItemList());
	    }
	    chaId = (Integer) ev.getNewValue();
	    setRoundItemList(championshipService.getRoundItemList(chaId));
	    round = 0;
	} else {
	    clearLists();
	}
    }

    public void roundChangeEvent(ValueChangeEvent ev) {
	if (ev.getNewValue() != null) {
	    round = (Integer) ev.getNewValue();
	}
    }

    private void clearLists() {
	championshipItemList.clear();
	roundItemList.clear();
    }

    public void generateProcess() {

	MakeSuperTable makeSuperTable = new MakeSuperTable();

	switch (generateMode) {
	    case 0: // Se procesa solamente la ronda seleccionada de un campeonato
		makeSuperTable.processRound(chaId, round);
		break;
	    case 1: // Se procesan las rondas de un campeonato desde una ronda determinada
		// Se excluye del siguiente bucle el item 0 porque es el titulo de cabecera de la lista de rounds
		for (SelectItem item : roundItemList.subList(1, roundItemList.size())) {
		    if ((Integer) item.getValue() >= round) {
			makeSuperTable.processRound(chaId, (Integer) item.getValue());
		    }
		}
		break;
	    case 2: // Se procesan todas las rondas de un campeonato (un campeonato completo)
		// Se excluye del siguiente bucle el item 0 porque es el titulo de cabecera de la lista de rounds
		for (SelectItem item : roundItemList.subList(1, roundItemList.size())) {
		    makeSuperTable.processRound(chaId, (Integer) item.getValue());
		}
		System.out.println("Generaci�n SuperTable finalizada. Campeonato "
			+ new DAOFactory().getChampionshipDAO().load(chaId).getChaDescription());
		break;
	    case 3: // Se procesan todos los campeonatos desde uno seleccionado
		boolean processingSwitch = false;
		if (!championshipItemList.isEmpty()) {
		    for (int i = championshipItemList.size() - 1; i >= 0; i--) {
			if (chaId != 0 && chaId == (Integer) championshipItemList.get(i).getValue()) {
			    processingSwitch = true;
			}
			if (processingSwitch && chaId != 0) {
			    chaId = (Integer) championshipItemList.get(i).getValue();
			    setRoundItemList(championshipService.getRoundItemList(chaId));
			    // Se excluye del siguiente bucle el item 0 porque es el titulo de cabecera de la lista de rounds
			    for (SelectItem item : roundItemList.subList(1, roundItemList.size())) {
				if (item.getValue() != 0) {
				    makeSuperTable.processRound(chaId, (Integer) item.getValue());
				}
			    }
			    System.out.println("Generaci�n SuperTable finalizada. Campeonato "
				    + new DAOFactory().getChampionshipDAO().load(chaId).getChaDescription());

			}
		    }
		}
		break;
	    default:
	}
    }
}
