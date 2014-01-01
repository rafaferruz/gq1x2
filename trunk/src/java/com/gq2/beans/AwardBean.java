package com.gq2.beans;

import com.gq2.domain.Award;
import com.gq2.services.AwardService;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "awards")
@ViewScoped
public class AwardBean extends Award {

    private List<Award> awardList = new ArrayList();
    private AwardService awardService = new AwardService();
    private Award awardToDelete;
    private Award awardToEdit;

    public AwardBean() {
	    Award aw = (Award) ((HttpServletRequest) FacesContext.getCurrentInstance()
		    .getExternalContext().getRequest()).getSession().getAttribute("awardToEdit");
	    if (aw != null) {
		aw = awardService.load(aw.getAwaId());
		setAwaId(aw.getAwaId());
		setAwaSeason(aw.getAwaSeason());
		setAwaOrderNumber(aw.getAwaOrderNumber());
		setAwaDescription(aw.getAwaDescription());
		setAwaBetPrice(aw.getAwaBetPrice());
		setAwa14HitsAmount(aw.getAwa14HitsAmount());
		setAwa13HitsAmount(aw.getAwa13HitsAmount());
		setAwa12HitsAmount(aw.getAwa12HitsAmount());
		setAwa11HitsAmount(aw.getAwa11HitsAmount());
		setAwa10HitsAmount(aw.getAwa10HitsAmount());
		((HttpServletRequest) FacesContext.getCurrentInstance()
		    .getExternalContext().getRequest()).getSession().removeAttribute("awardToEdit");
	    } 
    }

    public List<Award> getAwardList() {
	return awardList;
    }

    public void setAwardList(List<Award> awardList) {
	this.awardList = awardList;
    }

    public Award getAwardToDelete() {
	return awardToDelete;
    }

    public void setAwardToDelete(Award awardToDelete) {
	this.awardToDelete = awardToDelete;
    }

    public Award getAwardToEdit() {
	return awardToEdit;
    }

    public void setAwardToEdit(Award awardToEdit) {
	this.awardToEdit = awardToEdit;
    }

    public void readAwards(Integer season, Integer orderNumber, String description) {
	setAwardList(awardService.loadConditionalAwardList(season, orderNumber, description));
    }

    public void search() {
	readAwards(getAwaSeason(), getAwaOrderNumber(), getAwaDescription());
    }

    public void insertAward() {
	if (getAwaSeason() == null || getAwaSeason() == 0
		|| getAwaOrderNumber() == null || getAwaOrderNumber() == 0
		|| getAwaDescription() == null || getAwaDescription().equals("")) {
	    // enviar mensaje
	    return;
	}
	Award award = awardService.loadConditionalAward(getAwaSeason(), getAwaOrderNumber(), getAwaDescription());
	if (award == null) {
	    // Persiste la apuesta
	    setAwaId(awardService.save(this));
	    if (this.getAwaId() == 0) {
		// fallo al insertar el registro
		// TODO Enviar mensaje de fallo en la persistencia del objeto
	    } else {
		awardList.add(award);
	    }
	} else {
	    // ya existe el registro
	    // TODO Enviar mensaje notificacion de que el objeto ya existe
	}
    }

    public String editAward() {
	((HttpServletRequest) FacesContext.getCurrentInstance()
		.getExternalContext().getRequest()).getSession().setAttribute("awardToEdit", awardToEdit);
	return "award";
    }

    public String save() {
	String navigateTo;
	if (getAwaId() == 0) {
	    awardService.save(this);
	    // No esta la lista de awards. Deberia guardarse como apuesta nueva
	    // TODO Enviar mensaje de que no existe y debe guardarse como nueva
	    navigateTo="awardList";
	} else {
	    awardService.update(this);
	    navigateTo="awardList";
	}
	return navigateTo;
    }

    private Integer findIdInAwardList(int id, List<Award> awards) {
	int idxOf;
	for (Award award : awards) {
	    if (award.getAwaId() == id) {
		idxOf = awards.indexOf(award);
		return idxOf;
	    }
	}
	return null;
    }

    public void deleteAward() {
	if (awardToDelete != null) {
	    awardService.delete(awardToDelete);
	    search();
	}
    }
    public String cancel(){
	return "awardList";
    }

    public String add() {
	return "add";
    }
}
