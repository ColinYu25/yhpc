package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

// Generated 2012-11-6 15:59:30 by Hibernate Tools 3.2.0.b9

/**
 * TCoreBzh generated by hbm2java
 */
public class TCoreBzh extends DaBaseModel implements java.io.Serializable {

	/**
	 * 企业基础信息子表(标准化证书信息)
	 */
	private static final long serialVersionUID = -5752373921936843026L;
	private TCoreCompany coreCompany;
	private String bzhType;
	private String licence;//证书号
	private String bzhGrade;
	private Date validityEnd;
	private Long userId;
	
	public TCoreBzh(){
		
	}
	
	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public TCoreBzh(Long id){
		super(id);
	}
	public TCoreCompany getCoreCompany() {
		return coreCompany;
	}
	public void setCoreCompany(TCoreCompany coreCompany) {
		this.coreCompany = coreCompany;
	}
	public String getBzhType() {
		return bzhType;
	}
	public void setBzhType(String bzhType) {
		this.bzhType = bzhType;
	}
	public String getBzhGrade() {
		return bzhGrade;
	}
	public void setBzhGrade(String bzhGrade) {
		this.bzhGrade = bzhGrade;
	}
	public Date getValidityEnd() {
		return validityEnd;
	}
	public void setValidityEnd(Date validityEnd) {
		this.validityEnd = validityEnd;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
