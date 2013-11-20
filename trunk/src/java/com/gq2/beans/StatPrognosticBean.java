package com.gq2.beans;

import com.gq2.domain.PrePool;
import com.gq2.services.PrePoolService;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "statPrognostic")
@ViewScoped
public class StatPrognosticBean {

    static final int MINIMUN_SIGN_1 = 4;    // m�nimo n�mero de 1 esperados
    static final int MAXIMUN_SIGN_1 = 9;    // maximo n�mero de 1 esperados
    static final int MINIMUN_SIGN_X = 2;    // m�nimo n�mero de X esperados
    static final int MAXIMUN_SIGN_X = 5;    // maximo n�mero de X esperados
    static final int MINIMUN_SIGN_2 = 2;    // m�nimo n�mero de 2 esperados
    static final int MAXIMUN_SIGN_2 = 2;    // maximo n�mero de 2 esperados
    static final int RMINUS = -2;
    static final int RPLUS = 2;
    
    private List<StatBean> statBeanList = new ArrayList();
    private StatBean regstat = null;
    private Integer season = null;
    private Boolean changeSeason = false;
    private Integer firstRound = null;
    private Integer lastRound = null;
    private Boolean changeRound = false;
    private Integer signs1 = 0;
    private Integer signsX = 0;
    private Integer signs2 = 0;
    private PrePoolService prePoolService = new PrePoolService();

    public StatPrognosticBean() {
    }

    public Integer getSeason() {
	return season;
    }

    public void setSeason(Integer season) {
	this.season = season;
	if (this.season != null && this.firstRound != null && this.lastRound != null
		&& (changeSeason == true || changeRound == true)) {
	    loadStatBeanListFromSeveralRounds(this.season, this.firstRound, this.lastRound);
	    setStatBeanList(this.statBeanList);
	}
    }

    public Integer getFirstRound() {
	return firstRound;
    }

    public void setFirstRound(Integer round) {
	this.firstRound = round;
	if (this.season != null && this.firstRound != null && this.lastRound != null
		&& (changeSeason == true || changeRound == true)) {
	    loadStatBeanListFromSeveralRounds(this.season, this.firstRound, this.lastRound);
	}
    }

    public Integer getLastRound() {
	return lastRound;
    }

    public void setLastRound(Integer round) {
	this.lastRound = round;
	if (this.season != null && this.firstRound != null && this.lastRound != null
		&& (changeSeason == true || changeRound == true)) {
	    loadStatBeanListFromSeveralRounds(this.season, this.firstRound, this.lastRound);
	}
    }

    public List getStatBeanList() {
	return statBeanList;
    }

    public void setStatBeanList(List statBeanList) {
	this.statBeanList = statBeanList;
    }

    public Integer getSigns1() {
	return signs1;
    }

    public void setSigns1(Integer signs1) {
	this.signs1 = signs1;
    }

    public Integer getSigns2() {
	return signs2;
    }

    public void setSigns2(Integer signs2) {
	this.signs2 = signs2;
    }

    public Integer getSignsX() {
	return signsX;
    }

    public void setSignsX(Integer signsX) {
	this.signsX = signsX;
    }

    public void loadStatBeanListFromSeveralRounds(Integer season, Integer firstRound, Integer lastRound) {
	statBeanList.clear();
	for (Integer pre_order_number : prePoolService.loadPrePoolOrderNumberList(season, firstRound, lastRound)) {
	    regstat = new StatBean();
	    loadNlastPreTool(season, pre_order_number);
	    statBeanList.add(regstat);
	}
    }

