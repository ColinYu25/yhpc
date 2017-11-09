package com.safetys.nbyhpc.domain.persistence.iface;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;

public interface DaBasePersistenceIface<T> {
	
	public void create(T entity);
	
	public void update(T entity);
	
	public void delete(T entity);
	
	/**
	 * 假删除
	 * @throws Exception
	 */
	public void deleteFake(T entity);
	
	public void marge(T entity);
	
	public T findById(long id);
	
	public Object findById(Class clazz, Serializable id);
	
	/**
	 * 根据hql查找数据，如果Pagination 参数为空，则不分页
	 * @param hql
	 * @param params
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<T> find(String hql, Object[] params, Pagination page);
	
	public List<T> find(DetachedCriteriaProxy proxy, Pagination page);
	
	public int executeBySql(String sql);
	
	public int executeBySql(String sql, Object params[]);
	
	/**
	 * 执行hql的更新语句
	 * @param hql
	 * @param params
	 * @return
	 */
	public int executeHql(String hql, Object params[]);
	
	
	/**
	 * 根据sql查询
	 * @param sql
	 * @return
	 */
	public List find(String sql);
	
	public ResultSet findBySql(String sql);
	
	/**
	 * 根据sql查询， 可携带参数
	 * @param sql
	 * @param params
	 * @return
	 */
	public List find(String sql, Object... params);

	/**
	 * 根据hql查询， 可携带参数
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<T> findByHql(String sql, Object... params);
	
}
