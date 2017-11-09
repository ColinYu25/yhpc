package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

// Generated 2012-11-6 15:59:30 by Hibernate Tools 3.2.0.b9

/**
 * TCoreCompany generated by hbm2java
 */
public class TCoreCompany extends DaBaseModel implements java.io.Serializable {
	/**
	 * 企业基础信息库
	 */
	private static final long serialVersionUID = -2335778146908423694L;
	private String companyName;
	private String regAddress;
	private Long firstArea;
	private Long secondArea;
	private Long thirdArea;
	private Long fouthArea;
	private Long fifthArea;
	private String zipCode;
	private String naEcoLevel1;
	private String naEcoLevel2;
	private String managementLevel1;
	private String managementLevel2;
	private String economicType1;
	private String economicType2;
	private Date establishmentDay;
	private String businessRegNum;
	private String orgCode;
	private Double regCapital;
	private Double yearSales;
	private String legalPerson;
	private Double floorArea;
	private String principalPerson;
	private String principalMobile;
	private String principalTelephone;
	private String fax;
	private boolean enterprise;
	private boolean focusFireUnits;
	private boolean focusTownUnits;
	private Integer employeeNum;
	private Integer insuredNum;
	private String businessScope;
	private Integer haveSecurityOrg;
	private Integer securityPerson;
	private Integer specialBoiler;
	private Integer specialPressureVessel;
	private Integer specialLift;
	private Integer specialLiftingMachine;
	private Integer specialMotorVehicle;
	private Date anpRecordsDate;
	private Date anpValidityEnd;
	private String majorGrade;
	private Date majorRecordsDate;
	private Date emergencyRecordsDate;
	private Integer emergencyDrill;
	private Date yhQuarterReportsDate;
	private Integer yhNoManageNum;
	private Integer yhHiddenDangerLicenses;
	private Integer checkJusticeNum;
	private Integer checkUsualNum;
	private Integer checkDocumentNum;
	private String checkFindTrouble;
	private Integer accNonFireDeathNum;
	private Integer accNonFireDeaths;
	private String accNonFireCasualties;
	private Double accNonFireDeathLosses;
	private Integer accNonFireInjuryNum;
	private Double accNonLastAccidentRate;
	private Integer accNonFireInjuries;
	private Double accNonFireInjurieLosses;
	private Integer accFireNum;
	private Integer accFireDeaths;
	private Integer accFireInjuries;
	private Double accFireLosses;
	private String creditGrade;
	private Date creditPromiseDate;
	private Date creditPerformDate;
	private Long userId;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
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

	public Long getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(Long thirdArea) {
		this.thirdArea = thirdArea;
	}

	public Long getFouthArea() {
		return fouthArea;
	}

	public void setFouthArea(Long fouthArea) {
		this.fouthArea = fouthArea;
	}

	public Long getFifthArea() {
		return fifthArea;
	}

