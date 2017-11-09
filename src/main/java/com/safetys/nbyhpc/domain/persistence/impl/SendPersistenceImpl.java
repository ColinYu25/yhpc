package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjSend;
import com.safetys.nbyhpc.domain.persistence.iface.SendPersistenceIface;

public class SendPersistenceImpl implements SendPersistenceIface {
	private PersistenceDao<ZjSend> persistenceDao; 

	public Long createSend(ZjSend send) throws ApplicationAccessException {
		return (Long) persistenceDao.save(send);
	}
	
	public void uptSend(ZjSend send) throws ApplicationAccessException {
		persistenceDao.update(send);
	}

	public ZjSend loadSend(ZjSend send) throws ApplicationAccessException {
		return persistenceDao.get(ZjSend.class, send.getId());
	}

	public List<ZjSend> loadSends(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void setPersistenceDao(PersistenceDao<ZjSend> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
