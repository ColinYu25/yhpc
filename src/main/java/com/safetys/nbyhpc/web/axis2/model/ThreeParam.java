package com.safetys.nbyhpc.web.axis2.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThreeParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1506051032657678543L;

	private String tel;

	private String fillMan;

	private Long areaCode;

	private String chargeMan;

	private String reportMonth;

	public ThreeParam() {
	}

	public Long getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}

	public String getChargeMan() {
		return chargeMan;
	}

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
	}

	// public String getFillDate() {
	// return fillDate;
	// }
	//
	// public void setFillDate(String fillDate) {
	// this.fillDate = fillDate;
	// }

	public String getFillMan() {
		return fillMan;
	}

	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getReportMonth() {
		if (reportMonth == null) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
			return formatter.format(cal.getTime());
		}
		return reportMonth;
	}

	public void setReportMonth(String reportMonth) {
		this.reportMonth = reportMonth;
	}

}
