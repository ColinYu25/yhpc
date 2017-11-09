package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaCompanyPassRel.java
 * @date 2009-08-11
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaCompanyPassRel extends DaBaseModel {

	/**
	 * 企业确认信息表实体
	 */
	private static final long serialVersionUID = 290043471421079812L;

	private DaCompany daCompany;

	private DaIndustryParameter daIndustryParameter;

	private boolean isPass;// 是否审核通过

	private String content;

	public DaCompanyPassRel() {
	}

	public DaCompanyPassRel(long id) {
		super(id);
	}

	public DaCompany getDaCompany() {
		return this.daCompany;
	}

	public void setDaCompany(DaCompany daCompany) {
		this.daCompany = daCompany;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DaIndustryParameter getDaIndustryParameter() {
		return daIndustryParameter;
	}

	public void setDaIndustryParameter(DaIndustryParameter daIndustryParameter) {
		this.daIndustryParameter = daIndustryParameter;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

}
