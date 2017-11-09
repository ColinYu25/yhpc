package com.safetys.framework.domain.model;

import com.safetys.framework.domain.model.base.BaseModel;

public class FkArea extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3725036240033417041L;

	private Long fatherId;

	private String areaName;

	private Long areaCode;

	private Integer sortNum;

	private String gradePath;
	
	private Integer groupNum;

	public FkArea() {
	}

	public FkArea(String areaName, Long id, Long fatherId) {
		this.areaName = areaName;
		setId(id);
		this.fatherId = fatherId;
	}

	public FkArea(Long areaCode, String areaName, Long fatherId) {
		this.areaName = areaName;
		this.fatherId = fatherId;
		this.areaCode = areaCode;
	}

	public Long getFatherId() {
		return this.fatherId;
	}

	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}

	public Integer getSortNum() {
		return this.sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getText() {
		return getAreaName();
	}

	public Long getNodeId() {
		return getId();
	}

	public boolean isLeaf() {
		boolean isLeaf = true;

		if ((getFatherId() != null) && (getFatherId().longValue() > 0L)) {
			isLeaf = false;
		}
		return isLeaf;
	}

	public Long getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}

	public String getGradePath() {
		return this.gradePath;
	}

	public void setGradePath(String gradePath) {
		this.gradePath = gradePath;
	}

	public Integer getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}
}