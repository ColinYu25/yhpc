package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaTrouble;
import com.safetys.nbyhpc.domain.persistence.iface.TroublePersistenceIface;

public class TroublePersistenceImpl implements TroublePersistenceIface {
	private PersistenceDao<DaTrouble> persistenceDao;

	public Long createTrouble(DaTrouble trouble) throws ApplicationAccessException {
		return (Long) persistenceDao.save(trouble);
	}

	public void deleteTrouble(DaTrouble trouble) throws ApplicationAccessException {
		trouble = loadTrouble(trouble);
		trouble.setDeleted(true);
		persistenceDao.merge(trouble);
	}

	public DaTrouble loadTrouble(DaTrouble trouble) throws ApplicationAccessException {
		return persistenceDao.get(DaTrouble.class, trouble.getId());
	}

	public List<DaTrouble> loadTroubles(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateTrouble(DaTrouble trouble) throws ApplicationAccessException {
		persistenceDao.update(trouble);
	}

	public void mergeTrouble(DaTrouble trouble) throws ApplicationAccessException {
		persistenceDao.merge(trouble);
	}	

	public void setPersistenceDao(PersistenceDao<DaTrouble> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
