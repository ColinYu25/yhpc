package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaSeasonReportDetail;

public interface SeasonReportDetailPersistenceIface {

	public Long createSeasonReportDetail(DaSeasonReportDetail seasonReportDetail)
			throws ApplicationAccessException;

	public void updateSeasonReportDetail(DaSeasonReportDetail seasonReportDetail)
			throws ApplicationAccessException;

	public void mergeSeasonReportDetail(DaSeasonReportDetail seasonReportDetail)
			throws ApplicationAccessException;

	public void deleteSeasonReportDetail(DaSeasonReportDetail seasonReportDetail)
			throws ApplicationAccessException;

	public List<DaSeasonReportDetail> loadSeasonReportDetails(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public DaSeasonReportDetail loadSeasonReportDetail(DaSeasonReportDetail seasonReportDetail)
			throws ApplicationAccessException;
}