package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaNomalDanger1;

public interface NomalDanger1PersistenceIface {
	public DaNomalDanger1 loadNomalDanger(DaNomalDanger1 DaNomalDanger1)
			throws ApplicationAccessException;

	public List<DaNomalDanger1> loadNomalDangers(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public Long createNomalDanger(DaNomalDanger1 DaNomalDanger1)
			throws ApplicationAccessException;

	public void updateNomalDanger(DaNomalDanger1 DaNomalDanger1)
			throws ApplicationAccessException;

	public void mergeNomalDanger(DaNomalDanger1 DaNomalDanger1)
			throws ApplicationAccessException;

	public void deleteNomalDanger(DaNomalDanger1 DaNomalDanger1)
			throws ApplicationAccessException;
	public int executeUpdate(String hql) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

}
