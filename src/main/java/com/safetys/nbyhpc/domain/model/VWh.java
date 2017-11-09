package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @author llj
 * @create_time: 2014-5-14
 * @Description:
 */
public class VWh extends DaBaseModel {

	private static final long serialVersionUID = 805467517225407390L;

	private Long whid;

	private Long year;

	private String uuid;
	
	private String uuid1;

	private int isSynchro;
	
	private int deleted;

	private Date createTime;

	private Date modifyTime;

	private double annualInput;

	

	public Long getWhid() {
		return whid;
	}

	public void setWhid(Long whid) {
		this.whid = whid;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
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

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public double getAnnualInput() {
		return annualInput;
	}

	public void setAnnualInput(double annualInput) {
		this.annualInput = annualInput;
	}

	
	
	
}
