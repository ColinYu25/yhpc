package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaStatistic;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.persistence.iface.StatisticPersistenceIface;

public class StatisticPersistenceImpl implements StatisticPersistenceIface {
	private PersistenceDao<DaStatistic> persistenceDao;

	public Long createStatistic(DaStatistic statistic)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(statistic);
	}

	public void deleteStatistic(DaStatistic statistic)
			throws ApplicationAccessException {
		statistic = loadStatistic(statistic);
		statistic.setDeleted(true);
		persistenceDao.merge(statistic);
	}

	public DaStatistic loadStatistic(DaStatistic statistic)
			throws ApplicationAccessException {
		return persistenceDao.get(Statistic.class, statistic.getId());
	}

	public List<DaStatistic> loadStatistics(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateStatistic(DaStatistic statistic)
			throws ApplicationAccessException {
		persistenceDao.update(statistic);

	}

	/**
	 * @return the persistenceDao
	 */
	public PersistenceDao<DaStatistic> getPersistenceDao() {
		return persistenceDao;
	}

	/**
	 * @param persistenceDao the persistenceDao to set
	 */
	public void setPersistenceDao(PersistenceDao<DaStatistic> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
