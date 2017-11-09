package com.safetys.nbyhpc.web.axis2.model;

import java.io.Serializable;

public class NomalDanger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9162006019332354312L;

	private Integer type;
	
	private Integer tableType;

	private Integer company;

	private Integer generalDanger;

	private Integer generalDangerGovern;

	private Double planMoney;
	
	/**
	 * 省隐患排查已经更新，多了一项应排查企业数
	 */
	private Integer shouldTroubleshooting;// 应排查企业数
	
	//不是交换数据
	private Double governMoney; //重大隐患累计落实隐患治理资金
	private Double normalGovernMoney;//一般隐患累计落实隐患治理资金

	public NomalDanger() {
	}

	public Integer getCompany() {
		if (company == null)
			return 0;
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	public Integer getGeneralDanger() {
		if (generalDanger == null)
			return 0;
		return generalDanger;
	}

	public void setGeneralDanger(Integer generalDanger) {
		this.generalDanger = generalDanger;
	}

	public Integer getGeneralDangerGovern() {
		if (generalDangerGovern == null)
			return 0;
		return generalDangerGovern;
	}

	public void setGeneralDangerGovern(Integer generalDangerGovern) {
		this.generalDangerGovern = generalDangerGovern;
	}

	public Double getPlanMoney() {
		if (planMoney == null)
			return 0.0;
		return planMoney;
	}

	public void setPlanMoney(Double planMoney) {
		this.planMoney = planMoney;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTableType() {
		return tableType;
	}

	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}

	public Integer getShouldTroubleshooting() {
		return shouldTroubleshooting;
	}

	public void setShouldTroubleshooting(Integer shouldTroubleshooting) {
		this.shouldTroubleshooting = shouldTroubleshooting;
	}

	public Double getGovernMoney() {
		return governMoney;
	}

	public void setGovernMoney(Double governMoney) {
		this.governMoney = governMoney;
	}

	public Double getNormalGovernMoney() {
		return normalGovernMoney;
	}

	public void setNormalGovernMoney(Double normalGovernMoney) {
		this.normalGovernMoney = normalGovernMoney;
	}
	
}
