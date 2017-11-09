package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjIdea;
import com.safetys.nbyhpc.domain.persistence.iface.IdeaPersistenceIface;

public class IdeaPersistenceImpl implements IdeaPersistenceIface{
	public PersistenceDao<ZjIdea> persistenceDao;
	public void createIdea(ZjIdea idea){
		persistenceDao.save(idea);
	}
	public List<ZjIdea> loadIdeas(DetachedCriteriaProxy detachedCriteriaProxy){
		return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}
	public PersistenceDao<ZjIdea> getPersistenceDao() {
		return persistenceDao;
	}
	public void setPersistenceDao(PersistenceDao<ZjIdea> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
