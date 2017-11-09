package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaBagCompanyRel;
import com.safetys.nbyhpc.facade.iface.BagCompanyRelFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) BagAction.java
 * @date 2009-08-18
 * @author zhangxu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class BagCompanyRelAction extends DaAppAction{

	/**
	 * 打包企业对照表
	 */
	private static final long serialVersionUID = -3154272369898191730L;

	private BagCompanyRelFacadeIface bagCompanyRelFacadeIface;
	
	private DaBagCompanyRel bagCompanyRel;
	
	private List<DaBagCompanyRel> bagCompanyRels;
	
	private Pagination pagination;// 分页对象

	private String ids;
	
	private String bagId;
	
	private String companyPassIds;
	
	public String deleteBagCompanyRel(){
		try {
			bagCompanyRelFacadeIface.deleteBagCompanyRel(ids);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String createBagCompanyRel(){
		try {
			bagCompanyRelFacadeIface.createBagCompanyRel(bagId, companyPassIds, getUserId());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public DaBagCompanyRel getBagCompanyRel() {
		return bagCompanyRel;
	}

	public void setBagCompanyRel(DaBagCompanyRel bagCompanyRel) {
		this.bagCompanyRel = bagCompanyRel;
	}

	public BagCompanyRelFacadeIface getBagCompanyRelFacadeIface() {
		return bagCompanyRelFacadeIface;
	}

	public void setBagCompanyRelFacadeIface(
			BagCompanyRelFacadeIface bagCompanyRelFacadeIface) {
		this.bagCompanyRelFacadeIface = bagCompanyRelFacadeIface;
	}

	public List<DaBagCompanyRel> getBagCompanyRels() {
		return bagCompanyRels;
	}

	public void setBagCompanyRels(List<DaBagCompanyRel> bagCompanyRels) {
		this.bagCompanyRels = bagCompanyRels;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getBagId() {
		return bagId;
	}

	public void setBagId(String bagId) {
		this.bagId = bagId;
	}

	public String getCompanyPassIds() {
		return companyPassIds;
	}

	public void setCompanyPassIds(String companyPassIds) {
		this.companyPassIds = companyPassIds;
	}
	
	
	
}
