package com.safetys.nbyhpc.domain.persistence.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class BasePersistenceImpl {

	Logger log = Logger.getLogger(this.getClass());

	private PersistenceDao persistenceDao;

	public void create(DaBaseModel entity) {
		persistenceDao.save(entity);
	}

	public void delete(DaBaseModel entity) {
		persistenceDao.delete(entity);
	}

	public List find(String hql, Object[] params, Pagination page) {
		if (page == null){
			return this.persistenceDao.executeHQLQuery(hql, params);
		}else{
			if (params == null){
				return this.persistenceDao.findPageByHQL(hql, page);
			}
			return persistenceDao.findPageByHQL(hql, page, params);
		}
	}

	public List<DaBaseModel> find(DetachedCriteriaProxy proxy, Pagination page) {
		if (page == null){
			return persistenceDao.findAllByCriteria(proxy);	
		}else{
			return persistenceDao.findPageByCriteria(proxy, page);
		}
	}


	public Object findById(Class clazz, Serializable id) {
		return persistenceDao.get(clazz, id);
	}


	public void update(DaBaseModel entity) {
		persistenceDao.update(entity);
	}

	
	public void createAll(List entities) throws Exception {
		if (entities == null || entities.size() == 0){
			return ;
		}
		for (Object obj : entities) {
			persistenceDao.save(obj);
		}
	}

	public void updateAll(List entities) throws Exception {
		if (entities == null || entities.size() == 0){
			return ;
		}
		for (Object obj : entities) {
			persistenceDao.update(obj);
		}
	}
	
	public void mergeAll(List entities) throws Exception {
		if (entities == null || entities.size() == 0){
			return ;
		}
		for (Object obj : entities) {
			persistenceDao.merge(obj);
		}
	}

	
	public int executeHql(String hql, Object[] params) {
		return persistenceDao.executeHQLUpdate(hql, params);
	}

	public List find(String sql){
		return persistenceDao.executeSQLQuery(sql);
	}
	public void marge(DaBaseModel entity) throws Exception {
		persistenceDao.merge(entity);
	}

	public PersistenceDao<DaBaseModel> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaBaseModel> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
	
	public int executeBySql(String sql){
		return this.getPersistenceDao().executeSQLUpdate(sql);
	}
	
	public int executeBySql(String sql, Object params[]){
		return this.getPersistenceDao().executeSQLUpdate(sql, params);
	}

	public void deleteAll(List entities) throws Exception {
		if (entities == null || entities.size() == 0){
			return ;
		}
		for (Object obj : entities) {
			persistenceDao.delete(obj);
		}
	}

	public ResultSet findBySql(String sql) {
		return persistenceDao.findBySql(sql);
	}

}
