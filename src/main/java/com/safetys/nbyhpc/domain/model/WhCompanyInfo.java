package com.safetys.nbyhpc.domain.model;

// Generated 2011-1-4 15:28:28 by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * WhCompanyInfo generated by hbm2java
 */
public class WhCompanyInfo extends DaBaseModel {

	private DaCompany company;

	private String frdb;

	private String frdbPhone;

	private Boolean haschangeFrdb;

	private String fgfzr;

	private String fgfzrPhone;

	private Boolean haschangeFgfzr;

	private Boolean hasAqbm;

	private String aqbm;

	private Integer zzagy;

	private String aqbmFzr;

	private Boolean haschangeAqbm;

	private int works;

	private Integer lostWorks;

	private Integer newWorks;

	private double annualProduction;

	private double annualInput;

	private String licence;

	private Date enddate;

	private String permitScope;

	private Boolean hasNewproject;

	private Boolean hasTestproject;

	private Boolean hasExceedTestproject;

	private Boolean hasVoidApgb;

	private Boolean hasApbgls;

	private String aqpjjg;

	private String grade;//达标级别

	private Date levelEnddate;

	private Boolean isAqbzhRunok;

	private double standardScore;

	private Boolean hasTroubleRule;

	private Boolean hasTroubleWork;

	private Boolean hasYhpctz;

	private Boolean hasYfbsyh;

	private Boolean hasZdyhzg;

	private Boolean hasLszgyj;

	private Boolean hasYjjyyaPs;

	private Boolean isYjjyyaBa;

	private Date yjjyyaBaDate;

	private Integer yjjyyaBaDept;//省、市、县区

	private Boolean hasYjyl;

	private Integer ylrs;

	private Boolean isZdwxy;

	private int zdwxyNum;

	private Boolean hasNewzdwxy;

	private Boolean hasReportZdwxy;

	private Boolean frdbHasCert;

	private Boolean fgfzrHasCert;

	private Boolean aqbmfzrHasCert;

	private double tzyzryPercent;

	private String workinfo;

	private String workinfoProblem;

	private String workAdvise;

	private Integer state;

	private Date reportdate;

	private Integer year;

	private String aqbmFzrPhone;

	private Long userId;
	
	private int isSynchro;
	
	private Set<WhAccident> accidents = new HashSet<WhAccident>();
	
	public WhCompanyInfo() {
	}

	public double getAnnualInput() {
		return annualInput;
	}

	public void setAnnualInput(double annualInput) {
		this.annualInput = annualInput;
	}

	public double getAnnualProduction() {
		return annualProduction;
	}

	public void setAnnualProduction(double annualProduction) {
		this.annualProduction = annualProduction;
	}

	public String getAqbm() {
		return aqbm;
	}

	public void setAqbm(String aqbm) {
		this.aqbm = aqbm;
	}

	public String getAqbmFzr() {
		return aqbmFzr;
	}

	public void setAqbmFzr(String aqbmFzr) {
		this.aqbmFzr = aqbmFzr;
	}

	public Boolean getAqbmfzrHasCert() {
		return aqbmfzrHasCert;
	}

	public void setAqbmfzrHasCert(Boolean aqbmfzrHasCert) {
		this.aqbmfzrHasCert = aqbmfzrHasCert;
	}

	public String getAqbmFzrPhone() {
		return aqbmFzrPhone;
	}

	public void setAqbmFzrPhone(String aqbmFzrPhone) {
		this.aqbmFzrPhone = aqbmFzrPhone;
	}

	public String getAqpjjg() {
		return aqpjjg;
	}

