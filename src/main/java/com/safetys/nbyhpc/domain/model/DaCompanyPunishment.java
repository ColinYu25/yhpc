package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaCompanyPunishment extends DaBaseModel{
	
	private static final long serialVersionUID = 805467517225407390L;
	
	private DaCompany company;
	
	private Long companyId;
	
	private Date punishTime;
	
	private String punishType;
    
	
	public DaCompanyPunishment() {
	}
	
	public DaCompanyPunishment(long id) {
		super(id);
	}
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getPunishTime() {
		return punishTime;
	}

	public void setPunishTime(Date punishTime) {
		this.punishTime = punishTime;
	}

	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}
	
	
}
