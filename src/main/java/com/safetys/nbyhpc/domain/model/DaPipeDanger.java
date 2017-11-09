package com.safetys.nbyhpc.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * 重大隐患实体
 * @(#) DaCompany.java
 * @date 2009-08-08
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaPipeDanger extends DaBaseModel {

	/**
	 * 重大隐患实体
	 */
	private static final long serialVersionUID = -3254977862997279298L;

	private DaPipelineInfo pipeLine;

	private String dangerNo;

	private String dangerAdd;

	private Boolean govCoordination;

	private Boolean partStopProduct;

	private Boolean fullStopProduct;

	private Boolean target;

	private Boolean resource;

	private Boolean safetyMethod;

	private Boolean goods;

	private Date finishDate;

	private float governMoney;

	private Boolean emphasisProject;

	private Long userId;

	private Long firstArea;

	private Long secondArea;

	private Long fifthArea;

	private Long fouthArea;

	private Long thirdArea;

	private String description;

	private String linkMan;

	private String linkTel;

	private String linkMobile;

	private String chargePerson;

	private Date fillDate;

	private String fillMan;
	
	private int nowYear;
	
	private Date startTime;
	
	private Date endTime;

	private Set<DaPipeDangerGorver> daPipeDangerGorvers = new HashSet<DaPipeDangerGorver>(0);

	private Set<DaPipeRollcallCompany> daPipeRollcallCompanies = new HashSet<DaPipeRollcallCompany>(0);

	private Set<DaIndustryParameter> daIndustryParameters = new HashSet<DaIndustryParameter>(0);
	
	private String industryParameters;
	
	private String isGorver;
	
	private String isRollcall;
	
	private DaPipeDangerGorver daPipeDangerGorver;//lisl 列表显示计划治理和治理隐患
	
	private String isOver;//是否超期

	public String getIsGorver() {
		return isGorver;
	}

	public void setIsGorver(String isGorver) {
		this.isGorver = isGorver;
	}

	public DaPipeDanger() {
	}

	public DaPipeDanger(long id) {
		super(id);
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}
	
	public String getDangerAdd() {
		return dangerAdd;
	}

	public void setDangerAdd(String dangerAdd) {
		this.dangerAdd = dangerAdd;
	}

	public String getDangerNo() {
		return dangerNo;
	}

	public void setDangerNo(String dangerNo) {
		this.dangerNo = dangerNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEmphasisProject() {
		return emphasisProject;
	}

	public void setEmphasisProject(Boolean emphasisProject) {
		this.emphasisProject = emphasisProject;
	}

	public Long getFifthArea() {
		return fifthArea;
	}

	public void setFifthArea(Long fifthArea) {
		this.fifthArea = fifthArea;
	}

	public Date getFillDate() {
		return fillDate;
	}

	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}

	public String getFillMan() {
		return fillMan;
	}

	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Long getFirstArea() {
		return firstArea;
	}

	public void setFirstArea(Long firstArea) {
		this.firstArea = firstArea;
	}

	public Long getFouthArea() {
		return fouthArea;
	}

	public void setFouthArea(Long fouthArea) {
		this.fouthArea = fouthArea;
	}

	public Boolean getFullStopProduct() {
		return fullStopProduct;
	}

	public void setFullStopProduct(Boolean fullStopProduct) {
		this.fullStopProduct = fullStopProduct;
	}

	public Boolean getGoods() {
		return goods;
	}

	public void setGoods(Boolean goods) {
		this.goods = goods;
	}

	public Boolean getGovCoordination() {
		return govCoordination;
	}

	public void setGovCoordination(Boolean govCoordination) {
		this.govCoordination = govCoordination;
	}

	public float getGovernMoney() {
		return governMoney;
	}

	public void setGovernMoney(float governMoney) {
		this.governMoney = governMoney;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public Boolean getPartStopProduct() {
		return partStopProduct;
	}

	public void setPartStopProduct(Boolean partStopProduct) {
		this.partStopProduct = partStopProduct;
	}

	public Boolean getResource() {
		return resource;
	}

	public void setResource(Boolean resource) {
		this.resource = resource;
	}

	public Boolean getSafetyMethod() {
		return safetyMethod;
	}

	public void setSafetyMethod(Boolean safetyMethod) {
		this.safetyMethod = safetyMethod;
	}

	public Long getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(Long secondArea) {
		this.secondArea = secondArea;
	}

	public Boolean getTarget() {
		return target;
	}

	public void setTarget(Boolean target) {
		this.target = target;
	}

	public Long getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(Long thirdArea) {
		this.thirdArea = thirdArea;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIsRollcall() {
		return isRollcall;
	}

	public void setIsRollcall(String isRollcall) {
		this.isRollcall = isRollcall;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Set<DaIndustryParameter> getDaIndustryParameters() {
		return daIndustryParameters;
	}

	public void setDaIndustryParameters(
			Set<DaIndustryParameter> daIndustryParameters) {
		this.daIndustryParameters = daIndustryParameters;
	}
	
	/**
	 * 设置重大隐患类型
	 * @param trades
	 */
	public void setIndustryParameters(String trades) {
		industryParameters = trades;
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
						daIndustryParameters.add(new DaIndustryParameter(Long.parseLong(trade.trim())));
					}
				}
			}
		}
	}

	public String getIndustryParameters() {
		return industryParameters;
	}

	public int getNowYear() {
		return nowYear;
	}

	public void setNowYear(int nowYear) {
		this.nowYear = nowYear;
	}

	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}

	public Set<DaPipeDangerGorver> getDaPipeDangerGorvers() {
		return daPipeDangerGorvers;
	}

	public void setDaPipeDangerGorvers(Set<DaPipeDangerGorver> daPipeDangerGorvers) {
		this.daPipeDangerGorvers = daPipeDangerGorvers;
	}

	public Set<DaPipeRollcallCompany> getDaPipeRollcallCompanies() {
		return daPipeRollcallCompanies;
	}

	public void setDaPipeRollcallCompanies(
			Set<DaPipeRollcallCompany> daPipeRollcallCompanies) {
		this.daPipeRollcallCompanies = daPipeRollcallCompanies;
	}

	public DaPipelineInfo getPipeLine() {
		return pipeLine;
	}

	public void setPipeLine(DaPipelineInfo pipeLine) {
		this.pipeLine = pipeLine;
	}

	public DaPipeDangerGorver getDaPipeDangerGorver() {
		return daPipeDangerGorver;
	}

	public void setDaPipeDangerGorver(DaPipeDangerGorver daPipeDangerGorver) {
		this.daPipeDangerGorver = daPipeDangerGorver;
	}
}
