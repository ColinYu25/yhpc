package com.safetys.nbyhpc.domain.model;


import java.io.Serializable;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class ZjIdea extends DaBaseModel {

	private static final long serialVersionUID = -1768015571133908025L;

	private FkUser fkUser;
	
	private Long userId;

	private String content;

	private Long tableType;

	private Long tableId;

	public ZjIdea() {
	}
	
	public ZjIdea(FkUser fkUser, String content, Long tableType, Long tableId) {
		this.fkUser = fkUser;
		this.content = content;
		this.tableType = tableType;
		this.tableId = tableId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Serializable getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTableType() {
		return this.tableType;
	}

	public void setTableType(Long tableType) {
		this.tableType = tableType;
	}

	public Long getTableId() {
		return this.tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public FkUser getFkUser() {
		return fkUser;
	}

	public void setFkUser(FkUser fkUser) {
		this.fkUser = fkUser;
	}
}
