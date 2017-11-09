package com.safetys.nbyhpc.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaItemSeasonReport.java
 * @date 2009-08-03
 * @author zhangx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaItemSeasonReport extends DaBaseModel {

	/**
	 * 工程季报表实体
	 */
	private static final long serialVersionUID = -9103886469334328965L;

	private DaItem daItem;

	private Integer ordinaryDangerFinding;

	private Integer ordinaryDangerNotGovern;

	private String description;

	private String chargePerson;

	private String tel;

	private String fillDate;

	private String fillMan;

	private Long userId;

	private Long firstArea;

	private Long secondArea;

	private Long fifthArea;

	private Long fouthArea;

	private Long thirdArea;

	private String type;

	private Set<DaItemSeasonDetail> daItemSeasonDetails = new HashSet<DaItemSeasonDetail>(
			0);

	private List<DaItemSeasonDetail> itemSeasonDetails;

	public DaItemSeasonReport() {
	}

	public DaItemSeasonReport(long id) {
		super(id);
	}

	public DaItem getDaItem() {
		return this.daItem;
	}

	public void setDaItem(DaItem daItem) {
		this.daItem = daItem;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChargePerson() {
		return this.chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFillDate() {
		return this.fillDate;
	}

	public void setFillDate(String fillDate) {
		this.fillDate = fillDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Integer getOrdinaryDangerFinding() {
		return ordinaryDangerFinding;
	}

	public void setOrdinaryDangerFinding(Integer ordinaryDangerFinding) {
		this.ordinaryDangerFinding = ordinaryDangerFinding;
	}

	public Integer getOrdinaryDangerNotGovern() {
		return ordinaryDangerNotGovern;
	}

	public void setOrdinaryDangerNotGovern(Integer ordinaryDangerNotGovern) {
		this.ordinaryDangerNotGovern = ordinaryDangerNotGovern;
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

	public Long getFifthArea() {
		return fifthArea;
	}

	public void setFifthArea(Long fifthArea) {
		this.fifthArea = fifthArea;
	}

	public Long getFouthArea() {
		return fouthArea;
	}

	public void setFouthArea(Long fouthArea) {
		this.fouthArea = fouthArea;
	}

	public Long getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(Long thirdArea) {
		this.thirdArea = thirdArea;
	}

	public Set getDaItemSeasonDetails() {
		return this.daItemSeasonDetails;
	}

	public void setDaItemSeasonDetails(
			Set<DaItemSeasonDetail> daItemSeasonDetails) {
		this.daItemSeasonDetails = daItemSeasonDetails;
	}

	public String getFillMan() {
		return fillMan;
	}

	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<DaItemSeasonDetail> getItemSeasonDetails() {
		return itemSeasonDetails;
	}

	public void setItemSeasonDetails(List<DaItemSeasonDetail> itemSeasonDetails) {
		this.itemSeasonDetails = itemSeasonDetails;
	}
}
