package com.safetys.nbyhpc.domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaSeasonReport.java
 * @date 2009-08-04
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaSeasonReport extends DaBaseModel {

	/**
	 * 季报表实体
	 */
	private static final long serialVersionUID = -5164324712527440136L;

	private DaCompanyPass daCompanyPass;
	
	private DaIndustryParameter daIndustryParameter;

	private Long companyPassId;
	
	private Long bagId;

	private DaBag daBag;

	private String dangerAdd;

	private Integer ordinaryDangerFinding=0;
	
	private int ordinaryDangerFindingBefore=0;

	private Integer ordinaryDangerNotGovern=0;
	
	private int ordinaryDangerNotGovernBefore=0;

	private String description;

	private Integer type;// 季报类型。值为1代表第一种季报；值为2代表第二种季报，以此类推

	private Long userId;

	private Long firstArea;

	private Long secondArea;

	private Long fifthArea;

	private Long fouthArea;

	private Long thirdArea;

	private String linkMan;

	private String linkTel;

	private String linkMobile;

	private String chargePerson;

	private String fillDate;

	private String fillMan;

	private Set<DaSeasonReportDetail> daSeasonReportDetails = new HashSet<DaSeasonReportDetail>();

	private List<DaSeasonReportDetail> seasonReportDetails;

	private Set<DaIndustryParameter> daIndustryParameters = new HashSet<DaIndustryParameter>();

	private Integer industryId;

	public Integer getIndustryId() {
		return industryId;
	}

	public void setIndustryId(Integer industryId) {
		this.industryId = industryId;
	}

	public DaSeasonReport() {
	}

	public DaSeasonReport(long id) {
		super(id);
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public DaBag getDaBag() {
		return daBag;
	}

	public void setDaBag(DaBag daBag) {
		this.daBag = daBag;
	}

	public DaCompanyPass getDaCompanyPass() {
		return daCompanyPass;
	}

	public void setDaCompanyPass(DaCompanyPass daCompanyPass) {
		this.daCompanyPass = daCompanyPass;
	}

	public Set<DaIndustryParameter> getDaIndustryParameters() {
		return daIndustryParameters;
	}

	public void setDaIndustryParameters(
			Set<DaIndustryParameter> daIndustryParameters) {
		this.daIndustryParameters = daIndustryParameters;
	}

	public String getDangerAdd() {
		return dangerAdd;
	}

	public void setDangerAdd(String dangerAdd) {
		this.dangerAdd = dangerAdd;
	}

	public Set<DaSeasonReportDetail> getDaSeasonReportDetails() {
		return daSeasonReportDetails;
	}

	public void setDaSeasonReportDetails(
			Set<DaSeasonReportDetail> daSeasonReportDetails) {
		this.daSeasonReportDetails = daSeasonReportDetails;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getFifthArea() {
		return fifthArea;
	}

	public void setFifthArea(Long fifthArea) {
		this.fifthArea = fifthArea;
	}

	public String getFillDate() {
		return fillDate;
	}

	public void setFillDate(String fillDate) {
		this.fillDate = fillDate;
	}

	public String getFillMan() {
		return fillMan;
	}

	public void setFillMan(String fillMan) {
		this.fillMan = fillMan;
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

	public Integer getOrdinaryDangerFinding() {
		return ordinaryDangerFinding;
	}

	public void setOrdinaryDangerFinding(Integer ordinaryDangerFinding) {
		this.ordinaryDangerFinding = ordinaryDangerFinding;
	}

	public Integer getOrdinaryDangerNotGovern() {
		return ordinaryDangerNotGovern;
	}

	public void setOrdinaryDangerNotGovern(Integer ordinaryDangerNotGovern) {
		this.ordinaryDangerNotGovern = ordinaryDangerNotGovern;
	}

	public Long getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(Long secondArea) {
		this.secondArea = secondArea;
	}

	public Long getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(Long thirdArea) {
		this.thirdArea = thirdArea;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompanyPassId() {
		return companyPassId;
	}

	public void setCompanyPassId(Long companyPassId) {
		this.companyPassId = companyPassId;
	}

	public List<DaSeasonReportDetail> getSeasonReportDetails() {
		if (seasonReportDetails == null && daSeasonReportDetails!=null && daSeasonReportDetails.size()>0) {
			seasonReportDetails = new ArrayList<DaSeasonReportDetail>();
			Iterator<DaSeasonReportDetail> iterator = daSeasonReportDetails
					.iterator();

			while (iterator.hasNext()) {
				DaSeasonReportDetail sdrd = iterator.next();
				seasonReportDetails.add(sdrd);
			}
		}
		return seasonReportDetails;
	}

	public void setSeasonReportDetails(
			List<DaSeasonReportDetail> seasonReportDetails) {
		this.seasonReportDetails = seasonReportDetails;
	}

	public Long getBagId() {
		return bagId;
	}

	public void setBagId(Long bagId) {
		this.bagId = bagId;
	}

	public int getOrdinaryDangerFindingBefore() {
		return ordinaryDangerFindingBefore;
	}

	public void setOrdinaryDangerFindingBefore(Integer ordinaryDangerFindingBefore) {
		this.ordinaryDangerFindingBefore = ordinaryDangerFindingBefore;
	}

	public int getOrdinaryDangerNotGovernBefore() {
		return ordinaryDangerNotGovernBefore;
	}

	public void setOrdinaryDangerNotGovernBefore(
			Integer ordinaryDangerNotGovernBefore) {
		this.ordinaryDangerNotGovernBefore = ordinaryDangerNotGovernBefore;
	}

	public DaIndustryParameter getDaIndustryParameter() {
		return daIndustryParameter;
	}

	public void setDaIndustryParameter(DaIndustryParameter daIndustryParameter) {
		this.daIndustryParameter = daIndustryParameter;
	}

}