	public void setFifthArea(Long fifthArea) {
		this.fifthArea = fifthArea;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getNaEcoLevel1() {
		return naEcoLevel1;
	}

	public void setNaEcoLevel1(String naEcoLevel1) {
		this.naEcoLevel1 = naEcoLevel1;
	}

	public String getNaEcoLevel2() {
		return naEcoLevel2;
	}

	public void setNaEcoLevel2(String naEcoLevel2) {
		this.naEcoLevel2 = naEcoLevel2;
	}

	public String getManagementLevel1() {
		return managementLevel1;
	}

	public void setManagementLevel1(String managementLevel1) {
		this.managementLevel1 = managementLevel1;
	}

	public String getManagementLevel2() {
		return managementLevel2;
	}

	public void setManagementLevel2(String managementLevel2) {
		this.managementLevel2 = managementLevel2;
	}

	public String getEconomicType1() {
		return economicType1;
	}

	public void setEconomicType1(String economicType1) {
		this.economicType1 = economicType1;
	}

	public String getEconomicType2() {
		return economicType2;
	}

	public void setEconomicType2(String economicType2) {
		this.economicType2 = economicType2;
	}

	public Date getEstablishmentDay() {
		return establishmentDay;
	}

	public void setEstablishmentDay(Date establishmentDay) {
		this.establishmentDay = establishmentDay;
	}

	public String getBusinessRegNum() {
		return businessRegNum;
	}

	public void setBusinessRegNum(String businessRegNum) {
		this.businessRegNum = businessRegNum;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Double getRegCapital() {
		return regCapital;
	}

	public void setRegCapital(Double regCapital) {
		this.regCapital = regCapital;
	}

	public Double getYearSales() {
		return yearSales;
	}

	public void setYearSales(Double yearSales) {
		this.yearSales = yearSales;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public Double getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(Double floorArea) {
		this.floorArea = floorArea;
	}

	public String getPrincipalPerson() {
		return principalPerson;
	}

	public void setPrincipalPerson(String principalPerson) {
		this.principalPerson = principalPerson;
	}

	public String getPrincipalMobile() {
		return principalMobile;
	}

	public void setPrincipalMobile(String principalMobile) {
		this.principalMobile = principalMobile;
	}

	public String getPrincipalTelephone() {
		return principalTelephone;
	}

	public void setPrincipalTelephone(String principalTelephone) {
		this.principalTelephone = principalTelephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public boolean isEnterprise() {
		return enterprise;
	}

	public void setEnterprise(boolean enterprise) {
		this.enterprise = enterprise;
	}

	public boolean isFocusFireUnits() {
		return focusFireUnits;
	}

	public void setFocusFireUnits(boolean focusFireUnits) {
		this.focusFireUnits = focusFireUnits;
	}

	public boolean isFocusTownUnits() {
		return focusTownUnits;
	}

	public void setFocusTownUnits(boolean focusTownUnits) {
		this.focusTownUnits = focusTownUnits;
	}

	public Integer getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(Integer employeeNum) {
		this.employeeNum = employeeNum;
	}

	public Integer getInsuredNum() {
		return insuredNum;
	}

	public void setInsuredNum(Integer insuredNum) {
		this.insuredNum = insuredNum;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public Integer getHaveSecurityOrg() {
		return haveSecurityOrg;
	}

	public void setHaveSecurityOrg(Integer haveSecurityOrg) {
		this.haveSecurityOrg = haveSecurityOrg;
	}

	public Integer getSecurityPerson() {
		return securityPerson;
	}

	public void setSecurityPerson(Integer securityPerson) {
		this.securityPerson = securityPerson;
	}

	public Integer getSpecialBoiler() {
		return specialBoiler;
	}

	public void setSpecialBoiler(Integer specialBoiler) {
		this.specialBoiler = specialBoiler;
	}

	public Integer getSpecialPressureVessel() {
		return specialPressureVessel;
	}

	public void setSpecialPressureVessel(Integer specialPressureVessel) {
		this.specialPressureVessel = specialPressureVessel;
	}

	public Integer getSpecialLift() {
		return specialLift;
	}

	public void setSpecialLift(Integer specialLift) {
		this.specialLift = specialLift;
	}

	public Integer getSpecialLiftingMachine() {
		return specialLiftingMachine;
	}

	public void setSpecialLiftingMachine(Integer specialLiftingMachine) {
		this.specialLiftingMachine = specialLiftingMachine;
	}

	public Integer getSpecialMotorVehicle() {
		return specialMotorVehicle;
	}

	public void setSpecialMotorVehicle(Integer specialMotorVehicle) {
		this.specialMotorVehicle = specialMotorVehicle;
	}

	public Date getAnpRecordsDate() {
		return anpRecordsDate;
	}

	public void setAnpRecordsDate(Date anpRecordsDate) {
		this.anpRecordsDate = anpRecordsDate;
	}

	public Date getAnpValidityEnd() {
		return anpValidityEnd;
	}

	public void setAnpValidityEnd(Date anpValidityEnd) {
		this.anpValidityEnd = anpValidityEnd;
	}

	public String getMajorGrade() {
		return majorGrade;
	}

	public void setMajorGrade(String majorGrade) {
		this.majorGrade = majorGrade;
	}

	public Date getMajorRecordsDate() {
		return majorRecordsDate;
	}

	public void setMajorRecordsDate(Date majorRecordsDate) {
		this.majorRecordsDate = majorRecordsDate;
	}

	public Date getEmergencyRecordsDate() {
		return emergencyRecordsDate;
	}

	public void setEmergencyRecordsDate(Date emergencyRecordsDate) {
		this.emergencyRecordsDate = emergencyRecordsDate;
	}

	public Integer getEmergencyDrill() {
		return emergencyDrill;
	}

	public void setEmergencyDrill(Integer emergencyDrill) {
		this.emergencyDrill = emergencyDrill;
	}

	public Date getYhQuarterReportsDate() {
		return yhQuarterReportsDate;
	}

	public void setYhQuarterReportsDate(Date yhQuarterReportsDate) {
		this.yhQuarterReportsDate = yhQuarterReportsDate;
	}

	public Integer getYhNoManageNum() {
		return yhNoManageNum;
	}

	public void setYhNoManageNum(Integer yhNoManageNum) {
		this.yhNoManageNum = yhNoManageNum;
	}

	public Integer getYhHiddenDangerLicenses() {
		return yhHiddenDangerLicenses;
	}

	public void setYhHiddenDangerLicenses(Integer yhHiddenDangerLicenses) {
		this.yhHiddenDangerLicenses = yhHiddenDangerLicenses;
	}

	public Integer getCheckJusticeNum() {
		return checkJusticeNum;
	}

	public void setCheckJusticeNum(Integer checkJusticeNum) {
		this.checkJusticeNum = checkJusticeNum;
	}

	public Integer getCheckUsualNum() {
		return checkUsualNum;
	}

	public void setCheckUsualNum(Integer checkUsualNum) {
		this.checkUsualNum = checkUsualNum;
	}

	public Integer getCheckDocumentNum() {
		return checkDocumentNum;
	}

	public void setCheckDocumentNum(Integer checkDocumentNum) {
		this.checkDocumentNum = checkDocumentNum;
	}

	public String getCheckFindTrouble() {
		return checkFindTrouble;
	}

	public void setCheckFindTrouble(String checkFindTrouble) {
		this.checkFindTrouble = checkFindTrouble;
	}

	public Integer getAccNonFireDeathNum() {
		return accNonFireDeathNum;
	}

	public void setAccNonFireDeathNum(Integer accNonFireDeathNum) {
		this.accNonFireDeathNum = accNonFireDeathNum;
	}

	public Integer getAccNonFireDeaths() {
		return accNonFireDeaths;
	}

	public void setAccNonFireDeaths(Integer accNonFireDeaths) {
		this.accNonFireDeaths = accNonFireDeaths;
	}

	public String getAccNonFireCasualties() {
		return accNonFireCasualties;
	}

	public void setAccNonFireCasualties(String accNonFireCasualties) {
		this.accNonFireCasualties = accNonFireCasualties;
	}

	public Double getAccNonFireDeathLosses() {
		return accNonFireDeathLosses;
	}

	public void setAccNonFireDeathLosses(Double accNonFireDeathLosses) {
		this.accNonFireDeathLosses = accNonFireDeathLosses;
	}

	public Integer getAccNonFireInjuryNum() {
		return accNonFireInjuryNum;
	}

	public void setAccNonFireInjuryNum(Integer accNonFireInjuryNum) {
		this.accNonFireInjuryNum = accNonFireInjuryNum;
	}

	public Double getAccNonLastAccidentRate() {
		return accNonLastAccidentRate;
	}

	public void setAccNonLastAccidentRate(Double accNonLastAccidentRate) {
		this.accNonLastAccidentRate = accNonLastAccidentRate;
	}

	public Integer getAccNonFireInjuries() {
		return accNonFireInjuries;
	}

	public void setAccNonFireInjuries(Integer accNonFireInjuries) {
		this.accNonFireInjuries = accNonFireInjuries;
	}

	public Double getAccNonFireInjurieLosses() {
		return accNonFireInjurieLosses;
	}

	public void setAccNonFireInjurieLosses(Double accNonFireInjurieLosses) {
		this.accNonFireInjurieLosses = accNonFireInjurieLosses;
	}

	public Integer getAccFireNum() {
		return accFireNum;
	}

	public void setAccFireNum(Integer accFireNum) {
		this.accFireNum = accFireNum;
	}

	public Integer getAccFireDeaths() {
		return accFireDeaths;
	}

	public void setAccFireDeaths(Integer accFireDeaths) {
		this.accFireDeaths = accFireDeaths;
	}

	public Integer getAccFireInjuries() {
		return accFireInjuries;
	}

	public void setAccFireInjuries(Integer accFireInjuries) {
		this.accFireInjuries = accFireInjuries;
	}

	public Double getAccFireLosses() {
		return accFireLosses;
	}

	public void setAccFireLosses(Double accFireLosses) {
		this.accFireLosses = accFireLosses;
	}

	public String getCreditGrade() {
		return creditGrade;
	}

	public void setCreditGrade(String creditGrade) {
		this.creditGrade = creditGrade;
	}

	public Date getCreditPromiseDate() {
		return creditPromiseDate;
	}

	public void setCreditPromiseDate(Date creditPromiseDate) {
		this.creditPromiseDate = creditPromiseDate;
	}

	public Date getCreditPerformDate() {
		return creditPerformDate;
	}

	public void setCreditPerformDate(Date creditPerformDate) {
		this.creditPerformDate = creditPerformDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
