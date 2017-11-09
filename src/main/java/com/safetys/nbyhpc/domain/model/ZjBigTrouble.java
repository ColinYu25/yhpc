package com.safetys.nbyhpc.domain.model;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class ZjBigTrouble extends DaBaseModel{

	private static final long serialVersionUID = 3372320431107378679L;

	private String reportMonth;
	
	private String reportMonth2;
	
	private String reportState2;
	
	private Integer MainState;

	private String chargeMan;

	private String companyName;

	private String address;

	private String content;

	private Integer targetTask;

	private Integer worker;

	private Integer safetyMethod;

	private Integer goods;

	private Integer governDate;

	//private Long userId;
	
	private String year;
	
	private String month;
	
	private FkUser userId;
	
	private Integer reportState;
	
	private String stateTime;
	
	private String remark;
	
	private Integer guapaiLevel;//挂牌级别
	
	/**
	 * 是否是县市区用户
	 */
	private String isCounty;
	/**
	 * 1:重大隐患数
	 * 2：已销号数
	 * 3：1-2
	 */
	private Integer troubleType;
	/**
	 * 1：矿山表
	 * 2：其他表
	 */
	private Integer tableType;
	/**
	 * 0:未销号
	 * 1:已销号
	 */
	private Integer state;
	/**
	 * 行业与领域
	 */
	private Integer tradeType;
	
	/**
	 * 增加页面搜索条件
	 */
	private boolean isWdw;//是否达到“五到位”要求的
	
	private boolean isGuapai;//是否督办督办

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public ZjBigTrouble() {
	}

	public Integer getGoods() {
		return goods;
	}

	public void setGoods(Integer goods) {
		this.goods = goods;
	}

	public Integer getGovernDate() {
		return governDate;
	}

	public void setGovernDate(Integer governDate) {
		this.governDate = governDate;
	}

	public Integer getSafetyMethod() {
		return safetyMethod;
	}

	public void setSafetyMethod(Integer safetyMethod) {
		this.safetyMethod = safetyMethod;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getTargetTask() {
		return targetTask;
	}

	public void setTargetTask(Integer targetTask) {
		this.targetTask = targetTask;
	}

	public Integer getWorker() {
		return worker;
	}

	public void setWorker(Integer worker) {
		this.worker = worker;
	}

	public String getReportMonth() {
		return this.reportMonth;
	}

	public void setReportMonth(String reportMonth) {
		this.reportMonth = reportMonth;
	}

	public String getChargeMan() {
		return this.chargeMan;
	}

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public Long getUserId() {
//		return this.userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getStateTime() {
		return stateTime;
	}

	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getTableType() {
		return tableType;
	}

	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}

	public Integer getTroubleType() {
		return troubleType;
	}

	public void setTroubleType(Integer troubleType) {
		this.troubleType = troubleType;
	}


	public FkUser getUserId() {
		return userId;
	}

	public void setUserId(FkUser userId) {
		this.userId = userId;
	}

	public Integer getReportState() {
		return reportState;
	}

	public void setReportState(Integer reportState) {
		this.reportState = reportState;
	}

	public String getReportMonth2() {
		return reportMonth2;
	}

	public void setReportMonth2(String reportMonth2) {
		this.reportMonth2 = reportMonth2;
	}

	public String getReportState2() {
		return reportState2;
	}

	public void setReportState2(String reportState2) {
		this.reportState2 = reportState2;
	}

	public Integer getMainState() {
		return MainState;
	}

	public void setMainState(Integer mainState) {
		MainState = mainState;
	}

	public String getIsCounty() {
		return isCounty;
	}

	public void setIsCounty(String isCounty) {
		this.isCounty = isCounty;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public Integer getGuapaiLevel() {
		return guapaiLevel;
	}

	
	public void setGuapaiLevel(Integer guapaiLevel) {
		this.guapaiLevel = guapaiLevel;
	}

	
	public boolean isWdw() {
		return isWdw;
	}

	
	public void setWdw(boolean isWdw) {
		this.isWdw = isWdw;
	}

	
	public boolean isGuapai() {
		return isGuapai;
	}

	
	public void setGuapai(boolean isGuapai) {
		this.isGuapai = isGuapai;
	}

}
