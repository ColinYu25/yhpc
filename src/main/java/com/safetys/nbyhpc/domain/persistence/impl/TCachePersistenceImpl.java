package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.TCaches;
import com.safetys.nbyhpc.domain.persistence.iface.TCachePersistenceIface;

public class TCachePersistenceImpl implements TCachePersistenceIface {
	private PersistenceDao<TCaches> persistenceDao;

	public Long createTCaches(TCaches tcaches) throws ApplicationAccessException {
		return (Long) persistenceDao.save(tcaches);
	}

	public TCaches loadTCaches(TCaches tcaches) throws ApplicationAccessException {
		return persistenceDao.get(TCaches.class, tcaches.getId());
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public void executeHQLUpdate(String hql) throws ApplicationAccessException {
		persistenceDao.executeHQLUpdate(hql);
	}

	public void executeSQLUpdate(String sql) throws ApplicationAccessException {

		persistenceDao.executeSQLQuery(sql);
	}

	public List<TCaches> loadTCaches(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		List<TCaches> list = null;
		try {
			if (pagination != null)
				list = persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
			else
				list = persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void updateTCaches(TCaches tcaches) throws ApplicationAccessException {
		persistenceDao.update(tcaches);
		persistenceDao.merge(tcaches);
	}

	public void mergeTCaches(TCaches tcaches) throws ApplicationAccessException {
		persistenceDao.merge(tcaches);
	}

	public void setPersistenceDao(PersistenceDao<TCaches> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
