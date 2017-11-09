package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyPunishment;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPunishmentPersistenceIface;

public class CompanyPunishmentPersistenceImpl implements CompanyPunishmentPersistenceIface{
	
	private PersistenceDao<DaCompanyPunishment> persistenceDao;
	
	
	
	public void updateCompanyPunishment(String hql)
    throws ApplicationAccessException{
		persistenceDao.executeHQLUpdate(hql);
	}
	

	
	public List<DaCompanyPunishment> loadDaCompanyPunishments(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}
	
	
	public DaCompanyPunishment loadCompanyPunishment(DaCompanyPunishment companyPunishment)
			throws ApplicationAccessException {
		return persistenceDao.get(DaCompanyPunishment.class, companyPunishment.getId());
	}
	
	public List<DaCompanyPunishment> loadDaCompanyPunishment(String sql)
			throws ApplicationAccessException{
		return persistenceDao.executeHQLQuery(sql);
	}
    
	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public void setPersistenceDao(PersistenceDao<DaCompanyPunishment> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
	
}
