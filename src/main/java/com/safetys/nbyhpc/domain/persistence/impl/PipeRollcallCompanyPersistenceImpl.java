package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.persistence.iface.PipeRollcallCompanyPersistenceIface;

public class PipeRollcallCompanyPersistenceImpl implements PipeRollcallCompanyPersistenceIface {
	private PersistenceDao<DaPipeRollcallCompany> persistenceDao;

	public Long createRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException {
		return (Long) persistenceDao.save(rollcallCompany);
	}

	public void deleteRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException {
		rollcallCompany = loadRollcallCompany(rollcallCompany);
		rollcallCompany.setDeleted(true);
		persistenceDao.merge(rollcallCompany);
	}

	public DaPipeRollcallCompany loadRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException {
		return persistenceDao.get(DaPipeRollcallCompany.class, rollcallCompany.getId());
	}

	public List<DaPipeRollcallCompany> loadRollcallCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException {
		persistenceDao.update(rollcallCompany);
	}

	public void mergeRollcallCompany(DaPipeRollcallCompany rollcallCompany) throws ApplicationAccessException {
		persistenceDao.merge(rollcallCompany);
	}
	
	public void executeHQLUpdate(String hql) throws ApplicationAccessException{
		persistenceDao.executeHQLUpdate(hql);
	}

	public void setPersistenceDao(PersistenceDao<DaPipeRollcallCompany> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
