package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaRollcallDeferFile;
import com.safetys.nbyhpc.domain.persistence.iface.RollcallDeferFilePersistenceIface;

public class RollcallDeferFilePersistenceImpl implements RollcallDeferFilePersistenceIface {
	private PersistenceDao<DaRollcallDeferFile> persistenceDao;

	public Long createRollcallDeferFile(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		return (Long) persistenceDao.save(rollcallDeferFile);
	}

	public void deleteRollcallDeferFile(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		rollcallDeferFile = loadRollcallDeferFile(rollcallDeferFile);
		rollcallDeferFile.setDeleted(true);
		persistenceDao.merge(rollcallDeferFile);
	}

	public DaRollcallDeferFile loadRollcallDeferFile(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		return persistenceDao.get(DaRollcallDeferFile.class, rollcallDeferFile.getId());
	}

	public List<DaRollcallDeferFile> loadRollcallDeferFiles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateRollcallDeferFile(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		persistenceDao.update(rollcallDeferFile);
	}

	public void mergeRollcallDeferFile(DaRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		persistenceDao.merge(rollcallDeferFile);
	}	

	public void setPersistenceDao(PersistenceDao<DaRollcallDeferFile> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
