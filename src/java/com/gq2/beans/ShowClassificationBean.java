package com.gq2.beans;

import com.gq2.services.ChampionshipService;
import com.gq2.services.ClassificationService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author ESTHER
 */
@ManagedBean(name = "showClasif")
@SessionScoped
public class ShowClassificationBean {

    private List<SelectItem> championshipItemList = new ArrayList();
    private List<SelectItem> roundItemList = new ArrayList();
    private List<ClassificationBean> classificationBeanList = new ArrayList();
    private ClassificationBean cla = null;
    private int chaId;
    private int round;
    private Date roundDate = null;
    private ChampionshipService championshipService = new ChampionshipService();
    private ClassificationService classificationService = new ClassificationService();

    /**
     * Creates a new instance of fgenerarSuperTableBean
     */
    public ShowClassificationBean() {
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

    public void setRoundItemList(List roundItemList) {
	this.roundItemList = roundItemList;
    }

    public List getRoundItemList() {
	return this.roundItemList;
    }

    public void setClassificationBeanList(List<ClassificationBean> classificationBeanList) {
	this.classificationBeanList = classificationBeanList;
    }

    public Collection getClassificationBeanList() {
	return classificationBeanList;
    }

    public int getChaId() {
	return chaId;
    }

    public void setChaId(int chaId) {
	this.chaId = chaId;
    }

    public int getRound() {
	return round;
    }

    public void setRound(int round) {
	this.round = round;
    }
    private void getRoundClassifications(){
	    setClassificationBeanList(classificationService.getClassificationBeanList(chaId, round));
    }

    public void championshipChangeEvent(ValueChangeEvent ev) {
	if (ev.getNewValue() != 0) {
	    if (championshipItemList.isEmpty()) {
		setChampionshipItemList(championshipService.getChampionshipItemList());
	    }
	    chaId = (Integer) ev.getNewValue();
	    setRoundItemList(championshipService.getRoundItemList(chaId));
	} else {
	    clearLists();
	}
    }

    public void roundChangeEvent(ValueChangeEvent ev) {
	if (ev.getNewValue() != null) {
	    round = (Integer) ev.getNewValue();
	    getRoundClassifications();
	}
    }


    private void clearLists() {
	championshipItemList.clear();
	roundItemList.clear();
    }
}
