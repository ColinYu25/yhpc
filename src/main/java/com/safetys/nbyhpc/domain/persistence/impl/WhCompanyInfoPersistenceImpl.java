package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.WhCompanyInfo;
import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public class WhCompanyInfoPersistenceImpl {

	private PersistenceDao<WhCompanyInfo> persistenceDao;
	
	public Long create(DaBaseModel entity)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(entity);
	}

	public void update(DaBaseModel entity) throws ApplicationAccessException {
		persistenceDao.update(entity);
	}
	
	public void delete(WhCompanyInfo entity)
			throws ApplicationAccessException {
		entity = findById(entity.getId());
		entity.setIsSynchro(1);
		entity.setDeleted(true);
		persistenceDao.merge(entity);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public WhCompanyInfo findById(Long id)
			throws ApplicationAccessException {
		return (WhCompanyInfo)persistenceDao.load(WhCompanyInfo.class, id);
	}

	public Object findById(Class clazz, long id){
		return persistenceDao.load(clazz, id);
	}
	
	public List<WhCompanyInfo> find(
			DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination)
			throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
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

	public void executeHql(String hql, Object[] params) {
		if (params == null){
			this.persistenceDao.executeHQLUpdate(hql);
		}
		persistenceDao.executeHQLUpdate(hql, params);
	}
	
	public void mergeCompanyAcount(WhCompanyInfo entity)
			throws ApplicationAccessException {
		persistenceDao.merge(entity);
	}

	public PersistenceDao<WhCompanyInfo> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<WhCompanyInfo> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
