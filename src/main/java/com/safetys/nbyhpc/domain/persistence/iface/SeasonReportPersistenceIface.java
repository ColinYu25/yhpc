package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaSeasonReport;

public interface SeasonReportPersistenceIface {

	public Long createSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException;

	public void updateSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException;

	public void mergeSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException;

	public void deleteSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException;

	public List<DaSeasonReport> loadSeasonReports(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public DaSeasonReport loadSeasonReport(DaSeasonReport seasonReport)
			throws ApplicationAccessException;
	
	
	public void cacheClear();
}