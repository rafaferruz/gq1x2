package com.gq2.reports.beans;

import com.gq2.beans.*;
import com.gq2.enums.GenerationBetType;
import com.gq2.reports.AwardedHit;
import com.gq2.reports.ChampionshipScoresReportGenerator;
import com.gq2.services.ChampionshipService;
import com.gq2.services.ScoreService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "championshipScoresReport")
@ViewScoped
public class ChampionshipScoresReportBean extends ScoreBean implements Serializable {

    private List<SelectItem> championshipItemList = new ArrayList();
    private ChampionshipService championshipService = new ChampionshipService();

    public ChampionshipScoresReportBean() {
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
	    setScoChaId((Integer) ev.getNewValue());
	}
    }

        public void generateReport() {

	/*
	 * Se obtiene un objeto ReportService
	 */
	ScoreService reportService = new ScoreService();

	List<ScoreBean> scoreBeanList;
	scoreBeanList = reportService.getChampionshipScoresList(getScoChaId());

	/*
	 * Se lanza el generador del report	
	 */
	FacesContext fc = FacesContext.getCurrentInstance();
	ChampionshipScoresReportGenerator modelReportGenerator = new ChampionshipScoresReportGenerator();

	// Pinta el pdf a la salida
	modelReportGenerator.writePDFReport(fc, scoreBeanList, getChampionshipDescription(getScoChaId()), 
		"championshipScoresReport");


    }
	private String getChampionshipDescription(Integer chaId){
	    for (SelectItem selectItem:championshipItemList){
		if (selectItem.getValue()==chaId){
		    return selectItem.getLabel();
		}
	    }
	    return null;
	}

}
