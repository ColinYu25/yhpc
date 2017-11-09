package com.safetys.nbyhpc.domain.model;

// Generated 2009-7-31 18:24:16 by Hibernate Tools 3.2.0.b9

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaDangerGorver.java
 * @date 2009-08-25
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 */
public class DaSeasonReportOther extends DaBaseModel {


	/**
	 * 部门季报实体
	 */
	private static final long serialVersionUID = -3356830193461251209L;

	private DaIndustryParameter daIndustryParameter;

	private Integer companyNum;

	private Integer troubNum;

	private Integer troubCleanNum;

	private Integer bigTroubNum;

	private Integer bigTroubCleanNum;

	private Integer planBigTroubNum;

	private Integer target;

	private Integer goods;

	private Integer resource;

	private Integer finishDate;

	private Integer safetyMethod;

	private Double governMoney;

	private Long userId;

	private String linkMan;

	private String linkTel;

	private String linkMobile;

	private String chargePerson;

	private Date fillDate;

	private String fillMan;

	private Long firstArea;
	
	private Long secondArea;
	
	private Long thirdArea;
	
	private String beginDate;
	
	private String endDate;
	
	private Integer nowYear;
	
	public Long getFirstArea() {
		return firstArea;
	}

	public void setFirstArea(Long firstArea) {
		this.firstArea = firstArea;
	}

	public Long getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(Long secondArea) {
		this.secondArea = secondArea;
	}

	public Long getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(Long thirdArea) {
		this.thirdArea = thirdArea;
	}

	public DaSeasonReportOther() {
	}

	public DaSeasonReportOther(long id) {
		super(id);
	}

	public Integer getBigTroubCleanNum() {
		return bigTroubCleanNum;
	}

	public void setBigTroubCleanNum(Integer bigTroubCleanNum) {
		this.bigTroubCleanNum = bigTroubCleanNum;
	}

	public Integer getBigTroubNum() {
		return bigTroubNum;
	}

	public void setBigTroubNum(Integer bigTroubNum) {
		this.bigTroubNum = bigTroubNum;
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public Integer getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(Integer companyNum) {
		this.companyNum = companyNum;
	}

	public DaIndustryParameter getDaIndustryParameter() {
		return daIndustryParameter;
	}

	public void setDaIndustryParameter(DaIndustryParameter daIndustryParameter) {
		this.daIndustryParameter = daIndustryParameter;
	}

	public Date getFillDate() {
		return fillDate;
	}

	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}

	public String getFillMan() {
		return fillMan;
	}

	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
	}

	public Integer getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Integer finishDate) {
		this.finishDate = finishDate;
	}

	public Integer getGoods() {
		return goods;
	}

	public void setGoods(Integer goods) {
		this.goods = goods;
	}

	public Double getGovernMoney() {
		return governMoney;
	}

	public void setGovernMoney(Double governMoney) {
		this.governMoney = governMoney;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public Integer getPlanBigTroubNum() {
		return planBigTroubNum;
	}

	public void setPlanBigTroubNum(Integer planBigTroubNum) {
		this.planBigTroubNum = planBigTroubNum;
	}

	public Integer getResource() {
		return resource;
	}

	public void setResource(Integer resource) {
		this.resource = resource;
	}

	public Integer getSafetyMethod() {
		return safetyMethod;
	}

	public void setSafetyMethod(Integer safetyMethod) {
		this.safetyMethod = safetyMethod;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Integer getTroubCleanNum() {
		return troubCleanNum;
	}

	public void setTroubCleanNum(Integer troubCleanNum) {
		this.troubCleanNum = troubCleanNum;
	}

	public Integer getTroubNum() {
		return troubNum;
	}

	public void setTroubNum(Integer troubNum) {
		this.troubNum = troubNum;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getNowYear() {
		return nowYear;
	}

	public void setNowYear(Integer nowYear) {
		this.nowYear = nowYear;
	}



}
