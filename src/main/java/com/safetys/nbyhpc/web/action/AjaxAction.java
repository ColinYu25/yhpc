/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.facade.iface.SqlFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @author liucj 创建时间：2011-10-25
 * Ajax实现操作类
 */
public class AjaxAction extends DaAppAction{
	
	private static final long serialVersionUID = 1L;
	
	private SqlFacadeIface sqlFacadeIface;
	/**
	 * 表或对象名称
	 */
	private String tableName;
	/**
	 * 字段或属性名称
	 */
	private String fieldName;
	/**
	 * 数据ID
	 */
	private int id;
	/**
	 * 修改的值
	 */
	private String value;
	
	public String update(){
		try {
			PrintWriter out = this.getResponse().getWriter();
			//boolean b = sqlFacadeIface.updateSqlVal(tableName, fieldName, id, value);
			if(value.equals("true") || value.equals("false")){
				value = value.equals("true") ? "1" : "0";
			}
			boolean b = sqlFacadeIface.updateHqlVal(tableName, fieldName, id, value);
			out.print(b);
		}catch (IOException e) {
			e.printStackTrace();
		}catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setSqlFacadeIface(SqlFacadeIface sqlFacadeIface) {
		this.sqlFacadeIface = sqlFacadeIface;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
