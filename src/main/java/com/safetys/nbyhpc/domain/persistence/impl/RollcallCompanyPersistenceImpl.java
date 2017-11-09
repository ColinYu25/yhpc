package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.persistence.iface.RollcallCompanyPersistenceIface;

public class RollcallCompanyPersistenceImpl implements RollcallCompanyPersistenceIface {
	private PersistenceDao<DaRollcallCompany> persistenceDao;

	public Long createRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException {
		return (Long) persistenceDao.save(rollcallCompany);
	}

	public void deleteRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException {
		rollcallCompany = loadRollcallCompany(rollcallCompany);
		rollcallCompany.setDeleted(true);
		persistenceDao.merge(rollcallCompany);
	}

	public DaRollcallCompany loadRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException {
		return persistenceDao.get(DaRollcallCompany.class, rollcallCompany.getId());
	}

	public List<DaRollcallCompany> loadRollcallCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException {
		persistenceDao.update(rollcallCompany);
	}

	public void mergeRollcallCompany(DaRollcallCompany rollcallCompany) throws ApplicationAccessException {
		persistenceDao.merge(rollcallCompany);
	}
	
	public void executeHQLUpdate(String hql) throws ApplicationAccessException{
		persistenceDao.executeHQLUpdate(hql);
	}

	public void setPersistenceDao(PersistenceDao<DaRollcallCompany> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
