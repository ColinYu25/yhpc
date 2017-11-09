package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaTroubleCompany;
import com.safetys.nbyhpc.domain.persistence.iface.TroubleCompanyPersistenceIface;

public class TroubleCompanyPersistenceImpl implements TroubleCompanyPersistenceIface {
	private PersistenceDao<DaTroubleCompany> persistenceDao;

	public Long createTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException {
		return (Long) persistenceDao.save(troubleCompany);
	}

	public void deleteTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException {
		troubleCompany = loadTroubleCompany(troubleCompany);
		troubleCompany.setDeleted(true);
		persistenceDao.merge(troubleCompany);
	}

	public DaTroubleCompany loadTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException {
		return persistenceDao.get(DaTroubleCompany.class, troubleCompany.getId());
	}

	public List<DaTroubleCompany> loadTroubleCompanys(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException {
		persistenceDao.update(troubleCompany);
	}

	public void mergeTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException {
		persistenceDao.merge(troubleCompany);
	}	

	public void setPersistenceDao(PersistenceDao<DaTroubleCompany> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
