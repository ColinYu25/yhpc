package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPassPersistenceIface;

public class CompanyPassPersistenceImpl implements CompanyPassPersistenceIface{

	private PersistenceDao<DaCompanyPass> persistenceDao;
	
	public void updateCompanyPass(DaCompanyPass companyPass)
		throws ApplicationAccessException{
		persistenceDao.merge(companyPass);
	}
	
	public void deleteCompanyPass(DaCompanyPass companyPass) throws ApplicationAccessException {
			persistenceDao.delete(companyPass);
	}
	
	public List<DaCompanyPass> loadCompanyPasses(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public PersistenceDao<DaCompanyPass> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaCompanyPass> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
