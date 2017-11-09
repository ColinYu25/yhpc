/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
/**
 * SQL-HQL查询、修改接口类
 * @author liucj 创建时间：2011-10-25
 *
 */
public interface SqlPersistenceIface {
	/**
	 * SQL查询
	 * @param sql
	 * @return
	 * @throws ApplicationAccessException
	 */
	public ResultSet findBySql(String sql) throws ApplicationAccessException;
	
	/**
	 * SQL修改接口
	 * @param sql
	 * @throws ApplicationAccessException
	 */
	public void updateBySql(String sql) throws ApplicationAccessException;
	
	/**
	 * SQL修改接口（可带参数）
	 * @param sql
	 * @param obj
	 * @throws ApplicationAccessException
	 */
	public void updateBySql(String sql, Object[] obj)
			throws ApplicationAccessException;
	
	/**
	 * HQL带分页查询
	 * @param hql
	 * @param page
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<?> findByHql(String hql, Pagination page) throws ApplicationAccessException;
	
	/**
	 * HQL带分页查询（可带参数）
	 * @param hql
	 * @param page
	 * @param obj
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<?> findByHql(String hql, Pagination page, Object[] obj) throws ApplicationAccessException;
	/**
	 * HQL修改接口
	 * @param hql
	 * @throws ApplicationAccessException
	 */
	public void updateByHql(String hql) throws ApplicationAccessException;
	
	/**
	 * HQL修改接口（可带参数）
	 * @param hql
	 * @param obj
	 * @throws ApplicationAccessException
	 */
	public void updateByHql(String hql, Object[] obj)
			throws ApplicationAccessException;

}