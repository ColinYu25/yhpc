package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.Date;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.ZjMineReportDetail;
import com.safetys.nbyhpc.domain.persistence.iface.MineDetailPersistenceIface;

public class MineDetailPersistenceImpl implements MineDetailPersistenceIface{
	
	private PersistenceDao<ZjMineReportDetail> persistenceDao;	

	public long createMineReportDetail(ZjMineReportDetail mineReportDetail) throws ApplicationAccessException {		
		return (Long)persistenceDao.save(mineReportDetail);
	}

	public void deleteMineReportDetail(ZjMineReportDetail mineReportDetail) throws ApplicationAccessException {
		mineReportDetail = loadMineReportDetail(mineReportDetail.getId());
		mineReportDetail.setModifyTime(new Date());
		mineReportDetail.setDeleted(true);
		persistenceDao.merge(mineReportDetail);//设置假删除
//		persistenceDao.delete(mineReportDetail);
	}

	public ZjMineReportDetail loadMineReportDetail(long id) throws ApplicationAccessException {
		return persistenceDao.get(ZjMineReportDetail.class, id);
	}

	public List<ZjMineReportDetail> loadMineReportDetails(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination == null) {
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
		} else {
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		}
	}

	public void updateMineReportDetail(ZjMineReportDetail mineReportDetail) throws ApplicationAccessException {
		persistenceDao.update(mineReportDetail);
	}
	
	public void setPersistenceDao(PersistenceDao<ZjMineReportDetail> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
