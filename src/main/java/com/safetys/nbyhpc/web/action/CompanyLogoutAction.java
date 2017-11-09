package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyLogout;
import com.safetys.nbyhpc.facade.iface.CompanyLogoutFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class CompanyLogoutAction extends DaAppAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -825741123851958042L;
	
	private CompanyLogoutFacadeIface companyLogoutFacadeIface;
	
	private DaCompanyLogout daCompanyLogout;
	
	private List<DaCompanyLogout> daCompanyLogouts;
	
	private List<DaCompany> companies;
	
	private Pagination pagination;
	
	private String ids;
	
	private String companyIds;
	
	private String state;
	
	private DaCompany company;
	
	private String type;//区分已注销或暂停经营，无经营场所
	
	private String companiesIds;
	
	private String tp;
	
	public String loadCompanyLogouts(){
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			List<Long> list= companyLogoutFacadeIface.loadCompanyLogoutIds(type);
			companies=companyLogoutFacadeIface.loadCompanies(company,list,pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 创建注销或无经营场所或停止营业
	 * @return
	 */
	public String createCompanyLogout(){
		try {
			companyLogoutFacadeIface.createCompanyLogout(companiesIds,tp,getUserId());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String deleteCompanyLogout(){
		try {
			companyLogoutFacadeIface.deleteCompanyLogout(ids);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String updateOff(){
		try{
			companyLogoutFacadeIface.updateOff(companyIds,state,getUserId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public CompanyLogoutFacadeIface getCompanyLogoutFacadeIface() {
		return companyLogoutFacadeIface;
	}

	public void setCompanyLogoutFacadeIface(
			CompanyLogoutFacadeIface companyLogoutFacadeIface) {
		this.companyLogoutFacadeIface = companyLogoutFacadeIface;
	}

	public DaCompanyLogout getDaCompanyLogout() {
		return daCompanyLogout;
	}

	public void setDaCompanyLogout(DaCompanyLogout daCompanyLogout) {
		this.daCompanyLogout = daCompanyLogout;
	}

	public List<DaCompanyLogout> getDaCompanyLogouts() {
		return daCompanyLogouts;
	}

	public void setDaCompanyLogouts(List<DaCompanyLogout> daCompanyLogouts) {
		this.daCompanyLogouts = daCompanyLogouts;
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

	public List<DaCompany> getCompanies() {
		return companies;
	}

	public void setCompanies(List<DaCompany> companies) {
		this.companies = companies;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public String getCompaniesIds() {
		return companiesIds;
	}

	public void setCompaniesIds(String companiesIds) {
		this.companiesIds = companiesIds;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public String getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
