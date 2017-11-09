package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaTroubleFile;
import com.safetys.nbyhpc.domain.persistence.iface.TroubleFilePersistenceIface;

public class TroubleFilePersistenceImpl implements TroubleFilePersistenceIface {
	private PersistenceDao<DaTroubleFile> persistenceDao;

	public Long createTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException {
		return (Long) persistenceDao.save(troubleFile);
	}

	public void deleteTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException {
		troubleFile = loadTroubleFile(troubleFile);
		troubleFile.setDeleted(true);
		persistenceDao.merge(troubleFile);
	}

	public DaTroubleFile loadTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException {
		return persistenceDao.get(DaTroubleFile.class, troubleFile.getId());
	}

	public List<DaTroubleFile> loadTroubleFiles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException {
		persistenceDao.update(troubleFile);
	}

	public void mergeTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException {
		persistenceDao.merge(troubleFile);
	}	

	public void setPersistenceDao(PersistenceDao<DaTroubleFile> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
