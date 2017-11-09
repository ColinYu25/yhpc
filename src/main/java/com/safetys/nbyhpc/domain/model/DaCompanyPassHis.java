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
public class DaCompanyPassHis extends DaBaseModel {

	/**
	 * 企业确认表实体
	 */
	private static final long serialVersionUID = 290043471421079812L;

	private DaCompanyHis daCompanyHis;

	private long userId;

	private boolean enterprise;

	private boolean orga;
	
	private Long comUserId;
	
	private Long backupDate;
	
	private boolean affirm;//是否已经审核成功

	private Set<DaSeasonReport> daSeasonReports = new HashSet<DaSeasonReport>(0);

	private Set<DaDanger1> daDangers1 = new HashSet<DaDanger1>(0);	
	

	private Set<DaBagCompanyRel> daBagCompanyRels = new HashSet<DaBagCompanyRel>(0);

	public DaCompanyPassHis() {
	}

	public DaCompanyPassHis(long id) {
		super(id);
	}



	public DaCompanyHis getDaCompanyHis() {
		return daCompanyHis;
	}

	public void setDaCompanyHis(DaCompanyHis daCompanyHis) {
		this.daCompanyHis = daCompanyHis;
	}

	public Set<DaBagCompanyRel> getDaBagCompanyRels() {
		return daBagCompanyRels;
	}

	public void setDaBagCompanyRels(Set<DaBagCompanyRel> daBagCompanyRels) {
		this.daBagCompanyRels = daBagCompanyRels;
	}

	public Set<DaDanger1> getDaDangers1() {
		return daDangers1;
	}

	public void setDaDangers1(Set<DaDanger1> daDangers1) {
		this.daDangers1 = daDangers1;
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

	public Long getBackupDate() {
		return backupDate;
	}

	public void setBackupDate(Long backupDate) {
		this.backupDate = backupDate;
	}

	

}
