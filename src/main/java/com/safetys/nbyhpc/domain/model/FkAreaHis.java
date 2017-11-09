package com.safetys.nbyhpc.domain.model;

import com.safetys.framework.domain.model.base.BaseModel;

public class FkAreaHis extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3725036240033417041L;

	private Long fatherId;

	private String areaName;

	private Long areaCode;

	private Integer sortNum;

	private String gradePath;
	
	private Long areaCodeOld;
	
	private Integer groupNum;
	
	private Integer backupDate;
	

	public FkAreaHis() {
	}

	public FkAreaHis(String areaName, Long id, Long pId) {
		this.areaName = areaName;
		setId(id);
		this.fatherId = fatherId;
	}

	public FkAreaHis(Long areaCode, String areaName, Long pId) {
		this.areaName = areaName;
		this.fatherId = fatherId;
		this.areaCode = areaCode;
	}



	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getGradePath() {
		return gradePath;
	}

	public void setGradePath(String gradePath) {
		this.gradePath = gradePath;
	}

	public Long getFatherId() {
		return fatherId;
	}

	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}

	
	public Long getAreaCodeOld() {
		return areaCodeOld;
	}

	public void setAreaCodeOld(Long areaCodeOld) {
		this.areaCodeOld = areaCodeOld;
	}

	public Integer getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}

	public Integer getBackupDate() {
		return backupDate;
	}

	public void setBackupDate(Integer backupDate) {
		this.backupDate = backupDate;
	}

	
}