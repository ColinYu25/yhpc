package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;

public interface NomalDangerPersistenceIface {
	public DaNomalDanger loadNomalDanger(DaNomalDanger daNomalDanger)
			throws ApplicationAccessException;

	public List<DaNomalDanger> loadNomalDangers(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public Long createNomalDanger(DaNomalDanger daNomalDanger)
			throws ApplicationAccessException;

	public void updateNomalDanger(DaNomalDanger daNomalDanger)
			throws ApplicationAccessException;

	public void mergeNomalDanger(DaNomalDanger daNomalDanger)
			throws ApplicationAccessException;

	public void deleteNomalDanger(DaNomalDanger daNomalDanger)
			throws ApplicationAccessException;
	public int executeUpdate(String hql) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;
	
	public List<DaNomalDanger> loadNomalDangers(String hql, Pagination pagination, Object[] params);

}
