package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * 挂牌对应FILE关系表
 */
public class DaPipeRollcallFile extends DaBaseModel {

	
	private static final long serialVersionUID = -6313542232609732095L;
	
	private DaPipeRollcallCompany daPipeRollcallCompany;
	
	private DaPipeFile daPipeFile;
	
	private String depiction;
	
	private Integer type;
	
	private Long userId;
	
	public DaPipeRollcallFile(){
		
	}

	public DaPipeFile getDaPipeFile() {
		return daPipeFile;
	}

	public void setDaPipeFile(DaPipeFile daPipeFile) {
		this.daPipeFile = daPipeFile;
	}

	public DaPipeRollcallCompany getDaPipeRollcallCompany() {
		return daPipeRollcallCompany;
	}

	public void setDaPipeRollcallCompany(DaPipeRollcallCompany daPipeRollcallCompany) {
		this.daPipeRollcallCompany = daPipeRollcallCompany;
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
