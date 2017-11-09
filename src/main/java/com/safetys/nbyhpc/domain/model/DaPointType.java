package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaPointType extends DaBaseModel{

	
	/**
	 * 得分实体
	 */
	private static final long serialVersionUID = 1029988090534378308L;

	private DaIndustryParameter daIndustryParameter;

	private String name;

	private Double point;

	private Long userId;

	private Integer sortNum;
	
	public DaPointType() {
	}

	public DaPointType(long id) {
		super(id);
	}

	public DaIndustryParameter getDaIndustryParameter() {
		return daIndustryParameter;
	}

	public void setDaIndustryParameter(DaIndustryParameter daIndustryParameter) {
		this.daIndustryParameter = daIndustryParameter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	} 
	
	
}
