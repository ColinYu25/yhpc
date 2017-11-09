package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaBag;
import com.safetys.nbyhpc.domain.persistence.iface.BagPersistenceIface;

public class BagPersistenceImpl implements BagPersistenceIface {

	private PersistenceDao<DaBag> persistenceDao;
	
	public Long createBag(DaBag daBag) throws ApplicationAccessException {
		return (Long) persistenceDao.save(daBag);
	}

	public void deleteBag(DaBag daBag) throws ApplicationAccessException {
		daBag=loadBag(daBag);
		daBag.setDeleted(true);
		persistenceDao.merge(daBag);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaBag loadBag(DaBag daBag) throws ApplicationAccessException {
		return persistenceDao.load(daBag.getClass(), daBag.getId());
	}

	public List<DaBag> loadBags(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeBag(DaBag daBag) throws ApplicationAccessException {
			persistenceDao.merge(daBag);
	}

	public void updateBag(DaBag daBag) throws ApplicationAccessException {
			persistenceDao.update(daBag);
	}

	public PersistenceDao<DaBag> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaBag> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
