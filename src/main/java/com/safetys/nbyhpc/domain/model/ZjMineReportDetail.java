package com.safetys.nbyhpc.domain.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class ZjMineReportDetail extends DaBaseModel {

	private static final long serialVersionUID = -1893684354875577201L;

	private ZjMineReport zjMineReport;// 主表对象

	private Integer type;// 行业类型

	/**
	 * @author lih
	 * @date 2012-06-18
	 */
	private Integer shouldTroubleshooting;// 应排查企业数

	private Integer company;// 实际排查企业数

	private Integer generalDanger;

	private Integer generalDangerGovern;

	private Double planMoney;

	public ZjMineReportDetail() {
	}

	public ZjMineReportDetail(Integer shouldTroubleshooting, Integer company, Integer generalDanger,
			Integer generalDangerGovern, Double planMoney, Integer type) {
		this.shouldTroubleshooting = shouldTroubleshooting;
		this.type = type;
		this.company = company;
		this.generalDanger = generalDanger;
		this.generalDangerGovern = generalDangerGovern;
		this.planMoney = planMoney;
	}

	public void addMineDetail(Integer shouldTroubleshooting, Integer company, Integer generalDanger,
			Integer generalDangerGovern, Double planMoney) {
		this.shouldTroubleshooting += shouldTroubleshooting;
		this.company += company;
		this.generalDanger += generalDanger;
		this.generalDangerGovern += generalDangerGovern;
		this.planMoney += planMoney;
	}

	public void addMineDetail(ZjMineReportDetail detail) {
		try {
			this.shouldTroubleshooting += detail.getShouldTroubleshooting()==null?0:detail.getShouldTroubleshooting();
			this.company += detail.getCompany()==null?0:detail.getCompany();
			this.generalDanger += detail.getGeneralDanger();
			this.generalDangerGovern += detail.getGeneralDangerGovern();
			this.planMoney += detail.getPlanMoney();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initMineDetail() {
		this.shouldTroubleshooting = 0;
		this.company = 0;
		this.generalDanger = 0;
		this.generalDangerGovern = 0;
		this.planMoney = 0.0;
	}

	public ZjMineReport getZjMineReport() {
		return this.zjMineReport;
	}

	public void setZjMineReport(ZjMineReport zjMineReport) {
		this.zjMineReport = zjMineReport;
	}

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	public Integer getGeneralDanger() {
		return generalDanger;
	}

	public void setGeneralDanger(Integer generalDanger) {
		this.generalDanger = generalDanger;
	}

	public Integer getGeneralDangerGovern() {
		return generalDangerGovern;
	}

	public void setGeneralDangerGovern(Integer generalDangerGovern) {
		this.generalDangerGovern = generalDangerGovern;
	}

	public boolean getGeneralDangerIsDividend() {
		String quotient = String.valueOf(((double) generalDangerGovern) / generalDanger);
		boolean flag = (quotient.length() <= 4);
		return flag;
	}

	public boolean getCompanyIsDividend() {
		String quotient = String.valueOf(((double) company) / shouldTroubleshooting);
		boolean flag = (quotient.length() <= 4);
		return flag;
	}

	public Double getPlanMoney() {
		NumberFormat format = new DecimalFormat("0.0");
		return Double.parseDouble(format.format(planMoney));
	}

	public String getPlanMoneyString() {
		NumberFormat format = new DecimalFormat("0.0");
		return String.valueOf(Double.parseDouble(format.format(planMoney)));
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

	public Integer getShouldTroubleshooting() {
		return shouldTroubleshooting;
	}

	public void setShouldTroubleshooting(Integer shouldTroubleshooting) {
		this.shouldTroubleshooting = shouldTroubleshooting;
	}
}
