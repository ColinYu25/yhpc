package com.safetys.nbyhpc.domain.model;

import java.util.Date;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * @(#) DaSecurityManage
 * @date 20l1-10-25
 * @author zouxw
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DaSecurityManage extends DaBaseModel {

	private static final long serialVersionUID = 2146273207343412637L;
	/**
	 * 安管机构和人员
	 */
	private DaCompany daCompany;//企业
	private String smSet;//安管机构设置 
	private Integer fulltimeStaff;//专职人员人数 
	private String mainPrincipal;//主要负责人姓名
	private String mpCertificate;//主要负责人证书号
//	private Date mpValidity;//主要负责人证书有效期
	private String mpValidity;//暂时将字段主要负责人证书有效期类型修改为String
	private String securityPrincipal;//安全负责人姓名
	private String spCertificate;//安全负责人证书号
//	private Date spValidity;//安全负责人证书有效期
	private String spValidity;//暂时将字段主要负责人证书有效期类型修改为String
	private Long userId;
	
	public DaCompany getDaCompany() {
		return daCompany;
	}
	public void setDaCompany(DaCompany daCompany) {
		this.daCompany = daCompany;
	}
	public String getSmSet() {
		return smSet;
	}
	public void setSmSet(String smSet) {
		this.smSet = smSet;
	}
	public Integer getFulltimeStaff() {
		return fulltimeStaff;
	}
	public void setFulltimeStaff(Integer fulltimeStaff) {
		this.fulltimeStaff = fulltimeStaff;
	}
	public String getMainPrincipal() {
		return mainPrincipal;
	}
	public void setMainPrincipal(String mainPrincipal) {
		this.mainPrincipal = mainPrincipal;
	}
	public String getMpCertificate() {
		return mpCertificate;
	}
	public void setMpCertificate(String mpCertificate) {
		this.mpCertificate = mpCertificate;
	}
	public String getSecurityPrincipal() {
		return securityPrincipal;
	}
	public void setSecurityPrincipal(String securityPrincipal) {
		this.securityPrincipal = securityPrincipal;
	}
	public String getSpCertificate() {
		return spCertificate;
	}
	public void setSpCertificate(String spCertificate) {
		this.spCertificate = spCertificate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getMpValidity() {
		return mpValidity;
	}
	public void setMpValidity(String mpValidity) {
		this.mpValidity = mpValidity;
	}
	public String getSpValidity() {
		return spValidity;
	}
	public void setSpValidity(String spValidity) {
		this.spValidity = spValidity;
	}

}
