package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaPipeCompanyQuarterReprot extends DaBaseModel {
	
	private int year;
	private int quarter;
	private DaCompany company;
	
	public DaCompany getCompany() {
		return company;
	}
	public void setCompany(DaCompany company) {
		this.company = company;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
	
}
