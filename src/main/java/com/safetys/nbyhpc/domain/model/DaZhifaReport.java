package com.safetys.nbyhpc.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaZhifaReport extends DaBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1315509158886426247L;

	private String unit;
	
	private String auditor;

	private String informanter;

	private String phone;

	private Date writtenDate;
	
	private Long userId;
	
	private Long areaCode;
	
	private String areaName;
	
	private String writtenMonth;

	private Set<DaZhifaReportDetail> daZhifaReportDetails = new HashSet<DaZhifaReportDetail>();

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Date getWrittenDate() {
		return writtenDate;
	}

	public void setWrittenDate(Date writtenDate) {
		this.writtenDate = writtenDate;
	}

	public Set<DaZhifaReportDetail> getDaZhifaReportDetails() {
		return daZhifaReportDetails;
	}

	public void setDaZhifaReportDetails(
			Set<DaZhifaReportDetail> daZhifaReportDetails) {
		this.daZhifaReportDetails = daZhifaReportDetails;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getInformanter() {
		return informanter;
	}

	public void setInformanter(String informanter) {
		this.informanter = informanter;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getWrittenMonth() {
		return writtenMonth;
	}

	public void setWrittenMonth(String writtenMonth) {
		this.writtenMonth = writtenMonth;
	}
}
