package com.safetys.nbyhpc.web.action;

import java.util.Date;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaSecurityManage;
import com.safetys.nbyhpc.facade.iface.SecurityManageFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) SecurityManageAction.java
 * @date 2011-10-26
 * @author zouxw
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class SecurityManageAction extends DaAppAction {

	private static final long serialVersionUID = 5042031112341440066L;
	private SecurityManageFacadeIface securityManageFacadeIface;
	private DaSecurityManage daSecurityManage;
	private Long id;
	private Long cid;
	private String smSet;//安管机构设置 
	private Integer fulltimeStaff;//专职人员人数 
	private String mainPrincipal;//主要负责人姓名
	private String mpCertificate;//主要负责人证书号
	private String mpValidity;//主要负责人证书有效期
	private String securityPrincipal;//安全负责人姓名
	private String spCertificate;//安全负责人证书号
	private String spValidity;//安全负责人证书有效期
	private Date createTime;
	
	/**
	 * 修改安管信息
	 * 
	 * @return
	 */
	public String updateSecurityManage() {
		try {
			if(null == daSecurityManage){
				daSecurityManage = new DaSecurityManage();
			}
			if(null != this.getId()){
				daSecurityManage.setSmSet(this.getSmSet());
				daSecurityManage.setId(this.getId());
				daSecurityManage.setFulltimeStaff(this.getFulltimeStaff());
				daSecurityManage.setMainPrincipal(this.getMainPrincipal());
				daSecurityManage.setMpCertificate(this.getMpCertificate());
				daSecurityManage.setMpValidity(this.getMpValidity());
				daSecurityManage.setSecurityPrincipal(this.getSecurityPrincipal());
				daSecurityManage.setSpCertificate(this.getSpCertificate());
				daSecurityManage.setSpValidity(this.getSpValidity());
				DaCompany daCompany = new DaCompany();
				daCompany.setId(this.getCid());
				daSecurityManage.setDaCompany(daCompany);
				daSecurityManage.setCreateTime(this.getCreateTime());
				securityManageFacadeIface.updateSecurityManage(daSecurityManage);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR; 
		} catch (Exception e2) {
			e2.printStackTrace();
			return ERROR;
		}
		return null;
	}
	
	public SecurityManageFacadeIface getSecurityManageFacadeIface() {
		return securityManageFacadeIface;
	}
	public void setSecurityManageFacadeIface(
			SecurityManageFacadeIface securityManageFacadeIface) {
		this.securityManageFacadeIface = securityManageFacadeIface;
	}

	public DaSecurityManage getDaSecurityManage() {
		return daSecurityManage;
	}

	public void setDaSecurityManage(DaSecurityManage daSecurityManage) {
		this.daSecurityManage = daSecurityManage;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
