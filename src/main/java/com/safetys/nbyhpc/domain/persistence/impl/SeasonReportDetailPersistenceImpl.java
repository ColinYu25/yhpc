package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaSeasonReportDetail;
import com.safetys.nbyhpc.domain.persistence.iface.SeasonReportDetailPersistenceIface;

public class SeasonReportDetailPersistenceImpl implements
		SeasonReportDetailPersistenceIface {
	private PersistenceDao<DaSeasonReportDetail> persistenceDao;

	public Long createSeasonReportDetail(DaSeasonReportDetail seasonReportDetail)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(seasonReportDetail);
	}

	public void deleteSeasonReportDetail(DaSeasonReportDetail seasonReportDetail)
			throws ApplicationAccessException {
		seasonReportDetail = loadSeasonReportDetail(seasonReportDetail);
		seasonReportDetail.setDeleted(true);
		persistenceDao.merge(seasonReportDetail);
	}

	public DaSeasonReportDetail loadSeasonReportDetail(
			DaSeasonReportDetail seasonReportDetail)
			throws ApplicationAccessException {
		return persistenceDao.get(DaSeasonReportDetail.class,
				seasonReportDetail.getId());
	}

	public List<DaSeasonReportDetail> loadSeasonReportDetails(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateSeasonReportDetail(DaSeasonReportDetail seasonReportDetail)
			throws ApplicationAccessException {
		persistenceDao.update(seasonReportDetail);
	}

	public void mergeSeasonReportDetail(DaSeasonReportDetail seasonReportDetail)
			throws ApplicationAccessException {
		persistenceDao.merge(seasonReportDetail);
	}

	public void setPersistenceDao(
			PersistenceDao<DaSeasonReportDetail> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
