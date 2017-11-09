package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaPointType;


public interface PointTypePersistenceIface {
	
	public Long createPointType(DaPointType pointType) throws ApplicationAccessException;
	
	public void updatePointType(DaPointType pointType) throws ApplicationAccessException;
	
	public void mergePointType(DaPointType pointType) throws ApplicationAccessException;
	
	public void deletePointType(DaPointType pointType) throws ApplicationAccessException;
	
	public List<DaPointType> loadPointTypes(DetachedCriteriaProxy detachedCriteriaProxy, Pagination pagination) throws ApplicationAccessException;
	
	public DaPointType loadPointType(DaPointType pointType) throws ApplicationAccessException;
}