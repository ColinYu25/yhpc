package com.safetys.nbyhpc.web.action;

import java.util.Date;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaInjuryManage;
import com.safetys.nbyhpc.facade.iface.InjuryManageFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) InjuryManageAction.java
 * @date 2011-10-26
 * @author zouxw
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class InjuryManageAction extends DaAppAction {

	private static final long serialVersionUID = -3490645722943538375L;
	private InjuryManageFacadeIface injuryManageFacadeIface;
	private DaInjuryManage daInjuryManage;
	private Long id;
	private Long cid;
	private Integer insuredNum;//参保人数
	private Integer injuryNum;//工伤人数
	private Date createTime;
	
	/** 
	 * 修改工伤信息
	 * @return
	 */
	public String updateInjuryManage() {
//		try {
//			if(null == daInjuryManage){
//				daInjuryManage = new DaInjuryManage();
//			}
//			if(null != this.getId()){
//				daInjuryManage.setId(this.getId());
//				DaCompany daCompany = new DaCompany();
//				daCompany.setId(this.getCid());
//				daInjuryManage.setInjuryNum(this.getInjuryNum());
//				daInjuryManage.setInsuredNum(this.getInsuredNum());
//				daInjuryManage.setDaCompany(daCompany);
//				daInjuryManage.setCreateTime(this.getCreateTime());
//				injuryManageFacadeIface.updateInjuryManage(daInjuryManage);
//			}
//		} catch (ApplicationAccessException e) {
//			e.printStackTrace();
//			return ERROR;
//		} catch (Exception e2) {
//			e2.printStackTrace();
//			return ERROR;
//		}
		return null;
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

	public DaInjuryManage getDaInjuryManage() {
		return daInjuryManage;
	}

	public void setDaInjuryManage(DaInjuryManage daInjuryManage) {
		this.daInjuryManage = daInjuryManage;
	}

	public Integer getInsuredNum() {
		return insuredNum;
	}

	public void setInsuredNum(Integer insuredNum) {
		this.insuredNum = insuredNum;
	}

	public Integer getInjuryNum() {
		return injuryNum;
	}

	public void setInjuryNum(Integer injuryNum) {
		this.injuryNum = injuryNum;
	}

	public void setInjuryManageFacadeIface(
			InjuryManageFacadeIface injuryManageFacadeIface) {
		this.injuryManageFacadeIface = injuryManageFacadeIface;
	}

}