    public void loadNlastPreTool(Integer season, Integer orderNumber) {
	Integer orderNumber4 = orderNumber - 3;   // se consideran solamente las 2 roundItemList anteriores
	for (PrePool prePool : prePoolService.loadNlastPreTool(season, orderNumber, orderNumber4)) {
	    switch (prePool.getPreRat4ScoreSign()) {
		case "W":
		    regstat.setA41(regstat.getA41() + 1);
		    break;
		case "D":
		    regstat.setA4X(regstat.getA4X() + 1);
		    break;
		case "L":
		    regstat.setA42(regstat.getA42() + 1);
		    break;
	    }
	}
// Se lee solamente el PrePool de la ultima jornada
	for (PrePool prePool : prePoolService.loadNlastPreTool(season, orderNumber, orderNumber)) {
	    switch (prePool.getPreRat4ScoreSign()) {
		case "W":
		    regstat.setSp1(regstat.getSp1() + 1);
		    break;
		case "D":
		    regstat.setSpX(regstat.getSpX() + 1);
		    break;
		case "L":
		    regstat.setSp2(regstat.getSp2() + 1);
		    break;
	    }
// Se calculan n�meros de signos seg�n diferencias de rating, en modalidad A y B
	    if (prePool.getPreRat4PreviousDiference() > -10) {
		regstat.setSsra1(regstat.getSsra1() + 1);
	    }
	    if (prePool.getPreRat4PreviousDiference() <= -10 && prePool.getPreRat4PreviousDiference() >= -50) {
		regstat.setSsraX(regstat.getSsraX() + 1);
	    }
	    if (prePool.getPreRat4PreviousDiference() < -50) {
		regstat.setSsra2(regstat.getSsra2() + 1);
	    }
	    if (prePool.getPreRat4PreviousDiference() > 0) {
		regstat.setSsrb1(regstat.getSsrb1() + 1);
	    }
	    if (prePool.getPreRat4PreviousDiference() <= 0 && prePool.getPreRat4PreviousDiference() >= -40) {
		regstat.setSsrbX(regstat.getSsrbX() + 1);
	    }
	    if (prePool.getPreRat4PreviousDiference() < -40) {
		regstat.setSsrb2(regstat.getSsrb2() + 1);
	    }

	}
	calculatePercentSigns();
	calculateExpedtedSignsRange();
	calculateExpedtedSignsRangeOnRatingDiferenceA();
	calculateExpedtedSignsRangeOnRatingDiferenceB();
    }

    private void calculatePercentSigns() {
	Integer A4T = regstat.getA41() + regstat.getA4X() + regstat.getA42();
	regstat.setPorc1(100 * regstat.getA41() / A4T);
	regstat.setPorcX(100 * regstat.getA4X() / A4T);
	regstat.setPorc2(100 * regstat.getA42() / A4T);
    }

    private void calculateExpedtedSignsRange() {
// C�lculo de horquilla de signos esperados
	Integer min1 = Math.max(MINIMUN_SIGN_1, (-1 * ((regstat.getPorc1() < 45 ? regstat.getPorc1() + RMINUS : regstat.getPorc1() + RPLUS) - 45) / 5) - 1 + 6);
	min1 = Math.min(min1, MAXIMUN_SIGN_1 - 2);
	String minstr1 = min1.toString() + "--" + Integer.toString(min1 + 2);
	regstat.setSe1(minstr1);

	Integer minX = Math.max(MINIMUN_SIGN_X, (-1 * ((regstat.getPorcX() < 29 ? regstat.getPorcX() + RMINUS : regstat.getPorcX() + RPLUS) - 29) / 5) - 1 + 4);
	minX = Math.min(minX, MAXIMUN_SIGN_X - 2);
	String minstrX = minX.toString() + "--" + Integer.toString(minX + 2);
	regstat.setSeX(minstrX);

	Integer min2 = Math.max(MINIMUN_SIGN_2, (-1 * ((regstat.getPorc2() < 26 ? regstat.getPorc2() + RMINUS : regstat.getPorc2() + RPLUS) - 26) / 5) - 1 + 4);
	min2 = Math.min(min2, MAXIMUN_SIGN_2 - 2);
	String minstr2 = min2.toString() + "--" + Integer.toString(min2 + 2);
	regstat.setSe2(minstr2);

	if ((regstat.getSp1() - min1) < 0) {
	    regstat.setDse1(regstat.getSp1() - min1);
	}
	if ((regstat.getSp1() - min1 - 2) > 0) {
	    regstat.setDse1(regstat.getSp1() - min1 - 2);
	}
	if ((regstat.getSpX() - minX) < 0) {
	    regstat.setDseX(regstat.getSpX() - minX);
	}
	if ((regstat.getSpX() - minX - 2) > 0) {
	    regstat.setDseX(regstat.getSpX() - minX - 2);
	}
	if ((regstat.getSp2() - min2) < 0) {
	    regstat.setDse2(regstat.getSp2() - min2);
	}
	if ((regstat.getSp2() - min2 - 2) > 0) {
	    regstat.setDse2(regstat.getSp2() - min2 - 2);
	}

    }

