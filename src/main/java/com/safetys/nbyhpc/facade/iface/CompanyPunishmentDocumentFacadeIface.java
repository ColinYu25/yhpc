package com.safetys.nbyhpc.facade.iface;

import java.util.List;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPunishmentDocument;

public interface CompanyPunishmentDocumentFacadeIface {
	
	public List<DaCompanyPunishmentDocument> loadCompanyPunishmentDocuments(DaCompanyPunishmentDocument daCompanyPunishmentDocument)throws ApplicationAccessException;
	
	
	public void updatePunishment(DaCompanyPunishmentDocument companyPunishmentDocument)throws ApplicationAccessException;
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;
	
	public List<DaCompanyPunishmentDocument> loadCompanyPunishmentDocuments(DaCompany company) throws ApplicationAccessException;
//	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException;
}
