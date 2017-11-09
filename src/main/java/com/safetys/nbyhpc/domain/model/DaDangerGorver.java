package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaDangerGorver.java
 * @date 2009-08-10
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 */
public class DaDangerGorver extends DaBaseModel {

	/**
	 * 重大隐患治理实体
	 */
	private static final long serialVersionUID = -3162476386176263118L;

	private DaDanger daDanger;

	private String dangerAdd;

	private String gorverContent;

	private Date finishDate;

	private float money;

	private String memo;

	private Long userId;

	private String linkMan;

	private String linkTel;

	private String linkMobile;

	private String chargePerson;

	private Date fillDate;

	private String fillMan;
	
	private boolean evaluate;
	
	private boolean evaluateExpert;
	
	private boolean evaluateGovernment;
	
	private String checkGorverDepar;

	private Date checkDate;

	private String checkContent;

	//监管责任单位
	private String governDepartment;
	//监管责任人
	private String governPerson;
	public DaDangerGorver() {
	}

	public DaDangerGorver(long id) {
		super(id);
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public DaDanger getDaDanger() {
		return daDanger;
	}

	public void setDaDanger(DaDanger daDanger) {
		this.daDanger = daDanger;
	}

	public String getDangerAdd() {
		return dangerAdd;
	}

	public void setDangerAdd(String dangerAdd) {
		this.dangerAdd = dangerAdd;
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

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getGorverContent() {
		return gorverContent;
	}

	public void setGorverContent(String gorverContent) {
		this.gorverContent = gorverContent;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isEvaluate() {
		return evaluate;
	}

	public void setEvaluate(boolean evaluate) {
		this.evaluate = evaluate;
	}

	public boolean isEvaluateExpert() {
		return evaluateExpert;
	}

	public void setEvaluateExpert(boolean evaluateExpert) {
		this.evaluateExpert = evaluateExpert;
	}

	public boolean isEvaluateGovernment() {
		return evaluateGovernment;
	}

	public void setEvaluateGovernment(boolean evaluateGovernment) {
		this.evaluateGovernment = evaluateGovernment;
	}

	public String getCheckGorverDepar() {
		return checkGorverDepar;
	}

	public void setCheckGorverDepar(String checkGorverDepar) {
		this.checkGorverDepar = checkGorverDepar;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	public String getGovernDepartment() {
		return governDepartment;
	}

	public void setGovernDepartment(String governDepartment) {
		this.governDepartment = governDepartment;
	}

	public String getGovernPerson() {
		return governPerson;
	}

	public void setGovernPerson(String governPerson) {
		this.governPerson = governPerson;
	}
	
	

}
