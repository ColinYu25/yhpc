package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.dao.PersistenceDao2;
import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;

public class CompanyPersistenceImpl implements CompanyPersistenceIface {
	private PersistenceDao<DaCompany> persistenceDao;
	private PersistenceDao2<DaCompany> persistenceDao2;
	

	public Long createCompany(DaCompany company) throws ApplicationAccessException {
		persistenceDao.save(company);
		return (Long) persistenceDao.save(company);
	}

	public void deleteCompany(DaCompany company) throws ApplicationAccessException {
		company = loadCompany(company);
		company.setDeleted(true);
		persistenceDao.merge(company);
	}

	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return persistenceDao.get(DaCompany.class, company.getId());
	}

	public DaCompany loadCompanyInfo(DetachedCriteriaProxy detachedCriteriaProxy) throws ApplicationAccessException {
		if (persistenceDao.findAllByCriteria(detachedCriteriaProxy).size() > 0) {
			return (DaCompany) persistenceDao.findAllByCriteria(detachedCriteriaProxy).get(0);
		} else {
			return null;
		}

	}
	public List<DaCompany> loadCompanyByUserId(String  Hql) throws ApplicationAccessException {
		return this.persistenceDao.executeHQLQuery(Hql);
	}
	
	public List<DaCompany> loadCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}
	
	public List<DaCompany> loadCompanies2(DetachedCriteriaProxy2 detachedCriteriaProxy) throws ApplicationAccessException {
	
		List<DaCompany> list=persistenceDao2.findAllByCriteria(detachedCriteriaProxy);
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	public void updateCompany(DaCompany company) throws ApplicationAccessException {
		
		persistenceDao.update(company);
	}

	public void mergeCompany(DaCompany company) throws ApplicationAccessException {
		persistenceDao.merge(company);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public void executeHQLUpdate(String hql) throws ApplicationAccessException {
		persistenceDao.executeHQLUpdate(hql);
	}
	
	public void executeSQLUpdate(String sql) throws ApplicationAccessException {
//		System.out.println("sql: "+sql);
		persistenceDao.executeQuery(sql);
	}

	public void setPersistenceDao(PersistenceDao<DaCompany> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	/**
	 * @return the persistenceDao2
	 */
	public PersistenceDao2<DaCompany> getPersistenceDao2() {
		return persistenceDao2;
	}

	/**
	 * @param persistenceDao2 the persistenceDao2 to set
	 */
	public void setPersistenceDao2(PersistenceDao2<DaCompany> persistenceDao2) {
		this.persistenceDao2 = persistenceDao2;
	}

}
