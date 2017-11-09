package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPipeNomalDanger;

public interface PipeNomalDangerPersistenceIface {
	public DaPipeNomalDanger loadNomalDanger(DaPipeNomalDanger DaPipeNomalDanger)
			throws ApplicationAccessException;

	public List<DaPipeNomalDanger> loadNomalDangers(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException;

	public Long createNomalDanger(DaPipeNomalDanger DaPipeNomalDanger)
			throws ApplicationAccessException;

	public void updateNomalDanger(DaPipeNomalDanger DaPipeNomalDanger)
			throws ApplicationAccessException;

	public void mergeNomalDanger(DaPipeNomalDanger DaPipeNomalDanger)
			throws ApplicationAccessException;

	public void deleteNomalDanger(DaPipeNomalDanger DaPipeNomalDanger)
			throws ApplicationAccessException;
	public int executeUpdate(String hql) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

}
