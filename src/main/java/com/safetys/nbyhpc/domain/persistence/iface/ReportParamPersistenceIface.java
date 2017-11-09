package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjReportParam;


public interface ReportParamPersistenceIface {
	
	public Long createReportParam(ZjReportParam reportParam) throws ApplicationAccessException;
	
	public void updateReportParam(ZjReportParam reportParam) throws ApplicationAccessException;
	
	public void mergeReportParam(ZjReportParam reportParam) throws ApplicationAccessException;
	
	public void deleteReportParam(ZjReportParam reportParam) throws ApplicationAccessException;
	
	public List<ZjReportParam> loadReportParams(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public ZjReportParam loadReportParam(ZjReportParam reportParam) throws ApplicationAccessException;
	
	public ResultSet findBySql(String sql) throws ApplicationAccessException;
}