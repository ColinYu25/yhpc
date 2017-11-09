package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class TCaches extends DaBaseModel{

	/**
	 * 会议记录、治理方案实体
	 */
	private static final long serialVersionUID = -5793121656491306585L;

	private String name;
	
	private String content;
	
	private Date uptdate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getUptdate() {
		return uptdate;
	}

	public void setUptdate(Date uptdate) {
		this.uptdate = uptdate;
	}
	
	
	
	
}
