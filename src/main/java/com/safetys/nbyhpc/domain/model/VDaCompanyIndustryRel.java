package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaCompany.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class VDaCompanyIndustryRel extends DaBaseModel {

	private static final long serialVersionUID = 805467517225407391L;

	private Long parDaComId;

	private Long parDaIndId;

	public Long getParDaComId() {
		return parDaComId;
	}

	public void setParDaComId(Long parDaComId) {
		this.parDaComId = parDaComId;
	}

	public Long getParDaIndId() {
		return parDaIndId;
	}

	public void setParDaIndId(Long parDaIndId) {
		this.parDaIndId = parDaIndId;
	}

		
}
