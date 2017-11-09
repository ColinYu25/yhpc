package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaThreeIllegal;
import com.safetys.nbyhpc.domain.persistence.iface.ThreeIllegalPersistenceIface;

public class ThreeIllegalPersistenceImpl implements ThreeIllegalPersistenceIface {
	private PersistenceDao<DaThreeIllegal> persistenceDao;

	public Long createThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException {
		return (Long) persistenceDao.save(threeIllegal);
	}

	public void deleteThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException {
		threeIllegal = loadThreeIllegal(threeIllegal);
		threeIllegal.setDeleted(true);
		persistenceDao.merge(threeIllegal);
	}

	public DaThreeIllegal loadThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException {
		return persistenceDao.get(DaThreeIllegal.class, threeIllegal.getId());
	}

	public List<DaThreeIllegal> loadThreeIllegals(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException {
		persistenceDao.update(threeIllegal);
	}

	public void mergeThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException {
		persistenceDao.merge(threeIllegal);
	}	

	public void setPersistenceDao(PersistenceDao<DaThreeIllegal> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
