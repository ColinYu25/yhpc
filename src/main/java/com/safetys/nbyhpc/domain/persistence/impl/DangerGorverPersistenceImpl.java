package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDangerGorver;
import com.safetys.nbyhpc.domain.model.Department;
import com.safetys.nbyhpc.domain.persistence.iface.DangerGorverPersistenceIface;

public class DangerGorverPersistenceImpl implements DangerGorverPersistenceIface {
	private PersistenceDao<DaDangerGorver> persistenceDao;
	public Long createDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException {
		return (Long) persistenceDao.save(dangerGorver);
	}

	public void deleteDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException {
		dangerGorver = loadDangerGorver(dangerGorver);
		dangerGorver.setDeleted(true);
		persistenceDao.merge(dangerGorver);
	}

	public DaDangerGorver loadDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException {
		return persistenceDao.get(DaDangerGorver.class, dangerGorver.getId());
	}

	public List<DaDangerGorver> loadDangerGorvers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException {
		persistenceDao.update(dangerGorver);
	}

	public void mergeDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException {
		persistenceDao.merge(dangerGorver);
	}	

	public void setPersistenceDao(PersistenceDao<DaDangerGorver> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}	
}
