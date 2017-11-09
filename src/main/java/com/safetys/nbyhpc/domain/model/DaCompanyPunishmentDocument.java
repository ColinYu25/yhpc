package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaCompanyPunishmentDocument extends DaBaseModel{
	
	private static final long serialVersionUID = 805467517225407390L;
	
	private DaCompany company;
	
	private Long companyId;
	
	private Date documentTime;
	
	private String documentName;

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getDocumentTime() {
		return documentTime;
	}

	public void setDocumentTime(Date documentTime) {
		this.documentTime = documentTime;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
    
	
}
