package com.safetys.nbyhpc.domain.model;


import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaItemDanger.java
 * @date 2009-08-12
 * @author zhangx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaItemDangerGover extends DaBaseModel {

	/**
	 * 隐患治理情况表
	 */
	private static final long serialVersionUID = -3723964553150191274L;

	private DaItemDanger daItemDanger;

	private Date finishDate;

	private String gorverContent;

	private Double money;

	private String memo;

	private Long userId;

	private String zcbChargeman;

	private String zcbFillman;

	private String zcbFilldate;

	public DaItemDangerGover() {
	}

	public DaItemDangerGover(long id) {
		super(id);
	}



	public DaItemDanger getDaItemDanger() {
		return this.daItemDanger;
	}

	public void setDaItemDanger(DaItemDanger daItemDanger) {
		this.daItemDanger = daItemDanger;
	}


	public String getGorverContent() {
		return this.gorverContent;
	}

	public void setGorverContent(String gorverContent) {
		this.gorverContent = gorverContent;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getZcbChargeman() {
		return this.zcbChargeman;
	}

	public void setZcbChargeman(String zcbChargeman) {
		this.zcbChargeman = zcbChargeman;
	}

	public String getZcbFillman() {
		return this.zcbFillman;
	}

	public void setZcbFillman(String zcbFillman) {
		this.zcbFillman = zcbFillman;
	}

	public String getZcbFilldate() {
		return this.zcbFilldate;
	}

	public void setZcbFilldate(String zcbFilldate) {
		this.zcbFilldate = zcbFilldate;
	}

}
