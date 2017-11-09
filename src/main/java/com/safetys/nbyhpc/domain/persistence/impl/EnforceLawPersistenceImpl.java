package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaZhifaReport;
import com.safetys.nbyhpc.domain.persistence.iface.EnforceLawPersistenceIface;

public class EnforceLawPersistenceImpl implements EnforceLawPersistenceIface {
	private PersistenceDao<DaZhifaReport> persistenceDao;

	public Long createEnforceLaw(DaZhifaReport daZhifaReport)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(daZhifaReport);
	}

	public List<DaZhifaReport> loadEnforceLaws(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination != null) {
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		} else {
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		}

	}

	public DaZhifaReport loadEnforceLaw(DaZhifaReport daZhifaReport) throws ApplicationAccessException {
		return persistenceDao.load(daZhifaReport.getClass(), daZhifaReport.getId());
	}
	
	public void updateEnforceLaw(DaZhifaReport daZhifaReport) throws ApplicationAccessException {
		persistenceDao.merge(daZhifaReport);
	}
	
	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}
	
	public void setPersistenceDao(PersistenceDao<DaZhifaReport> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public PersistenceDao<DaZhifaReport> getPersistenceDao() {
		return persistenceDao;
	}
}
