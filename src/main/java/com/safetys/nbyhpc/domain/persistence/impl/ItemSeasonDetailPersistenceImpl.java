package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaItemSeasonDetail;
import com.safetys.nbyhpc.domain.persistence.iface.ItemSeasonDetailPersistenceIface;

public class ItemSeasonDetailPersistenceImpl implements ItemSeasonDetailPersistenceIface{

	private PersistenceDao<DaItemSeasonDetail> persistenceDao;
	
	public Long createItemSeasonDetail(DaItemSeasonDetail itemSeasonDetail) throws ApplicationAccessException {
		return (Long) persistenceDao.save(itemSeasonDetail);
	}

	public void deleteItemSeasonDetail(DaItemSeasonDetail itemSeasonDetail) throws ApplicationAccessException {
//		itemSeasonDetail=loadItemSeasonDetail(itemSeasonDetail);
//		itemSeasonDetail.setDeleted(true);
//		persistenceDao.merge(itemSeasonDetail);
		persistenceDao.delete(itemSeasonDetail);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaItemSeasonDetail loadItemSeasonDetail(DaItemSeasonDetail itemSeasonDetail) throws ApplicationAccessException {
		return persistenceDao.load(itemSeasonDetail.getClass(), itemSeasonDetail.getId());
	}

	public List<DaItemSeasonDetail> loadItemSeasonDetails(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeItemSeasonDetail(DaItemSeasonDetail itemSeasonDetail) throws ApplicationAccessException {
		persistenceDao.merge(itemSeasonDetail);
		
	}

	public void updateItemSeasonDetail(DaItemSeasonDetail itemSeasonDetail) throws ApplicationAccessException {
		persistenceDao.update(itemSeasonDetail);
		
	}

	public PersistenceDao<DaItemSeasonDetail> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaItemSeasonDetail> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
