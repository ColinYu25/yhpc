package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @author llj
 * @create_time: 2014-5-14
 * @Description:
 */
public class VReport extends DaBaseModel {

	private static final long serialVersionUID = 805467517225407390L;

	private Long yhid;

	private long year;

	private long quarter;
	
	private String uuid;
	
	private String uuid1;

	private int isSynchro;
	
	private Date reportDate;
	
	

	

	public Long getYhid() {
		return yhid;
	}

	public void setYhid(Long yhid) {
		this.yhid = yhid;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Long getQuarter() {
		return quarter;
	}

	public void setQuarter(Long quarter) {
		this.quarter = quarter;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid1() {
		return uuid1;
	}

	public void setUuid1(String uuid1) {
		this.uuid1 = uuid1;
	}

	public int getIsSynchro() {
		return isSynchro;
	}

	public void setIsSynchro(int isSynchro) {
		this.isSynchro = isSynchro;
	}

	/**
	 * @return the reportDate
	 */
	public Date getReportDate() {
		return reportDate;
	}

	/**
	 * @param reportDate the reportDate to set
	 */
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	
	
}
