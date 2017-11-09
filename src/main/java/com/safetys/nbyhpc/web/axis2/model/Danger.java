package com.safetys.nbyhpc.web.axis2.model;

import java.io.Serializable;

public class Danger implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9143271541916853134L;
	
	private Long id;

	private String companyName;

	private String address;

	private String content;

	private Integer targetTask;

	private Integer worker;

	private Integer safetyMethod;

	private Integer goods;
	
	private Integer guapaiLevel;

	private Integer governDate;

	private String stateTime;
	
	private Long nbDangerId;//对应省局数据交换
	
	private String remark;   //对应省局数据交换  放企业ID


	/**
	 * 1：矿山表 2：其他表
	 */
	private Integer tableType;

	/**
	 * 0:未销号 1:已销号
	 */
	private Integer state;

	/**
	 * 行业与领域
	 */
	private Integer tradeType;

	public Danger() {
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getStateTime() {
		return stateTime;
	}

	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}

	public Integer getTableType() {
		return tableType;
	}

	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}

	public Integer getTargetTask() {
		return targetTask;
	}

	public void setTargetTask(Integer targetTask) {
		this.targetTask = targetTask;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getWorker() { 
		return worker;
	}

	public void setWorker(Integer worker) {
		this.worker = worker;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGuapaiLevel() {
		return guapaiLevel;
	}

	public void setGuapaiLevel(Integer guapaiLevel) {
		this.guapaiLevel = guapaiLevel;
	}

	public Long getNbDangerId() {
		return nbDangerId;
	}

	public void setNbDangerId(Long nbDangerId) {
		this.nbDangerId = nbDangerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
