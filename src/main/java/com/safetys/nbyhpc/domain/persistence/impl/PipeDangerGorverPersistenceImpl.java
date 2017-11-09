package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeDangerGorver;
import com.safetys.nbyhpc.domain.persistence.iface.PipeDangerGorverPersistenceIface;

public class PipeDangerGorverPersistenceImpl implements PipeDangerGorverPersistenceIface {
	private PersistenceDao<DaPipeDangerGorver> persistenceDao;

	public Long createDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException {
		return (Long) persistenceDao.save(dangerGorver);
	}

	public void deleteDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException {
		dangerGorver = loadDangerGorver(dangerGorver);
		dangerGorver.setDeleted(true);
		persistenceDao.merge(dangerGorver);
	}

	public DaPipeDangerGorver loadDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException {
		return persistenceDao.get(DaPipeDangerGorver.class, dangerGorver.getId());
	}

	public List<DaPipeDangerGorver> loadDangerGorvers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException {
		persistenceDao.update(dangerGorver);
	}

	public void mergeDangerGorver(DaPipeDangerGorver dangerGorver) throws ApplicationAccessException {
		persistenceDao.merge(dangerGorver);
	}	

	public void setPersistenceDao(PersistenceDao<DaPipeDangerGorver> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
