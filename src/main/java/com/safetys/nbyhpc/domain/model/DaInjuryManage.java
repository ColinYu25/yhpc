package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaInjuryManage
 * @date 20l1-10-26
 * @author zouxw
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
/**
 * 工伤管理
 */
public class DaInjuryManage extends DaBaseModel {

	private static final long serialVersionUID = 6927066542209996565L;
	private DaCompany daCompany;//企业
	private Integer insuredNum;//参保人数
	private Double injuryNum;//工伤率
	
	public DaCompany getDaCompany() {
		return daCompany;
	}
	public void setDaCompany(DaCompany daCompany) {
		this.daCompany = daCompany;
	}
	public Integer getInsuredNum() {
		return insuredNum;
	}
	public void setInsuredNum(Integer insuredNum) {
		this.insuredNum = insuredNum;
	}
	public Double getInjuryNum() {
		return injuryNum;
	}
	public void setInjuryNum(Double injuryNum) {
		this.injuryNum = injuryNum;
	}
	
}