	public void setAqpjjg(String aqpjjg) {
		this.aqpjjg = aqpjjg;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getFgfzr() {
		return fgfzr;
	}

	public void setFgfzr(String fgfzr) {
		this.fgfzr = fgfzr;
	}

	public Boolean getFgfzrHasCert() {
		return fgfzrHasCert;
	}

	public void setFgfzrHasCert(Boolean fgfzrHasCert) {
		this.fgfzrHasCert = fgfzrHasCert;
	}

	public String getFgfzrPhone() {
		return fgfzrPhone;
	}

	public void setFgfzrPhone(String fgfzrPhone) {
		this.fgfzrPhone = fgfzrPhone;
	}

	public String getFrdb() {
		return frdb;
	}

	public void setFrdb(String frdb) {
		this.frdb = frdb;
	}

	public Boolean getFrdbHasCert() {
		return frdbHasCert;
	}

	public void setFrdbHasCert(Boolean frdbHasCert) {
		this.frdbHasCert = frdbHasCert;
	}

	public String getFrdbPhone() {
		return frdbPhone;
	}

	public void setFrdbPhone(String frdbPhone) {
		this.frdbPhone = frdbPhone;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Boolean getHasApbgls() {
		return hasApbgls;
	}

	public void setHasApbgls(Boolean hasApbgls) {
		this.hasApbgls = hasApbgls;
	}

	public Boolean getHasAqbm() {
		return hasAqbm;
	}

	public void setHasAqbm(Boolean hasAqbm) {
		this.hasAqbm = hasAqbm;
	}

	public Boolean getHaschangeAqbm() {
		return haschangeAqbm;
	}

	public void setHaschangeAqbm(Boolean haschangeAqbm) {
		this.haschangeAqbm = haschangeAqbm;
	}

	public Boolean getHaschangeFgfzr() {
		return haschangeFgfzr;
	}

	public void setHaschangeFgfzr(Boolean haschangeFgfzr) {
		this.haschangeFgfzr = haschangeFgfzr;
	}

	public Boolean getHaschangeFrdb() {
		return haschangeFrdb;
	}

	public void setHaschangeFrdb(Boolean haschangeFrdb) {
		this.haschangeFrdb = haschangeFrdb;
	}

	public Boolean getHasExceedTestproject() {
		return hasExceedTestproject;
	}

	public void setHasExceedTestproject(Boolean hasExceedTestproject) {
		this.hasExceedTestproject = hasExceedTestproject;
	}

	public Boolean getHasLszgyj() {
		return hasLszgyj;
	}

	public void setHasLszgyj(Boolean hasLszgyj) {
		this.hasLszgyj = hasLszgyj;
	}

	public Boolean getHasNewproject() {
		return hasNewproject;
	}

	public void setHasNewproject(Boolean hasNewproject) {
		this.hasNewproject = hasNewproject;
	}

	public Boolean getHasNewzdwxy() {
		return hasNewzdwxy;
	}

	public void setHasNewzdwxy(Boolean hasNewzdwxy) {
		this.hasNewzdwxy = hasNewzdwxy;
	}

	public Boolean getHasReportZdwxy() {
		return hasReportZdwxy;
	}

	public void setHasReportZdwxy(Boolean hasReportZdwxy) {
		this.hasReportZdwxy = hasReportZdwxy;
	}

	public Boolean getHasTestproject() {
		return hasTestproject;
	}

	public void setHasTestproject(Boolean hasTestproject) {
		this.hasTestproject = hasTestproject;
	}

	public Boolean getHasTroubleRule() {
		return hasTroubleRule;
	}

	public void setHasTroubleRule(Boolean hasTroubleRule) {
		this.hasTroubleRule = hasTroubleRule;
	}

	public Boolean getHasTroubleWork() {
		return hasTroubleWork;
	}

	public void setHasTroubleWork(Boolean hasTroubleWork) {
		this.hasTroubleWork = hasTroubleWork;
	}

	public Boolean getHasVoidApgb() {
		return hasVoidApgb;
	}

	public void setHasVoidApgb(Boolean hasVoidApgb) {
		this.hasVoidApgb = hasVoidApgb;
	}

	public Boolean getHasYfbsyh() {
		return hasYfbsyh;
	}

	public void setHasYfbsyh(Boolean hasYfbsyh) {
		this.hasYfbsyh = hasYfbsyh;
	}

	public Boolean getHasYhpctz() {
		return hasYhpctz;
	}

	public void setHasYhpctz(Boolean hasYhpctz) {
		this.hasYhpctz = hasYhpctz;
	}

	public Boolean getHasYjjyyaPs() {
		return hasYjjyyaPs;
	}

	public void setHasYjjyyaPs(Boolean hasYjjyyaPs) {
		this.hasYjjyyaPs = hasYjjyyaPs;
	}

	public Boolean getHasYjyl() {
		return hasYjyl;
	}

	public void setHasYjyl(Boolean hasYjyl) {
		this.hasYjyl = hasYjyl;
	}

	public Boolean getHasZdyhzg() {
		return hasZdyhzg;
	}

	public void setHasZdyhzg(Boolean hasZdyhzg) {
		this.hasZdyhzg = hasZdyhzg;
	}

	public Boolean getIsAqbzhRunok() {
		return isAqbzhRunok;
	}

	public void setIsAqbzhRunok(Boolean isAqbzhRunok) {
		this.isAqbzhRunok = isAqbzhRunok;
	}

	public Boolean getIsYjjyyaBa() {
		return isYjjyyaBa;
	}

	public void setIsYjjyyaBa(Boolean isYjjyyaBa) {
		this.isYjjyyaBa = isYjjyyaBa;
	}

	public Boolean getIsZdwxy() {
		return isZdwxy;
	}

	public void setIsZdwxy(Boolean isZdwxy) {
		this.isZdwxy = isZdwxy;
	}

	public Date getLevelEnddate() {
		return levelEnddate;
	}

	public void setLevelEnddate(Date levelEnddate) {
		this.levelEnddate = levelEnddate;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public Integer getLostWorks() {
		return lostWorks;
	}

	public void setLostWorks(Integer lostWorks) {
		this.lostWorks = lostWorks;
	}

	public Integer getNewWorks() {
		return newWorks;
	}

	public void setNewWorks(Integer newWorks) {
		this.newWorks = newWorks;
	}

	public String getPermitScope() {
		return permitScope;
	}

	public void setPermitScope(String permitScope) {
		this.permitScope = permitScope;
	}

	public Date getReportdate() {
		return reportdate;
	}

	public void setReportdate(Date reportdate) {
		this.reportdate = reportdate;
	}

	public double getStandardScore() {
		return standardScore;
	}

	public void setStandardScore(double standardScore) {
		this.standardScore = standardScore;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public double getTzyzryPercent() {
		return tzyzryPercent;
	}

	public void setTzyzryPercent(double tzyzryPercent) {
		this.tzyzryPercent = tzyzryPercent;
	}

	public String getWorkAdvise() {
		return workAdvise;
	}

	public void setWorkAdvise(String workAdvise) {
		this.workAdvise = workAdvise;
	}

	public String getWorkinfo() {
		return workinfo;
	}

	public void setWorkinfo(String workinfo) {
		this.workinfo = workinfo;
	}

	public String getWorkinfoProblem() {
		return workinfoProblem;
	}

	public void setWorkinfoProblem(String workinfoProblem) {
		this.workinfoProblem = workinfoProblem;
	}

	public int getWorks() {
		return works;
	}

	public void setWorks(int works) {
		this.works = works;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getYjjyyaBaDate() {
		return yjjyyaBaDate;
	}

	public void setYjjyyaBaDate(Date yjjyyaBaDate) {
		this.yjjyyaBaDate = yjjyyaBaDate;
	}

	public Integer getYjjyyaBaDept() {
		return yjjyyaBaDept;
	}

	public void setYjjyyaBaDept(Integer yjjyyaBaDept) {
		this.yjjyyaBaDept = yjjyyaBaDept;
	}

	public Integer getYlrs() {
		return ylrs;
	}

	public void setYlrs(Integer ylrs) {
		this.ylrs = ylrs;
	}

	public int getZdwxyNum() {
		return zdwxyNum;
	}

	public void setZdwxyNum(int zdwxyNum) {
		this.zdwxyNum = zdwxyNum;
	}

	public Integer getZzagy() {
		return zzagy;
	}

	public void setZzagy(Integer zzagy) {
		this.zzagy = zzagy;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Set<WhAccident> getAccidents() {
		return accidents;
	}

	public void setAccidents(Set<WhAccident> accidents) {
		this.accidents = accidents;
	}

	public int getIsSynchro() {
		return isSynchro;
	}

	public void setIsSynchro(int isSynchro) {
		this.isSynchro = isSynchro;
	}

	

}