    private void calculateExpedtedSignsRangeOnRatingDiferenceA() {
// C�lculo de horquilla de signos esperados segun diferencia rating A y B
	Integer min1 = Math.max(MINIMUN_SIGN_1, regstat.getSsra1() - 1);
	min1 = Math.min(min1, MAXIMUN_SIGN_1 - 2);
	String minstr1 = min1.toString() + "--" + Integer.toString(min1 + 2);
	regstat.setSesra1(minstr1);

	Integer minX = Math.max(MINIMUN_SIGN_X, regstat.getSsraX() - 1);
	minX = Math.min(minX, MAXIMUN_SIGN_X - 2);
	String minstrX = minX.toString() + "--" + Integer.toString(minX + 2);
	regstat.setSesraX(minstrX);

	Integer min2 = Math.max(MINIMUN_SIGN_2, regstat.getSsra2() - 1);
	min2 = Math.min(min2, MAXIMUN_SIGN_2 - 2);
	String minstr2 = min2.toString() + "--" + Integer.toString(min2 + 2);
	regstat.setSesra2(minstr2);

	if ((regstat.getSp1() - min1) < 0) {
	    regstat.setDsesra1(regstat.getSp1() - min1);
	}
	if ((regstat.getSp1() - min1 - 2) > 0) {
	    regstat.setDsesra1(regstat.getSp1() - min1 - 2);
	}
	if ((regstat.getSpX() - minX) < 0) {
	    regstat.setDsesraX(regstat.getSpX() - minX);
	}
	if ((regstat.getSpX() - minX - 2) > 0) {
	    regstat.setDsesraX(regstat.getSpX() - minX - 2);
	}
	if ((regstat.getSp2() - min2) < 0) {
	    regstat.setDsesra2(regstat.getSp2() - min2);
	}
	if ((regstat.getSp2() - min2 - 2) > 0) {
	    regstat.setDsesra2(regstat.getSp2() - min2 - 2);
	}
    }

    private void calculateExpedtedSignsRangeOnRatingDiferenceB() {
// Modo B    
	Integer min1 = Math.max(MINIMUN_SIGN_1, regstat.getSsrb1() - 1);
	min1 = Math.min(min1, MAXIMUN_SIGN_1 - 2);
	String minstr1 = min1.toString() + "--" + Integer.toString(min1 + 2);
	regstat.setSesrb1(minstr1);

	Integer minX = Math.max(MINIMUN_SIGN_X, regstat.getSsrbX() - 1);
	minX = Math.min(minX, MAXIMUN_SIGN_X - 2);
	String minstrX = minX.toString() + "--" + Integer.toString(minX + 2);
	regstat.setSesrbX(minstrX);

	Integer min2 = Math.max(MINIMUN_SIGN_2, regstat.getSsrb2() - 1);
	min2 = Math.min(min2, MAXIMUN_SIGN_2 - 2);
	String minstr2 = min2.toString() + "--" + Integer.toString(min2 + 2);
	regstat.setSesrb2(minstr2);

	if ((regstat.getSp1() - min1) < 0) {
	    regstat.setDsesrb1(regstat.getSp1() - min1);
	}
	if ((regstat.getSp1() - min1 - 2) > 0) {
	    regstat.setDsesrb1(regstat.getSp1() - min1 - 2);
	}
	if ((regstat.getSpX() - minX) < 0) {
	    regstat.setDsesrbX(regstat.getSpX() - minX);
	}
	if ((regstat.getSpX() - minX - 2) > 0) {
	    regstat.setDsesrbX(regstat.getSpX() - minX - 2);
	}
	if ((regstat.getSp2() - min2) < 0) {
	    regstat.setDsesrb2(regstat.getSp2() - min2);
	}
	if ((regstat.getSp2() - min2 - 2) > 0) {
	    regstat.setDsesrb2(regstat.getSp2() - min2 - 2);
	}
    }

    public void changeValueSeason(ValueChangeEvent ev) {
	changeSeason = (ev.getNewValue() != ev.getOldValue()) ? true : false;
    }

    public void changeValueRound(ValueChangeEvent ev) {
	changeRound = (ev.getNewValue() != ev.getOldValue()) ? true : false;
    }
}
