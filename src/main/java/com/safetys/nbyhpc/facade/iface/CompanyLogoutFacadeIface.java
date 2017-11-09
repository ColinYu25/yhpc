package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyLogout;

public interface CompanyLogoutFacadeIface {
	
	public Long createCompanyLogout(String ids,String type,Long userId) throws ApplicationAccessException;
	
	public void updateCompanyLogout(DaCompanyLogout companyLogout)throws ApplicationAccessException;
	
	public void deleteCompanyLogout(String ids)throws ApplicationAccessException;
	
	public DaCompanyLogout loadCompanyLogout(DaCompanyLogout companyLogout)throws ApplicationAccessException;

	public List<DaCompanyLogout> loadCompanyLogouts(DaCompanyLogout companyLogout,Pagination pagination)throws ApplicationAccessException;
	
	public List<DaCompany> loadCompanies(DaCompany company,List<Long> ids,Pagination pagination,UserDetailWrapper userDetail)throws ApplicationAccessException;

	public List<Long> loadCompanyLogoutIds(String type)throws ApplicationAccessException;
	
	//public List<DaCompany> loadUnCheckedCompanies(DaCompany daCompany,Pagination pagination) throws ApplicationAccessException;
	public void updateOff(String ids,String type,Long userId)throws ApplicationAccessException;
}
