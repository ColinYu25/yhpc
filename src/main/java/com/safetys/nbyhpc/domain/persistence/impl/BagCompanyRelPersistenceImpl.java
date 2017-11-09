package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaBagCompanyRel;
import com.safetys.nbyhpc.domain.persistence.iface.BagCompanyRelPersistenceIface;

public class BagCompanyRelPersistenceImpl implements
		BagCompanyRelPersistenceIface {
	
	private PersistenceDao<DaBagCompanyRel> persistenceDao;
	
	public Long createBagCompanyRel(DaBagCompanyRel daBagCompanyRel)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(daBagCompanyRel);
	}

	public void deleteBagCompanyRel(DaBagCompanyRel daBagCompanyRel)
			throws ApplicationAccessException {
		daBagCompanyRel=loadBagCompanyRel(daBagCompanyRel);
		daBagCompanyRel.setDeleted(true);
		persistenceDao.merge(daBagCompanyRel);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaBagCompanyRel loadBagCompanyRel(DaBagCompanyRel daBagCompanyRel)
			throws ApplicationAccessException {
		return persistenceDao.load(daBagCompanyRel.getClass(), daBagCompanyRel.getId());
	}

	public List<DaBagCompanyRel> loadBagCompanyRels(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeBagCompanyRel(DaBagCompanyRel daBagCompanyRel)
			throws ApplicationAccessException {
		persistenceDao.merge(daBagCompanyRel);
	}

	public void updateBagCompanyRel(DaBagCompanyRel daBagCompanyRel)
			throws ApplicationAccessException {
		persistenceDao.update(daBagCompanyRel);
	}

	public PersistenceDao<DaBagCompanyRel> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaBagCompanyRel> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	

}
