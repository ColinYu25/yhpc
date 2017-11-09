package com.safetys.nbyhpc.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaCompany.java
 * @date 2009-08-08
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaTrouble extends DaBaseModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6383290190819038633L;

	private DaTroubleCompany daTroubleCompany;

	private String troubleNo;

	private String dangerAdd;

	private Long userId;

	private Long firstArea;

	private Long secondArea;

	private Long fifthArea;

	private Long fouthArea;

	private Long thirdArea;
	
	private String firstAreaName;

	private String secondAreaName;
	
	private String thirdAreaName;

	private String description;
	
	private String troubleCompanyName;
	
	private String fddelegate;
	
	private String principal;
	
	private String linkTel;
	
	private String linkMobile;

	private Set<DaTroubleFile> daTroubleFiles = new HashSet<DaTroubleFile>(0);

	private Set<DaDept> daDeptes = new HashSet<DaDept>(0);


	public DaTrouble() {
	}

	public DaTrouble(long id) {
		super(id);
	}


	public DaTroubleCompany getDaTroubleCompany() {
		return daTroubleCompany;
	}

	public void setDaTroubleCompany(DaTroubleCompany daTroubleCompany) {
		this.daTroubleCompany = daTroubleCompany;
	}

	public Set<DaDept> getDaDeptes() {
		return daDeptes;
	}

	public void setDaDeptes(Set<DaDept> daDeptes) {
		this.daDeptes = daDeptes;
	}

	public String getDangerAdd() {
		return dangerAdd;
	}

	public void setDangerAdd(String dangerAdd) {
		this.dangerAdd = dangerAdd;
	}

	public Set<DaTroubleFile> getDaTroubleFiles() {
		return daTroubleFiles;
	}

	public void setDaTroubleFiles(Set<DaTroubleFile> daTroubleFiles) {
		this.daTroubleFiles = daTroubleFiles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getTroubleNo() {
		return troubleNo;
	}

	public void setTroubleNo(String troubleNo) {
		this.troubleNo = troubleNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFddelegate() {
		return fddelegate;
	}

	public void setFddelegate(String fddelegate) {
		this.fddelegate = fddelegate;
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

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getTroubleCompanyName() {
		return troubleCompanyName;
	}

	public void setTroubleCompanyName(String troubleCompanyName) {
		this.troubleCompanyName = troubleCompanyName;
	}

	public String getFirstAreaName() {
		return firstAreaName;
	}

	public void setFirstAreaName(String firstAreaName) {
		this.firstAreaName = firstAreaName;
	}

	public String getSecondAreaName() {
		return secondAreaName;
	}

	public void setSecondAreaName(String secondAreaName) {
		this.secondAreaName = secondAreaName;
	}

	public String getThirdAreaName() {
		return thirdAreaName;
	}

	public void setThirdAreaName(String thirdAreaName) {
		this.thirdAreaName = thirdAreaName;
	}

}
