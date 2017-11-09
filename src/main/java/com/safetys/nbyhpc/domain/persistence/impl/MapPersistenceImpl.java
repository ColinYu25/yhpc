/*
 * @(#) MaterialPersistenceImpl.java        
 * Date 2008-11-5                                           
 * Copyright (c) 2008 Safetys.cn.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.MapMarker;
import com.safetys.nbyhpc.domain.persistence.iface.MapPersistenceIface;

public class MapPersistenceImpl implements MapPersistenceIface {

	PersistenceDao<MapMarker> markerPersistenceDao;

	public MapMarker creatMapMarker(MapMarker MapMarker) {
		return (MapMarker) markerPersistenceDao.merge(MapMarker);
	}
	
	public List<MapMarker> loadMapMarkers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) {
		if (pagination != null)
			return markerPersistenceDao.findPageByCriteria(detachedCriteriaProxy,
					pagination);
		else
			return markerPersistenceDao.findAllByCriteria(detachedCriteriaProxy);
	}
	

	// public List executeSQLQuery(String sql) {
	// return mapPersistenceDao.executeSQLQuery(sql);
	// }
	
	public int executeSQLUpdate(String sql) {
		return markerPersistenceDao.executeSQLUpdate(sql);
	}
	
	@SuppressWarnings("unchecked")
	public void setPersistenceDao(PersistenceDao persistenceDao) {
		this.markerPersistenceDao = (PersistenceDao<MapMarker>) persistenceDao;
	}

}
