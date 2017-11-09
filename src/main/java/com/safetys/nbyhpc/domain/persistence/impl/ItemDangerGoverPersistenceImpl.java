package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaItemDangerGover;
import com.safetys.nbyhpc.domain.persistence.iface.ItemDangerGoverPersistenceIface;

public class ItemDangerGoverPersistenceImpl implements
		ItemDangerGoverPersistenceIface {

	private PersistenceDao<DaItemDangerGover> persistenceDao;
	
	public Long createItemDangerGover(DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException {
		persistenceDao.save(itemDangerGover);
		return null;
	}

	public void deleteItemDangerGover(DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException {
		itemDangerGover=loadItemDangerGover(itemDangerGover);
		itemDangerGover.setDeleted(true);
		persistenceDao.merge(itemDangerGover);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaItemDangerGover loadItemDangerGover(
			DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException {
		return persistenceDao.load(itemDangerGover.getClass(), itemDangerGover.getId());
	}

	public List<DaItemDangerGover> loadItemDangerGovers(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeItemDangerGover(DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException {
		persistenceDao.merge(itemDangerGover);
	}

	public void updateItemDangerGover(DaItemDangerGover itemDangerGover)
			throws ApplicationAccessException {
		persistenceDao.update(itemDangerGover);
	}

	public PersistenceDao<DaItemDangerGover> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaItemDangerGover> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
