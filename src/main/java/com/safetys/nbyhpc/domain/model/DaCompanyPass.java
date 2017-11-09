package com.safetys.nbyhpc.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaCompanyPass.java
 * @date 2009-07-31
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaCompanyPass extends DaBaseModel {

	/**
	 * 企业确认表实体
	 */
	private static final long serialVersionUID = 290043471421079812L;

	private DaCompany daCompany;

	private long userId;

	private boolean enterprise;

	private boolean orga;
	
	private Long comUserId;
	
	private boolean affirm;//是否已经审核成功

	private Set<DaSeasonReport> daSeasonReports = new HashSet<DaSeasonReport>(0);

	private Set<DaDanger> daDangers = new HashSet<DaDanger>(0);	
	

	private Set<DaBagCompanyRel> daBagCompanyRels = new HashSet<DaBagCompanyRel>(0);

	public DaCompanyPass() {
	}

	public DaCompanyPass(long id) {
		super(id);
	}

	public DaCompany getDaCompany() {
		return this.daCompany;
	}

	public void setDaCompany(DaCompany daCompany) {
		this.daCompany = daCompany;
	}

	public Set<DaBagCompanyRel> getDaBagCompanyRels() {
		return daBagCompanyRels;
	}

	public void setDaBagCompanyRels(Set<DaBagCompanyRel> daBagCompanyRels) {
		this.daBagCompanyRels = daBagCompanyRels;
	}

	public Set<DaDanger> getDaDangers() {
		return daDangers;
	}

	public void setDaDangers(Set<DaDanger> daDangers) {
		this.daDangers = daDangers;
	}

	public Set<DaSeasonReport> getDaSeasonReports() {
		return daSeasonReports;
	}

	public void setDaSeasonReports(Set<DaSeasonReport> daSeasonReports) {
		this.daSeasonReports = daSeasonReports;
	}

	public boolean isEnterprise() {
		return enterprise;
	}

	public void setEnterprise(boolean enterprise) {
		this.enterprise = enterprise;
	}

	public boolean isOrga() {
		return orga;
	}

	public void setOrga(boolean orga) {
		this.orga = orga;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isAffirm() {
		return affirm;
	}

	public void setAffirm(boolean affirm) {
		this.affirm = affirm;
	}

	public Long getComUserId() {
		return comUserId;
	}

	public void setComUserId(Long comUserId) {
		this.comUserId = comUserId;
	}


}
