package com.safetys.nbyhpc.domain.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaItemDanger.java
 * @date 2009-08-11
 * @author zhangx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaItemDanger1 extends DaBaseModel{

	/**
	 * 项目重大隐患实体
	 */
	private static final long serialVersionUID = -118534734016211641L;
	
	private DaItemHis daItemHis;

	private String zzCompany;

	private String zzChargeman;

	private String zzTel;

	private String description;

	private boolean govCoordination;

	private boolean partStopProduct;

	private boolean fullStopProduct;

	private boolean target;

	private boolean worker;

	private boolean safetyMethod;

	private boolean goods;

	private Date finishDate;

	private Double governMoney;

	private boolean emphasisProject;

	private Long userId;

	private Long firstArea;

	private Long secondArea;

	private Long fifthArea;

	private Long fouthArea;

	private Long thirdArea;

	private String zcbChargeman;

	private String zcbFillman;

	private String zcbFilldate;
	
	private Set<DaItemDangerGover> daItemDangerGovers = new HashSet<DaItemDangerGover>(0);

	private Set<DaIndustryParameter> daIndustryParameters = new HashSet<DaIndustryParameter>(0);
	
	private Integer govern;//隐患是否以整治(0：未整治,1：已整治)
	
	public DaItemDanger1() {
	}

	public DaItemDanger1(long id) {
		super(id);
	}

	public String getZzCompany() {
		return this.zzCompany;
	}

	public void setZzCompany(String zzCompany) {
		this.zzCompany = zzCompany;
	}

	public String getZzChargeman() {
		return this.zzChargeman;
	}

	public void setZzChargeman(String zzChargeman) {
		this.zzChargeman = zzChargeman;
	}

	public String getZzTel() {
		return this.zzTel;
	}

	public void setZzTel(String zzTel) {
		this.zzTel = zzTel;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<DaIndustryParameter> getDaIndustryParameters() {
		return daIndustryParameters;
	}

	public void setDaIndustryParameters(
			Set<DaIndustryParameter> daIndustryParameters) {
		this.daIndustryParameters = daIndustryParameters;
	}

	public Set<DaItemDangerGover> getDaItemDangerGovers() {
		return daItemDangerGovers;
	}

	public void setDaItemDangerGovers(Set<DaItemDangerGover> daItemDangerGovers) {
		this.daItemDangerGovers = daItemDangerGovers;
	}



	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}



	public Double getGovernMoney() {
		return governMoney;
	}

	public void setGovernMoney(Double governMoney) {
		this.governMoney = governMoney;
	}




	public boolean isEmphasisProject() {
		return emphasisProject;
	}

	public void setEmphasisProject(boolean emphasisProject) {
		this.emphasisProject = emphasisProject;
	}

	public boolean isFullStopProduct() {
		return fullStopProduct;
	}

	public void setFullStopProduct(boolean fullStopProduct) {
		this.fullStopProduct = fullStopProduct;
	}

	public boolean isGoods() {
		return goods;
	}

	public void setGoods(boolean goods) {
		this.goods = goods;
	}

	public boolean isGovCoordination() {
		return govCoordination;
	}

	public void setGovCoordination(boolean govCoordination) {
		this.govCoordination = govCoordination;
	}

	public boolean isPartStopProduct() {
		return partStopProduct;
	}

	public void setPartStopProduct(boolean partStopProduct) {
		this.partStopProduct = partStopProduct;
	}

	public boolean isSafetyMethod() {
		return safetyMethod;
	}

	public void setSafetyMethod(boolean safetyMethod) {
		this.safetyMethod = safetyMethod;
	}

	public boolean isTarget() {
		return target;
	}

	public void setTarget(boolean target) {
		this.target = target;
	}



	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getZcbChargeman() {
		return zcbChargeman;
	}

	public void setZcbChargeman(String zcbChargeman) {
		this.zcbChargeman = zcbChargeman;
	}

	public String getZcbFilldate() {
		return zcbFilldate;
	}

	public void setZcbFilldate(String zcbFilldate) {
		this.zcbFilldate = zcbFilldate;
	}

	public String getZcbFillman() {
		return zcbFillman;
	}

	public void setZcbFillman(String zcbFillman) {
		this.zcbFillman = zcbFillman;
	}

	public DaItemHis getDaItemHis() {
		return daItemHis;
	}

	public void setDaItemHis(DaItemHis daItemHis) {
		this.daItemHis = daItemHis;
	}

	public boolean isWorker() {
		return worker;
	}

	public void setWorker(boolean worker) {
		this.worker = worker;
	}

	public void setIndustryParameters(String trades) {
		if (trades != null && !"".equals(trades)) {
			for (String trade : trades.split(",")) {
				if (Long.parseLong(trade.trim()) != -1L) {
					boolean exists = false;
					for (DaIndustryParameter industry : daIndustryParameters) {
						if (industry.getId() == Long.parseLong(trade.trim())) {
							exists = true;
						}
					}
					if (!exists) {
						daIndustryParameters.add(new DaIndustryParameter(Long
								.parseLong(trade.trim())));
					}
				}
			}
		}
	}

	public Integer getGovern() {
		return govern;
	}

	public void setGovern(Integer govern) {
		this.govern = govern;
	}

	public Long getFirstArea() {
		return firstArea;
	}

	public void setFirstArea(Long firstArea) {
		this.firstArea = firstArea;
	}

	public Long getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(Long secondArea) {
		this.secondArea = secondArea;
	}

	public Long getFifthArea() {
		return fifthArea;
	}

	public void setFifthArea(Long fifthArea) {
		this.fifthArea = fifthArea;
	}

	public Long getFouthArea() {
		return fouthArea;
	}

	public void setFouthArea(Long fouthArea) {
		this.fouthArea = fouthArea;
	}

	public Long getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(Long thirdArea) {
		this.thirdArea = thirdArea;
	}


}
