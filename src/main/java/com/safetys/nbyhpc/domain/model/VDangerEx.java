package com.safetys.nbyhpc.domain.model;

import java.util.Date;

/**
 * VDangerExId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VDangerEx implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2653689059146744236L;
	private Long id;
	private String coName;
	private String hiddenDangerInfoSources;
	private String hiddenDangerCategory;
	private String regulationClassification;
	private Date investigationDate;
	private String hiddenDangerPlace;
	private String hiddenDangerArea;
	private String hiddenDangerDescription;
	private String responsibleOrg;
	private String responsibleResponsible;
	private Date rectificationLimitDate;
	private String rectificationType;
	private String rectificationSituation;
	private String rectificationMeasures;
	private Date checkDate;
	private String checkOrg;
	private String checkSituation;
	private Long listedSupervisorySituation;
	private Date listedSupervisoryDate;
	private String listedSupervisoryLevel;
	private String listedSupervisoryOrg;
	private String listedSupervisoryNumber;
	private String hiddenDangerReport;
	private Long improvementTargetSitu;
	private String improvementDate;
	private Long improvementResSitu;
	private String improvementRespDate;
	private Long controlMeasuresSitu;
	private String controlMeasuresDate;
	private Long rectificationMoneySitu;
	private String rectificationMoneyDate;
	private String rectificationTimeSitu;
	private String rectificationTimeDate;
	private Long contingencyPlanSitu;
	private String contingencyPlanDate;
	private String controlPlanSitu;
	private String controlPlanDate;
	private Long rectificationMoney;
	private Date rectificationFinishTime;
	private Date cancelDate;
	private Date confirmTime;
	private String inspector;
	private String outid;
	private String longitude;
	private String latitude;
	private String repairUnit;
	private Long versionId;
	private Long isDeleted;
	private String exFlag;
	// Constructors

	/** default constructor */
	public VDangerEx() {
	}

	// Property accessors

	public String getCoName() {
		return this.coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getHiddenDangerInfoSources() {
		return this.hiddenDangerInfoSources;
	}

	public void setHiddenDangerInfoSources(String hiddenDangerInfoSources) {
		this.hiddenDangerInfoSources = hiddenDangerInfoSources;
	}

	public String getHiddenDangerCategory() {
		return this.hiddenDangerCategory;
	}

	public void setHiddenDangerCategory(String hiddenDangerCategory) {
		this.hiddenDangerCategory = hiddenDangerCategory;
	}

	public String getRegulationClassification() {
		return this.regulationClassification;
	}

	public void setRegulationClassification(String regulationClassification) {
		this.regulationClassification = regulationClassification;
	}

	public Date getInvestigationDate() {
		return this.investigationDate;
	}

	public void setInvestigationDate(Date investigationDate) {
		this.investigationDate = investigationDate;
	}

	public String getHiddenDangerPlace() {
		return this.hiddenDangerPlace;
	}

	public void setHiddenDangerPlace(String hiddenDangerPlace) {
		this.hiddenDangerPlace = hiddenDangerPlace;
	}

	public String getHiddenDangerArea() {
		return this.hiddenDangerArea;
	}

	public void setHiddenDangerArea(String hiddenDangerArea) {
		this.hiddenDangerArea = hiddenDangerArea;
	}

	public String getHiddenDangerDescription() {
		return this.hiddenDangerDescription;
	}

	public void setHiddenDangerDescription(String hiddenDangerDescription) {
		this.hiddenDangerDescription = hiddenDangerDescription;
	}

	public String getResponsibleOrg() {
		return this.responsibleOrg;
	}

	public void setResponsibleOrg(String responsibleOrg) {
		this.responsibleOrg = responsibleOrg;
	}


	public Date getRectificationLimitDate() {
		return this.rectificationLimitDate;
	}

	public void setRectificationLimitDate(Date rectificationLimitDate) {
		this.rectificationLimitDate = rectificationLimitDate;
	}

	public String getRectificationType() {
		return this.rectificationType;
	}

	public void setRectificationType(String rectificationType) {
		this.rectificationType = rectificationType;
	}



	public String getRectificationMeasures() {
		return this.rectificationMeasures;
	}

	public void setRectificationMeasures(String rectificationMeasures) {
		this.rectificationMeasures = rectificationMeasures;
	}

	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckOrg() {
		return this.checkOrg;
	}

	public void setCheckOrg(String checkOrg) {
		this.checkOrg = checkOrg;
	}

	public String getCheckSituation() {
		return this.checkSituation;
	}

	public void setCheckSituation(String checkSituation) {
		this.checkSituation = checkSituation;
	}

	public Long getListedSupervisorySituation() {
		return this.listedSupervisorySituation;
	}

	public void setListedSupervisorySituation(Long listedSupervisorySituation) {
		this.listedSupervisorySituation = listedSupervisorySituation;
	}

	public Date getListedSupervisoryDate() {
		return this.listedSupervisoryDate;
	}

	public void setListedSupervisoryDate(Date listedSupervisoryDate) {
		this.listedSupervisoryDate = listedSupervisoryDate;
	}

	public String getListedSupervisoryLevel() {
		return this.listedSupervisoryLevel;
	}

	public void setListedSupervisoryLevel(String listedSupervisoryLevel) {
		this.listedSupervisoryLevel = listedSupervisoryLevel;
	}

	public String getListedSupervisoryOrg() {
		return this.listedSupervisoryOrg;
	}

	public void setListedSupervisoryOrg(String listedSupervisoryOrg) {
		this.listedSupervisoryOrg = listedSupervisoryOrg;
	}

	public String getListedSupervisoryNumber() {
		return this.listedSupervisoryNumber;
	}

	public void setListedSupervisoryNumber(String listedSupervisoryNumber) {
		this.listedSupervisoryNumber = listedSupervisoryNumber;
	}

	public String getHiddenDangerReport() {
		return this.hiddenDangerReport;
	}

	public void setHiddenDangerReport(String hiddenDangerReport) {
		this.hiddenDangerReport = hiddenDangerReport;
	}

	public Long getImprovementTargetSitu() {
		return this.improvementTargetSitu;
	}

	public void setImprovementTargetSitu(Long improvementTargetSitu) {
		this.improvementTargetSitu = improvementTargetSitu;
	}

	public String getImprovementDate() {
		return this.improvementDate;
	}

	public void setImprovementDate(String improvementDate) {
		this.improvementDate = improvementDate;
	}

	public Long getImprovementResSitu() {
		return this.improvementResSitu;
	}

	public void setImprovementResSitu(Long improvementResSitu) {
		this.improvementResSitu = improvementResSitu;
	}

	public String getImprovementRespDate() {
		return this.improvementRespDate;
	}

	public void setImprovementRespDate(String improvementRespDate) {
		this.improvementRespDate = improvementRespDate;
	}

	public Long getControlMeasuresSitu() {
		return this.controlMeasuresSitu;
	}

	public void setControlMeasuresSitu(Long controlMeasuresSitu) {
		this.controlMeasuresSitu = controlMeasuresSitu;
	}

	public String getControlMeasuresDate() {
		return this.controlMeasuresDate;
	}

	public void setControlMeasuresDate(String controlMeasuresDate) {
		this.controlMeasuresDate = controlMeasuresDate;
	}

	public Long getRectificationMoneySitu() {
		return this.rectificationMoneySitu;
	}

	public void setRectificationMoneySitu(Long rectificationMoneySitu) {
		this.rectificationMoneySitu = rectificationMoneySitu;
	}

	public String getRectificationMoneyDate() {
		return this.rectificationMoneyDate;
	}

	public void setRectificationMoneyDate(String rectificationMoneyDate) {
		this.rectificationMoneyDate = rectificationMoneyDate;
	}

	public String getRectificationTimeSitu() {
		return this.rectificationTimeSitu;
	}

	public void setRectificationTimeSitu(String rectificationTimeSitu) {
		this.rectificationTimeSitu = rectificationTimeSitu;
	}

	public String getRectificationTimeDate() {
		return this.rectificationTimeDate;
	}

	public void setRectificationTimeDate(String rectificationTimeDate) {
		this.rectificationTimeDate = rectificationTimeDate;
	}

	public Long getContingencyPlanSitu() {
		return this.contingencyPlanSitu;
	}

	public void setContingencyPlanSitu(Long contingencyPlanSitu) {
		this.contingencyPlanSitu = contingencyPlanSitu;
	}

	public String getContingencyPlanDate() {
		return this.contingencyPlanDate;
	}

	public void setContingencyPlanDate(String contingencyPlanDate) {
		this.contingencyPlanDate = contingencyPlanDate;
	}

	public String getControlPlanSitu() {
		return this.controlPlanSitu;
	}

	public void setControlPlanSitu(String controlPlanSitu) {
		this.controlPlanSitu = controlPlanSitu;
	}

	public String getControlPlanDate() {
		return this.controlPlanDate;
	}

	public void setControlPlanDate(String controlPlanDate) {
		this.controlPlanDate = controlPlanDate;
	}

	public Long getRectificationMoney() {
		return this.rectificationMoney;
	}

	public void setRectificationMoney(Long rectificationMoney) {
		this.rectificationMoney = rectificationMoney;
	}

	public Date getRectificationFinishTime() {
		return this.rectificationFinishTime;
	}

	public void setRectificationFinishTime(Date rectificationFinishTime) {
		this.rectificationFinishTime = rectificationFinishTime;
	}

	public Date getCancelDate() {
		return this.cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getInspector() {
		return this.inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public String getOutid() {
		return this.outid;
	}

	public void setOutid(String outid) {
		this.outid = outid;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getRepairUnit() {
		return this.repairUnit;
	}

	public void setRepairUnit(String repairUnit) {
		this.repairUnit = repairUnit;
	}

	public Long getVersionId() {
		return this.versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	public Long getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Long isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the responsibleResponsible
	 */
	public String getResponsibleResponsible() {
		return responsibleResponsible;
	}

	/**
	 * @param responsibleResponsible the responsibleResponsible to set
	 */
	public void setResponsibleResponsible(String responsibleResponsible) {
		this.responsibleResponsible = responsibleResponsible;
	}

	/**
	 * @return the rectificationSituation
	 */
	public String getRectificationSituation() {
		return rectificationSituation;
	}

	/**
	 * @param rectificationSituation the rectificationSituation to set
	 */
	public void setRectificationSituation(String rectificationSituation) {
		this.rectificationSituation = rectificationSituation;
	}

	/**
	 * @return the exFlag
	 */
	public String getExFlag() {
		return exFlag;
	}

	/**
	 * @param exFlag the exFlag to set
	 */
	public void setExFlag(String exFlag) {
		this.exFlag = exFlag;
	}

}