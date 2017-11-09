package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallDeferFile;
import com.safetys.nbyhpc.domain.persistence.iface.PipeRollcallDeferFilePersistenceIface;

public class PipeRollcallDeferFilePersistenceImpl implements PipeRollcallDeferFilePersistenceIface {
	private PersistenceDao<DaPipeRollcallDeferFile> persistenceDao;

	public Long createRollcallDeferFile(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		return (Long) persistenceDao.save(rollcallDeferFile);
	}

	public void deleteRollcallDeferFile(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		rollcallDeferFile = loadRollcallDeferFile(rollcallDeferFile);
		rollcallDeferFile.setDeleted(true);
		persistenceDao.merge(rollcallDeferFile);
	}

	public DaPipeRollcallDeferFile loadRollcallDeferFile(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		return persistenceDao.get(DaPipeRollcallDeferFile.class, rollcallDeferFile.getId());
	}

	public List<DaPipeRollcallDeferFile> loadRollcallDeferFiles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateRollcallDeferFile(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		persistenceDao.update(rollcallDeferFile);
	}

	public void mergeRollcallDeferFile(DaPipeRollcallDeferFile rollcallDeferFile) throws ApplicationAccessException {
		persistenceDao.merge(rollcallDeferFile);
	}	

	public void setPersistenceDao(PersistenceDao<DaPipeRollcallDeferFile> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
