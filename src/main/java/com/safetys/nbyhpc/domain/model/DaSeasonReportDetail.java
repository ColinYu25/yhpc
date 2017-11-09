package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaSeasonReportDetail.java
 * @date 2009-08-04
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaSeasonReportDetail extends DaBaseModel {

	/**
	 * 季报详细表实体
	 */
	private static final long serialVersionUID = 2964484311316093921L;

	private DaSeasonReport daSeasonReport;
	
	private Long daSeasonReportId;

	private String dangerContent;

	private boolean isCompleted;

	private String dangerTime;

	private Integer dangerType;// 隐患输入类型 暂不使用

	private String description;// 隐患描述 暂不使用
	
	private Long userId;

	public DaSeasonReportDetail() {
	}

	public DaSeasonReportDetail(long id) {
		super(id);
	}

	public DaSeasonReport getDaSeasonReport() {
		return daSeasonReport;
	}

	public void setDaSeasonReport(DaSeasonReport daSeasonReport) {
		this.daSeasonReport = daSeasonReport;
	}

	public Long getDaSeasonReportId() {
		return daSeasonReportId;
	}

	public void setDaSeasonReportId(Long daSeasonReportId) {
		this.daSeasonReportId = daSeasonReportId;
	}

	public String getDangerContent() {
		return dangerContent;
	}

	public void setDangerContent(String dangerContent) {
		this.dangerContent = dangerContent;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getDangerTime() {
		return dangerTime;
	}

	public void setDangerTime(String dangerTime) {
		this.dangerTime = dangerTime;
	}

	public Integer getDangerType() {
		return dangerType;
	}

	public void setDangerType(Integer dangerType) {
		this.dangerType = dangerType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
