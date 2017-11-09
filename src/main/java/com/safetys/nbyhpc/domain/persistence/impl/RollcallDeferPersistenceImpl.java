package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaRollcallDefer;
import com.safetys.nbyhpc.domain.persistence.iface.RollcallDeferPersistenceIface;

public class RollcallDeferPersistenceImpl implements RollcallDeferPersistenceIface {
	private PersistenceDao<DaRollcallDefer> persistenceDao;

	public Long createRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException {
		return (Long) persistenceDao.save(rollcallDefer);
	}

	public void deleteRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException {
		rollcallDefer = loadRollcallDefer(rollcallDefer);
		rollcallDefer.setDeleted(true);
		persistenceDao.merge(rollcallDefer);
	}

	public DaRollcallDefer loadRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException {
		return persistenceDao.get(DaRollcallDefer.class, rollcallDefer.getId());
	}

	public List<DaRollcallDefer> loadRollcallDefers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException {
		persistenceDao.update(rollcallDefer);
	}

	public void mergeRollcallDefer(DaRollcallDefer rollcallDefer) throws ApplicationAccessException {
		persistenceDao.merge(rollcallDefer);
	}	

	public void setPersistenceDao(PersistenceDao<DaRollcallDefer> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
