package com.safetys.nbyhpc.domain.model;

import java.util.Date;

/**
 * VNormalDangerExId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VNormalDangerEx implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -508017611071745796L;
	// Fields
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
	private String rectificationResponsible;
	private String rectificationLimitDate;
	private Date rectificationFinishTime;
	private String rectificationType;
	private String rectificationSituation;
	private String rectificationMeasures;
	private String checkDate;
	private String checkOrg;
	private String checkSituation;
	private Date confirmTime;
	private String inspector;
	private String outid;
	private String longitude;
	private String latitude;
	private String repairUnit;
	private String checkMan;
	private Long versionId;
	private Long isDeleted;
	private Long isDanger;
	
	private String exFlag;

	// Constructors

	/** default constructor */
	public VNormalDangerEx() {
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

	public String getRectificationResponsible() {
		return this.rectificationResponsible;
	}

	public void setRectificationResponsible(String rectificationResponsible) {
		this.rectificationResponsible = rectificationResponsible;
	}

	public String getRectificationLimitDate() {
		return this.rectificationLimitDate;
	}

	public void setRectificationLimitDate(String rectificationLimitDate) {
		this.rectificationLimitDate = rectificationLimitDate;
	}

	public Date getRectificationFinishTime() {
		return this.rectificationFinishTime;
	}

	public void setRectificationFinishTime(Date rectificationFinishTime) {
		this.rectificationFinishTime = rectificationFinishTime;
	}

	

	public String getRectificationMeasures() {
		return this.rectificationMeasures;
	}

	public void setRectificationMeasures(String rectificationMeasures) {
		this.rectificationMeasures = rectificationMeasures;
	}

	public String getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(String checkDate) {
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

	public String getCheckMan() {
		return this.checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
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

	public Long getIsDanger() {
		return this.isDanger;
	}

	public void setIsDanger(Long isDanger) {
		this.isDanger = isDanger;
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
	 * @return the rectificationType
	 */
	public String getRectificationType() {
		return rectificationType;
	}

	/**
	 * @param rectificationType the rectificationType to set
	 */
	public void setRectificationType(String rectificationType) {
		this.rectificationType = rectificationType;
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