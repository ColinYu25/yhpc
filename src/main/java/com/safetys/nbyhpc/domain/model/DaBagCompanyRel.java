package com.safetys.nbyhpc.domain.model;



import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaBagCompanyRel.java
 * @date 2009-08-18
 * @author zhangxu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaBagCompanyRel extends DaBaseModel {

	/**
	 * 打包企业对照表
	 */
	private static final long serialVersionUID = 4152080372084828543L;

	private DaBag daBag;

	private Long parDaComId;

	private Long userId;

	public DaBagCompanyRel() {
	}

	public DaBagCompanyRel(long id) {
		super(id);
	}

	public DaBag getDaBag() {
		return this.daBag;
	}

	public void setDaBag(DaBag daBag) {
		this.daBag = daBag;
	}

	public Long getParDaComId() {
		return this.parDaComId;
	}

	public void setParDaComId(Long parDaComId) {
		this.parDaComId = parDaComId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

}
