package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyPunishmentDocument;


public interface CompanyPunishmentDocumentPersistenceIface {
	public void updateCompanyPunishmentDocument(String hql)
	            throws ApplicationAccessException;
	
	public List<DaCompanyPunishmentDocument> loadDaCompanyPunishmentDocuments(String sql)
			throws ApplicationAccessException;
	
	public ResultSet findBySql(String sql) throws ApplicationAccessException;
	
	public List<DaCompanyPunishmentDocument> loadDaCompanyPunishmentDocuments(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException ;
   
	
	public DaCompanyPunishmentDocument loadCompanyPunishmentDocument(DaCompanyPunishmentDocument companyPunishmentDocument)throws ApplicationAccessException;
}
