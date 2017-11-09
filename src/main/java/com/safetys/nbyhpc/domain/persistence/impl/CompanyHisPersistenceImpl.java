package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyHis;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyHisPersistenceIface;

public class CompanyHisPersistenceImpl implements CompanyHisPersistenceIface {
	private PersistenceDao<DaCompanyHis> persistenceDao;

	public Long createCompany(DaCompanyHis company) throws ApplicationAccessException {
		persistenceDao.save(company);
		return (Long) persistenceDao.save(company);
	}

	public void deleteCompany(DaCompanyHis company) throws ApplicationAccessException {
		company = loadCompany(company);
		company.setDeleted(true);
		persistenceDao.merge(company);
	}

	public DaCompanyHis loadCompany(DaCompanyHis company) throws ApplicationAccessException {
		return persistenceDao.get(DaCompanyHis.class, company.getId());
	}

	public DaCompanyHis loadCompanyInfo(DetachedCriteriaProxy detachedCriteriaProxy) throws ApplicationAccessException {
		if (persistenceDao.findAllByCriteria(detachedCriteriaProxy).size() > 0) {
			return (DaCompanyHis) persistenceDao.findAllByCriteria(detachedCriteriaProxy).get(0);
		} else {
			return null;
		}

	}

	public List<DaCompanyHis> loadCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {

		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateCompany(DaCompanyHis company) throws ApplicationAccessException {
		persistenceDao.update(company);
	}

	public void mergeCompany(DaCompanyHis company) throws ApplicationAccessException {
		persistenceDao.merge(company);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public void executeHQLUpdate(String hql) throws ApplicationAccessException {
		persistenceDao.executeHQLUpdate(hql);
	}

	public void executeSQLUpdate(String sql) throws ApplicationAccessException {
//		System.out.println("sql: " + sql);
		persistenceDao.executeQuery(sql);
	}

	public void setPersistenceDao(PersistenceDao<DaCompanyHis> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
