package com.safetys.nbyhpc.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class DaAcceptance extends DaBaseModel{

	/**验收实体
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileFileName;
	
	private String filePath;
	
	private Date fileCreateTime;
	
	private Long userId;

	private Set<DaRollcallCompany> daRollcallCompanys = new HashSet<DaRollcallCompany>();
	
	public Date getFileCreateTime() {
		return fileCreateTime;
	}

	public void setFileCreateTime(Date fileCreateTime) {
		this.fileCreateTime = fileCreateTime;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Set<DaRollcallCompany> getDaRollcallCompanys() {
		return daRollcallCompanys;
	}

	public void setDaRollcallCompanys(Set<DaRollcallCompany> daRollcallCompanys) {
		this.daRollcallCompanys = daRollcallCompanys;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
