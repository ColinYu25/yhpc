package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaCompanyPunishment;

public interface CompanyPunishmentFacadeIface {
	
	public List<DaCompanyPunishment> loadCompanyPunishments(DaCompanyPunishment daCompanyPunishment)throws ApplicationAccessException;
	
	
	public void updatePunishment(DaCompanyPunishment companyPunishment)throws ApplicationAccessException;
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;
	
	public List<DaCompanyPunishment> loadCompanyPunishment(DaCompany company) throws ApplicationAccessException;
//	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException;
}
