package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaSeasonReport;
import com.safetys.nbyhpc.domain.persistence.iface.SeasonReportPersistenceIface;

public class SeasonReportPersistenceImpl implements
		SeasonReportPersistenceIface {
	private PersistenceDao<DaSeasonReport> persistenceDao;

	public Long createSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(seasonReport);
	}

	public void deleteSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException {
		seasonReport = loadSeasonReport(seasonReport);
		seasonReport.setDeleted(true);
		persistenceDao.merge(seasonReport);
	}

	public DaSeasonReport loadSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException {
		return persistenceDao.get(DaSeasonReport.class, seasonReport.getId());
	}

	public List<DaSeasonReport> loadSeasonReports(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException {
		persistenceDao.update(seasonReport);
	}

	public void mergeSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException {
		persistenceDao.merge(seasonReport);
	}


	public void setPersistenceDao(PersistenceDao<DaSeasonReport> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public void cacheClear() {
		persistenceDao.clearCache();

	}

}
