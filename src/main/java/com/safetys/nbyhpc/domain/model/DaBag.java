package com.safetys.nbyhpc.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaBag.java
 * @date 2009-08-18
 * @author zhangxu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaBag extends DaBaseModel {

	/**
	 * 包实体
	 */
	private static final long serialVersionUID = -9183587512447384092L;

	private String name; 

	private String regAddress;

	private String propertyname;

	private String fddelegate;

	private String phoneCode;

	private String depiction;

	private Long firstArea;

	private Long secondArea;

	private Long thirdArea;

	private Long fouthArea;

	private Long fifthArea;

	private Long userId;
	
	private String bagType; 

	private Set<DaSeasonReport> daSeasonReports = new HashSet<DaSeasonReport>(0);

	private Set<DaBagCompanyRel> daBagCompanyRels = new HashSet<DaBagCompanyRel>(0);
	
	private String orderProperty;
	
	private boolean orderType;
	
	private boolean seasonOne=false; //判断是否存在第一集报表
	
	private boolean seasonTwo=false;//判断是否存在第二集报表
	
	private boolean seasonThree=false;//判断是否存在第三集报表
	
	private boolean seasonFour=false;//判断是否存在第四集报表

	public DaBag() {
	}

	public DaBag(long id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegAddress() {
		return this.regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getPropertyname() {
		return this.propertyname;
	}

	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}

	public String getFddelegate() {
		return this.fddelegate;
	}

	public void setFddelegate(String fddelegate) {
		this.fddelegate = fddelegate;
	}

	public String getPhoneCode() {
		return this.phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getDepiction() {
		return this.depiction;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}

	public Set<DaBagCompanyRel> getDaBagCompanyRels() {
		return daBagCompanyRels;
	}

	public void setDaBagCompanyRels(Set<DaBagCompanyRel> daBagCompanyRels) {
		this.daBagCompanyRels = daBagCompanyRels;
	}

	public Set<DaSeasonReport> getDaSeasonReports() {
		return daSeasonReports;
	}

	public void setDaSeasonReports(Set<DaSeasonReport> daSeasonReports) {
		this.daSeasonReports = daSeasonReports;
	}

	public Long getFifthArea() {
		return fifthArea;
	}

	public void setFifthArea(Long fifthArea) {
		this.fifthArea = fifthArea;
	}

	public Long getFirstArea() {
		return firstArea;
	}

	public void setFirstArea(Long firstArea) {
		this.firstArea = firstArea;
	}

	public Long getFouthArea() {
		return fouthArea;
	}

	public void setFouthArea(Long fouthArea) {
		this.fouthArea = fouthArea;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBagType() {
		return bagType;
	}

	public void setBagType(String bagType) {
		this.bagType = bagType;
	}

	public boolean isSeasonOne() {
		return seasonOne;
	}

	public void setSeasonOne(boolean seasonOne) {
		this.seasonOne = seasonOne;
	}

	public boolean isSeasonTwo() {
		return seasonTwo;
	}

	public void setSeasonTwo(boolean seasonTwo) {
		this.seasonTwo = seasonTwo;
	}

	public boolean isSeasonThree() {
		return seasonThree;
	}

	public void setSeasonThree(boolean seasonThree) {
		this.seasonThree = seasonThree;
	}

	public boolean isSeasonFour() {
		return seasonFour;
	}

	public void setSeasonFour(boolean seasonFour) {
		this.seasonFour = seasonFour;
	}

	public String getOrderProperty() {
		return orderProperty;
	}

	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	public boolean isOrderType() {
		return orderType;
	}

	public void setOrderType(boolean orderType) {
		this.orderType = orderType;
	}



}
