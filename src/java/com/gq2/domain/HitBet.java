package com.gq2.domain;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author RAFAEL FERRUZ
 */
    public class HitBet {

	private int hits;
	private List<SelectItem> hitItemList = new ArrayList<>();

	public HitBet(int hits) {
	    this.hits = hits;
	}
	public HitBet(int hits, Integer numberColumn) {
	    this.hits = hits;
	    hitItemList.add(new SelectItem(numberColumn, numberColumn.toString()));
	}

	public int getHits() {
	    return hits;
	}

	public void setHits(int hits) {
	    this.hits = hits;
	}

	public List<SelectItem> getHitItemList() {
	    return hitItemList;
	}

	public void setHitItemList(List<SelectItem> hitItemList) {
	    this.hitItemList = hitItemList;
	}

	public HitBet addHit(Integer numberColumn) {
	    setHits(getHits() + 1);
	    hitItemList.add(new SelectItem(numberColumn, numberColumn.toString()));
	    return this;
	}
    }
