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
public class VDanger extends DaBaseModel {

	private static final long serialVersionUID = 805467517225407390L;

	private Long firstArea;

	private Long secondArea;

	private Long thirdArea;
	
	private Long yhid;

	private String uuid;
	
	private String uuid1;

	private String description;

	private int deleted;

	private Date createTime;
	
	private Date modifyTime;

	private Date finishDate;
	
	private Date happenDate;
	
	private Date planDate;
	
	private int isSynchro;
	
	private int IsNormalChange;
	
	private String dangerNo;
	
	private String dangerAdd;
	
	private int resolveState;
	
	private String gorverContent;
	
	private String hiddenType1;
	
	private String hiddenType2;

	public int getResolveState() {
		return resolveState;
	}

	public void setResolveState(int resolveState) {
		this.resolveState = resolveState;
	}

	public String getDangerAdd() {
		return dangerAdd;
	}

	public void setDangerAdd(String dangerAdd) {
		this.dangerAdd = dangerAdd;
	}

	public String getDangerNo() {
		return dangerNo;
	}

	public void setDangerNo(String dangerNo) {
		this.dangerNo = dangerNo;
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

	public Long getYhid() {
		return yhid;
	}

	public void setYhid(Long yhid) {
		this.yhid = yhid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid1() {
		return uuid1;
	}

	public void setUuid1(String uuid1) {
		this.uuid1 = uuid1;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getHappenDate() {
		return happenDate;
	}

	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public int getIsSynchro() {
		return isSynchro;
	}

	public void setIsSynchro(int isSynchro) {
		this.isSynchro = isSynchro;
	}

	public int getIsNormalChange() {
		return IsNormalChange;
	}

	public void setIsNormalChange(int isNormalChange) {
		IsNormalChange = isNormalChange;
	}

	/**
	 * @return the gorverContent
	 */
	public String getGorverContent() {
		return gorverContent;
	}

	/**
	 * @param gorverContent the gorverContent to set
	 */
	public void setGorverContent(String gorverContent) {
		this.gorverContent = gorverContent;
	}

	public String getHiddenType1() {
		return hiddenType1;
	}

	public void setHiddenType1(String hiddenType1) {
		this.hiddenType1 = hiddenType1;
	}

	public String getHiddenType2() {
		return hiddenType2;
	}

	public void setHiddenType2(String hiddenType2) {
		this.hiddenType2 = hiddenType2;
	}

}
