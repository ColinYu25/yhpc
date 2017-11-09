package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaSeasonReportOther;


public interface SeasonReportOtherPersistenceIface {
	
	public Long createSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException;
	
	public void updateSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException;
	
	public void mergeSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException;
	
	public void deleteSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException;
	
	public List<DaSeasonReportOther> loadSeasonReportOthers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaSeasonReportOther loadSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException;
	
	public ResultSet findBySql(String sql) throws ApplicationAccessException;
}