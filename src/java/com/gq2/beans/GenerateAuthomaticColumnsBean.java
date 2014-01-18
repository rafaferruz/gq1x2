package com.gq2.beans;

import com.gq2.DAO.DAOFactory;
import com.gq2.enums.GenerationBetType;
import com.gq2.enums.ReductionType;
import com.gq2.services.ChampionshipService;
import com.gq2.services.MakeAuthomaticColumns;
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
@ManagedBean(name = "genAuthomaticColumns")
@ViewScoped
public class GenerateAuthomaticColumnsBean {

    private List<SelectItem> championshipItemList = new ArrayList();
    private List<SelectItem> roundItemList = new ArrayList();
    private int generateMode = 1;
    private int chaId;
    private int round;
    private Boolean disabledRounds = false;
    private ChampionshipService championshipService = new ChampionshipService();
        private Integer generationBetType = 0;
        private List<String> reductionTypes = new ArrayList<>();
    private List<ReductionType> reductionTypeList = new ArrayList();
    private List<GenerationBetType> generationBetTypeList = new ArrayList();


    /**
     * Creates a new instance of fgenerarAuthomaticColumnsBean
     */
    public GenerateAuthomaticColumnsBean() {
    }

    public GenerateAuthomaticColumnsBean(int chaId, int round) {
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

    public Integer getGenerationBetType() {
	return generationBetType;
    }

    public void setGenerationBetType(Integer generationBetType) {
	this.generationBetType = generationBetType;
    }

    public List<String> getReductionTypes() {
	return reductionTypes;
    }

    public void setReductionTypes(List<String> reductionTypes) {
	this.reductionTypes = reductionTypes;
    }

    public List<ReductionType> getReductionTypeList() {
	reductionTypeList.clear();
	setReductionTypeList(ReductionType.listReductionTypes());
	return reductionTypeList;
    }

    public void setReductionTypeList(List<ReductionType> reductionTypeList) {
	this.reductionTypeList = reductionTypeList;
    }
    public List<GenerationBetType> getGenerationBetTypeList() {
	setGenerationBetTypeList(GenerationBetType.listGenerationBetTypes());
	return generationBetTypeList;
    }

    public void setGenerationBetTypeList(List<GenerationBetType> generationBetTypeList) {
	this.generationBetTypeList = generationBetTypeList;
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

	MakeAuthomaticColumns makeAuthomaticColumns = new MakeAuthomaticColumns();

	switch (generateMode) {
	    case 0: // Se procesa solamente la ronda seleccionada de un campeonato
		makeAuthomaticColumns.processRound(chaId, round, getGenerationBetType(), getReductionTypes());
		break;
	    case 1: // Se procesan las rondas de un campeonato desde una ronda determinada
		// Se excluye del siguiente bucle el item 0 porque es el titulo de cabecera de la lista de rounds
		for (SelectItem item : roundItemList.subList(1, roundItemList.size())) {
		    if ((Integer) item.getValue() >= round) {
			makeAuthomaticColumns.processRound(chaId, (Integer) item.getValue(), getGenerationBetType(), getReductionTypes());
		    }
		}
		break;
	    case 2: // Se procesan todas las rondas de un campeonato (un campeonato completo)
		// Se excluye del siguiente bucle el item 0 porque es el titulo de cabecera de la lista de rounds
		for (SelectItem item : roundItemList.subList(1, roundItemList.size())) {
		    makeAuthomaticColumns.processRound(chaId, (Integer) item.getValue(), getGenerationBetType(), getReductionTypes());
		}
		System.out.println("Generaci�n AuthomaticColumns finalizada. Campeonato "
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
				    makeAuthomaticColumns.processRound(chaId, (Integer) item.getValue(), getGenerationBetType(), getReductionTypes());
				}
			    }
			    System.out.println("Generaci�n AuthomaticColumns finalizada. Campeonato "
				    + new DAOFactory().getChampionshipDAO().load(chaId).getChaDescription());

			}
		    }
		}
		break;
	    default:
	}
    }
}
