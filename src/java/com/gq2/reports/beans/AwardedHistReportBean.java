package com.gq2.reports.beans;

import com.gq2.enums.GenerationBetType;
import com.gq2.enums.ReductionType;
import com.gq2.reports.AwardedHit;
import com.gq2.reports.AwardedHitsReportGenerator;
import com.gq2.reports.AwardedHitsReportService;
import com.gq2.services.ChampionshipService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "awardedHitsReport")
@ViewScoped
public class AwardedHistReportBean {

    private List<SelectItem> championshipItemList = new ArrayList();
    private List<SelectItem> roundItemList = new ArrayList();
    private int generateMode = 1;
    private int chaId;
    private Integer season;
    private ChampionshipService championshipService = new ChampionshipService();
    private Integer generationBetType = 0;
    private List<String> reductionTypes = new ArrayList<>();
    private List<ReductionType> reductionTypeList = new ArrayList();
    private List<GenerationBetType> generationBetTypeList = new ArrayList();

    /**
     * Creates a new instance of fgenerarAuthomaticColumnsBean
     */
    public AwardedHistReportBean() {
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
	Calendar cal = Calendar.getInstance();
	cal.setTime(championshipService.load(chaId).getChaStartDate());
	setSeason(cal.get(Calendar.YEAR));

    }

    public Integer getSeason() {
	return season;
    }

    public void setSeason(Integer season) {
	this.season = season;
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

    public void generateReport() {

	/*
	 * Se obtiene un objeto ReportService
	 */
	AwardedHitsReportService reportService = new AwardedHitsReportService();

	/*
	 * Se obtiene la lista de datos para enviar al report
	 */
	for (int i = 0; i < reductionTypes.size(); i++) {
	    if (reductionTypes.get(i).equals("0")) {
		reductionTypes.set(i, "14");
		break;
	    }
	}
	List<AwardedHit> awardedHitList;
	awardedHitList = reportService.getAwardedHitList(season, null,
		GenerationBetType.parse(generationBetType).getText(), reductionTypes, null);

	/*
	 * Se lanza el generador del report	
	 */
	FacesContext fc = FacesContext.getCurrentInstance();
	AwardedHitsReportGenerator modelReportGenerator = new AwardedHitsReportGenerator();

	// Pinta el pdf a la salida
	modelReportGenerator.writePDFReport(fc, awardedHitList,
		"awardedHitsReport");

    }
}
