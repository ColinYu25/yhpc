package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.TCoreCompany;
import com.safetys.nbyhpc.domain.persistence.iface.PubCompanyPersistenceIface;

public class PubCompanyPersistenceImpl implements PubCompanyPersistenceIface {
	private PersistenceDao<TCoreCompany> persistenceDao;

	public Long createCompany(TCoreCompany company)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(company);
	}

	public void deleteCompany(TCoreCompany company)
			throws ApplicationAccessException {
		company = loadCompany(company);
		company.setDeleted(true);
		persistenceDao.merge(company);
	}

	public TCoreCompany loadCompany(TCoreCompany company)
			throws ApplicationAccessException {
		return persistenceDao.get(TCoreCompany.class, company.getId());
	}

	public List<TCoreCompany> loadCompanies(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateCompany(TCoreCompany company)
			throws ApplicationAccessException {
		persistenceDao.update(company);
	}

	public void mergeCompany(TCoreCompany company)
			throws ApplicationAccessException {
		persistenceDao.merge(company);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public int executeSQLUpdate(final String sql) {
		return executeSQLUpdate(sql, null);
	}

	public int executeSQLUpdate(final String sql, final Object[] values) {
		return persistenceDao.executeSQLUpdate(sql, values);
	}

	public void executeHQLUpdate(String hql) throws ApplicationAccessException {
		persistenceDao.executeHQLUpdate(hql);
	}

	public void setPersistenceDao(PersistenceDao<TCoreCompany> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
