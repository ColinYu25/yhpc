/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.nbyhpc.domain.model.base.DaBaseModel;
import com.safetys.nbyhpc.util.SafetyTrouble;

/**
 * 打非治违周报主表
 * 
 * author lih
 * 
 * @since 2012-5-9
 * @version 1.0.0
 */
public class ZjDfzwReport extends DaBaseModel {

	private static final long serialVersionUID = -1190802769740112166L;

	private Date reportDateBegin;

	private Date reportDateEnd;

	private String tel;

	private String fillMan;

	private FkUser user;

	private String chargeMan;

	private Date fillDate;

	private Integer state;

	private FkUser nowUser;
	
	private boolean notAllowedroolBack;
	
	private boolean cityCreate;

	private boolean isReport = false;

	private boolean isProvince = false;

	private boolean isView = false;

	private boolean queryReportMonth;
	
	private boolean isAnweiCheck;

	private Long secondArea;// 搜索地级市

	private boolean isPrint = false;// 是否显示打印页

	private Integer backObstacle = 0;

	private String roleDepic;//部门
	
	private boolean addStatistic=false;  //新增汇总表状态
	
	/**报表类型：1：周报；2：月报*/
	private Integer reportType;

	private Set<ZjDfzwReportDetail> dfzwReportDetails = new HashSet<ZjDfzwReportDetail>(0);
	
	private String anweiBackState="0";//安委会是否退回标志，默认为“0”不可以退回；“1”可以退回
	
	public ZjDfzwReport() {
	}

	public ZjDfzwReport(Long id) {
		super(id);
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFillMan() {
		return this.fillMan;
	}

	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
	}

	public String getChargeMan() {
		return this.chargeMan;
	}

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
	}

	public Date getFillDate() {
		return this.fillDate;
	}

	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public FkUser getUser() {
		return user;
	}

	public void setUser(FkUser user) {
		this.user = user;
	}

	public Set<ZjDfzwReportDetail> getDfzwReportDetails() {
		return dfzwReportDetails;
	}

	public void setDfzwReportDetails(Set<ZjDfzwReportDetail> dfzwReportDetails) {
		this.dfzwReportDetails = dfzwReportDetails;
	}

	public Date getReportDateBegin() {
		return reportDateBegin;
	}

	public void setReportDateBegin(Date reportDateBegin) {
		this.reportDateBegin = reportDateBegin;
	}

	public Date getReportDateEnd() {
		return reportDateEnd;
	}

	public void setReportDateEnd(Date reportDateEnd) {
		this.reportDateEnd = reportDateEnd;
	}

	public FkUser getNowUser() {
		return nowUser;
	}

	public void setNowUser(FkUser nowUser) {
		this.nowUser = nowUser;
	}

	public boolean isReport() {
		return isReport;
	}
	
	public boolean getIsReport() {
		return isReport;
	}

	public void setReport(boolean isReport) {
		this.isReport = isReport;
	}

	public boolean isProvince() {
		return isProvince;
	}

	public void setProvince(boolean isProvince) {
		this.isProvince = isProvince;
	}

	public boolean isView() {
		return isView;
	}

	public void setView(boolean isView) {
		this.isView = isView;
	}

	public boolean isQueryReportMonth() {
		return queryReportMonth;
	}

	public void setQueryReportMonth(boolean queryReportMonth) {
		this.queryReportMonth = queryReportMonth;
	}

	public Long getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(Long secondArea) {
		this.secondArea = secondArea;
	}

	public boolean isPrint() {
		return isPrint;
	}

	public void setPrint(boolean isPrint) {
		this.isPrint = isPrint;
	}

	public Integer getBackObstacle() {
		return backObstacle;
	}

	public void setBackObstacle(Integer backObstacle) {
		this.backObstacle = backObstacle;
	}

	/**
	 * 获取上报的中文显示
	 * 
	 * @return
	 */
	public String getStateChinese() {
		if (state == SafetyTrouble.NO_REPORT) {
			return "未上报";
		} else if (state == SafetyTrouble.COUNTY_REPORT) {
			return "乡镇级上报";
		} else if (state == SafetyTrouble.CITY_REPORT) {
			return "县级上报";
		} else if (state == SafetyTrouble.PROVINCE_REPORT) {
			return "市级上报";
		}
		return "";
	}

	public boolean getAllowReport() {
		return (Boolean) SafetyTrouble.getState(nowUser, state).get("allow");
	}

	public boolean getAllowRollback() {
		return SafetyTrouble.allowBack(nowUser, state - backObstacle);
	}

	public String getRoleDepic() {
		return roleDepic;
	}

	public void setRoleDepic(String roleDepic) {
		this.roleDepic = roleDepic;
	}

	public boolean isAnweiCheck() {
		return isAnweiCheck;
	}

	public void setAnweiCheck(boolean isAnweiCheck) {
		this.isAnweiCheck = isAnweiCheck;
	}

	public boolean isNotAllowedroolBack() {
		return notAllowedroolBack;
	}

	public void setNotAllowedroolBack(boolean notAllowedroolBack) {
		this.notAllowedroolBack = notAllowedroolBack;
	}

	public boolean isCityCreate() {
		return cityCreate;
	}

	public void setCityCreate(boolean cityCreate) {
		this.cityCreate = cityCreate;
	}

	public boolean isAddStatistic() {
		return addStatistic;
	}

	public void setAddStatistic(boolean addStatistic) {
		this.addStatistic = addStatistic;
	}

	/**
	 * @return the anweiBackState
	 */
	public String getAnweiBackState() {
		return anweiBackState;
	}

	/**
	 * @param anweiBackState the anweiBackState to set
	 */
	public void setAnweiBackState(String anweiBackState) {
		this.anweiBackState = anweiBackState;
	}
	
	
	
}
