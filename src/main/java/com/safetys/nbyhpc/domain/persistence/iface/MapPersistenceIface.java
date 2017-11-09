/*
 * @(#) MaterialPersistenceIface.java        
 * Date 2008-11-5                                           
 * Copyright (c) 2008 Safetys.cn.
 * All rights reserved.
 */
package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.MapMarker;

public interface MapPersistenceIface {

	public MapMarker creatMapMarker(MapMarker MapMarker);
	
	public List<MapMarker> loadMapMarkers(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination);

	// public List executeSQLQuery(String sql);
	
	public int executeSQLUpdate(String sql);

}
