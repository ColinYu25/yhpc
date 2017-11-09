package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.dao.PersistenceDao2;
import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.TCompany;
import com.safetys.nbyhpc.domain.persistence.iface.TCompanyPersistenceIface;

public class TCompanyPersistenceImpl implements TCompanyPersistenceIface {
	private PersistenceDao<TCompany> persistenceDao;
	private PersistenceDao2<TCompany> persistenceDao2;


	public Long createCompany(TCompany company) throws ApplicationAccessException {
		return (Long) persistenceDao.save(company);
	}

	public void deleteCompany(TCompany company) throws ApplicationAccessException {
		company = loadCompany(company);
		company.setDeleted(true);
		persistenceDao.merge(company);
	}

	public TCompany loadCompany(TCompany company) throws ApplicationAccessException {
		return persistenceDao.get(TCompany.class, company.getId());
	}

	public TCompany loadCompanies(DetachedCriteriaProxy detachedCriteriaProxy) throws ApplicationAccessException {
		List<TCompany> list=persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		if (list.size() > 0) {
			return (TCompany) list.get(0);
		} else {
			return null;
		}

	}
	
	public TCompany loadCompanies2(DetachedCriteriaProxy2 detachedCriteriaProxy) throws ApplicationAccessException {
		List<TCompany> list=persistenceDao2.findAllByCriteria(detachedCriteriaProxy);
		if (list.size() > 0) {
			return (TCompany) list.get(0);
		} else {
			return null;
		}

	}

	public TCompany loadCoreCompany(TCompany company) throws ApplicationAccessException {
		return persistenceDao.get(TCompany.class, company.getId());
	}

	public void updateCompany(TCompany company) throws ApplicationAccessException {
		persistenceDao.update(company);
	}

	public void mergeCompany(TCompany company) throws ApplicationAccessException {
		persistenceDao.merge(company);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public void executeHQLUpdate(String hql) throws ApplicationAccessException {
		persistenceDao.executeHQLUpdate(hql);
	}

	public void setPersistenceDao(PersistenceDao<TCompany> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public List<TCompany> loadCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}
	
	public List<TCompany> loadCompanies2(DetachedCriteriaProxy2 detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao2.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao2.findAllByCriteria(detachedCriteriaProxy);
	}

	/**
	 * @return the persistenceDao2
	 */
	public PersistenceDao2<TCompany> getPersistenceDao2() {
		return persistenceDao2;
	}

	/**
	 * @param persistenceDao2 the persistenceDao2 to set
	 */
	public void setPersistenceDao2(PersistenceDao2<TCompany> persistenceDao2) {
		this.persistenceDao2 = persistenceDao2;
	}

}
