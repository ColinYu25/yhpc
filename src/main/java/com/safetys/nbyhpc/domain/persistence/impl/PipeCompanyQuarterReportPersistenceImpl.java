package com.safetys.nbyhpc.domain.persistence.impl;

import java.sql.ResultSet;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaCompanyAcount;
import com.safetys.nbyhpc.domain.model.DaPipeCompanyQuarterReprot;
import com.safetys.nbyhpc.domain.persistence.iface.PipeCompanyQuarterReportPersistenceIface;

public class PipeCompanyQuarterReportPersistenceImpl implements PipeCompanyQuarterReportPersistenceIface {

	private PersistenceDao<DaPipeCompanyQuarterReprot> persistenceDao;
	
	public Long create(DaPipeCompanyQuarterReprot entity)
			throws ApplicationAccessException {
		return (Long) persistenceDao.save(entity);
	}

	public void update(DaPipeCompanyQuarterReprot entity) throws ApplicationAccessException {
		persistenceDao.update(entity);
	}
	
	public void delete(DaPipeCompanyQuarterReprot entity)
			throws ApplicationAccessException {
		entity = findById(entity.getId());
		entity.setDeleted(true);
		persistenceDao.merge(entity);
	}

	public ResultSet findBySql(String sql) throws ApplicationAccessException {
		return persistenceDao.findBySql(sql);
	}

	public DaPipeCompanyQuarterReprot findById(Long id)
			throws ApplicationAccessException {
		return (DaPipeCompanyQuarterReprot)persistenceDao.load(DaPipeCompanyQuarterReprot.class, id);
	}

	public List<DaPipeCompanyQuarterReprot> find(
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

	public void mergeCompanyAcount(DaPipeCompanyQuarterReprot entity)
			throws ApplicationAccessException {
		persistenceDao.merge(entity);
	}

	public void updateCompanyAcount(DaCompanyAcount daCompanyAcount)
			throws ApplicationAccessException {
		persistenceDao.update(daCompanyAcount);
	}

	public PersistenceDao<DaPipeCompanyQuarterReprot> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<DaPipeCompanyQuarterReprot> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
