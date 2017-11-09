package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaItemSeasonReport;
import com.safetys.nbyhpc.domain.persistence.iface.ItemSeasonReportPersistenceIface;

public class ItemSeasonReportPersistenceImpl implements ItemSeasonReportPersistenceIface{

	private PersistenceDao<DaItemSeasonReport> persistenceDao;
	
	public Long createItemSeasonReport(DaItemSeasonReport ItemSeasonReport) throws ApplicationAccessException {
		return (Long) persistenceDao.save(ItemSeasonReport);
	}

	public void deleteItemSeasonReport(DaItemSeasonReport ItemSeasonReport) throws ApplicationAccessException {
		ItemSeasonReport=loadItemSeasonReport(ItemSeasonReport);
		ItemSeasonReport.setDeleted(true);
		persistenceDao.merge(ItemSeasonReport);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaItemSeasonReport loadItemSeasonReport(DaItemSeasonReport ItemSeasonReport) throws ApplicationAccessException {
		return persistenceDao.load(ItemSeasonReport.getClass(), ItemSeasonReport.getId());
	}

	public List<DaItemSeasonReport> loadItemSeasonReports(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void mergeItemSeasonReport(DaItemSeasonReport ItemSeasonReport) throws ApplicationAccessException {
		persistenceDao.merge(ItemSeasonReport);
		
	}

	public void updateItemSeasonReport(DaItemSeasonReport ItemSeasonReport) throws ApplicationAccessException {
		persistenceDao.update(ItemSeasonReport);
		
	}

	public PersistenceDao<DaItemSeasonReport> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaItemSeasonReport> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
