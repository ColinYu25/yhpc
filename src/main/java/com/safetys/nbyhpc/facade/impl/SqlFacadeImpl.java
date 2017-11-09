/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.facade.impl;

import java.util.ArrayList;
import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.persistence.iface.SqlPersistenceIface;
import com.safetys.nbyhpc.facade.iface.SqlFacadeIface;
/**
 * SQL-HQL查询、修改实现类
 * @author liucj 创建时间：2011-10-25
 *
 */
public class SqlFacadeImpl implements SqlFacadeIface{
	
	private SqlPersistenceIface sqlPersistenceIface;
	
	public boolean updateSqlVal(String tableName, String fieldName, int id, String value) throws ApplicationAccessException{
		boolean b = false;
		String sql = "update " + tableName + " set " + fieldName + "='" + value +"' where id=" + id;
		try {
			sqlPersistenceIface.updateBySql(sql, null);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			//throw e;
		}
		return b;
	}
	
	public boolean updateHqlVal(String objName, String fieldName, int id, String value) throws ApplicationAccessException{
		boolean b = false;
		String hql = "update " + objName + " obj set obj." + fieldName + "='" + value.trim() +"' where id=" + id;
		try {
			sqlPersistenceIface.updateByHql(hql);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			//throw e;
		}
		return b;
	}
	
	public void setSqlPersistenceIface(SqlPersistenceIface sqlPersistenceIface) {
		this.sqlPersistenceIface = sqlPersistenceIface;
	}
}
