package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaItemSeasonReport;

public interface ItemSeasonReportPersistenceIface {
	public Long createItemSeasonReport(DaItemSeasonReport itemSeasonReport) throws ApplicationAccessException;

	public void updateItemSeasonReport(DaItemSeasonReport itemSeasonReport) throws ApplicationAccessException;

	public void mergeItemSeasonReport(DaItemSeasonReport itemSeasonReport) throws ApplicationAccessException;

	public void deleteItemSeasonReport(DaItemSeasonReport itemSeasonReport) throws ApplicationAccessException;

	public List<DaItemSeasonReport> loadItemSeasonReports(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaItemSeasonReport loadItemSeasonReport(DaItemSeasonReport itemSeasonReport) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;
}
