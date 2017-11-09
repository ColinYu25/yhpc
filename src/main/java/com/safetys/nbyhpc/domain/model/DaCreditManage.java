package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaCreditManage
 * @date 20l1-10-27
 * @author zouxw
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaCreditManage extends DaBaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6611550047965861627L;
	/**
	 * 信用管理
	 */
	private DaCompany daCompany;//企业
	private String grade;//信用等级
	private String promise;//承诺书
	private String publicity;//公示
	private String yearReport;//年度报告
	
	public DaCompany getDaCompany() {
		return daCompany;
	}
	public void setDaCompany(DaCompany daCompany) {
		this.daCompany = daCompany;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getPromise() {
		return promise;
	}
	public void setPromise(String promise) {
		this.promise = promise;
	}
	public String getPublicity() {
		return publicity;
	}
	public void setPublicity(String publicity) {
		this.publicity = publicity;
	}
	public String getYearReport() {
		return yearReport;
	}
	public void setYearReport(String yearReport) {
		this.yearReport = yearReport;
	}

}
