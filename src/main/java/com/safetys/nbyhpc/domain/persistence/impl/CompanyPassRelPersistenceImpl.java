package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyPassRel;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPassRelPersistenceIface;

public class CompanyPassRelPersistenceImpl implements
		CompanyPassRelPersistenceIface {
	private PersistenceDao<DaCompanyPassRel> persistenceDao;

	public Long creatCompanyPassRel(DaCompanyPassRel companyPassRel)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(companyPassRel);

	}

	public void deleteCompanyPassRel(DaCompanyPassRel companyPassRel)
			throws ApplicationAccessException {
		persistenceDao.delete(companyPassRel);
	}

	public DaCompanyPassRel loadCompanyPassRel(DaCompanyPassRel companyPassRel)
			throws ApplicationAccessException {
		return persistenceDao.load(DaCompanyPassRel.class, companyPassRel
				.getId());
	}

	public void updateCompanyPassRel(DaCompanyPassRel companyPassRel)
			throws ApplicationAccessException {
		persistenceDao.update(companyPassRel);

	}

	public List<DaCompanyPassRel> loadCompanyPassRels(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if(pagination!=null){
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,pagination);
		}
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public List<DaCompanyPassRel> loadAllCompanyPassRels(
			DetachedCriteriaProxy detachedCriteriaProxy)
			throws ApplicationAccessException {
		return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void setPersistenceDao(
			PersistenceDao<DaCompanyPassRel> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
