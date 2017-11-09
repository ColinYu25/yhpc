package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaCompanyAcount.java
 * @date 2009-08-17
 * @author zhangxu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaCompanyAcount extends DaBaseModel {

	/**
	 * 异地经营台帐表
	 */
	private static final long serialVersionUID = -9042335716688253184L;

	private DaCompany daCompany;

	private Long firstArea;

	private Long secondArea;

	private Long thirdArea;

	private Long oldfirstArea;

	private Long oldsecondArea;

	private Long oldthirdArea;

	private boolean isningbo;

	private String fddelegate;

	private String tel;

	private String address;

	public DaCompanyAcount() {
	}

	public DaCompanyAcount(long id) {
		super(id);
	}

	public DaCompany getDaCompany() {
		return daCompany;
	}

	public void setDaCompany(DaCompany daCompany) {
		this.daCompany = daCompany;
	}

	public Long getFirstArea() {
		return firstArea;
	}

	public void setFirstArea(Long firstArea) {
		this.firstArea = firstArea;
	}

	public boolean isIsningbo() {
		return isningbo;
	}

	public void setIsningbo(boolean isningbo) {
		this.isningbo = isningbo;
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

	public String getFddelegate() {
		return this.fddelegate;
	}

	public void setFddelegate(String fddelegate) {
		this.fddelegate = fddelegate;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getOldfirstArea() {
		return oldfirstArea;
	}

	public void setOldfirstArea(Long oldfirstArea) {
		this.oldfirstArea = oldfirstArea;
	}

	public Long getOldsecondArea() {
		return oldsecondArea;
	}

	public void setOldsecondArea(Long oldsecondArea) {
		this.oldsecondArea = oldsecondArea;
	}

	public Long getOldthirdArea() {
		return oldthirdArea;
	}

	public void setOldthirdArea(Long oldthirdArea) {
		this.oldthirdArea = oldthirdArea;
	}

}
