package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjDanger;
import com.safetys.nbyhpc.domain.persistence.iface.ZjDangerPersistenceIface;

public class ZjDangerPersistenceImpl implements ZjDangerPersistenceIface {
	private PersistenceDao<ZjDanger> persistenceDao;

	public Long createZjDanger(ZjDanger danger) throws ApplicationAccessException {
		return (Long) persistenceDao.save(danger);
	}

	public ZjDanger loadZjDanger(ZjDanger danger) throws ApplicationAccessException {
		return persistenceDao.get(ZjDanger.class, danger.getId());
	}

	public List<ZjDanger> loadZjDangers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateZjDanger(ZjDanger danger) throws ApplicationAccessException {
		persistenceDao.update(danger);
	}
	
	public void mergeZjDanger(ZjDanger danger) throws ApplicationAccessException {
		persistenceDao.merge(danger);
	}	

	public void setPersistenceDao(PersistenceDao<ZjDanger> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
