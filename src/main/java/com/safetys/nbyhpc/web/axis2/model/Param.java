package com.safetys.nbyhpc.web.axis2.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Param implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1074744550104151395L;

	private String tel;

	private String fillMan;

	// private String fillDate;

	private Long areaCode;

	private String chargeMan;

	private Integer type;

	private String reportMonth;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Param() {
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
