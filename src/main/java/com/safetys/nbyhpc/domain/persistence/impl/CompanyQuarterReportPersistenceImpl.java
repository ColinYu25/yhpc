 package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyAcount;
import com.safetys.nbyhpc.domain.model.DaCompanyQuarterReprot;

public class CompanyQuarterReportPersistenceImpl {

	private PersistenceDao<DaCompanyQuarterReprot> persistenceDao;
	
	public Long create(DaCompanyQuarterReprot entity)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(entity);
	}

	public void update(DaCompanyQuarterReprot entity) throws ApplicationAccessException {
		persistenceDao.update(entity);
	}
	
	public void delete(DaCompanyQuarterReprot entity)
			throws ApplicationAccessException {
		entity = findById(entity.getId());
		entity.setDeleted(true);
		persistenceDao.merge(entity);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaCompanyQuarterReprot findById(Long id)
			throws ApplicationAccessException {
		return (DaCompanyQuarterReprot)persistenceDao.load(DaCompanyQuarterReprot.class, id);
	}

	public List<DaCompanyQuarterReprot> find(
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

	public void mergeCompanyAcount(DaCompanyQuarterReprot entity)
			throws ApplicationAccessException {
		persistenceDao.merge(entity);
	}

	public void updateCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException {
		persistenceDao.update(daCompanyAcount);
	}

	public PersistenceDao<DaCompanyQuarterReprot> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaCompanyQuarterReprot> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
