package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaCompany.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class VNormalDanger extends DaBaseModel {

	/**
	 * 企业实体
	 */
	private static final long serialVersionUID = 805467517225407390L;

	private String companyName;

	private Long firstArea;

	private Long secondArea;

	private Long thirdArea;
	
	private Long yhid;

	private String uuid;
	
	private String uuid1;

	private String description;

	private String type;
	
	private String inType;

	private String second_type;

	private float Governmoney;

	private int repaired;

	private int deleted;

	private Date createTime;

	private Date completedDate;
	
	private int isSynchro;

	// 隐患来源编码
	private String hazardSourceCode;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSecond_type() {
		return second_type;
	}

	public void setSecond_type(String second_type) {
		this.second_type = second_type;
	}

	public float getGovernmoney() {
		return Governmoney;
	}

	public void setGovernmoney(float governmoney) {
		Governmoney = governmoney;
	}

	public int getRepaired() {
		return repaired;
	}

	public void setRepaired(int repaired) {
		this.repaired = repaired;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public int getIsSynchro() {
		return isSynchro;
	}

	public void setIsSynchro(int isSynchro) {
		this.isSynchro = isSynchro;
	}

	public Long getYhid() {
		return yhid;
	}

	public void setYhid(Long yhid) {
		this.yhid = yhid;
	}

	public String getInType() {
		return inType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	public String getUuid1() {
		return uuid1;
	}

	public void setUuid1(String uuid1) {
		this.uuid1 = uuid1;
	}

	/**
	 * @return the hazardSourceCode
	 */
	public String getHazardSourceCode() {
		return hazardSourceCode;
	}

	/**
	 * @param hazardSourceCode the hazardSourceCode to set
	 */
	public void setHazardSourceCode(String hazardSourceCode) {
		this.hazardSourceCode = hazardSourceCode;
	}
	
	

}
