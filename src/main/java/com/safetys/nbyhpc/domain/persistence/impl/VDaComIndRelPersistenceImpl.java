package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.VDaCompanyIndustryRel;
import com.safetys.nbyhpc.domain.persistence.iface.VDaComIndRelPersistenceIface;

public class VDaComIndRelPersistenceImpl implements VDaComIndRelPersistenceIface {

	private PersistenceDao<VDaCompanyIndustryRel> persistenceDao;

	public List<VDaCompanyIndustryRel> loadCompanies(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public List<VDaCompanyIndustryRel> getHy(long comId) {
		String hql = "from VDaCompanyIndustryRel  where  parDaComId=" + comId+" ";
		return persistenceDao.executeHQLQuery(hql);
	}
	
	
	public List<VDaCompanyIndustryRel> getHy(long comId,long parDaIndId) {
		String hql = "from VDaCompanyIndustryRel  where  parDaComId=" + comId+"  and parDaIndId="+parDaIndId;
		return persistenceDao.executeHQLQuery(hql);
	}

	public PersistenceDao<VDaCompanyIndustryRel> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<VDaCompanyIndustryRel> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
