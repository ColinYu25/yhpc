package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaActualTableNotice;
import com.safetys.nbyhpc.domain.persistence.iface.ActualizeTableNoticePersistenceIface;

public class ActualizeTableNoticePersistenceImpl implements ActualizeTableNoticePersistenceIface{

	private PersistenceDao<DaActualTableNotice> persistenceDao;
	
	public Long createDaActualTableNotice(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException {
		return (Long)persistenceDao.save(daActualTableNotice);
	}

	public void deleteDaActualTableNotice(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException {
		daActualTableNotice=loadDaActualTableNotice(daActualTableNotice);
		daActualTableNotice.setDeleted(true);
		persistenceDao.merge(daActualTableNotice);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaActualTableNotice loadDaActualTableNotice(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException {
		return persistenceDao.load(daActualTableNotice.getClass(), daActualTableNotice.getId());
	}

	public List<DaActualTableNotice> loadDaActualTableNotices(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeDaActualTableNotice(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException {
		persistenceDao.merge(daActualTableNotice);
	}

	public void updateDaActualTableNotice(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException {
		persistenceDao.update(daActualTableNotice);
	}

	public PersistenceDao<DaActualTableNotice> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaActualTableNotice> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
