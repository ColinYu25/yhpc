package com.safetys.nbyhpc.domain.model;


import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaDangerGorver.java
 * @date 2009-08-10
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 */
public class DaThreeIllegal extends DaBaseModel{


	/**
	 * 打非实体
	 */
	private static final long serialVersionUID = -497920273948365802L;

	private DaIndustryParameter daIndustryParameter;

	private String chargePerson;

	private String tel;
	
	private String fillMan;
	
	private String userCompany;

	private Date fillDate;

	private Integer illegalBuildGetting;

	private Integer illegalBuildCanceling;

	private Integer illegalBuildCandeled;

	private Integer illegalProduceGetting;

	private Integer illegalProduceCanceled;

	private Integer illegalProduceCanceling;

	private Integer illegalTradeGetting;

	private Integer illegalTradeCalceled;
	
	private Integer illegalTradeCanceling;

	private Long userId;
	
	private Long firstArea;

	private Long secondArea;

	private Long thirdArea;



	public DaThreeIllegal() {
	}
	
	public DaThreeIllegal(long id) {
		super(id);
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
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

	public Integer getIllegalBuildCanceling() {
		return illegalBuildCanceling;
	}

	public void setIllegalBuildCanceling(Integer illegalBuildCanceling) {
		this.illegalBuildCanceling = illegalBuildCanceling;
	}

	public Integer getIllegalBuildCandeled() {
		return illegalBuildCandeled;
	}

	public void setIllegalBuildCandeled(Integer illegalBuildCandeled) {
		this.illegalBuildCandeled = illegalBuildCandeled;
	}

	public Integer getIllegalBuildGetting() {
		return illegalBuildGetting;
	}

	public void setIllegalBuildGetting(Integer illegalBuildGetting) {
		this.illegalBuildGetting = illegalBuildGetting;
	}

	public Integer getIllegalProduceCanceled() {
		return illegalProduceCanceled;
	}

	public void setIllegalProduceCanceled(Integer illegalProduceCanceled) {
		this.illegalProduceCanceled = illegalProduceCanceled;
	}

	public Integer getIllegalProduceCanceling() {
		return illegalProduceCanceling;
	}

	public void setIllegalProduceCanceling(Integer illegalProduceCanceling) {
		this.illegalProduceCanceling = illegalProduceCanceling;
	}

	public Integer getIllegalProduceGetting() {
		return illegalProduceGetting;
	}

	public void setIllegalProduceGetting(Integer illegalProduceGetting) {
		this.illegalProduceGetting = illegalProduceGetting;
	}

	public Integer getIllegalTradeCalceled() {
		return illegalTradeCalceled;
	}

	public void setIllegalTradeCalceled(Integer illegalTradeCalceled) {
		this.illegalTradeCalceled = illegalTradeCalceled;
	}

	public Integer getIllegalTradeCanceling() {
		return illegalTradeCanceling;
	}

	public void setIllegalTradeCanceling(Integer illegalTradeCanceling) {
		this.illegalTradeCanceling = illegalTradeCanceling;
	}

	public Integer getIllegalTradeGetting() {
		return illegalTradeGetting;
	}

	public void setIllegalTradeGetting(Integer illegalTradeGetting) {
		this.illegalTradeGetting = illegalTradeGetting;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFillMan() {
		return fillMan;
	}

	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}

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


}
