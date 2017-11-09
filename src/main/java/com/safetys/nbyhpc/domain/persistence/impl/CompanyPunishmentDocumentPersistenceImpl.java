package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyPunishmentDocument;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPunishmentDocumentPersistenceIface;

public class CompanyPunishmentDocumentPersistenceImpl implements CompanyPunishmentDocumentPersistenceIface{
	
	private PersistenceDao<DaCompanyPunishmentDocument> persistenceDao;
	
	
	
	public void updateCompanyPunishmentDocument(String hql)
    throws ApplicationAccessException{
		persistenceDao.executeHQLUpdate(hql);
	}
	

	
	public List<DaCompanyPunishmentDocument> loadDaCompanyPunishmentDocuments(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}
	
	
	public DaCompanyPunishmentDocument loadCompanyPunishmentDocument(DaCompanyPunishmentDocument companyPunishmentDocument)
			throws ApplicationAccessException {
		return persistenceDao.get(DaCompanyPunishmentDocument.class, companyPunishmentDocument.getId());
	}
	
	public List<DaCompanyPunishmentDocument> loadDaCompanyPunishmentDocuments(String hql)
			throws ApplicationAccessException{
		return persistenceDao.executeHQLQuery(hql);
	}
    
	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public void setPersistenceDao(PersistenceDao<DaCompanyPunishmentDocument> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
	
}
