/*
 * Copyright (c) 2002-2018 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
//Generated 2014-5-5 15:43:07 by Safetys Code Build Tools for d7
package com.safetys.nbyhpc.domain.model.android;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.mobile.MBaseModel;

/**
 * 客户端密钥
 */
public class ClientAuthKey extends MBaseModel {

	private static final long serialVersionUID = -4117094232592835192L;

	/**
	 * 密钥超时时间，默认30天
	 */
	public static long ACTION_TIME_OUT = 2592000000L;

	/**
	 * 密钥激活状态
	 */
	public static final String ACTIVE = "active";
	/**
	 * 密钥死亡状态
	 */
	public static final String DEAD = "dead";

	private Long userId;// 用户ID
	private String status;// 密钥状态
	private String authKey;// 密钥
	private Date actionDate;// 上次操作时间

	public ClientAuthKey() {
		super();
	}

	public ClientAuthKey(Long userId, String authKey) {
		super();
		this.userId = userId;
		this.authKey = authKey;
		this.status = ACTIVE;
		this.actionDate = new Date();
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Date getActionDate() {
		return actionDate;
	}

}