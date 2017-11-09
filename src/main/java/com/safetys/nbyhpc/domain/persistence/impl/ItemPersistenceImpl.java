package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.persistence.iface.ItemPersistenceIface;

public class ItemPersistenceImpl implements ItemPersistenceIface{

	private PersistenceDao<DaItem> persistenceDao;
	
	public Long createItem(DaItem item) throws ApplicationAccessException {
		return (Long) persistenceDao.save(item);
	}

	public void deleteItem(DaItem item) throws ApplicationAccessException {
		item=loadItem(item);
		item.setDeleted(true);
		persistenceDao.merge(item);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaItem loadItem(DaItem item) throws ApplicationAccessException {
		return persistenceDao.load(item.getClass(), item.getId());
	}

	public List<DaItem> loadItems(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeItem(DaItem item) throws ApplicationAccessException {
		persistenceDao.merge(item);
	}

	public void updateItem(DaItem item) throws ApplicationAccessException {
		persistenceDao.update(item);
	}

	public PersistenceDao<DaItem> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaItem> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
