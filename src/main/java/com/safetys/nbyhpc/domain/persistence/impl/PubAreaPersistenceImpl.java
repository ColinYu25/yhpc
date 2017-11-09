package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.TCoreArea;
import com.safetys.nbyhpc.domain.persistence.iface.PubAreaPersistenceIface;

public class PubAreaPersistenceImpl implements PubAreaPersistenceIface {
	private PersistenceDao<TCoreArea> persistenceDao;
	
	public List<TCoreArea> executeHQLQuery(String hql, Object[] values) {
		return (List<TCoreArea>) persistenceDao.executeHQLQuery(hql, values);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public int executeSQLUpdate(final String sql) {
		return executeSQLUpdate(sql, null);
	}

	public int executeSQLUpdate(final String sql, final Object[] values) {
		return persistenceDao.executeSQLUpdate(sql, values);
	}

	public void executeHQLUpdate(String hql) throws ApplicationAccessException {
		persistenceDao.executeHQLUpdate(hql);
	}

	public void setPersistenceDao(PersistenceDao<TCoreArea> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
