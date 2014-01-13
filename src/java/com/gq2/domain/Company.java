package com.gq2.domain;

import com.gq2.reports.ReportMap;

public class Company {
    
    private int id;
    private String name ="";
	private ReportMap reportMap = ReportMap.NONE; // Personalización de informes

    public Company() {
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public ReportMap getReportMap() {
	return reportMap;
    }

    public void setReportMap(ReportMap reportMap) {
	this.reportMap = reportMap;
    }

    
    
    
}
