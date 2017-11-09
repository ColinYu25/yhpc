package com.safetys.nbyhpc.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * 延期挂牌
 */
public class DaPipeRollcallDefer extends DaBaseModel{

	
	private static final long serialVersionUID = 7137552059262719447L;

	private DaPipeRollcallCompany daPipeRollcallCompany;
	
	private String deferReason;
	
	private String time;
	
	private Date deferTime;
	
	private boolean isDone;
	
	private Integer isAgree;
	
	private Integer type;
	
	private Long userId;
	
	private String remark;
	
	private String signatory;
	
	private String govment;
	
	private Set<DaPipeRollcallDeferFile> daPipeRollcallDeferFiles = new HashSet<DaPipeRollcallDeferFile>(0);
	
	private Date rollcallUnitTime;
	
	private String sendoffMan;
	
	private String signinMan;
	
	private Date signinTime;
	
	private Date companyApplyTime;
	
	private String presideMan;
	
	public Date getCompanyApplyTime() {
		return companyApplyTime;
	}

	public void setCompanyApplyTime(Date companyApplyTime) {
		this.companyApplyTime = companyApplyTime;
	}

	public String getPresideMan() {
		return presideMan;
	}

	public void setPresideMan(String presideMan) {
		this.presideMan = presideMan;
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

	public DaPipeRollcallDefer() {
	}
	
	public DaPipeRollcallDefer(long id) {
		super(id);
	}

	public String getDeferReason() {
		return deferReason;
	}

	public void setDeferReason(String deferReason) {
		this.deferReason = deferReason;
	}

	public Date getDeferTime() {
		return deferTime;
	}

	public void setDeferTime(Date deferTime) {
		this.deferTime = deferTime;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public DaPipeRollcallCompany getDaPipeRollcallCompany() {
		return daPipeRollcallCompany;
	}

	public void setDaPipeRollcallCompany(DaPipeRollcallCompany daPipeRollcallCompany) {
		this.daPipeRollcallCompany = daPipeRollcallCompany;
	}

	public Set<DaPipeRollcallDeferFile> getDaPipeRollcallDeferFiles() {
		return daPipeRollcallDeferFiles;
	}

	public void setDaPipeRollcallDeferFiles(
			Set<DaPipeRollcallDeferFile> daPipeRollcallDeferFiles) {
		this.daPipeRollcallDeferFiles = daPipeRollcallDeferFiles;
	}

	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSignatory() {
		return signatory;
	}

	public void setSignatory(String signatory) {
		this.signatory = signatory;
	}

	public String getGovment() {
		return govment;
	}

	public void setGovment(String govment) {
		this.govment = govment;
	}

}
