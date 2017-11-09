package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaStatistic;

public interface StatisticPersistenceIface {

	public Long createStatistic(DaStatistic statistic) throws ApplicationAccessException;

	public void updateStatistic(DaStatistic statistic) throws ApplicationAccessException;

	public void deleteStatistic(DaStatistic statistic) throws ApplicationAccessException;

	public List<DaStatistic> loadStatistics(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;

	public DaStatistic loadStatistic(DaStatistic statistic) throws ApplicationAccessException;
}