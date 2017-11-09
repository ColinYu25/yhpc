package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.FkAreaHis;
import com.safetys.nbyhpc.domain.persistence.iface.FkAreaHisPersistenceIface;

public class FkAreaHisPersistenceImpl implements FkAreaHisPersistenceIface {

	public void setPersistenceDao(PersistenceDao persistenceDao) {
		a = persistenceDao;
	}

	public FkAreaHisPersistenceImpl() {
	}

	public List loadAreas(DetachedCriteriaProxy detachedCriteriaProxy) {
		return a.findAllByCriteria(detachedCriteriaProxy);
	}

	public List loadAreasByHql(String hql) {
		return a.executeQuery(hql);
	}


	public List loadAreas(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) {
		return a.findPageByCriteria(detachedCriteriaProxy, pagination);
	}

	public void updateArea(FkAreaHis fkArea) {
		a.update(fkArea);
	}

	public void createArea(FkAreaHis fkArea) {
		a.save(fkArea);
	}

	public PersistenceDao a;
}

/*
 * DECOMPILATION REPORT
 * 
 * Decompiled from:
 * D:\workspace3.7-2013\nbyhpc1\src\main\webapp\WEB-INF\lib\framework
 * -core-1.1.0.jar Total time: 344 ms Jad reported messages/errors: Exit status:
 * 0 Caught exceptions:
 */