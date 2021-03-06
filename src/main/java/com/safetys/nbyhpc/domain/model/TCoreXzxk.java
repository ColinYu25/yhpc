package com.safetys.nbyhpc.domain.model;

import java.math.BigDecimal;
import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

// Generated 2012-11-6 15:59:30 by Hibernate Tools 3.2.0.b9

/**
 * TCoreXzxk generated by hbm2java
 */
public class TCoreXzxk extends DaBaseModel implements java.io.Serializable {

	/**
	 * 企业基础信息子表(许可证证书信息)
	 */
	private static final long serialVersionUID = 1145553000081049709L;
	private TCoreCompany coreCompany;
	private String xkType;
	private String licence;
	private Date validityEnd;
	private String permitScope;
	private Long userId;

	public TCoreXzxk() {
		
	}
	
	public TCoreXzxk(Long id) {
		super(id);
	}
	
	public String getXkType() {
		return xkType;
	}
	public void setXkType(String xkType) {
		this.xkType = xkType;
	}
	public String getLicence() {
		return licence;
	}
	public void setLicence(String licence) {
		this.licence = licence;
	}
	public Date getValidityEnd() {
		return validityEnd;
	}
	public void setValidityEnd(Date validityEnd) {
		this.validityEnd = validityEnd;
	}
	public String getPermitScope() {
		return permitScope;
	}
	public void setPermitScope(String permitScope) {
		this.permitScope = permitScope;
	}

	public TCoreCompany getCoreCompany() {
		return coreCompany;
	}

	public void setCoreCompany(TCoreCompany coreCompany) {
		this.coreCompany = coreCompany;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
