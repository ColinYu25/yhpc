package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.bjsxt.hibernate.MemCached;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;

public class TradeTypePersistenceImpl implements TradeTypePersistenceIface {
	private PersistenceDao<DaIndustryParameter> persistenceDao;

	public Long createTradeType(DaIndustryParameter tradeType) throws ApplicationAccessException {
		return (Long) persistenceDao.save(tradeType);
	}

	public void deleteTradeType(DaIndustryParameter tradeType) throws ApplicationAccessException {
		tradeType = loadTradeType(tradeType);
		tradeType.setDeleted(true);
		persistenceDao.merge(tradeType);
	}

	public DaIndustryParameter loadTradeType(DaIndustryParameter tradeType) throws ApplicationAccessException {

		return persistenceDao.get(DaIndustryParameter.class, tradeType.getId());
	}

	public DaIndustryParameter loadTradeType(Long id) throws ApplicationAccessException {

		return persistenceDao.get(DaIndustryParameter.class, id);
	}

	public DaIndustryParameter loadTradeType(String tradeDepiction) throws ApplicationAccessException {

		DetachedCriteriaProxy detachedCriteria = DetachedCriteriaProxy.forClass(DaIndustryParameter.class);
		detachedCriteria.add(RestrictionsProxy.eq("depiction", tradeDepiction));
		List<DaIndustryParameter> industryParaments = persistenceDao.findByCriteria(detachedCriteria, 1);
		if (industryParaments.size() > 0)
			return industryParaments.get(0);
		return null;

	}

	public DaIndustryParameter loadTradeType(String tradeDepiction, int type) throws ApplicationAccessException {

		MemCached cache = MemCached.getInstance();
		String cacheName = "loadTradeType_" + tradeDepiction;
		if (cache.get(cacheName) != null) {
			System.out.println("缓存行业: " + cacheName);
			return (DaIndustryParameter) cache.get(cacheName);
		} else {
			DetachedCriteriaProxy detachedCriteria = DetachedCriteriaProxy.forClass(DaIndustryParameter.class);
			detachedCriteria.add(RestrictionsProxy.eq("depiction", tradeDepiction));
			detachedCriteria.add(RestrictionsProxy.eq("type", type));
			List<DaIndustryParameter> industryParaments = persistenceDao.findByCriteria(detachedCriteria, 1);
			if (industryParaments.size() > 0){
				cache.add(cacheName, industryParaments.get(0));
			return industryParaments.get(0);
			}
		}
		return null;
	}

	public List<DaIndustryParameter> loadTradeTypes(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException {
		if (pagination != null)
			return persistenceDao.findPageByCriteria(detachedCriteriaProxy, pagination);
		else
			return persistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}

	public void updateTradeType(DaIndustryParameter tradeType) throws ApplicationAccessException {
		persistenceDao.update(tradeType);
	}

	public void mergeTradeType(DaIndustryParameter tradeType) throws ApplicationAccessException {
		persistenceDao.merge(tradeType);
	}

	public void setPersistenceDao(PersistenceDao<DaIndustryParameter> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public List<DaIndustryParameter> executeHQLQuery(String hql) throws ApplicationAccessException {
		List<DaIndustryParameter> daIndustryParameters = persistenceDao.executeHQLQuery(hql);
		System.out.println("daIndustryParameters.size(): " + daIndustryParameters.size());
		if (daIndustryParameters != null && daIndustryParameters.size() > 0) {
			return daIndustryParameters;
		} else {
			return null;
		}

	}

}
