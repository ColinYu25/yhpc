package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) ZjReportParam.java
 * @date 2010-03-03
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class ZjSend extends DaBaseModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6608433515506713345L;

	private Integer type;

	private String reportMonth;
	
	private Long userId;
	
	private Date setTime;
	
	private Integer isSend; 
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ZjSend() {
	}

	public ZjSend(long id) {
		super(id);
	}

	public String getReportMonth() {
		return reportMonth;
	}

	public void setReportMonth(String reportMonth) {
		this.reportMonth = reportMonth;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	public Date getSetTime() {
		return setTime;
	}

	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}

	/**
	 * @return the isSend
	 */
	public Integer getIsSend() {
		return isSend;
	}

	/**
	 * @param isSend the isSend to set
	 */
	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}


}
