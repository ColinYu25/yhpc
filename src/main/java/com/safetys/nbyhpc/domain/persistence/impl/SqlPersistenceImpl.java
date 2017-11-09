/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.persistence.iface.SqlPersistenceIface;
/**
 * SQL-HQL查询、修改实现类
 * @author liucj 创建时间：2011-10-25
 *
 */
public class SqlPersistenceImpl implements SqlPersistenceIface {
	
	private PersistenceDao<?> persistenceDao;
	
	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}
	
	@SuppressWarnings("deprecation")
	public void updateBySql(String sql)  throws ApplicationAccessException{
		persistenceDao.executeUpdate(sql);
	}
	
	public void updateBySql(String sql, Object[] obj) throws ApplicationAccessException{
		persistenceDao.executeSQLUpdate(sql, obj);
	}
	
	public List<?> findByHql(String hql, Pagination page) throws ApplicationAccessException {
		return persistenceDao.findPageByHQL(hql, page);
	}
	
	public List<?> findByHql(String hql, Pagination page, Object[] obj) throws ApplicationAccessException {
		return persistenceDao.findPageByHQL(hql, page, obj);
	}
	
	public void updateByHql(String hql) throws ApplicationAccessException{
		persistenceDao.executeHQLUpdate(hql);
	}
	
	public void updateByHql(String hql, Object[] obj) throws ApplicationAccessException{
		persistenceDao.executeHQLUpdate(hql, obj);
	}

	public void setPersistenceDao(PersistenceDao<?> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
	
}
