package com.safetys.nbyhpc.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaDept extends DaBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1511342441988057442L;

	private DaDept daDept;
	
	private DaTrouble daTrouble;

	private Long firstArea;

	private Long secondArea;

	private Long fifthArea;

	private Long fouthArea;

	private Long thirdArea;

	private String description;
	
	private String linkMan;

	private String linkTel;

	private String passMan;

	private Long userId;
	
	private String findDeptName;
	
	private Boolean submit;
	
	private String mostlyCompany;

	private String copyCompany;
	
	private Date submitTime;
	
	private Date receiptTime;
	
	private String findDeptTrade;
	
	private Long mostlyCompanyArea = 0L;
	
	private String mostlyCompanyAreaName;
	
	private Integer troubleCopyType; //抄告参数类型（1代表：抄告  2代表：下达  3代表：上报  4代表：回复  5代表：反馈）
	
	private Boolean back;
	
	private Boolean result;
	
	private String type;//0 所有 1 回复 2 已回复 3 未反馈 4 已反馈
	
	private Set<DaDept> daDeptes = new HashSet<DaDept>(0);
	

	public DaDept() {
	}

	public DaDept(Long id) {
		super(id);
	}

	public String getCopyCompany() {
		return copyCompany;
	}

	public void setCopyCompany(String copyCompany) {
		this.copyCompany = copyCompany;
	}

	public DaDept getDaDept() {
		return daDept;
	}

	public void setDaDept(DaDept daDept) {
		this.daDept = daDept;
	}

	public Set<DaDept> getDaDeptes() {
		return daDeptes;
	}

	public void setDaDeptes(Set<DaDept> daDeptes) {
		this.daDeptes = daDeptes;
	}

	public DaTrouble getDaTrouble() {
		return daTrouble;
	}

	public void setDaTrouble(DaTrouble daTrouble) {
		this.daTrouble = daTrouble;
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

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getMostlyCompany() {
		return mostlyCompany;
	}

	public void setMostlyCompany(String mostlyCompany) {
		this.mostlyCompany = mostlyCompany;
	}

	public String getPassMan() {
		return passMan;
	}

	public void setPassMan(String passMan) {
		this.passMan = passMan;
	}

	public Date getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	public Long getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(Long secondArea) {
		this.secondArea = secondArea;
	}

	public Boolean getSubmit() {
		return submit;
	}

	public void setSubmit(Boolean submit) {
		this.submit = submit;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
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

	public Long getMostlyCompanyArea() {
		return mostlyCompanyArea;
	}

	public void setMostlyCompanyArea(Long mostlyCompanyArea) {
		this.mostlyCompanyArea = mostlyCompanyArea;
	}

	public String getFindDeptName() {
		return findDeptName;
	}

	public void setFindDeptName(String findDeptName) {
		this.findDeptName = findDeptName;
	}

	public String getFindDeptTrade() {
		return findDeptTrade;
	}

	public void setFindDeptTrade(String findDeptTrade) {
		this.findDeptTrade = findDeptTrade;
	}

	public Integer getTroubleCopyType() {
		return troubleCopyType;
	}

	public void setTroubleCopyType(Integer troubleCopyType) {
		this.troubleCopyType = troubleCopyType;
	}

	public Boolean getBack() {
		return back;
	}

	public void setBack(Boolean back) {
		this.back = back;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMostlyCompanyAreaName() {
		return mostlyCompanyAreaName;
	}

	public void setMostlyCompanyAreaName(String mostlyCompanyAreaName) {
		this.mostlyCompanyAreaName = mostlyCompanyAreaName;
	}


}
