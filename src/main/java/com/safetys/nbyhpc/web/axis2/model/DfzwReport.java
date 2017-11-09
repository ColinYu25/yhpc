/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.web.axis2.model;

import java.io.Serializable;

/**
 * 打非治违周报主表
 * 
 * author lih
 * 
 * @since 2012-5-9
 * @version 1.0.0
 */
public class DfzwReport implements Serializable {

	private static final long serialVersionUID = -1170802769740112166L;

	private Long areaCode;
	
	private String reportDateBeginStr;

	private String reportDateEndStr;

	private String tel;

	private String fillMan;

	private String chargeMan;

	private Integer state;

	public DfzwReport() {
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFillMan() {
		return this.fillMan;
	}

	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
	}

	public String getChargeMan() {
		return this.chargeMan;
	}

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}

	public String getReportDateBeginStr() {
		return reportDateBeginStr;
	}

	public void setReportDateBeginStr(String reportDateBeginStr) {
		this.reportDateBeginStr = reportDateBeginStr;
	}

	public String getReportDateEndStr() {
		return reportDateEndStr;
	}

	public void setReportDateEndStr(String reportDateEndStr) {
		this.reportDateEndStr = reportDateEndStr;
	}
	
}
