package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaActualTableNotice;

public interface ActualizeTableNoticePersistenceIface {
	public Long createDaActualTableNotice(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException;

	public void updateDaActualTableNotice(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException;

	public void mergeDaActualTableNotice(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException;

	public void deleteDaActualTableNotice(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException;

	public List<DaActualTableNotice> loadDaActualTableNotices(DetachedCriteriaProxy detachedCriteriaProxy,
			Pagination pagination) throws ApplicationAccessException;

	public DaActualTableNotice loadDaActualTableNotice(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

}
