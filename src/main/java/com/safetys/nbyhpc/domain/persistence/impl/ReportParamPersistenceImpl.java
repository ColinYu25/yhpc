package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjReportParam;
import com.safetys.nbyhpc.domain.persistence.iface.ReportParamPersistenceIface;

public class ReportParamPersistenceImpl implements ReportParamPersistenceIface {
	private PersistenceDao<ZjReportParam> persistenceDao;

	public Long createReportParam(ZjReportParam reportParam) throws ApplicationAccessException {
		return (Long) persistenceDao.save(reportParam);
	}

	public void deleteReportParam(ZjReportParam reportParam) throws ApplicationAccessException {
		persistenceDao.delete(reportParam);
	}

	public ZjReportParam loadReportParam(ZjReportParam reportParam) throws ApplicationAccessException {
		return persistenceDao.get(ZjReportParam.class, reportParam.getId());
	}

	public List<ZjReportParam> loadReportParams(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if(pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateReportParam(ZjReportParam reportParam) throws ApplicationAccessException {
		persistenceDao.update(reportParam);
	}
	
	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public void mergeReportParam(ZjReportParam reportParam) throws ApplicationAccessException {
		persistenceDao.merge(reportParam);
	}	

	public void setPersistenceDao(PersistenceDao<ZjReportParam> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
