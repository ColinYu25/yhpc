package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;
/**
 * @(#) DaCompanyLogout.java
 * @date 2009-08-01
 * @author zhangx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaCompanyLogout extends  DaBaseModel{

	/**
	 * 企业注销实体
	 */
	private static final long serialVersionUID = 1101689144665147395L;

	private DaCompany daCompany;

	private Integer type;

	private Long userId;

	public DaCompanyLogout() {
	}

	public DaCompanyLogout(long id) {
		super(id);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public DaCompany getDaCompany() {
		return daCompany;
	}

	public void setDaCompany(DaCompany daCompany) {
		this.daCompany = daCompany;
	}

}
