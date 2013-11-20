package com.gq2.beans;

import com.gq2.services.MakeClassification;
import com.gq2.services.ChampionshipService;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "genClasif")
@ViewScoped
public class GenerateClassificationBean {

    private int chaId;
    private int round;
    private int generateMode;
    private Boolean disabledRounds = true;
    private int claId;
    private List<SelectItem> championshipItemList = new ArrayList();
    private List<SelectItem> roundItemList = new ArrayList();
    private ChampionshipService championshipService = new ChampionshipService();

    public GenerateClassificationBean() {
    }

    public GenerateClassificationBean(int chaId, int round) {
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

    public int getClaId() {
	return claId;
    }

    public void setClaId(int claId) {
	this.claId = claId;
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

	MakeClassification makeClassification = new MakeClassification();

	switch (generateMode) {
	    case 0: // Se procesa solamente la ronda seleccionada de un campeonato
		makeClassification.processRound(chaId, round);
		break;
	    case 1: // Se procesan las rondas de un campeonato desde una ronda determinada
		// Se excluye del siguiente bucle el item 0 porque es el titulo de cabecera de la lista de rounds
		for (SelectItem item : roundItemList.subList(1, roundItemList.size())) {
		    if ((Integer) item.getValue() >= round) {
			makeClassification.processRound(chaId, (Integer) item.getValue());
		    }
		}
		break;
	    case 2: // Se procesan todas las rondas de un campeonato
		// Se excluye del siguiente bucle el item 0 porque es el titulo de cabecera de la lista de rounds
		for (SelectItem item : roundItemList.subList(1, roundItemList.size())) {
		    makeClassification.processRound(chaId, (Integer) item.getValue());
		}
		break;
	    case 3: // Se procesan todos los campeonatos desde uno seleccionado
		boolean processingSwitch = false;
		if (!championshipItemList.isEmpty()) {
		    for (int i = championshipItemList.size() - 1; i >= 0; i--) {
			if (chaId != 0 && chaId == (Integer) championshipItemList.get(i).getValue()) {
			    processingSwitch = true;
			}
			if (processingSwitch) {
			    chaId = (Integer) championshipItemList.get(i).getValue();
			    setRoundItemList(championshipService.getRoundItemList(chaId));
			    // Se excluye del siguiente bucle el item 0 porque es el titulo de cabecera de la lista de rounds
			    for (SelectItem item : roundItemList.subList(1, roundItemList.size())) {
				if (item.getValue() != 0) {
				    makeClassification.processRound(chaId, (Integer) item.getValue());
				}
			    }
			}
		    }
		}
		break;
	    default:
	}
    }
}
