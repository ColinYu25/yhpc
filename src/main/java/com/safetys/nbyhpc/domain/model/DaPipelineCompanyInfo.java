package com.safetys.nbyhpc.domain.model;

// Generated 2011-1-8 11:07:39 by Hibernate Tools 3.2.0.b9

import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * DaPipelineCompanyinfo generated by hbm2java
 */
public class DaPipelineCompanyInfo extends DaBaseModel {

	private DaCompany cqCompany;

	private DaCompany company;

	private String contacter;//联系人
	
	private Long areaCode;//所在区域

	private int useType;

	private String masterDept;//上级主管单位

	private Long userId;
	
	private Long pipeId;
	
	private int type;//0：油气管道 1：燃气管道

	private Set daPipelineInfos = new HashSet(0);

	public DaPipelineCompanyInfo() {
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	public DaCompany getCqCompany() {
		return cqCompany;
	}

	public void setCqCompany(DaCompany cqCompany) {
		this.cqCompany = cqCompany;
	}

	public Set getDaPipelineInfos() {
		return daPipelineInfos;
	}

	public void setDaPipelineInfos(Set daPipelineInfos) {
		this.daPipelineInfos = daPipelineInfos;
	}

	public String getMasterDept() {
		return masterDept;
	}

	public void setMasterDept(String masterDept) {
		this.masterDept = masterDept;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getUseType() {
		return useType;
	}

	public void setUseType(int useType) {
		this.useType = useType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Long areaCode) {
		this.areaCode = areaCode;
	}

	public Long getPipeId() {
		return pipeId;
	}

	public void setPipeId(Long pipeId) {
		this.pipeId = pipeId;
	}


}
