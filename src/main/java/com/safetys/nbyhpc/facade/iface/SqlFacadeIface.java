/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.facade.iface;

import com.safetys.framework.exception.ApplicationAccessException;
/**
 * SQL-HQL查询、修改接口类
 * @author liucj 创建时间：2011-10-25
 *
 */
public interface SqlFacadeIface {
	/**
	 * SQL语句根据条件修改字段值
	 * @param tableName 表名
	 * @param fieldName 字段名
	 * @param id 数据ID
	 * @param value　修改的值
	 * @throws ApplicationAccessException
	 */
	public boolean updateSqlVal(String tableName, String fieldName, int id,
			String value) throws ApplicationAccessException;
	
	/**
	 * HQL语句根据条件修改字段值
	 * @param objName 对象名
	 * @param fieldName 属性名
	 * @param id 数据ID
	 * @param value 修改的值
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean updateHqlVal(String objName, String fieldName, int id, String value) throws ApplicationAccessException;

}