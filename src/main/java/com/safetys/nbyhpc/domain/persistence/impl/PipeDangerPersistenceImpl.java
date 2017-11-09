package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.persistence.iface.PipeDangerPersistenceIface;

public class PipeDangerPersistenceImpl implements PipeDangerPersistenceIface {
	private PersistenceDao<DaPipeDanger> persistenceDao;

	public Long createDanger(DaPipeDanger danger) throws ApplicationAccessException {
		return (Long) persistenceDao.save(danger);
	}

	public void deleteDanger(DaPipeDanger danger) throws ApplicationAccessException {
		danger = loadDanger(danger);
		danger.setDeleted(true);
		persistenceDao.merge(danger);
	}

	public DaPipeDanger loadDanger(DaPipeDanger danger) throws ApplicationAccessException {
		return persistenceDao.get(DaPipeDanger.class, danger.getId());
	}

	public List<DaPipeDanger> loadDangers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		List<DaPipeDanger> list = null;
		try{
			if(pagination != null)
				list = persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
			else
				list = persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		}catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public void updateDanger(DaPipeDanger danger) throws ApplicationAccessException {
		persistenceDao.update(danger);
	}

	public void mergeDanger(DaPipeDanger danger) throws ApplicationAccessException {
		persistenceDao.merge(danger);
	}	

	public void setPersistenceDao(PersistenceDao<DaPipeDanger> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
