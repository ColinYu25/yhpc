package com.safetys.nbyhpc.domain.persistence.iface;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.TCaches;


public interface TCachePersistenceIface {
	
	public Long createTCaches(TCaches tcaches) throws ApplicationAccessException;
	
	public void updateTCaches(TCaches tcaches) throws ApplicationAccessException;
	
	public void mergeTCaches(TCaches tcaches) throws ApplicationAccessException;
	
	public List<TCaches> loadTCaches(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public TCaches loadTCaches(TCaches tcaches) throws ApplicationAccessException;
	
	public ResultSet findBySql(String sql) throws ApplicationAccessException;

	public void executeHQLUpdate(String hql) throws ApplicationAccessException;
	
	public void  executeSQLUpdate(String sql) throws ApplicationAccessException;
}