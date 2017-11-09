package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaSeasonReportOther;
import com.safetys.nbyhpc.domain.persistence.iface.SeasonReportOtherPersistenceIface;

public class SeasonReportOtherPersistenceImpl implements SeasonReportOtherPersistenceIface {
	private PersistenceDao<DaSeasonReportOther> persistenceDao;
	
	public ResultSet findBySql(String sql) throws ApplicationAccessException{
		return persistenceDao.findBySql(sql);
	}

	public Long createSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException {
		return (Long) persistenceDao.save(seasonReportOther);
	}

	public void deleteSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException {
		seasonReportOther = loadSeasonReportOther(seasonReportOther);
		seasonReportOther.setDeleted(true);
		persistenceDao.merge(seasonReportOther);
	}

	public DaSeasonReportOther loadSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException {
		return persistenceDao.get(DaSeasonReportOther.class, seasonReportOther.getId());
	}

	public List<DaSeasonReportOther> loadSeasonReportOthers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException {
		persistenceDao.update(seasonReportOther);
	}

	public void mergeSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException {
		persistenceDao.merge(seasonReportOther);
	}	

	public void setPersistenceDao(PersistenceDao<DaSeasonReportOther> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
