package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPunishment;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPunishmentPersistenceIface;
import com.safetys.nbyhpc.facade.iface.CompanyPunishmentFacadeIface;

public class CompanyPunishmentFacadeImpl implements CompanyPunishmentFacadeIface{
	
	private CompanyPunishmentPersistenceIface companyPunishmentPersistenceIface;
	
	
	private CompanyPersistenceIface companyPersistenceIface;
//	private CompanyPassPersistenceIface companyPassPersistenceIface;
	
	public List<DaCompanyPunishment> loadCompanyPunishments(DaCompanyPunishment daCompanyPunishment)throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPunishment.class, "ddg");
		if(daCompanyPunishment != null) {			
				detachedCriteriaProxy.add(RestrictionsProxy.eq("ddg.daCompany.id", daCompanyPunishment.getCompany().getId()));			
		}
		return companyPunishmentPersistenceIface.loadDaCompanyPunishments(detachedCriteriaProxy, null);
		
	}
    
	
//	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException {
//		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPass.class, "dcp");
//		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId",(long)userDetail.getUserId()));
//		return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null);
//	}
	
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return companyPersistenceIface.loadCompany(company);
	}
	
	public List<DaCompanyPunishment> loadCompanyPunishment(DaCompany company) throws ApplicationAccessException{
		
		String sql="from DaCompanyPunishment t where t.companyId="+company.getId();
//		String sql="select * from da_company_punishment t where ( SELECT months_between(sysdate,t.punish_time )as m FROM DUAL )>=0 and  ( SELECT months_between(sysdate,t.punish_time )as m FROM DUAL )<=12";
		return companyPunishmentPersistenceIface.loadDaCompanyPunishment(sql);
		
	}
	public void updatePunishment(DaCompanyPunishment companyPunishment)
			throws ApplicationAccessException {
//		String hql = "update DaCompanyPunishment t set t.punishTime =to_date('"+companyPunishment.getPunishTime()+"' , 'yyyy-mm-dd'),t.punishType='"+companyPunishment.getPunishType()+"'where id = "+companyPunishment.getId();
		String hql = "update DaCompanyPunishment t set t.punishTime =to_date('"+companyPunishment.getPunishTime()+"' , 'yyyy-mm-dd'),t.punishType='"+companyPunishment.getPunishType()+"'where id = "+companyPunishment.getId();
//		companyPunishmentPersistenceIface.updateCompanyPunishment(hql);
	}

	public void setCompanyPunishmentPersistenceIface(
			CompanyPunishmentPersistenceIface companyPunishmentPersistenceIface) {
		this.companyPunishmentPersistenceIface = companyPunishmentPersistenceIface;
	}


	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}
	

}
