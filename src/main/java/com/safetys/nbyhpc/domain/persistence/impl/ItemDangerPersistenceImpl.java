package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaItemDanger;
import com.safetys.nbyhpc.domain.persistence.iface.ItemDangerPersistenceIface;

public class ItemDangerPersistenceImpl implements ItemDangerPersistenceIface {

	private PersistenceDao<DaItemDanger>  persistenceDao;
	
	public Long createItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException {
		return (Long) persistenceDao.save(itemDanger);
	}

	public void deleteItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException {
		persistenceDao.delete(itemDanger);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaItemDanger loadItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException {
		return persistenceDao.load(itemDanger.getClass(), itemDanger.getId());
	}

	public List<DaItemDanger> loadItemDangers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException {
		persistenceDao.merge(itemDanger);
	}

	public void updateItemDanger(DaItemDanger itemDanger) throws ApplicationAccessException {
		persistenceDao.update(itemDanger);
	}

	public PersistenceDao<DaItemDanger> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaItemDanger> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
	
}
