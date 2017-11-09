package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyAcount;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyAcountPersistenceIface;

public class CompanyAcountPersistenceImpl implements
		CompanyAcountPersistenceIface {

	private PersistenceDao<DaCompanyAcount> persistenceDao;
	
	public Long createCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(daCompanyAcount);
	}

	public void deleteCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException {
		daCompanyAcount=loadCompanyAcount(daCompanyAcount);
		daCompanyAcount.setDeleted(true);
		persistenceDao.merge(daCompanyAcount);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaCompanyAcount loadCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException {
		return persistenceDao.load(daCompanyAcount.getClass(), daCompanyAcount.getId());
	}

	public List<DaCompanyAcount> loadCompanyAcounts(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException {
		persistenceDao.merge(daCompanyAcount);
	}

	public void updateCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException {
		persistenceDao.update(daCompanyAcount);
	}

	public PersistenceDao<DaCompanyAcount> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaCompanyAcount> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
