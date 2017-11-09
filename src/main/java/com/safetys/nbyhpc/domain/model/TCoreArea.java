package com.safetys.nbyhpc.domain.model;

import com.safetys.framework.domain.model.base.BaseModel;

public class TCoreArea extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3725036240033417041L;

	private Long pId;

	private String areaName;

	private Long areaCode;

	private Integer sortNum;

	private String gradePath;
	
	private String shortName;
	
	private String englishName;

	public TCoreArea() {
	}

	public TCoreArea(String areaName, Long id, Long pId) {
		this.areaName = areaName;
		setId(id);
		this.pId = pId;
	}

	public TCoreArea(Long areaCode, String areaName, Long pId) {
		this.areaName = areaName;
		this.pId = pId;
		this.areaCode = areaCode;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

}