package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaBag;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.facade.iface.BagFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) BagAction.java
 * @date 2009-08-18
 * @author zhangxu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class BagAction extends DaAppAction {

	/**
	 * 包
	 */
	private static final long serialVersionUID = -9027140259357743153L;
	
	private BagFacadeIface bagFacadeIface;
	
	private DaBag bag;
	
	private List<DaBag> bags;
	
	private Pagination pagination;// 分页对象

	private String ids;
	
	private DaCompany company;
	
	private List<DaCompany> companies;
	
	private String companyIds;
	
	private String tp;
	
	private String bagId;
	
	private boolean flag;
	
	public String loadAllowBag(){
		try {
			flag=bagFacadeIface.isAllowBag(companyIds);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String updateBag(){
		try {
			bag.setUserId(getUserId());
			bagFacadeIface.updateBag(bag);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String loadBag(){
		try {
			bag=bagFacadeIface.loadBag(bag);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String loadBagCompanies(){
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			companies=bagFacadeIface.loadBagCompanies(company, pagination, bagId);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String loadAlreadyBags(){
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			bags=bagFacadeIface.loadAlreadyBags(bag, pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String loadBagsByBagType(){
		try {
			bags=bagFacadeIface.loadBagsByBagType(tp,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String loadBagTypes(){
		return SUCCESS;
	}
	
	public String loadUnBagCompanies(){
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			companies=bagFacadeIface.loadUnBagCompanies(company, pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String deleteBag(){
		try {
			bagFacadeIface.deleteBag(ids);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String createBag(){
		try {
			bag.setUserId(getUserId());
			bagFacadeIface.createBag(bag);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String createBagInit(){
		
		return SUCCESS;
	}
	
	public String loadBags(){
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			bags=bagFacadeIface.loadBags(bag, pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	public DaBag getBag() {
		return bag;
	}

	public void setBag(DaBag bag) {
		this.bag = bag;
	}

	public BagFacadeIface getBagFacadeIface() {
		return bagFacadeIface;
	}

	public void setBagFacadeIface(BagFacadeIface bagFacadeIface) {
		this.bagFacadeIface = bagFacadeIface;
	}

	public List<DaBag> getBags() {
		return bags;
	}

	public void setBags(List<DaBag> bags) {
		this.bags = bags;
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

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public List<DaCompany> getCompanies() {
		return companies;
	}

	public void setCompanies(List<DaCompany> companies) {
		this.companies = companies;
	}

	public String getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public String getBagId() {
		return bagId;
	}

	public void setBagId(String bagId) {
		this.bagId = bagId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
