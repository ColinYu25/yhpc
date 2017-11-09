package com.safetys.nbyhpc.domain.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaRollcallCompany.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaRollcallCompany extends DaBaseModel{

	/**
	 * 挂牌实体
	 */
	private static final long serialVersionUID = 3511338637281046599L;

	private DaDanger daDanger;

	private String level;

	private String govment;

	private String department;

	private Date completeTime;

	private Long userId;

	private Set<DaRollcallFile> daRollcallFiles = new HashSet<DaRollcallFile>(0);
	
	private Set<DaRollcallDefer> daRollcallDefers = new HashSet<DaRollcallDefer>(0);
	
	private boolean isNotice;
	
	private Date finishTime;
	
	private Date planTime;
	
	private String signatory;
	
	private String wordOne;
	
	private String wordYear;
	
	private String wordTwo;
	
	private Date rollcallUnitTime;
	
	private String sendoffMan;
	
	private String signinMan;
	
	private Date signinTime;
	
	private int nowYear;
	
	private int isSynchro;
	
	public String getSignatory() {
		return signatory;
	}

	public void setSignatory(String signatory) {
		this.signatory = signatory;
	}

	public String getWordOne() {
		return wordOne;
	}

	public void setWordOne(String wordOne) {
		this.wordOne = wordOne;
	}

	public String getWordTwo() {
		return wordTwo;
	}

	public void setWordTwo(String wordTwo) {
		this.wordTwo = wordTwo;
	}

	public String getWordYear() {
		return wordYear;
	}

	public void setWordYear(String wordYear) {
		this.wordYear = wordYear;
	}

	public DaRollcallCompany() {
	}
	
	public DaRollcallCompany(long id) {
		super(id);
	}


	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public DaDanger getDaDanger() {
		return daDanger;
	}

	public void setDaDanger(DaDanger daDanger) {
		this.daDanger = daDanger;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getGovment() {
		return govment;
	}

	public void setGovment(String govment) {
		this.govment = govment;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Set<DaRollcallFile> getDaRollcallFiles() {
		return daRollcallFiles;
	}

	public void setDaRollcallFiles(Set<DaRollcallFile> daRollcallFiles) {
		this.daRollcallFiles = daRollcallFiles;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}


	public boolean isNotice() {
		return isNotice;
	}

	public void setNotice(boolean isNotice) {
		this.isNotice = isNotice;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public Set<DaRollcallDefer> getDaRollcallDefers() {
		return daRollcallDefers;
	}

	public void setDaRollcallDefers(Set<DaRollcallDefer> daRollcallDefers) {
		this.daRollcallDefers = daRollcallDefers;
	}

	public Date getRollcallUnitTime() {
		return rollcallUnitTime;
	}

	public void setRollcallUnitTime(Date rollcallUnitTime) {
		this.rollcallUnitTime = rollcallUnitTime;
	}

	public String getSendoffMan() {
		return sendoffMan;
	}

	public void setSendoffMan(String sendoffMan) {
		this.sendoffMan = sendoffMan;
	}

	public String getSigninMan() {
		return signinMan;
	}

	public void setSigninMan(String signinMan) {
		this.signinMan = signinMan;
	}

	public Date getSigninTime() {
		return signinTime;
	}

	public void setSigninTime(Date signinTime) {
		this.signinTime = signinTime;
	}

	public int getNowYear() {
		return nowYear;
	}

	public void setNowYear(int nowYear) {
		this.nowYear = nowYear;
	}

	public int getIsSynchro() {
		return isSynchro;
	}

	public void setIsSynchro(int isSynchro) {
		this.isSynchro = isSynchro;
	}
	
	
}
