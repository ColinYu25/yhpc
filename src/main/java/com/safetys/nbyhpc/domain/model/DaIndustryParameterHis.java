package com.safetys.nbyhpc.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaIndustryParameter.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaIndustryParameterHis extends DaBaseModel {

	/**
	 * 行业参数实体
	 */
	private static final long serialVersionUID = 6199889414673213659L;

	private DaIndustryParameterHis daIndustryParameter;

	private String name;
	
	private String sort;

	private String depiction;

	private String gradePath;

	private Integer type;// 行业参数类型（1代表 企业行业；2代表 隐患类型；3代表 打非类型；4代表 统计上报行业；5代表季报行业, 10表示管道一般隐患类型，11表示管道重大隐患类型）。

	private Long userId;
	
	private int seq1;
	
	private Set<DaIndustryParameterHis> daIndustryParameters = new HashSet<DaIndustryParameterHis>(0);
	
	private Set<DaPointType> daPointTypes = new HashSet<DaPointType>(0);

	public DaIndustryParameterHis() {
	}

	public DaIndustryParameterHis(Long id) {
		super(id);
	}

	public DaIndustryParameterHis getDaIndustryParameter() {
		return this.daIndustryParameter;
	}

	public void setDaIndustryParameter(DaIndustryParameterHis daIndustryParameter) {
		this.daIndustryParameter = daIndustryParameter;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepiction() {
		return this.depiction;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}

	public String getGradePath() {
		return this.gradePath;
	}

	public void setGradePath(String gradePath) {
		this.gradePath = gradePath;
	}

	public Set<DaIndustryParameterHis> getDaIndustryParameters() {
		return daIndustryParameters;
	}

	public void setDaIndustryParameters(
			Set<DaIndustryParameterHis> daIndustryParameters) {
		this.daIndustryParameters = daIndustryParameters;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 判断行业的级别
	 * 
	 * @return
	 */
	public Integer getGradeRate() {
		Integer gradeRate = 0;
		if (gradePath != null) {
			gradeRate = gradePath.split("/").length - 2;
		}
		return gradeRate;
	}

	public Set<DaPointType> getDaPointTypes() {
		return daPointTypes;
	}

	public void setDaPointTypes(Set<DaPointType> daPointTypes) { 
		this.daPointTypes = daPointTypes;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getSeq1() {
		return seq1;
	}

	public void setSeq1(int seq1) {
		this.seq1 = seq1;
	}


	

}
