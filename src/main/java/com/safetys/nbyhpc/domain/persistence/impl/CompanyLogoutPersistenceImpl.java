package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyLogout;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyLogoutPersistenceIface;

public class CompanyLogoutPersistenceImpl implements CompanyLogoutPersistenceIface {

	private PersistenceDao<DaCompanyLogout> persistenceDao;
	
	@SuppressWarnings("deprecation")
	public void updateBySql(String sql)  throws ApplicationAccessException{
		persistenceDao.executeUpdate(sql);
	}
	
	public Long createCompanyLogout(DaCompanyLogout daCompanyLogout) throws ApplicationAccessException {
		return (Long) persistenceDao.save(daCompanyLogout);
	}

	public void deleteCompanyLogout(DaCompanyLogout daCompanyLogout) throws ApplicationAccessException {
		persistenceDao.delete(daCompanyLogout);
	}

	public DaCompanyLogout loadCompanyLogout(DaCompanyLogout daCompanyLogout) throws ApplicationAccessException {
		
		return persistenceDao.load(daCompanyLogout.getClass(), daCompanyLogout.getId());
	}

	public List<DaCompanyLogout> loadCompanyLogouts(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeCompanyLogout(DaCompanyLogout daCompanyLogout) throws ApplicationAccessException {
		persistenceDao.merge(daCompanyLogout);
		
	}

	public void updateCompanyLogout(DaCompanyLogout daCompanyLogout) throws ApplicationAccessException {
		persistenceDao.update(daCompanyLogout);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}
	
	public PersistenceDao<DaCompanyLogout> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaCompanyLogout> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}



}
