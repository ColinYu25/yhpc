package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.Date;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.persistence.iface.MinePersistenceIface;

public class MinePersistenceImpl implements MinePersistenceIface{

	private PersistenceDao<ZjMineReport> persistenceDao;	

	public long createMineReport(ZjMineReport mineReport) throws ApplicationAccessException {		
		return (Long)persistenceDao.save(mineReport);
	}

	public void deleteMineReport(ZjMineReport mineReport) throws ApplicationAccessException {
		mineReport = loadMineReport(mineReport.getId());
		mineReport.setModifyTime(new Date());
		mineReport.setDeleted(true);
		persistenceDao.merge(mineReport);//设置假删除
//		persistenceDao.delete(mineReport);
	}

	public ZjMineReport loadMineReport(long id) throws ApplicationAccessException {
		return persistenceDao.get(ZjMineReport.class, id);
	}

	public List<ZjMineReport> loadMineReports(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination == null) {
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		} else {
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		}
	}

	public void updateMineReport(ZjMineReport mineReport) throws ApplicationAccessException {
		persistenceDao.update(mineReport);
	}
	
	public void mergeMineReport(ZjMineReport mineReport) throws ApplicationAccessException {
		persistenceDao.merge(mineReport);
	}
	
	public void setPersistenceDao(PersistenceDao<ZjMineReport> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
