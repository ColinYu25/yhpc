package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaItemSeasonDetail.java
 * @date 2009-08-03
 * @author zhangx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaItemSeasonDetail extends DaBaseModel {

	/**
	 * 季报分包单位表
	 */
	private static final long serialVersionUID = 2450340966920269556L;

	private DaItemSeasonReport daItemSeasonReport;

	private String companyName;

	public DaItemSeasonDetail() {
	}

	public DaItemSeasonDetail(long id) {
		super(id);
	}

	public DaItemSeasonReport getDaItemSeasonReport() {
		return this.daItemSeasonReport;
	}

	public void setDaItemSeasonReport(DaItemSeasonReport daItemSeasonReport) {
		this.daItemSeasonReport = daItemSeasonReport;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
