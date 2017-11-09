package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPunishmentDocument;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPunishmentDocumentPersistenceIface;
import com.safetys.nbyhpc.facade.iface.CompanyPunishmentDocumentFacadeIface;

public class CompanyPunishmentDocumentFacadeImpl implements CompanyPunishmentDocumentFacadeIface{
	
	private CompanyPunishmentDocumentPersistenceIface companyPunishmentDocumentPersistenceIface;
	
	
	private CompanyPersistenceIface companyPersistenceIface;
//	private CompanyPassPersistenceIface companyPassPersistenceIface;
	
	public List<DaCompanyPunishmentDocument> loadCompanyPunishmentDocuments(DaCompanyPunishmentDocument daCompanyPunishmentDocument)throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPunishmentDocument.class, "ddg");
//		if(daCompanyPunishment != null) {			
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("ddg.daCompany.id", daCompanyPunishment.getCompany().getId()));			
//		}
		return companyPunishmentDocumentPersistenceIface.loadDaCompanyPunishmentDocuments(detachedCriteriaProxy, null);
		
	}
    
	
//	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException {
//		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPass.class, "dcp");
//		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId",(long)userDetail.getUserId()));
//		return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null);
//	}
	
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return companyPersistenceIface.loadCompany(company);
	}
	
	public List<DaCompanyPunishmentDocument> loadCompanyPunishmentDocuments(DaCompany company) throws ApplicationAccessException{
		
		String hql="from DaCompanyPunishmentDocument t where t.companyId="+company.getIndustryId();
//		String sql="select * from da_company_punishment t where ( SELECT months_between(sysdate,t.punish_time )as m FROM DUAL )>=0 and  ( SELECT months_between(sysdate,t.punish_time )as m FROM DUAL )<=12";
		return companyPunishmentDocumentPersistenceIface.loadDaCompanyPunishmentDocuments(hql);
	}
	public void updatePunishment(DaCompanyPunishmentDocument companyPunishmentDocument)
			throws ApplicationAccessException {
//		String hql = "update DaCompanyPunishment t set t.punishTime =to_date('"+companyPunishment.getPunishTime()+"' , 'yyyy-mm-dd'),t.punishType='"+companyPunishment.getPunishType()+"'where id = "+companyPunishment.getId();
		String hql = "update DaCompanyPunishmentDocument t set t.punishTime =to_date('"+companyPunishmentDocument.getDocumentTime()+"' , 'yyyy-mm-dd'),t.punishType='"+companyPunishmentDocument.getDocumentName()+"'where id = "+companyPunishmentDocument.getId();
		companyPunishmentDocumentPersistenceIface.updateCompanyPunishmentDocument(hql);
	}

	public void setCompanyPunishmentDocumentPersistenceIface(
			CompanyPunishmentDocumentPersistenceIface companyPunishmentDocumentPersistenceIface) {
		this.companyPunishmentDocumentPersistenceIface = companyPunishmentDocumentPersistenceIface;
	}


	public CompanyPersistenceIface getCompanyPersistenceIface() {
		return companyPersistenceIface;
	}


	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}
	

}
