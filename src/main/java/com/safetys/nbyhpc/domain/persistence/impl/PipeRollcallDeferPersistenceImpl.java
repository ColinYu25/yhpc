package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallDefer;
import com.safetys.nbyhpc.domain.persistence.iface.PipeRollcallDeferPersistenceIface;

public class PipeRollcallDeferPersistenceImpl implements PipeRollcallDeferPersistenceIface {
	private PersistenceDao<DaPipeRollcallDefer> persistenceDao;

	public Long createRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException {
		return (Long) persistenceDao.save(rollcallDefer);
	}

	public void deleteRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException {
		rollcallDefer = loadRollcallDefer(rollcallDefer);
		rollcallDefer.setDeleted(true);
		persistenceDao.merge(rollcallDefer);
	}

	public DaPipeRollcallDefer loadRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException {
		return persistenceDao.get(DaPipeRollcallDefer.class, rollcallDefer.getId());
	}

	public List<DaPipeRollcallDefer> loadRollcallDefers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException {
		persistenceDao.update(rollcallDefer);
	}

	public void mergeRollcallDefer(DaPipeRollcallDefer rollcallDefer) throws ApplicationAccessException {
		persistenceDao.merge(rollcallDefer);
	}	

	public void setPersistenceDao(PersistenceDao<DaPipeRollcallDefer> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
