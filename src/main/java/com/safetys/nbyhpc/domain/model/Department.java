package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class Department extends DaBaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2724745625952542835L;

	private String code;//主管部门编码

	private String name;//主管部门简称

	private String cityLevel;//市级部门全称
	
	private String countyLevel;//县级部门全称
	
	public Department(){
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityLevel() {
		return cityLevel;
	}

	public void setCityLevel(String cityLevel) {
		this.cityLevel = cityLevel;
	}

	public String getCountyLevel() {
		return countyLevel;
	}

	public void setCountyLevel(String countyLevel) {
		this.countyLevel = countyLevel;
	}
	
	
	

}