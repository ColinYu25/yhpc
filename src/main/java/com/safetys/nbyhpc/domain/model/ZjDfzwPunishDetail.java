/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * 打非治违处罚子表
 * 
 * author lih
 * 
 * @since 2012-5-9
 * @version 1.0.0
 */
public class ZjDfzwPunishDetail extends DaBaseModel {

	private static final long serialVersionUID = -2273520815790778678L;

	private ZjDfzwPunish dfzwPunish;

	private String type;

	private Integer checkTeam = 0;

	private Integer checkPerson = 0;

	private Integer companyNum = 0;

	private Integer warn = 0;

	private Integer orderRectify = 0;

	private Integer confiscate = 0;

	private Integer stopProduct = 0;

	private Integer tempDetain = 0;

	private Integer close = 0;

	private Integer administrativeDetain = 0;

	private Integer criminalResponsibility = 0;

	private Float penalty = 0f;

	public ZjDfzwPunishDetail() {
	}

	public ZjDfzwPunishDetail(String type, Integer checkTeam, Integer checkPerson,
			Integer companyNum, Integer warn, Integer orderRectify, Integer confiscate, Integer stopProduct,
			Integer tempDetain, Integer close, Integer administrativeDetain, Integer criminalResponsibility,
			Float penalty) {
		this.type = type;
		this.checkTeam = checkTeam;
		this.checkPerson = checkPerson;
		this.companyNum = companyNum;
		this.warn = warn;
		this.orderRectify = orderRectify;
		this.confiscate = confiscate;
		this.stopProduct = stopProduct;
		this.tempDetain = tempDetain;
		this.close = close;
		this.administrativeDetain = administrativeDetain;
		this.criminalResponsibility = criminalResponsibility;
		this.penalty = penalty;
	}
	
	public void addDfzwPunishDetail(Integer checkTeam, Integer checkPerson,
			Integer companyNum, Integer warn, Integer orderRectify, Integer confiscate, Integer stopProduct,
			Integer tempDetain, Integer close, Integer administrativeDetain, Integer criminalResponsibility,
			Float penalty) {
		this.checkTeam += checkTeam;
		this.checkPerson += checkPerson;
		this.companyNum += companyNum;
		this.warn += warn;
		this.orderRectify += orderRectify;
		this.confiscate += confiscate;
		this.stopProduct += stopProduct;
		this.tempDetain += tempDetain;
		this.close += close;
		this.administrativeDetain += administrativeDetain;
		this.criminalResponsibility += criminalResponsibility;
		this.penalty += penalty;
	}

	public ZjDfzwPunishDetail(Long id) {
		super(id);
	}

	public ZjDfzwPunish getDfzwPunish() {
		return dfzwPunish;
	}

	public void setDfzwPunish(ZjDfzwPunish zjDfzwPunish) {
		this.dfzwPunish = zjDfzwPunish;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCheckTeam() {
		return checkTeam;
	}

	public void setCheckTeam(Integer checkTeam) {
		this.checkTeam = checkTeam;
	}

	public Integer getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(Integer checkPerson) {
		this.checkPerson = checkPerson;
	}

	public Integer getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(Integer companyNum) {
		this.companyNum = companyNum;
	}

	public Integer getWarn() {
		return warn;
	}

	public void setWarn(Integer warn) {
		this.warn = warn;
	}

	public Integer getOrderRectify() {
		return orderRectify;
	}

	public void setOrderRectify(Integer orderRectify) {
		this.orderRectify = orderRectify;
	}

	public Integer getConfiscate() {
		return confiscate;
	}

	public void setConfiscate(Integer confiscate) {
		this.confiscate = confiscate;
	}

	public Integer getStopProduct() {
		return stopProduct;
	}

	public void setStopProduct(Integer stopProduct) {
		this.stopProduct = stopProduct;
	}

	public Integer getTempDetain() {
		return tempDetain;
	}

	public void setTempDetain(Integer tempDetain) {
		this.tempDetain = tempDetain;
	}

	public Integer getClose() {
		return close;
	}

	public void setClose(Integer close) {
		this.close = close;
	}

	public Integer getAdministrativeDetain() {
		return administrativeDetain;
	}

	public void setAdministrativeDetain(Integer administrativeDetain) {
		this.administrativeDetain = administrativeDetain;
	}

	public Integer getCriminalResponsibility() {
		return criminalResponsibility;
	}

	public void setCriminalResponsibility(Integer criminalResponsibility) {
		this.criminalResponsibility = criminalResponsibility;
	}

	public Float getPenalty() {
		return penalty;
	}

	public void setPenalty(Float penalty) {
		this.penalty = penalty;
	}


}
