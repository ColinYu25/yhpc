package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.facade.iface.RollcallCompanyFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class AcceptanceAction extends DaAppAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RollcallCompanyFacadeIface rollcallCompanyFacadeIface;
	
	private Pagination pagination;
	
	private List<DaRollcallCompany> rollcallCompanies;
	
	private DaCompany company;
	
	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public List<DaRollcallCompany> getRollcallCompanies() {
		return rollcallCompanies;
	}

	public void setRollcallCompanies(List<DaRollcallCompany> rollcallCompanies) {
		this.rollcallCompanies = rollcallCompanies;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setRollcallCompanyFacadeIface(
			RollcallCompanyFacadeIface rollcallCompanyFacadeIface) {
		this.rollcallCompanyFacadeIface = rollcallCompanyFacadeIface;
	}

	public String loadAcceptance(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			rollcallCompanies=rollcallCompanyFacadeIface.loadRollcallCompaniesOfLevel(company,pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadAcceptanceList(){
		
		return SUCCESS;
	}

}
