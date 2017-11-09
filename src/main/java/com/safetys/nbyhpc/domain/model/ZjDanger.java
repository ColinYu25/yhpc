package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) ZjReportParam.java
 * @date 2010-03-03
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class ZjDanger extends DaBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1731905206801748508L;

	private Long dangerId;

	private Integer state;

	public ZjDanger() {
	}

	public ZjDanger(long id) {
		super(id);
	}

	public Long getDangerId() {
		return dangerId;
	}

	public void setDangerId(Long dangerId) {
		this.dangerId = dangerId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
