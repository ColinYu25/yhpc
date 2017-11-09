package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.TCoreArea;

public interface PubAreaPersistenceIface {
	
	public List<TCoreArea> executeHQLQuery(String hql, Object values[]);
	
	public void executeHQLUpdate(String hql) throws ApplicationAccessException;

	public ResultSet findBySql(String sql) throws ApplicationAccessException;

	public int executeSQLUpdate(final String sql);

	public int executeSQLUpdate(final String sql, final Object[] values);
}
