package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaRollcallFile extends DaBaseModel {

	/**
	 * 挂牌对应FILE关系表
	 */
	private static final long serialVersionUID = -6313542232609732095L;
	
	private DaRollcallCompany daRollcallCompany;
	
	private DaFile daFile;
	
	private String depiction;
	
	private Integer type;
	
	private Long userId;
	
	public DaRollcallFile(){
		
	}

	public DaFile getDaFile() {
		return daFile;
	}

	public void setDaFile(DaFile daFile) {
		this.daFile = daFile;
	}

	public DaRollcallCompany getDaRollcallCompany() {
		return daRollcallCompany;
	}

	public void setDaRollcallCompany(DaRollcallCompany daRollcallCompany) {
		this.daRollcallCompany = daRollcallCompany;
	}

	public String getDepiction() {
		return depiction;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
